package com.sacredheartkingston.jtowner.facerecog;

public class BasicNeuron implements Neuron {
	
	double totalInput = 0;
	Neuron[] outputs;
	double[] weights;
	
	public BasicNeuron() {
		
	}
	
	public BasicNeuron(Neuron[] outputs, double[] weights) {
		this.outputs = outputs;
		this.weights = weights;
	}
	
	
	@Override
	public double run() {
		double output = Utils.sigmoid(totalInput);
		for(int i = 0; i < outputs.length; i++) {
			outputs[i].input(output, weights[i]);
		}
		return output;
	}
	
	@Override
	public void input(double input, double weight) {
		totalInput += input*weight;
	}

	@Override
	public void setOutputs(Neuron[] outputs, double[] weights) {
		this.outputs = outputs;
		this.weights = weights;
	}

	@Override
	public Neuron[] getOutputs() {
		return outputs;
	}
	
}
