package de.flovvolf.machinelearning.neuralnetwork.model;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.flovvolf.machinelearning.neuralnetwork.input.InputProvider;
import de.flovvolf.machinelearning.neuralnetwork.model.layer.InputLayer;
import de.flovvolf.machinelearning.neuralnetwork.model.layer.Layer;
import de.flovvolf.machinelearning.neuralnetwork.model.net.NeuralNetwork;

public class NetFactory {

	private LayerFactory layerFactory = new LayerFactory();

	/**
	 * Creates a default neural network with the following properties: - 3
	 * Layers
	 * 
	 * @return
	 */
	public NeuralNetwork createDefaultNet(InputProvider inputProvider) {
		NeuralNetwork net = new NeuralNetwork();
		InputLayer inputLayer = layerFactory.createDefaultInputLayer(inputProvider);
		createAndConnectLayers(inputLayer, 2, inputProvider);
		net.setInputLayer(inputLayer);
		return net;
	}

	private void createAndConnectLayers(Layer inputLayer, int count, InputProvider inputProvider) {
		Layer lastCreated = inputLayer;
		for (int i = 0; i < count; i++) {
			Layer hiddenLayer = layerFactory.createDefaultHiddenLayer();
			hiddenLayer.connectToPrevious(lastCreated);
			lastCreated = hiddenLayer;
		}
		Layer outputLayer = layerFactory.createDefaultOutputLayer(inputProvider);
		outputLayer.connectToPrevious(lastCreated);
	}

	public void persist(NeuralNetwork network, String fileName) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writer().writeValue(new File(fileName), network);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
