package de.flovvolf.machinelearning.sandbox.input;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.flovvolf.machinelearning.neuralnetwork.input.AbstractInputProvider;
import de.flovvolf.machinelearning.neuralnetwork.input.ExpectedOutcome;
import de.flovvolf.machinelearning.neuralnetwork.input.Instance;
import de.flovvolf.machinelearning.neuralnetwork.input.RawDataProvider;
import de.flovvolf.machinelearning.neuralnetwork.model.Input;

public class NumberRecognitionInputProvider extends AbstractInputProvider {

	private volatile List<String> attributeNames = Lists.newArrayList();
	private final DataCompressor dataCompressor;
	private List<String> outcomeClasses;
	private List<Instance> testingSet = Lists.newArrayList();
	private List<Instance> trainingSet = Lists.newArrayList();

	public NumberRecognitionInputProvider(RawDataProvider rawDataProvider) {
		super(rawDataProvider);
		this.dataCompressor = new DataCompressor();
	}

	@Override
	public List<String> getOutcomeClasses() {
		if (outcomeClasses == null) {
			outcomeClasses = Lists.newArrayList();
			for (int i = 0; i < 10; i++) {
				outcomeClasses.add(String.valueOf(i));
			}
		}
		return outcomeClasses;
	}

	@Override
	protected List<String> extractAttributeNames(String rawData) {
		String[] attributesAfterCompression = this.dataCompressor.compressedAttributeNames(rawData.split(","));
		this.attributeNames.addAll(Lists.newArrayList(attributesAfterCompression));
		return this.attributeNames;

	}

	@Override
	protected Instance convertToInstance(String rawData) {
		String[] dataSet = rawData.split(",");
		Double[] compressedDataSet = this.dataCompressor.compress(dataSet);
		List<Input> result = Lists.newArrayList();

		for (int i = 0; i < compressedDataSet.length; i++) {
			final int index = i;
			result.add(new Input(this.attributeNames.get(index), Double.valueOf(compressedDataSet[index])));
		}
		return new Instance(result, getExpectedOutcome(Integer.parseInt(dataSet[0])));
	}

	private ExpectedOutcome getExpectedOutcome(int expected) {
		Map<String, Double> map = Maps.newHashMap();
		for (int i = 0; i < 10; i++) {
			if (expected == i) {
				map.put(getOutcomeClasses().get(i), 1.0);
			} else {
				map.put(getOutcomeClasses().get(i), 0.0);
			}
		}
		return new ExpectedOutcome(map);
	}

	@Override
	public void init() {
		super.init();
		int testSwitch = 0;
		for (Instance instance : getRefinedData()) {
			if (testSwitch % 4 == 0) {
				this.testingSet.add(instance);
			} else {
				this.trainingSet.add(instance);
			}
			testSwitch++;
		}
	}

	@Override
	public List<Instance> getTrainingSet() {
		if (!isInitialized()) {
			throw new IllegalStateException("InputProvider was not initialized - call init first");
		}
		return this.trainingSet;
	}

	@Override
	public List<Instance> getTestingSet() {
		if (!isInitialized()) {
			throw new IllegalStateException("InputProvider was not initialized - call init first");
		}
		return this.testingSet;
	}
}
