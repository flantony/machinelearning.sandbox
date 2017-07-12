package de.flovvolf.machinelearning.neuralnetwork.input;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractInputProvider implements InputProvider {

	private final RawDataProvider rawDataProvider;
	private boolean isInitialized = false;
	protected List<String> attributeNames;
	private List<String> rawData;
	private List<Instance> refinedData;

	public AbstractInputProvider(RawDataProvider rawDataProvider) {
		this.rawDataProvider = rawDataProvider;
	}

	@Override
	public List<String> getAttributeList() {
		if (!isInitialized()) {
			throw new IllegalStateException("InputProvider was not initialized - call init first");
		}
		return attributeNames;
	}

	@Override
	public boolean isInitialized() {
		return this.isInitialized;
	}

	@Override
	public void init() {
		if (this.rawData == null) {
			this.rawData = rawDataProvider.getData();
		}
		String headLine = rawData.get(0);
		this.attributeNames = extractAttributeNames(headLine);
		rawData.remove(headLine);
		this.refinedData = rawData.stream().map(s -> convertToInstance(s)).collect(Collectors.toList());
		this.isInitialized = true;
	}

	protected List<Instance> getRefinedData() {
		if (!isInitialized) {
			throw new IllegalStateException("InputProvider was not initialized - call init first");
		}
		return refinedData;
	}

	protected abstract Instance convertToInstance(final String rawData);

	protected abstract List<String> extractAttributeNames(String rawData);

}
