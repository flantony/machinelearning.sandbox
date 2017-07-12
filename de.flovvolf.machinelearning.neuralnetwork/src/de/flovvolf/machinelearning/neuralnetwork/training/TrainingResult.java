package de.flovvolf.machinelearning.neuralnetwork.training;

import java.util.Map;

import de.flovvolf.machinelearning.neuralnetwork.input.ExpectedOutcome;

public class TrainingResult extends Result {

	private final ExpectedOutcome expectedOutcome;
	private final Map<String, Double> deltaL;
	private final Double error;

	public TrainingResult(Result result, Double predictionErrorForExpectedClass, ExpectedOutcome expectedOutcome,
			Map<String, Double> deltaL) {
		super(result.results);
		this.error = predictionErrorForExpectedClass;
		this.expectedOutcome = expectedOutcome;
		this.deltaL = deltaL;
	}

	public TrainingResult(TrainingResult result, ExpectedOutcome expectedOutcome, Map<String, Double> deltaL) {
		super(result.results);
		this.expectedOutcome = expectedOutcome;
		this.deltaL = deltaL;
		this.error = result.getError();

	}

	public Map<String, Double> getDelta() {
		return deltaL;
	}

	public String getExpected() {
		return this.expectedOutcome.getExpectedClass();
	}

	@Override
	public String toString() {
		return (getPrediction().equals(getExpected()) ? "correct   :) " : "incorrect :( ") + "( expected: "
				+ getExpected() + ", " + super.toString() + " )" + "--> Error was " + getError();
	}

	public Double getError() {
		return this.error;
	}

}
