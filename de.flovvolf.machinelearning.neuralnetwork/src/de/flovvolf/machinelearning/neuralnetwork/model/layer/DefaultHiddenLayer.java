package de.flovvolf.machinelearning.neuralnetwork.model.layer;

import java.util.List;

import de.flovvolf.machinelearning.neuralnetwork.model.neuron.BiasNeuron;
import de.flovvolf.machinelearning.neuralnetwork.model.neuron.Neuron;

public class DefaultHiddenLayer extends AbstractLayer implements HiddenLayer {

	public DefaultHiddenLayer() {
		super();
		this.neurons.add(new BiasNeuron());
	}

	public DefaultHiddenLayer(List<Neuron> neurons) {
		super(neurons);
		this.neurons.add(new BiasNeuron());
	}
}
