package de.flovvolf.machinelearning.neuralnetwork.model.neuron;

import java.util.Map;

public class DNA {
	private String neuronKey;

	public String getNeuronKey() {
		return neuronKey;
	}

	public void setNeuronKey(String neuronKey) {
		this.neuronKey = neuronKey;
	}

	public Map<String, Double> getWeights() {
		return weights;
	}

	public void setWeights(Map<String, Double> weights) {
		this.weights = weights;
	}

	private Map<String, Double> weights;
}
