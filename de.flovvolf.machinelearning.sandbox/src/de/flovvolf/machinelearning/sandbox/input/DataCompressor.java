package de.flovvolf.machinelearning.sandbox.input;

public class DataCompressor {

	public Double[] compress(String[] dataSet) {
		Double[][] image = createImage(dataSet);
		Double[] compressedRows = compressdRows(image);
		Double[] compressedColumns = compressdColumns(image);
		Double[] newDataSet = new Double[(compressedColumns.length + compressedRows.length)];
		int index = 0;
		for (Double d : compressedColumns) {
			newDataSet[index] = d;
			index++;
		}
		for (Double d : compressedRows) {
			newDataSet[index] = d;
			index++;
		}
		return newDataSet;
	}

	private Double[][] createImage(String[] dataSet) {
		Double[][] image = new Double[28][28];
		int inputIndex = 1;
		for (int r = 0; r < 28; r++) {
			for (int c = 0; c < 28; c++) {
				image[r][c] = Double.parseDouble(dataSet[inputIndex]);
				inputIndex++;
			}
		}
		return image;
	}

	private Double[] compressdColumns(Double[][] image) {
		Double[] columns = new Double[28];
		for (int c = 0; c < 28; c++) {
			double sum = 0.0;
			for (int r = 0; r < 28; r++) {
				sum += image[r][c];
			}
			columns[c] = (sum / 28) / 256;
		}
		return columns;
	}

	private Double[] compressdRows(Double[][] image) {
		Double[] rows = new Double[28];
		for (int r = 0; r < 28; r++) {
			double sum = 0.0;
			for (int c = 0; c < 28; c++) {
				sum += image[r][c];
			}
			rows[r] = (sum / 28) / 256;
		}
		return rows;
	}

	public String[] compressedAttributeNames(String[] rawData) {
		String[] attributesAfterCompression = new String[28 + 28];
		int index = 0;
		for (int r = 0; r < 28; r++) {
			attributesAfterCompression[index] = "R" + r;
			index++;
		}
		for (int c = 0; c < 28; c++) {
			attributesAfterCompression[index] = "C" + c;
			index++;
		}
		return attributesAfterCompression;
	}

}
