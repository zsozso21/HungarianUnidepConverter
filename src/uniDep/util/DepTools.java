package uniDep.util;

import is2.data.SentenceData09;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DepTools {

	/**
	 * @return Returns every children of parent, if the label is null or empty.
	 */
	public static List<Integer> getSpecificChilds(SentenceData09 sent, int parent, String label) {
		List<Integer> children = getChilds(sent, parent);
		
		if (label != null && !label.isEmpty()) {
			List<Integer> specChildren = new ArrayList<Integer>();
			for (int i : children) {
				if (sent.labels[i].equals(label)) {
					specChildren.add(i);
				}
			}
			return specChildren;
		}
		
		return children;
	}
	
	public static List<Integer> getChilds(SentenceData09 sent, int parent) {
		List<Integer> children = new ArrayList<Integer>();
		
		for (int i = 0; i < sent.heads.length; i++) {
			if (sent.heads[i] == parent) {
				children.add(i);
			}
		}
					
		return children;
	}
	
	public static List<List<String>> readLineSeparatedSentences(String fileName){
		List<List<String>> sentences = new ArrayList<List<String>>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			
			String line;
			List<String> sentence = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				if (line.length() == 0) {
					sentences.add(sentence);
					sentence = new ArrayList<String>();
				} else {
					sentence.add(line);
				}
			}
			
			sentences.add(sentence);
			
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sentences;
	}
	
	public static void writeLineSeparetedSentences(List<List<String>> sentences, String fileName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			
			if (sentences.size() > 0) {
				List<String> sentence = sentences.get(0);
				for (String wordLine : sentence) {
					writer.write(wordLine + '\n');
				}
			}
			
			for (int i = 1; i < sentences.size(); i++) {
				List<String> sentence = sentences.get(i);
				
				writer.write('\n');
				for (String wordLine : sentence) {
					writer.write(wordLine + '\n');
				}
			}
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
