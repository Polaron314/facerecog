package com.sacredheartkingston.jtowner.facerecog;

import java.util.List;

public class TrainingSet {
	
	private int inputLength;
	private int outputLength;
	private NeuralSet[] sets;
	
	public TrainingSet(NeuralSet[] sets) {
		this.sets = sets;
		this.inputLength = sets[1].getInputs().length;
		this.outputLength = sets[1].getOutputs().length;
	}

	public int getInputLength() {
		return inputLength;
	}
	
	public int getOutputLength() {
		return outputLength;
	}
	
	public NeuralSet[] getSets() {
		return sets;
	}
	
	public int getSetNumber() {
		return sets.length;
	}
	
}
