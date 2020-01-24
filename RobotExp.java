package faceDetection;

public class RobotExp {
	private double[] xCor;
	private double[] yCor;
	
	/**
	 * Constructor. X and Y coordinates of the facial key points are split and recorded.
	 * @param allCoordinates The 2D double array containing coordinates of points 
	 *                       describing the detected face
	 */
	public RobotExp(double[][] allCoordinates) {
		xCor = new double[allCoordinates.length / 2];
		yCor = new double[allCoordinates.length / 2];
		for (int i = 0; i < allCoordinates.length / 2; i++) {
			xCor[i] = allCoordinates[i][0];
			yCor[i] = allCoordinates[i + allCoordinates.length / 2][0];
		}
	}
	
	/**
	 * Draw the robotic expression
	 */
	public void draw() {
		PennDraw.setPenColor(PennDraw.BLUE);
		//draw mouth
		double[] xCorMouth = {xCor[48], xCor[60], xCor[61], xCor[61], xCor[62], xCor[54],
				xCor[63], xCor[64], xCor[65]};
		double[] yCorMouth = {yCor[48], yCor[60], yCor[61], yCor[61], yCor[62], yCor[54],
				yCor[63], yCor[64], yCor[65]};
		PennDraw.filledPolygon(xCorMouth, yCorMouth);
		//draw nose
		double[] xCorNose = new double[6];
		double[] yCorNose = new double[6];
		for (int i = 0; i < 6; i++) {
			xCorNose[i] = xCor[i + 30];
			yCorNose[i] = yCor[i + 30];
		}
		PennDraw.filledPolygon(xCorNose, yCorNose);
		//draw eyes
		double[] xCorLeftEye = new double[6];
		double[] yCorLeftEye = new double[6];
		double[] xCorRightEye = new double[6];
		double[] yCorRightEye = new double[6];
		for (int i = 0; i < 6; i++) {
			xCorLeftEye[i] = xCor[i + 42];
			yCorLeftEye[i] = yCor[i + 42];
		}
		for (int i = 0; i < 6; i++) {
			xCorRightEye[i] = xCor[i + 36];
			yCorRightEye[i] = yCor[i + 36];
		}
		PennDraw.filledPolygon(xCorLeftEye, yCorLeftEye);
		PennDraw.filledPolygon(xCorRightEye, yCorRightEye);
		//draw eyebrows
		double[] xCorLeftEyeBrow = new double[5];
		double[] yCorLeftEyeBrow = new double[5];
		double[] xCorRightEyeBrow = new double[5];
		double[] yCorRightEyeBrow = new double[5];
		for (int i = 0; i < 5; i++) {
			xCorLeftEyeBrow[i] = xCor[i + 22];
			yCorLeftEyeBrow[i] = yCor[i + 22];
		}
		for (int i = 0; i < 5; i++) {
			xCorRightEyeBrow[i] = xCor[i + 17];
			yCorRightEyeBrow[i] = yCor[i + 17];
		}
		PennDraw.polyline(xCorLeftEyeBrow, yCorLeftEyeBrow);
		PennDraw.polyline(xCorRightEyeBrow, yCorRightEyeBrow);
	}
	
	/**
	 * Draw the facial key points with their indices on the live webcam frame
	 */
	public void drawWithIndices() {
    	for (int i = 0; i < xCor.length; i++) {
    		PennDraw.setPenColor(PennDraw.BLACK);
    		PennDraw.point(xCor[i], yCor[i]);
    		PennDraw.setPenColor(PennDraw.RED);
    		PennDraw.text(xCor[i], yCor[i] - 5, i + "");
    	}
	}
}
