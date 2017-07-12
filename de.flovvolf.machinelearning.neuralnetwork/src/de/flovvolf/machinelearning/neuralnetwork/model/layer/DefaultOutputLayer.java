package de.flovvolf.machinelearning.neuralnetwork.model.layer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Maps;

import de.flovvolf.machinelearning.neuralnetwork.function.MathFunctions;
import de.flovvolf.machinelearning.neuralnetwork.input.ExpectedOutcome;
import de.flovvolf.machinelearning.neuralnetwork.model.Input;
import de.flovvolf.machinelearning.neuralnetwork.model.Output;
import de.flovvolf.machinelearning.neuralnetwork.model.neuron.Neuron;
import de.flovvolf.machinelearning.neuralnetwork.training.Result;
import de.flovvolf.machinelearning.neuralnetwork.training.TrainingResult;

public class DefaultOutputLayer extends AbstractLayer implements OutputLayer{

	public DefaultOutputLayer(List<Neuron> neurons) {
		super(neurons);
	}

	@Override
	public Result predict(List<Input> inputs) {
		List<Output> layerOutput = calculateLayer(inputs);
		return new Result(layerOutput);
	}

	@Override
	public TrainingResult test(List<Input> inputs, ExpectedOutcome outcomeClasses) {
		Result result = predict(inputs);
		Map<String, Double> outputsAsMap = result.getOutputsAsMap();
		return new TrainingResult(result, MathFunctions.error(outputsAsMap.get(outcomeClasses.getExpectedClass()),
				outcomeClasses.getValueForName(outcomeClasses.getExpectedClass())), outcomeClasses, null);
	}

	@Override
	public TrainingResult train(List<Input> inputs, ExpectedOutcome outcomeClasses) {
		Result result = predict(inputs);
		Map<String, Double> outputsAsMap = result.getOutputsAsMap();
		// Calculate Error per output node 
		Map<String, Double> deltaL = this.neurons.parallelStream()
				.map(neuron -> neuron.train(MathFunctions.error(outputsAsMap.get(neuron.getKey()),
						outcomeClasses.getValueForName(neuron.getKey())), inputs))
				.reduce((mapA, mapB) -> Stream.concat(mapA.entrySet().stream(), mapB.entrySet().stream())
						.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(), Double::sum)))
				.orElse(Maps.newHashMap());

		return new TrainingResult(result, MathFunctions.error(outputsAsMap.get(outcomeClasses.getExpectedClass()),
				outcomeClasses.getValueForName(outcomeClasses.getExpectedClass())), outcomeClasses, deltaL);
	}
}
