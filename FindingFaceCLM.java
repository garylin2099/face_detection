package faceDetection;

import java.io.IOException;
import java.util.List;

import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.detection.CLMDetectedFace;
import org.openimaj.image.processing.face.detection.CLMFaceDetector;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.math.geometry.point.Point2d;
import org.openimaj.math.geometry.point.Point2dImpl;
import org.openimaj.math.geometry.shape.Rectangle;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;

import Jama.Matrix;

public class FindingFaceCLM {
	public static void main(String[] args) throws IOException {
		//the dimension of the video frame can be adjusted
		VideoCapture vc = new VideoCapture( 800, 600 );
		VideoDisplay<MBFImage> vd = VideoDisplay.createVideoDisplay( vc );
		vd.addVideoListener( 
				  new VideoDisplayListener<MBFImage>() {
				    public void beforeUpdate( MBFImage frame ) {
				    	FaceDetector<CLMDetectedFace,FImage> fd = new CLMFaceDetector();
				    	//detectFaces() is the key function to find faces in the webcam frame. The function takes in
				    	//a single-band floating-point image so the multi-band image "frame" is transformed.
				    	List<CLMDetectedFace> faces = fd.detectFaces(Transforms.calculateIntensity(frame));

				    	for( CLMDetectedFace face : faces ) {
				    	    frame.drawShape(face.getBounds(), RGBColour.RED);
				    	    Rectangle bound = face.getBounds();
				    	    //the shape matrix contains the x and y coordinates of the points describing the face
				    	    Matrix pointMat = face.getShapeMatrix();
				    	    double[][] pointArray = pointMat.getArrayCopy();
				    	    //The dimension of "pointMat" or "pointArray" is 2n*1 where n is the total number of
				    	    //facial key points. The elements are in the order of "X0, X1,..., Xn, Y0, Y1,..., Yn".
				    	    //The interval between the indices of Xi and Yi is thus n
				    	    int interval = pointArray.length / 2;
				    	    //initialize the facial key points to be drawn on the live webcam frame
				    	    Point2d[] p = new Point2d[pointArray.length / 2];
				    	    for (int i = 0; i < p.length; i++) {
				    	    	p[i] = new Point2dImpl(pointArray[i][0], pointArray[i + interval][0]);
				    	    	//the coordinates of the points are relative to the face bound, translate them to the 
				    	    	//coordinates in the webcam frame
				    	    	p[i].translate((float) bound.minX(), (float) bound.minY());
				    	    	frame.drawPoint(p[i], RGBColour.GREEN, 5);
				    	    }

				    	    if (PennDraw.hasNextKeyTyped()) {
				    	    	char key = PennDraw.nextKeyTyped();
				    	    	//the point coordinates are inverted, reverse the coordinate system in PennDraw
				    	    	//to give a natural display of the detected face. Adjust the scale, too.
					    	    PennDraw.setXscale(bound.width, 0);
				    	    	PennDraw.setYscale(bound.height, 0);
				    			PennDraw.setPenRadius(0.01);
				    	    	RobotExp robotExpression = new RobotExp(pointArray);
				    	    	//press i to see the index of the facial key points, otherwise draw the robotic expression
				    	    	if (key == 'i') {
				    	    		robotExpression.drawWithIndices();
				    	    	} else {
				    	    	robotExpression.draw();
				    	    	}
				    	    }
				    	}
				    }

				    public void afterUpdate( VideoDisplay<MBFImage> display ) {
				    }
				  });
		
	}	
}
