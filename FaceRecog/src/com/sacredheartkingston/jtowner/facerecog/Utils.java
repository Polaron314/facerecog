package com.sacredheartkingston.jtowner.facerecog;

public class Utils {

	public static double sigmoid(double i) {
		return 1/(1+Math.pow(Math.E, -1*i));
	}
	
}
