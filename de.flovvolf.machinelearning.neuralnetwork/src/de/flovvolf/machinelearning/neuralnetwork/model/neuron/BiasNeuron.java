package de.flovvolf.machinelearning.neuralnetwork.model.neuron;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

import de.flovvolf.machinelearning.neuralnetwork.function.MathFunctions;
import de.flovvolf.machinelearning.neuralnetwork.model.Input;

public class BiasNeuron extends Neuron {

	private static final Input BIAS_INPUT = new Input("BIAS", 1.0);

	public BiasNeuron() {
		super("BIAS" + UUID.randomUUID().toString());
		setActivationFunction(MathFunctions.NO_FUCTION);
	}

	@Override
	protected Double activationFunction(List<Input> inputs) {
		return getActivationFunction().calculate(getWeight(BIAS_INPUT).getWeightedValue(BIAS_INPUT.getInput()));
	}

	@Override
	public Map<String, Double> train(Double sumOfPreviousDelta, List<Input> inputs) {
		Map<String, Double> deltas = Maps.newHashMap();
		deltas.put(BIAS_INPUT.getKey(), train(sumOfPreviousDelta, BIAS_INPUT.getInput(), getWeight(BIAS_INPUT)));
		return deltas;
	}

}
