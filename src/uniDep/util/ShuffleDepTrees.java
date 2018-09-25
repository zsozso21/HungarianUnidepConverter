package uniDep.util;

import java.util.Collections;
import java.util.List;

public class ShuffleDepTrees {

	public static void main(String[] args) {		
		if (args.length < 2) {
			System.out.println("Usage: java uniDep.util.ShuffleDepTrees inputFile outputFile");
			return;
		}
		
		List<List<String>> trees = DepTools.readLineSeparatedSentences(args[0]);
		Collections.shuffle(trees);
		DepTools.writeLineSeparetedSentences(trees, args[1]);
	}
}
