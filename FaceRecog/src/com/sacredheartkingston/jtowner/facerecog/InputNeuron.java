package com.sacredheartkingston.jtowner.facerecog;

public class InputNeuron implements Neuron {
	
	double totalInput = 0;
	Neuron[] outputs;
	double[] weights;
	int id;
	
	public InputNeuron(int id) {
		this.id = id;
	}
	
	public InputNeuron(Neuron[] outputs, double[] weights, double input, int id) {
		this.outputs = outputs;
		this.weights = weights;
		this.totalInput = input;
		this.id = id;
	}
	
	@Override
	public double run() {
		double output = Utils.sigmoid(totalInput);
		for(int i = 0; i < outputs.length; i++) {
			outputs[i].input(output, weights[i]);
		}
		totalInput /= 2;
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

	@Override
	public int[] getBinary() {
		int[] typeBinary = {0,1};
		int[] idBinary = Utils.getByte(this.id);
		int[] outputBinary = new int[32];
		for(int i = 0; i < outputs.length; i++) {
			int[] outNBinary = Utils.getByte(outputs[i].getID());
			for(int j = 0; j < 8; j++) {
				outputBinary[8*i + j] = outNBinary[j];
			}
		}
		int[] weightsBinary = new int[132];
		for(int i = 0; i < weights.length; i++) {
			int[] outNBinary = Utils.getFourBytes(Math.abs(Math.round(weights[i]*Math.pow(10, 8))));
			for(int j = 0; j < outNBinary.length; j++) {
				weightsBinary[33*i + j] = outNBinary[j];
			}
			if(weights[i] < 0) {
				weightsBinary[33*(i+1) - 1] = 1;
			}
		}
		int[] binary = Utils.concatAll(typeBinary, idBinary, outputBinary, weightsBinary);
		return binary;
	}

	@Override
	public int getID() {
		return id;
	}

}
