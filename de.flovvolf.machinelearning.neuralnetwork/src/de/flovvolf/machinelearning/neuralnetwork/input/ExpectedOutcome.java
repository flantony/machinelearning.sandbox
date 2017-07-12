package de.flovvolf.machinelearning.neuralnetwork.input;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class ExpectedOutcome {

	private final Map<String, Double> expectedResult;

	public ExpectedOutcome(Map<String, Double> expectedResult) {
		this.expectedResult = expectedResult;
	}

	public Double getValueForName(String name) {
		return expectedResult.get(name);
	}

	public String getExpectedClass() {
		return expectedResult.entrySet().stream().max(new Comparator<Entry<String, Double>>() {

			@Override
			public int compare(Entry<String, Double> entry1, Entry<String, Double> entry2) {
				return entry1.getValue().compareTo(entry2.getValue());
			}
		}).map(e -> e.getKey()).get();
	}
}
