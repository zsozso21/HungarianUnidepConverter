package uniDep.domainAdaptation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uniDep.util.DepTools;

public class CreateTrainTest {
	
	public static List<List<List<String>>> cutToTrainTest(List<List<String>> sentences, double trainRatio, int seed){
		List<List<List<String>>> result = new ArrayList<List<List<String>>>();
		List<List<String>> trainSentences = new ArrayList<List<String>>();
		List<List<String>> testSentences = new ArrayList<List<String>>();
		
		result.add(trainSentences);
		result.add(testSentences);
		
		Random random = new Random(seed);
		
		for (List<String> sentence : sentences) {
			if (random.nextDouble() <= trainRatio) {
				trainSentences.add(sentence);
			} else {
				testSentences.add(sentence);
			}
		}
		
		
		return result;
	}
	
	public static void main(String[] args) {
		List<List<String>> sentences = DepTools.readLineSeparatedSentences(args[0]);
		double ratio = args.length > 3 ? Double.parseDouble(args[3]) : 0.8;
		List<List<List<String>>> train_test = cutToTrainTest(sentences, ratio, 1);
		DepTools.writeLineSeparetedSentences(train_test.get(0), args[1]);
		DepTools.writeLineSeparetedSentences(train_test.get(1), args[2]);
	}
}
