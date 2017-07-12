package de.flovvolf.machinelearning.neuralnetwork.training;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.flovvolf.machinelearning.neuralnetwork.model.Output;

/**
 * @author Flow
 *
 */
public class Result {

	protected final Map<String, Output> results;

	public Result(List<Output> results) {
		this.results = results.stream().collect(Collectors.toMap(new Function<Output, String>() {

			@Override
			public String apply(Output o) {
				return o.getKey();
			}
		}, new Function<Output, Output>() {

			@Override
			public Output apply(Output o) {
				return o;
			}
		}));
	}

	protected Result(Map<String, Output> results) {
		this.results = results;
	}

	public String getPrediction() {
		return getOutputsAsMap().entrySet().stream().max(new Comparator<Entry<String, Double>>() {

			@Override
			public int compare(Entry<String, Double> e1, Entry<String, Double> e2) {
				return e1.getValue().compareTo(e2.getValue());
			}
		}).map(e -> e.getKey()).get();

	}

	@Override
	public String toString() {
		return "prediction: " + getPrediction();
	}

	public Map<String, Double> getOutputsAsMap() {
		return results.entrySet().stream()
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue().getOutput()));
	}
}
