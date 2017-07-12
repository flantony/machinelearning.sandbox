package de.flovvolf.machinelearning.neuralnetwork.model.layer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.flovvolf.machinelearning.neuralnetwork.input.ExpectedOutcome;
import de.flovvolf.machinelearning.neuralnetwork.model.Input;
import de.flovvolf.machinelearning.neuralnetwork.model.Output;
import de.flovvolf.machinelearning.neuralnetwork.model.neuron.DNA;
import de.flovvolf.machinelearning.neuralnetwork.model.neuron.Neuron;
import de.flovvolf.machinelearning.neuralnetwork.training.Result;
import de.flovvolf.machinelearning.neuralnetwork.training.TrainingResult;

public abstract class AbstractLayer implements Layer {

	@JsonInclude
	protected List<Neuron> neurons;
	@JsonIgnore
	private Layer previousLayer;
	private Layer followingLayer;

	public AbstractLayer() {
		this.neurons = Lists.newArrayList();
	}

	protected AbstractLayer(List<Neuron> neurons) {
		this();
		this.neurons.addAll(neurons);
	}

	@Override
	public List<? extends Neuron> getNeurons() {
		return neurons;
	}

	@Override
	public void connectToPrevious(Layer previousLayer) {
		this.previousLayer = previousLayer;
		this.previousLayer.connectToFollowing(this);
	}

	@Override
	public void connectToFollowing(Layer followingLayer) {
		this.followingLayer = followingLayer;
	}

	@Override
	public Layer getPreviousLayer() {
		return previousLayer;
	}

	@Override
	public Layer getFollowingLayer() {
		return followingLayer;
	}

	@Override
	public Result predict(final List<Input> inputs) {
		List<Output> outputs = calculateLayer(inputs);
		return getFollowingLayer().predict(outputs.stream().map(o -> o.toInput()).collect(Collectors.toList()));
	}

	@Override
	public TrainingResult test(List<Input> inputs, ExpectedOutcome outcome) {
		List<Output> outputs = calculateLayer(inputs);
		return getFollowingLayer().test(outputs.stream().map(o -> o.toInput()).collect(Collectors.toList()), outcome);
	}

	@Override
	public TrainingResult train(final List<Input> inputs, ExpectedOutcome expectation) {
		// Calculate this layer
		List<Output> ownOutput = calculateLayer(inputs);

		// Feed output to next layer
		TrainingResult result = getFollowingLayer()
				.train(ownOutput.stream().map(o -> o.toInput()).collect(Collectors.toList()), expectation);

		// train this layer
		Map<String, Double> deltaL = this.neurons.parallelStream()
				.map(neuron -> neuron.train(result.getDelta().get(neuron.getKey()), inputs))
				.reduce((mapA, mapB) -> Stream.concat(mapA.entrySet().stream(), mapB.entrySet().stream())
						.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(), Double::sum)))
				.orElse(Maps.newHashMap());
		return new TrainingResult(result, expectation, deltaL);
	}

	protected Map<String, Double> inputsAsMap(List<Input> inputs) {
		return inputs.stream().collect(Collectors.toMap(input -> input.getKey(), input -> input.getInput()));
	}

	protected Map<String, Double> outPutAsMap(List<Output> outputs) {
		return outputs.stream().collect(Collectors.toMap(output -> output.getKey(), output -> output.getOutput()));
	}

	protected List<Output> calculateLayer(List<Input> inputs) {
		return this.neurons.parallelStream().map(neuron -> neuron.activate(inputs)).collect(Collectors.toList());
	}

	@Override
	public void crossover(Map<String, Map<String, DNA>> DNAs) {
		if (getFollowingLayer() != null) {
			getFollowingLayer().crossover(DNAs);
		}
	}

}
