package com.sacredheartkingston.jtowner.facerecog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class GeneticTrainer {
	
	private TrainingSet set;
	List<Network> networks = new ArrayList<Network>();
	private double MUTATION_RATE = 0.001;
	private double ERROR_THRESHOLD = 0.01;
	
	public GeneticTrainer(TrainingSet set) {
		this.set = set;
	}
	
	public Network run(int numNetworks) {
		for(int i = 0; i < numNetworks; i++) {
			networks.add(new Network(set.getInputLength(), getHidden(set.getInputLength(),set.getOutputLength()), set.getOutputLength()));
			networks.get(i).initRandom();
		}
		
		boolean solved = false;
		int count = 1;
		while(!solved) {
			Map<Double, Network> networkError = new LinkedHashMap<Double,Network>();
			for(Network n : networks) {
				networkError.put(new Double(this.getError(n)), n);
			}
			Set<Double> errors = networkError.keySet();
			ArrayList<Double> sortedErrors = new ArrayList<Double>();
			sortedErrors.addAll(errors);
			Collections.sort(sortedErrors);
			if(sortedErrors.get(0) < ERROR_THRESHOLD) {
				solved = true;
				return networkError.get(sortedErrors.get(0));
			}
			System.out.println("Gen " + count + ": L " + sortedErrors.get(0) + ", M " + sortedErrors.get(sortedErrors.size()/2)+ ", H " + sortedErrors.get(sortedErrors.size() - 1));
			List<Network> newNetworks = new ArrayList<Network>();
			for(int i = 0; i < numNetworks; i++) {
				newNetworks.add(this.join(this.chooseRandom(sortedErrors, networkError), this.chooseRandom(sortedErrors, networkError)));
			}
			newNetworks.remove((int)Utils.random(0, newNetworks.size()));
			newNetworks.add(networkError.get(sortedErrors.get(0)));
			networks = newNetworks;
			count++;
		}
		return null;
	}
	
	private Network chooseRandom(ArrayList<Double> sortedErrors, Map<Double, Network> networkError) {
		int index = 0;
		if(Math.random() > .8) {
			index += sortedErrors.size()/2;
		}
		index += (int)Utils.random(0, sortedErrors.size()/2);
		return networkError.get(sortedErrors.get(index));
	}
	
	private double getError(Network n) {
		double error = 0;
		for(NeuralSet s : set.getSets()) {
			error += s.getError(n.run(s.getInputs()));
		}
		error /= set.getSetNumber();
		return error;
	}
	
	private int getHidden(int input, int output) {
		int avg = (input+output)/2;
		return avg*10;
	}
	
	private Network join(Network n1, Network n2) {
		int[] binary1 = n1.toBinary();
		int[] binary2 = n2.toBinary();
		int[] offspring = new int[binary1.length];
		int crossover = (int)Utils.random(0, binary1.length);
		for(int i = 0; i < binary1.length; i++) {
			if(i < crossover) {
				if(Math.random() > MUTATION_RATE) {
					offspring[i] = binary1[i];
				} else {
					offspring[i] = binary1[i] ^ 1;
				}
			} else {
				if(Math.random() > MUTATION_RATE) {
					offspring[i] = binary2[i];
				} else {
					offspring[i] = binary2[i] ^ 1;
				}
			}
		}
		return new Network(offspring, set.getInputLength(), getHidden(set.getInputLength(),set.getOutputLength()), set.getOutputLength(), 214);
	}
	
}
