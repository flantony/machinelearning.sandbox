package de.flovvolf.machinelearning.neuralnetwork.function;

public interface ActivationFunction {
	
	public double calculate(Double x);

	public double calculateDerivative (Double x);
}
