package com.sacredheartkingston.jtowner.facerecog;

public class InputNeuron implements Neuron {
	
	double totalInput = 0;
	Neuron[] outputs;
	double[] weights;
	
	public InputNeuron() {
		
	}
	
	public InputNeuron(Neuron[] outputs, double[] weights, double input) {
		this.outputs = outputs;
		this.weights = weights;
		this.totalInput = input;
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
		totalInput = input;
	}

	@Override
	public void setOutputs(Neuron[] outputs, double[] weights) {
		this.outputs = outputs;
		this.weights = weights;
	}
	
	public void setInput(double input) {
		this.totalInput = input;
	}

	@Override
	public Neuron[] getOutputs() {
		return outputs;
	}

}
