package com.sacredheartkingston.jtowner.facerecog;

public interface Neuron {
	
	public double run();
	
	public void input(double input, double weight);
	
	public void setOutputs(Neuron[] outputs, double[] weights);
	
	public Neuron[] getOutputs();
	
}
