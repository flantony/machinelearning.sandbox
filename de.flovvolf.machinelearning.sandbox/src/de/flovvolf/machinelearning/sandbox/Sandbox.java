package de.flovvolf.machinelearning.sandbox;

import de.flovvolf.machinelearning.neuralnetwork.input.InputProvider;
import de.flovvolf.machinelearning.neuralnetwork.model.NetFactory;
import de.flovvolf.machinelearning.neuralnetwork.model.net.NeuralNetwork;
import de.flovvolf.machinelearning.neuralnetwork.training.TrainingReport;
import de.flovvolf.machinelearning.sandbox.Training.Trainer;
import de.flovvolf.machinelearning.sandbox.input.CSVReader;
import de.flovvolf.machinelearning.sandbox.input.NumberRecognitionInputProvider;
import javafx.application.Application;
import javafx.stage.Stage;

public class Sandbox extends Application{

	public static void main(String[] args) {
//		launch(args);
		// Data provision
		InputProvider provider = new NumberRecognitionInputProvider(new CSVReader("train.csv"));
		provider.init();
		NeuralNetwork neuralNetwork = new NetFactory().createDefaultNet(provider);
		Trainer trainer = new Trainer(provider, neuralNetwork);
		TrainingReport report = trainer.train(500);
		System.out.println(report);
	}
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {	
//		VBox root = new VBox();
//		
//		Scene sceen = new Scene(root);
//		
//		primaryStage.setScene(sceen);
//		primaryStage.show();
	}
}
