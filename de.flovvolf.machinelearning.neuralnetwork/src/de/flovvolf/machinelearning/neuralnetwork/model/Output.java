package de.flovvolf.machinelearning.neuralnetwork.model;

public class Output {

	private final String key;
	private final Double output;

	public Output(String key, Double output) {
		this.key = key;
		this.output = output;
	}

	public String getKey() {
		return this.key;
	}

	public Double getOutput() {
		return output;
	}

	public Input toInput() {
		return new Input(key, getOutput());
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
		Output other = (Output) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

}
