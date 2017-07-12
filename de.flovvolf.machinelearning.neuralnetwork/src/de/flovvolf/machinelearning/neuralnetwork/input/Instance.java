package de.flovvolf.machinelearning.neuralnetwork.input;

import java.util.List;

import de.flovvolf.machinelearning.neuralnetwork.model.Input;

public class Instance {

	private final List<Input> input;
	private final ExpectedOutcome outcome;

	public Instance(List<Input> result, ExpectedOutcome outcome) {
		super();
		this.input = result;
		this.outcome = outcome;
	}

	public List<Input> getInput() {
		return this.input;
	}

	public ExpectedOutcome getOutcome() {
		return outcome;
	}

}
