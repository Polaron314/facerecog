package com.sacredheartkingston.jtowner.facerecog;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Main {
	
	public static void main(String[] args) {
		Main.imageTraining();
	}
	
	public static void imageTraining() {
		try {
			BufferedImage in = ImageIO.read(new File("circle.png"));
			double[] input = new double[in.getWidth()*in.getHeight()];
			for(int x = 0; x < in.getWidth(); x++) {
				for(int y = 0; y < in.getHeight(); y++) {
					Color c = new Color(in.getRGB(x, y));
					double grayscale = (c.getBlue()+c.getGreen()+c.getRed())/(3*255);
					input[x + y*in.getWidth()] = grayscale;
				}
			}
			double[] output = {0,1};
			NeuralSet set1 = new NeuralSet(input,output);
			
			BufferedImage in2 = ImageIO.read(new File("square.png"));
			double[] input2 = new double[in2.getWidth()*in2.getHeight()];
			for(int x = 0; x < in2.getWidth(); x++) {
				for(int y = 0; y < in2.getHeight(); y++) {
					Color c = new Color(in2.getRGB(x, y));
					double grayscale = (c.getBlue()+c.getGreen()+c.getRed())/(3*255);
					input2[x + y*in2.getWidth()] = grayscale;
				}
			}
			double[] output2 = {1,0};
			NeuralSet set2 = new NeuralSet(input2,output2);
			NeuralSet[] sets = {set1, set2};
			TrainingSet training = new TrainingSet(sets);
			GeneticTrainer g = new GeneticTrainer(training);
			Network trained = g.run(10);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void training() {
		double[] inputs = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		double[] outputs = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		NeuralSet set1 = new NeuralSet(inputs, outputs);
		double[] inputs2 = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0};
		double[] outputs2 = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0};
		NeuralSet set2 = new NeuralSet(inputs2, outputs2);
		NeuralSet[] sets = {set1, set2};
		TrainingSet training = new TrainingSet(sets);
		GeneticTrainer g = new GeneticTrainer(training);
		Network n = g.run(10);
		System.out.println(Arrays.toString(n.run(inputs2)));
	}
	
	public static void cloning() {
		Network orig = new Network(10, 40, 10);
		orig.initRandom();
		int[] binary = orig.toBinary();
		Network clone = new Network(binary, 10, 40, 10, 214);
		binary = clone.toBinary();
		Network clone2 = new Network(binary, 10, 40, 10, 214);
		double[] inputs = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
		System.out.println(Arrays.toString(orig.run(inputs)));
		System.out.println(Arrays.toString(clone.run(inputs)));
		System.out.println(Arrays.toString(clone2.run(inputs)));
	}
	
}
