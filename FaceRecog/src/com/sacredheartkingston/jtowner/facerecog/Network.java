package com.sacredheartkingston.jtowner.facerecog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Network {
	
	private Neuron[] neurons;
	int input;
	int basic;
	int output;
	int total;
	
	
	public Network(int input, int basic, int output) {
		this.input = input;
		this.basic = basic;
		this.output = output;
		this.total = input+basic+output;
		neurons = new Neuron[(input + basic + output)];
		for(int i = 0; i < input; i++) {
			neurons[i] = new InputNeuron();
		}
		for(int i = input; i < (input + basic); i++) {
			neurons[i] = new BasicNeuron();
		}
		for(int i = (input + basic); i < (input + basic + output); i++) {
			neurons[i] = new OutputNeuron();
		}
		
		for(int i = 0; i < (input + basic); i++) {
			neurons[i].setOutputs(this.getRandomOutputs(3), this.getRandomWeights(3,-1,1));
		}
		List<Neuron> currentNeurons = Arrays.asList(Arrays.copyOfRange(neurons, 0, input));
		List<Neuron> nextNeurons = new ArrayList<Neuron>();
		for(int i = 0; i < 5; i++) {
			for(int j = currentNeurons.size() - 1; j > -1; j--) {
				currentNeurons.get(j).run();
				nextNeurons.addAll(Arrays.asList(currentNeurons.get(j).getOutputs()));
			}
			for(int j = nextNeurons.size() - 1; j > -1; j--) {
				if(nextNeurons.get(j) instanceof OutputNeuron) {
					nextNeurons.remove(j);
				}
			}
			currentNeurons = nextNeurons;
			nextNeurons = new ArrayList<Neuron>();
		}
		for(int i = (input + basic); i < total; i++) {
			System.out.println(neurons[i].run());
		}
	}
	
	private Neuron[] getRandomOutputs(int number) {
		Neuron[] outNeurons = new Neuron[number];
		for(int i = 0; i < number; i++) {
			outNeurons[i] = neurons[(int) Utils.random(input, total)];
		}
		return outNeurons;
	}
	

	private double[] getRandomWeights(int number, double min, double max) {
		double[] newWeights = new double[number];
		for(int i = 0; i < number; i++) {
			newWeights[i] = Utils.random(min, max);
		}
		return newWeights;
	}
}
