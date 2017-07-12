package de.flovvolf.machinelearning.neuralnetwork.model.layer;

import java.util.List;
import java.util.Map;

import de.flovvolf.machinelearning.neuralnetwork.input.ExpectedOutcome;
import de.flovvolf.machinelearning.neuralnetwork.model.Input;
import de.flovvolf.machinelearning.neuralnetwork.model.neuron.DNA;
import de.flovvolf.machinelearning.neuralnetwork.model.neuron.Neuron;
import de.flovvolf.machinelearning.neuralnetwork.training.Result;
import de.flovvolf.machinelearning.neuralnetwork.training.TrainingResult;

public interface Layer {

	void connectToPrevious(Layer previousLayer);

	void connectToFollowing(Layer followingLayer);

	Result predict(List<Input> collect);

	TrainingResult test(List<Input> collect, ExpectedOutcome outcome);

	TrainingResult train(List<Input> collect, ExpectedOutcome expectation);

	Layer getFollowingLayer();

	Layer getPreviousLayer();

	List<? extends Neuron> getNeurons();

	void crossover(Map<String, Map<String, DNA>> crossoverDNA);

}
