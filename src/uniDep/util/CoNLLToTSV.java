package uniDep.util;

import java.util.ArrayList;
import java.util.List;

import is2.io.CONLLReader09;
import is2.data.SentenceData09;

import hu.u_szeged.pos.converter.CoNLLFeaturesToMSD;

/**
 * A CoNLL2009 formátumú fájlokat TSV formutúvá convertálja.
 * 
 * @author zsozso
 *
 */
public class CoNLLToTSV {
	
	public static List<SentenceData09> getCoNLLSentences(String fileName){
		ArrayList<SentenceData09> senteceList = new ArrayList<SentenceData09>();
		CONLLReader09 reader = new CONLLReader09(fileName);
		
		SentenceData09 sen;
		try {
		while ((sen = reader.getNext()) != null)
			senteceList.add(sen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return senteceList;
	}
	
	public static List<List<String>> conllToMSD(List<SentenceData09> conllSentences, boolean lemma){
		ArrayList<List<String>> msdSentence = new ArrayList<List<String>>();
		for (SentenceData09 conllSentence : conllSentences) {
			msdSentence.addAll(conllToMSD(conllSentence, lemma));
			msdSentence.add(new ArrayList<String>()); //üres sor
		}
		return msdSentence;
	}
	
	/**
	 * CoNLL2009 mondatok MSD kódra való átalakítása, szavak és kódok leválogatása.
	 */
	public static List<List<String>> conllToMSD(SentenceData09 conllSentence, boolean lemma){
		ArrayList<List<String>> msdSentence = new ArrayList<List<String>>();
		CoNLLFeaturesToMSD conllToMsd = new CoNLLFeaturesToMSD();
		for (int i = 1; i < conllSentence.length(); i++) {
			ArrayList<String> row = new ArrayList<String>();
			row.add(conllSentence.forms[i]);
			if (lemma) {
				row.add(conllSentence.lemmas[i]);
			}
			row.add(conllToMsd.convert(conllSentence.gpos[i], conllSentence.ofeats[i]));
			msdSentence.add(row);
		}
				
		return msdSentence;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Használat: java posTaggerTool.converter.CoNLLToTSV inputFile outPutFile [lemma]");
			System.out.println("lemma: 0 (alapértelezett), ha nem kérünk lemmát, különben az outputban lesz lemma is");
			return;
		}
		boolean lemma = args.length > 2 && !args[2].equals("0");
		TSVTool.writeTSV(args[1], CoNLLToTSV.conllToMSD(CoNLLToTSV.getCoNLLSentences(args[0]), lemma));
	}

}
