package com.sacredheartkingston.jtowner.facerecog;

public class OutputNeuron implements Neuron {
	
	double totalInput = 0;
	
	public OutputNeuron() {
	}
	
	@Override
	public double run() {
		double output = Utils.sigmoid(totalInput);
		return output;
	}
	
	@Override
	public void input(double input, double weight) {
		totalInput += input*weight;
	}

	@Override
	public void setOutputs(Neuron[] outputs, double[] weights) {

	}

	@Override
	public Neuron[] getOutputs() {
		return null;
	}

}
