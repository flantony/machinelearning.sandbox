package de.flovvolf.machinelearning.neuralnetwork.model.neuron;

import java.util.Random;

import de.flovvolf.machinelearning.neuralnetwork.function.MathFunctions;

public class Weight {

	private volatile Double weight;

	public Weight() {
		this(MathFunctions.HYPERBOLIC_TANGENCE.calculate(
				new Random(new Random().nextLong()).nextDouble() - new Random(new Random().nextLong()).nextDouble()));
	}

	public Weight(Double initialWeight) {
		this.setWeight(initialWeight);
	}

	public void correct(Double delta) {
		this.setWeight(this.getWeight() + delta);
	}

	public synchronized double getWeightedValue(Double value) {
		return this.getWeight() * value;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public void mutate() {
		Random random = new Random();
		if (random.nextDouble() - (random.nextDouble() * (1 + random.nextInt(50))) >= 0.0) {
			this.weight *= ((random.nextDouble() - random.nextDouble()) / 10.0);
		}
	}

}
