package com.sacredheartkingston.jtowner.facerecog;

import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) {
		Network n = new Network(10, 40, 10);
		n.initRandom();
		int[] binary = n.toBinary();
		Network n2 = new Network(binary, 10, 40, 10, 174);
		int[] input = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		n.run(input);
		n2.run(input);
	}
	
}
