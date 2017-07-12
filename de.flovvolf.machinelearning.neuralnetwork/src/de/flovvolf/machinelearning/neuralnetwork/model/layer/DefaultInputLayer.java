package de.flovvolf.machinelearning.neuralnetwork.model.layer;

import java.util.List;
import java.util.stream.Collectors;

import de.flovvolf.machinelearning.neuralnetwork.input.ExpectedOutcome;
import de.flovvolf.machinelearning.neuralnetwork.model.Input;
import de.flovvolf.machinelearning.neuralnetwork.model.Output;
import de.flovvolf.machinelearning.neuralnetwork.model.neuron.BiasNeuron;
import de.flovvolf.machinelearning.neuralnetwork.model.neuron.Neuron;
import de.flovvolf.machinelearning.neuralnetwork.training.TrainingResult;

public class DefaultInputLayer extends AbstractLayer implements InputLayer {

	public DefaultInputLayer(List<Neuron> neurons) {
		super(neurons);
		this.neurons.add(new BiasNeuron());
	}

	@Override
	public TrainingResult train(List<Input> inputs, ExpectedOutcome expectation) {
		List<Output> ownOutput = calculateLayer(inputs);
//		ownOutput.add(new Output("BIAS", 1.0));
		return getFollowingLayer().train(ownOutput.parallelStream().map(o -> o.toInput()).collect(Collectors.toList()),
				expectation);
	}

	@Override
	protected List<Output> calculateLayer(List<Input> inputs) {
		return inputs.parallelStream().filter(input -> input.getInput() != 0.0)
				.map(input -> new Output(input.getKey(), input.getInput())).collect(Collectors.toList());

	}

}
