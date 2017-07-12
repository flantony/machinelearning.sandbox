package de.flovvolf.machinelearning.neuralnetwork.input;

import de.flovvolf.machinelearning.neuralnetwork.training.TrainingReport;

public interface NetworkTrainer {

	public TrainingReport train(final int maxCycles);
}
