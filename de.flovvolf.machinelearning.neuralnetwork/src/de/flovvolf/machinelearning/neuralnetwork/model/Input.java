package de.flovvolf.machinelearning.neuralnetwork.model;

/**
 * @author Flow
 *
 */
public class Input {
	protected final Double input;
	private final String key;

	public Input(String key, Double input) {
		this.input = input;
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public Double getInput() {
		return input;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Input other = (Input) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Input [input=" + input + ", key=" + key + "]";
	}

}
