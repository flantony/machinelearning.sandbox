package de.flovvolf.machinelearning.neuralnetwork.model;

import java.util.UUID;

import de.flovvolf.machinelearning.neuralnetwork.function.ActivationFunction;
import de.flovvolf.machinelearning.neuralnetwork.function.MathFunctions;
import de.flovvolf.machinelearning.neuralnetwork.model.neuron.BiasNeuron;
import de.flovvolf.machinelearning.neuralnetwork.model.neuron.Neuron;

public class NeuronFactory {

	public Neuron createDefaultNeuron() {
		return createNeuron(UUID.randomUUID().toString());
	}

	public Neuron createNeuron(String key) {
		return createNeuron(key, MathFunctions.SIGMOIDS);
	}

	public Neuron createNeuron(String key, ActivationFunction activationFunction) {
		Neuron neuron = new Neuron(key);
		neuron.setActivationFunction(activationFunction);
		return neuron;
	}

	public Neuron createNeuron(ActivationFunction activationFunction) {
		return createNeuron(UUID.randomUUID().toString(), activationFunction);
	}

	public BiasNeuron createBias() {
		return new BiasNeuron();
	}
}
