package uniDep.morph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class POSTaggerAcc {

	public static void main(String args[]) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("/mnt/rgai3/unidep/unidep_msd_test/10_unimorph/hu.ud.test.pred.conll09"));
			//BufferedReader reader = new BufferedReader(new FileReader("/mnt/rgai3/unidep/unidep_msd_test/10_msd25/hu.ud.test.pred.conll09"));

			String line;
			int all = 0;
			int all_correct = 0;
			int tag_correct = 0;
			while ((line=reader.readLine())!= null) {
				if (line.length() > 0) {
					++all;
					String[] lineArr = line.split("\t");
					Set<String> goldSet = new HashSet();
					goldSet.addAll(Arrays.asList(lineArr[6].split("\\|")));
					
					Set<String> predSet = new HashSet();
					predSet.addAll(Arrays.asList(lineArr[7].split("\\|")));
					
					boolean goodTag = lineArr[4].equals(lineArr[5]);
					boolean goodMorphD1 = true;
					boolean goodMorphD2 = true;
					
					for (String p : predSet) {
						if (!goldSet.contains(p)) {
							goodMorphD1 = false;

							break;
						}
					}
					
					for (String p : goldSet) {
						if (!predSet.contains(p)) {
							goodMorphD2 = false;

							break;
						}
					}
					
					if (goodTag) {
						++tag_correct;
					}
					
					if (goodTag && (goodMorphD1 || goodMorphD2)) {
						++all_correct;
					} else {

						System.out.println(lineArr[4] + "\t" + lineArr[5] + "\t" + lineArr[6] + "\t" + lineArr[7] + "\t");
					}
				}
				
			}
			System.out.println(all + " " + all_correct + " " + all_correct/(double)all);
			System.out.println(all + " " + tag_correct + " " + tag_correct/(double)all);
			
			
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
