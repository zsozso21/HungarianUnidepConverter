package uniDep.domainAdaptation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uniDep.util.DepTools;

public class Create10Fold {
	
	public static List<List<List<List<String>>>> cutToTrainTest(List<List<String>> sentences, int fold, int seed){
		List<List<List<List<String>>>> result = new ArrayList<List<List<List<String>>>>();

		List<List<List<String>>> trainSentences = new ArrayList<List<List<String>>>();
		List<List<List<String>>> testSentences = new ArrayList<List<List<String>>>();
		
		for (int i = 0; i < fold; i++) {
			trainSentences.add(new ArrayList<List<String>>());
			testSentences.add(new ArrayList<List<String>>());
		}
		
		result.add(trainSentences);
		result.add(testSentences);
		
		Random random = new Random(seed);
		
		for (List<String> sentence : sentences) {
			for (int i = 0; i < fold; i++) {
				if (random.nextInt(fold) == i) {
					testSentences.get(i).add(sentence);
				} else {
					trainSentences.get(i).add(sentence);
				}
			}
			
//			if (random.nextDouble() <= trainRatio) {
//				trainSentences.add(sentence);
//			} else {
//				testSentences.add(sentence);
//			}
		}
		
		
		return result;
	}
	
	public static void main(String[] args) {
		List<List<String>> sentences = DepTools.readLineSeparatedSentences(args[0]);
		int fold = args.length > 3 ? Integer.parseInt(args[3]) : 10;
		List<List<List<List<String>>>> train_test = cutToTrainTest(sentences, fold, 1);
//		DepTools.writeLineSeparetedSentences(train_test.get(0), args[1]);
		for (int i = 0; i < fold; i++) {
			DepTools.writeLineSeparetedSentences(train_test.get(0).get(i), args[1] + "/hu.ud.split.train_" + i + ".conll09");
		}
		for (int i = 0; i < fold; i++) {
			DepTools.writeLineSeparetedSentences(train_test.get(1).get(i), args[1] + "/hu.ud.split.test_" + i + ".conll09");
		}
//		DepTools.writeLineSeparetedSentences(train_test.get(1), args[2]);
	}
}
