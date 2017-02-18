package com.sacredheartkingston.jtowner.facerecog;

public class BasicNeuron implements Neuron {
	
	double totalInput = 0;
	Neuron[] outputs;
	double[] weights;
	
	public BasicNeuron(Neuron[] outputs, double[] weights) {
		this.outputs = outputs;
		this.weights = weights;
	}
	
	public double run() {
		return Utils.sigmoid(totalInput);
	}
	
	public void input(double input, double weight) {
		totalInput += input*weight;
	}
	
}
