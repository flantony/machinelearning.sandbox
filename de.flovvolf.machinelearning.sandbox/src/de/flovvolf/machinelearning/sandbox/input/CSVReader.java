package de.flovvolf.machinelearning.sandbox.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.flovvolf.machinelearning.neuralnetwork.input.RawDataProvider;

public class CSVReader implements RawDataProvider {

	private final String file;

	public CSVReader(String file) {
		this.file = file;
	}

	@Override
	public List<String> getData() {
		ArrayList<String> result = Lists.newArrayList();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = reader.readLine();
			while (line != null) {
				result.add(line);
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
