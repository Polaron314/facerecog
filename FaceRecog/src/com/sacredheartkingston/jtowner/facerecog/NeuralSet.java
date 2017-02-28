package com.sacredheartkingston.jtowner.facerecog;

public class NeuralSet {
	
	private double[] inputs;
	private double[] outputs;
	
	public NeuralSet(double[] inputs, double[] outputs) {
		this.inputs = inputs;
		this.outputs = outputs;
	}
	
	public double[] getInputs() {
		return inputs;
	}
	
	public double[] getOutputs() {
		return outputs;
	}
	
	public double getError(double[] actual) {
		double error = 0;
		for(int i = 0; i < actual.length; i++) {
			error += Math.abs(outputs[i] - actual[i]);
		}
		return error;
	}
	
}
