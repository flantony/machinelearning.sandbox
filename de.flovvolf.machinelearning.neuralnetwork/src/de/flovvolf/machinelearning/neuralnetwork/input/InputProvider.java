package de.flovvolf.machinelearning.neuralnetwork.input;

import java.util.List;

public interface InputProvider {

	List<String> getAttributeList();

	List<String> getOutcomeClasses();

	List<Instance> getTrainingSet();

	List<Instance> getTestingSet();

	void init();
	
	boolean isInitialized();
}
