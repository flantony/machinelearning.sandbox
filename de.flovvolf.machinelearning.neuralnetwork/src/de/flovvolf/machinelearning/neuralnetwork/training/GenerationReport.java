package de.flovvolf.machinelearning.neuralnetwork.training;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.util.Precision;

import com.google.common.collect.Maps;

/**
 * @author Flow
 *
 */
public class GenerationReport {

	private class GenResult {
		final String expectation;
		final String prediction;
		final Double error;

		public GenResult(String expectation, String prediction, Double error) {
			super();
			this.expectation = expectation;
			this.prediction = prediction;
			this.error = error;
		}

	}

	private final int generationIndex;
	private final long startTime;
	private long endTime;
	private HashMap<Integer, GenResult> results;

	public GenerationReport(int i, long l) {
		this.generationIndex = i;
		this.startTime = l;
		this.results = Maps.newHashMap();
	}

	public void setEnd(long endTime) {
		this.endTime = endTime;

	}

	public void addResult(TrainingResult result) {
		int index = results.size();
		results.put(index, new GenResult(result.getExpected(), result.getPrediction(), result.getError()));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Generation ");
		builder.append(generationIndex);
		builder.append(" processing " + results.size() + " instances took "
				+ TimeUnit.MINUTES.convert(endTime - startTime, TimeUnit.SECONDS) + " seconds\n");
		Double goodPredictions = getGoodPredictions();
		Double badPredictions = getBadPredictions();
		Double averageError = getAverageError();
		builder.append("Good prediction: " + goodPredictions + "("
				+ (goodPredictions * 100.00 / (goodPredictions + badPredictions)) + "%)\n");
		builder.append("Bad prediction: " + badPredictions + "("
				+ (badPredictions * 100.00 / (goodPredictions + badPredictions)) + "%)\n");
		builder.append("Average error: " + averageError + "\n");
		return builder.toString();
	}

	private Double getAverageError() {
		if (results.size() != 0) {
			return results.entrySet().stream().map(entry -> entry.getValue().error).reduce((a, b) -> a + b).orElse(0.0)
					/ results.size();
		}
		return 0.0;
	}

	private Double getGoodPredictions() {
		return new Double(results.entrySet().stream()
				.filter(e -> e.getValue().prediction.equals(e.getValue().expectation)).count());
	}

	private Double getBadPredictions() {
		return new Double(results.entrySet().stream()
				.filter(e -> !(e.getValue().prediction.equals(e.getValue().expectation))).count());
	}

	public String getFileName() {
		return "gen" + generationIndex + "_" + Precision.round(getAverageError(), 2) + ".net";
	}

}
