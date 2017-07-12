package de.flovvolf.machinelearning.neuralnetwork.function;

import org.apache.commons.math3.analysis.function.Sigmoid;
import org.apache.commons.math3.analysis.function.Tanh;

public class MathFunctions {

	// static private final Double SQUAREROOT_OF_PI = new Sqrt().value(Math.PI);

	public static final ActivationFunction SIGMOIDS = new ActivationFunction() {

		/**
		 * f(x) = 1 / (1+ e.pow(-x)) 
		 * @return double - 0 > x > 1
		 */
		@Override
		public double calculate(Double x) {
			return new Sigmoid().value(x);
		}

		/**
		 * f(x)' = f(x (1-f(x))
		 * @return double - 0 > x > 1
		 */
		@Override
		public double calculateDerivative(Double x) {
			return (new Sigmoid().value(x)) * (1 - new Sigmoid().value(x));
		}
	};


	public static final ActivationFunction BINARY_STEP = new ActivationFunction() {

		/**
		 * 0 || 1
		 * @return double 0 || 1
		 */
		@Override
		public double calculate(Double x) {
			return (x < 0.0 ? 0.0 : 1.1);
		}

		/**
		 * 0 ||  Double.POSITIVE_INFINITY
		 * @return double 0 ||  Double.POSITIVE_INFINITY
		 */
		@Override
		public double calculateDerivative(Double x) {
			return (x != 0.0 ? 0.0 : Double.POSITIVE_INFINITY);
		}
	};

	
	public static final ActivationFunction HYPERBOLIC_TANGENCE = new ActivationFunction() {

		/**
		 * f(x) = 2 / (1+ e.pow(-2x)) 
		 * @return double - -1 > x > 1
		 */
		@Override
		public double calculate(Double x) {
			return new Tanh().value(x);
		}

		/**
		 * f(x)' = 1 f(x).pow(2) 
		 * @return double - 0 > x > 1
		 */
		@Override
		public double calculateDerivative(Double x) {
			double tanH = new Tanh().value(x);
			return 1 - (tanH + tanH);
		}
	};

	public static final ActivationFunction NO_FUCTION = new ActivationFunction() {

		@Override
		public double calculate(Double x) {
			return x;
		}

		@Override
		public double calculateDerivative(Double x) {
			return 1.0;
		};
	};

	// public static Double round(Double x) {
	// return (Math.abs(x) > 0.00001) ? Precision.round(x, 5) : 0.0;
	// }

	public static Double error(Double output, Double truth) {
		return truth - output;
	}
}
