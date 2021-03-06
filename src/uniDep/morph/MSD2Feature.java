package uniDep.morph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import hu.u_szeged.pos.converter.MSDToCoNLLFeatures;

public class MSD2Feature {
	private static MSDToCoNLLFeatures msdConverter = new MSDToCoNLLFeatures();
	
	public static String getMainPosFromMSD(String msd){
		if (msd ==null || msd.length() == 0) {
			return "";
		} else if (msd.equals("PUNCT")) {
			return msd;
		}
		return String.valueOf(msd.charAt(0));
	}
	
	public static String getFeatsFromMSD(String lemma, String msd){
		if (msd ==null
				) {
			return "";
		} else if (msd.equals("PUNCT")) {
			return "_";
		}
		return msdConverter.convert(lemma, msd);
	}

	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
			StringBuffer sb = new StringBuffer();
			
			String line;
			while ((line=reader.readLine())!= null) {
				if (line.length() > 0) {
					String[] lineArrr = line.split("\t");
					String lemma = lineArrr[1];
					String msd = lineArrr[2];
					sb.append(lineArrr[0]);
					sb.append('\t');
					sb.append(lineArrr[1]);
					sb.append('\t');
					sb.append(getMainPosFromMSD(msd));
					sb.append('\t');
					sb.append(getFeatsFromMSD(lemma, msd));
					sb.append('\n');
				} else {
					sb.append('\n');
				}
			}
			
			writer.write(sb.toString());
			
			reader.close();
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
