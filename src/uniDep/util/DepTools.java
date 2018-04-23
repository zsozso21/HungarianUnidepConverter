package uniDep.util;

import is2.data.SentenceData09;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
			Scanner sc = new Scanner(new File(fileName));
			
			String line;
			List<String> sentence = new ArrayList<String>();
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (line.length() == 0) {
					sentences.add(sentence);
					sentence = new ArrayList<String>();
				} else {
					sentence.add(line);
				}
			}
			
			sentences.add(sentence);
			
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sentences;
	}
	
	public static void writeLineSeparetedSentences(List<List<String>> sentences, String fileName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			
			for (List<String> sentence : sentences) {
				for (String wordLine : sentence) {
					writer.write(wordLine + '\n');
				}
				writer.write('\n');
			}
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
