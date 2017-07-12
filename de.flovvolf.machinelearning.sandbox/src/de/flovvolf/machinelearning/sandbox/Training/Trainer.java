package de.flovvolf.machinelearning.sandbox.Training;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.flovvolf.machinelearning.neuralnetwork.input.InputProvider;
import de.flovvolf.machinelearning.neuralnetwork.input.Instance;
import de.flovvolf.machinelearning.neuralnetwork.input.NetworkTrainer;
import de.flovvolf.machinelearning.neuralnetwork.model.NetFactory;
import de.flovvolf.machinelearning.neuralnetwork.model.net.NeuralNetwork;
import de.flovvolf.machinelearning.neuralnetwork.training.GenerationReport;
import de.flovvolf.machinelearning.neuralnetwork.training.TrainingReport;

public class Trainer implements NetworkTrainer {

	private final ExecutorService TRAINER = Executors.newSingleThreadExecutor();
	private final InputProvider provider;
	private final NeuralNetwork neuralNetwork;

	public Trainer(InputProvider provider, NeuralNetwork neuralNetwork) {
		super();
		this.neuralNetwork = neuralNetwork;
		this.provider = provider;
	}

	@Override
	public TrainingReport train(final int maxCycles) {
		Future<TrainingReport> future = TRAINER.submit(new Callable<TrainingReport>() {
			@Override
			public TrainingReport call() throws Exception {
				TrainingReport trainingReport = new TrainingReport(System.currentTimeMillis());
				try {

					int trainingCycleIndex = 1;
					int testingCycleIndex = 1;
					GenerationReport genReport = null;
					for (int i = 0; i < maxCycles; i++) {
						if (i % 3 != 0) {
							genReport = trainNetwork(trainingCycleIndex);
							System.out.println("Training cycle completed!\n");
							trainingCycleIndex++;
						} else {
							genReport = testNetwork(testingCycleIndex);
							System.err.println("Test cycle completed:\n" + genReport + "\nDumping network to file...");
							new NetFactory().persist(Trainer.this.neuralNetwork, genReport.getFileName());
							System.err.println("... done!\n");
							testingCycleIndex++;
						}
						trainingReport.addGenerationReport(genReport);

					}
					trainingReport.setEnd(System.currentTimeMillis());
				} catch (Exception e) {
					System.out.println("BÄM");
				}
				return trainingReport;
			}
		});
		return waitForResult(future);
	}

	private GenerationReport testNetwork(int testingCycleIndex) throws InterruptedException {
		GenerationReport generationReport = new GenerationReport(testingCycleIndex, System.currentTimeMillis());
		for (Instance instance : provider.getTestingSet()) {
			generationReport.addResult(Trainer.this.neuralNetwork.test(instance));
		}
		generationReport.setEnd(System.currentTimeMillis());
		return generationReport;
	}

	private GenerationReport trainNetwork(int i) throws InterruptedException {
		GenerationReport generationReport = new GenerationReport(i, System.currentTimeMillis());
		for (Instance instance : provider.getTrainingSet()) {
			generationReport.addResult(Trainer.this.neuralNetwork.train(instance));
		}
		generationReport.setEnd(System.currentTimeMillis());
		return generationReport;
	}

	private TrainingReport waitForResult(Future<TrainingReport> future) {
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO implement logging
			e.printStackTrace();
		}
		return null;
	}
}
