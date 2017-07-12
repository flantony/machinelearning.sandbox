package de.flovvolf.machinelearning.neuralnetwork.model.net;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import de.flovvolf.machinelearning.neuralnetwork.input.Instance;
import de.flovvolf.machinelearning.neuralnetwork.model.layer.InputLayer;
import de.flovvolf.machinelearning.neuralnetwork.model.layer.Layer;
import de.flovvolf.machinelearning.neuralnetwork.model.neuron.DNA;
import de.flovvolf.machinelearning.neuralnetwork.training.Result;
import de.flovvolf.machinelearning.neuralnetwork.training.TrainingResult;

public class NeuralNetwork {

	private InputLayer inputLayer;

	public InputLayer getInputLayer() {
		return inputLayer;
	}

	public void setInputLayer(InputLayer inputLayer) {
		this.inputLayer = inputLayer;
	}

	public Result predict(Instance instance) {
		return this.inputLayer.predict(instance.getInput());
	}

	public TrainingResult train(Instance instance) {
		return this.inputLayer.train(instance.getInput(), instance.getOutcome());
	}

	public TrainingResult test(Instance instance) {
		return this.inputLayer.test(instance.getInput(), instance.getOutcome());
	}

	/** 
	 * Takes the DNA of an other Neural Network. This will only have an effect on genetic Layers. Other Layers will ignore this call
	 * @param crossoverDNA - the DNA to crossover with.
	 */
	public void crossover(Map<String, Map<String, DNA>> crossoverDNA) {
		this.inputLayer.crossover(crossoverDNA);
	}

	public List<Layer> getLayers() {
		List<Layer> result = Lists.newArrayList();
		return addRecursive(this.inputLayer, result);
	}

	private List<Layer> addRecursive(Layer layer, List<Layer> result) {
		if (layer == null) {
			return result;
		} else {
			result.add(layer);
		}
		return addRecursive(layer.getFollowingLayer(), result);
	}

}
