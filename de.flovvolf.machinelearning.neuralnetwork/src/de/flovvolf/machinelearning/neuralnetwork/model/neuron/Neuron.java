package de.flovvolf.machinelearning.neuralnetwork.model.neuron;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

import de.flovvolf.machinelearning.neuralnetwork.function.ActivationFunction;
import de.flovvolf.machinelearning.neuralnetwork.model.Input;
import de.flovvolf.machinelearning.neuralnetwork.model.Output;

public class Neuron {

	private Double learningRate = 0.5;
	private final String key;
	private ActivationFunction activationFunction;
	private Map<Input, Weight> weights = Maps.newHashMap();
	@JsonIgnore
	private volatile Double netInput;

	public Neuron(String key) {
		super();
		this.key = key;
	}

	public Double getLearningRate() {
		return learningRate;
	}

	@JsonAnySetter
	public void setWeights(Map<Input, Weight> weights) {
		this.weights = weights;
	}

	public void setLearningRate(Double learningRate) {
		this.learningRate = learningRate;
	}

	public String getKey() {
		return key;
	}

	ActivationFunction getActivationFunction() {
		return this.activationFunction;
	}

	public void setActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
	}

	public Output activate(final List<Input> inputs) {
		return new Output(getKey(), activationFunction(inputs));
	}

	public Map<String, Double> train(Double sumOfPreviousDelta, List<Input> inputs) {
		return inputs.stream().collect(Collectors.toMap(input -> input.getKey(),
				input -> train(sumOfPreviousDelta, input.getInput(), getWeight(input))));
	}

	public Double train(Double deltaPrevious, Double input, Weight weight) {
		Double delta = (activationFunction.calculateDerivative(netInput) * deltaPrevious); //checked
		Double changeOfWeight = this.learningRate * delta * input;
		Double oldWeigt = weight.getWeight();
		weight.correct(changeOfWeight);
		return oldWeigt * delta; // checked
	}

	protected Double activationFunction(List<Input> inputs) {
		this.netInput = inputs.stream().map(input -> getWeight(input).getWeightedValue(input.getInput()))
				.reduce((a, b) -> a + b).orElse(0.0);
		return getActivationFunction().calculate(netInput);
	}

	protected synchronized Weight getWeight(Input input) {
		Weight weight = weights.get(input);
		if (weight == null) {
			weights.put(input, new Weight());
		}
		return weights.get(input);
	}

	protected synchronized Weight getWeight(String key) {
		return this.weights.entrySet().stream()
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue())).get(key);
	}

	@JsonAnyGetter
	protected Map<Input, Weight> getWeights() {
		return weights;
	}


	public void mutate() {
		Random random = new Random();
		if (random.nextDouble() - (random.nextDouble() * (1 + random.nextInt(50))) >= 0.0) {
			getWeights().entrySet().stream().map(entry -> entry.getValue()).forEach(weight -> weight.mutate());
		}
	}

}