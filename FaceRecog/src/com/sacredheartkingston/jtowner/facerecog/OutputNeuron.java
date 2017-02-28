package com.sacredheartkingston.jtowner.facerecog;

public class OutputNeuron implements Neuron {
	
	double totalInput = 0;
	int id;
	
	public OutputNeuron(int id) {
		this.id = id;
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

	@Override
	public int[] getBinary() {
		int[] typeBinary = {1,0};
		int[] idBinary = Utils.getTwoBytes(this.id);
		int[] outputBinary = new int[64];
		int[] weightsBinary = new int[132];
		int[] binary = Utils.concatAll(typeBinary, idBinary, outputBinary, weightsBinary);
		return binary;
	}

	@Override
	public int getID() {
		return id;
	}

}
