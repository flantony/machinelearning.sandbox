package de.flovvolf.machinelearning.neuralnetwork.training;

import java.util.List;

public class TrainingDataInstance {
	private final int index;
	private final double expectedOutcome;
	private final List<Double> inputValues;

	public TrainingDataInstance(int index, double expectedOutcome, List<Double> inputValues) {
		this.index = index;
		this.expectedOutcome = expectedOutcome;
		this.inputValues = inputValues;
	}

	public int getIndex() {
		return index;
	}

	public double getExpectedOutcome() {
		return expectedOutcome;
	}

	public List<Double> getInputValues() {
		return inputValues;
	}

}
