package de.flovvolf.machinelearning.neuralnetwork.input;

/**
 * @author Flow
 *
 */
public class OutcomeClass {
	final String name;

	public OutcomeClass(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "[" + name + "]";
	}

}
