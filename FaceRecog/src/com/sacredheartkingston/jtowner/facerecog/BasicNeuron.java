package com.sacredheartkingston.jtowner.facerecog;

import java.util.Arrays;

public class BasicNeuron implements Neuron {
	
	double totalInput = 0;
	Neuron[] outputs;
	double[] weights;
	int id;
	
	public BasicNeuron(int id) {

		this.id = id;
	}
	
	public BasicNeuron(Neuron[] outputs, double[] weights, int id) {
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

	@Override
	public int[] getBinary() {
		int[] typeBinary = {0,0};
		int[] idBinary = Utils.getTwoBytes(this.id);
		int[] outputBinary = new int[64];
		for(int i = 0; i < outputs.length; i++) {
			int[] outNBinary = Utils.getTwoBytes(outputs[i].getID());
			for(int j = 0; j < 16; j++) {
				outputBinary[16*i + j] = outNBinary[j];
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
		/*System.out.println(typeBinary.length);
		System.out.println(typeBinary.length+idBinary.length);
		System.out.println(typeBinary.length+idBinary.length+outputBinary.length);
		System.out.println(typeBinary.length+idBinary.length+outputBinary.length+weightsBinary.length);*/
		int[] binary = Utils.concatAll(typeBinary, idBinary, outputBinary, weightsBinary);
		return binary;
	}

	@Override
	public int getID() {
		return id;
	}
	
}
