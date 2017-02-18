package com.sacredheartkingston.jtowner.facerecog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
			neurons[i] = new InputNeuron(i);
		}
		for(int i = input; i < (input + basic); i++) {
			neurons[i] = new BasicNeuron(i);
		}
		for(int i = (input + basic); i < (input + basic + output); i++) {
			neurons[i] = new OutputNeuron(i);
		}
	}
	
	public Network(int[] binary, int input, int basic, int output, int binLength) {
		this(input, basic, output);
		for(int i = 0; i < total-output; i++) {
			int[] currentNeuron = Arrays.copyOfRange(binary, i*binLength, (i+1)*binLength);
			this.binaryToNeuron(currentNeuron, i);
		}
	}

	public void initRandom() {
		for(int i = 0; i < (input + basic); i++) {
			int numberOutputs = (int)Utils.random(1, 5);
			neurons[i].setOutputs(this.getRandomOutputs(numberOutputs), this.getRandomWeights(numberOutputs,-1,1));
		}
	}
	
	public void run() {
		Set<Neuron> currentNeurons = new HashSet<Neuron>();
		currentNeurons.addAll(Arrays.asList(Arrays.copyOfRange(neurons, 0, input)));
		List<Neuron> nextNeurons = new ArrayList<Neuron>();
		for(int i = 0; i < 7; i++) {
			for(Neuron n : currentNeurons) {
				n.run();
				nextNeurons.addAll(Arrays.asList(n.getOutputs()));
			}
			for(int j = nextNeurons.size() - 1; j > -1; j--) {
				if(nextNeurons.get(j) instanceof OutputNeuron) {
					nextNeurons.remove(j);
				}
			}
			System.out.println(currentNeurons.size());
			currentNeurons = new HashSet<Neuron>();
			currentNeurons.addAll(nextNeurons);
			nextNeurons = new ArrayList<Neuron>();
		}
		for(int i = (input + basic); i < total; i++) {
			System.out.println(neurons[i].run());
		}
	}
	
	public void run(int[] inputs) {
		for(int i = 0; i < this.input; i++) {
			neurons[i].input(inputs[i], 1);
		}
		Set<Neuron> currentNeurons = new HashSet<Neuron>();
		currentNeurons.addAll(Arrays.asList(Arrays.copyOfRange(neurons, 0, input)));
		List<Neuron> nextNeurons = new ArrayList<Neuron>();
		for(int i = 0; i < 7; i++) {
			for(Neuron n : currentNeurons) {
				n.run();
				nextNeurons.addAll(Arrays.asList(n.getOutputs()));
			}
			for(int j = nextNeurons.size() - 1; j > -1; j--) {
				if(nextNeurons.get(j) instanceof OutputNeuron) {
					nextNeurons.remove(j);
				}
			}
			System.out.println(currentNeurons.size());
			currentNeurons = new HashSet<Neuron>();
			currentNeurons.addAll(nextNeurons);
			nextNeurons = new ArrayList<Neuron>();
		}
		for(int i = (input + basic); i < total; i++) {
			System.out.println(neurons[i].run());
		}
	}
	
	public int[] toBinary() {
		int length = neurons[0].getBinary().length;
		int[] binary = new int[total*length];
		for(int i = 0; i < total; i++) {
			int[] neuronBinary = neurons[i].getBinary();
			for(int j = 0; j < length; j++) {
				binary[length*i + j] = neuronBinary[j];
			}
		}
		return binary;
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
	
	private void binaryToNeuron(int[] binary, int id) {
		int type = Utils.binaryToInt(Arrays.copyOfRange(binary, 0, 2));
		int[] outputIDs = new int[4];
		int countOutput = 0;
		for(int i = 0; i < 4; i++) {
			outputIDs[i] = Utils.binaryToInt(Arrays.copyOfRange(binary, 10 + i*8, 18 + i*8));
			if(outputIDs[i] > input - 1) {
				countOutput++;
			}
		}
		double[] allWeights = new double[4];
		for(int i = 0; i < 4; i++) {
			allWeights[i] = Utils.binaryToLong(Arrays.copyOfRange(binary, 42 + i*33, 42 + (i+1)*33 - 1)) * Math.pow(10, -8);
			if(binary[42+((i+1)*33) - 1] == 1) {
				allWeights[i] *= -1;
			}
		}
		Neuron[] outputs = new Neuron[countOutput];
		double[] weights = new double[countOutput];
		int actCount = 0;
		for(int i = 0; i < countOutput; i++) {
			while(outputIDs[i] < input - 1) {
				i++;
			}
			outputs[actCount] = neurons[outputIDs[i]];
			if(allWeights[i] == 0) {
				weights[actCount] = Utils.random(-1, 1);
			} else {
				weights[actCount] = allWeights[i];
			}
			actCount++;
		}
		neurons[id].setOutputs(outputs, weights);
	}
	
}
