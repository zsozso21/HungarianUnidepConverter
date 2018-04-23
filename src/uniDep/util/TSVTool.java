package uniDep.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TSVTool {

	public static List<List<List<String>>> readTSV(String fileName) {
		return readTSV(fileName, "\t");
	}

	public static List<List<List<String>>> readTSV(String fileName, String separator) {
		List<List<List<String>>> response = new ArrayList<List<List<String>>>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;

			List<List<String>> sentence = new ArrayList<List<String>>();
			response.add(sentence);
			while ((line = reader.readLine()) != null) {
				if (line.length() == 0) {
					sentence = new ArrayList<List<String>>();
					response.add(sentence);
				} else {
					sentence.add(Arrays.asList(line.split(separator)));
				}
			}

			reader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	public static void writeTSV(String fileName, List<List<String>> data) {
		writeTSV(fileName, data, "\t");
	}

	public static void writeTSV(String fileName, List<List<String>> data,
			String separator) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

			for (List<String> dim1 : data) {
				if (dim1.size() > 0) {
					writer.write(dim1.get(0));
				}
				for (int i = 1; i < dim1.size(); i++) {
					writer.write(separator + dim1.get(i));
				}
				writer.write("\n");
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeTSV(String fileName, String separator, List<List<List<String>>> data) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			
			for (List<List<String>> sentence : data) {
				for (List<String> row : sentence) {
					if (row.size() > 0) {
						writer.write(row.get(0));
					}
					for (int i = 1; i < row.size(); i++) {
						writer.write(separator + row.get(i));
					}
					writer.write("\n");
				}
				writer.write("\n");
			}
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
