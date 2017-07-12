package de.flovvolf.machinelearning.neuralnetwork.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.flovvolf.machinelearning.neuralnetwork.function.ActivationFunction;
import de.flovvolf.machinelearning.neuralnetwork.function.MathFunctions;
import de.flovvolf.machinelearning.neuralnetwork.input.InputProvider;
import de.flovvolf.machinelearning.neuralnetwork.model.layer.DefaultHiddenLayer;
import de.flovvolf.machinelearning.neuralnetwork.model.layer.DefaultInputLayer;
import de.flovvolf.machinelearning.neuralnetwork.model.layer.DefaultOutputLayer;
import de.flovvolf.machinelearning.neuralnetwork.model.layer.GeneticHiddenLayer;
import de.flovvolf.machinelearning.neuralnetwork.model.layer.HiddenLayer;
import de.flovvolf.machinelearning.neuralnetwork.model.layer.InputLayer;
import de.flovvolf.machinelearning.neuralnetwork.model.layer.OutputLayer;
import de.flovvolf.machinelearning.neuralnetwork.model.neuron.Neuron;

public class LayerFactory {

	NeuronFactory neuronFactory = new NeuronFactory();

	public InputLayer createDefaultInputLayer(InputProvider inputProvider) {
		return new DefaultInputLayer(createInputNeurons(inputProvider.getAttributeList()));
	}

	/**
	 * Create hidden layer with the following properties: - 10 Neurons - with
	 * ERROR Function
	 * 
	 * @return
	 */
	public DefaultHiddenLayer createDefaultHiddenLayer() {
		return new DefaultHiddenLayer(createNeurons());
	}

	/**
	 * Create output layer with the following properties: - 2 Neurons - 2
	 * Neurons - SIGMOIDS Function
	 * 
	 * @return
	 */
	public OutputLayer createDefaultOutputLayer(InputProvider inputProvider) {
		List<Neuron> neurons = inputProvider.getOutcomeClasses().stream()
				.map(outcome -> neuronFactory.createNeuron(outcome, MathFunctions.SIGMOIDS))
				.collect(Collectors.toList());
		return new DefaultOutputLayer(neurons);
	}

	public HiddenLayer createDefaultGeneticLayer(String layerKey) {
		return new GeneticHiddenLayer(layerKey, createNeurons(20, MathFunctions.HYPERBOLIC_TANGENCE));
	}

	private List<Neuron> createNeurons() {
		return createNeurons(60);
	}

	private List<Neuron> createNeurons(int number) {
		return createNeurons(number, MathFunctions.SIGMOIDS);
	}

	private List<Neuron> createNeurons(int number, ActivationFunction function) {
		ArrayList<Neuron> list = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			list.add(neuronFactory.createNeuron(function));
		}
		return list;
	}

	private List<Neuron> createInputNeurons(List<String> attributes) {
		return attributes.stream().map(attribute -> neuronFactory.createNeuron(attribute, MathFunctions.NO_FUCTION))
				.collect(Collectors.toList());
	}
}
