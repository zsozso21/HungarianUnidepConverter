package uniDep.converter;

import hu.u_szeged.pos.converter.CoNLLFeaturesToMSD;
import is2.data.SentenceData09;
import uniDep.util.CoNLLTool;
import uniDep.util.CoNLLToTSV;

import java.util.List;

public class Converter {
	
	public static void convertToMSD(List<SentenceData09> sentences){
		for (SentenceData09 sent : sentences) {
			for (int i = 0; i < sent.forms.length; i++) {
			    CoNLLFeaturesToMSD conll2msd = new CoNLLFeaturesToMSD();
			    String msd = conll2msd.convert(sent.gpos[i], sent.ofeats[i]);
			    sent.ofeats[i] = msd;
			}
		}
	}

	public static void main(String args[]) {
		List<SentenceData09> convertedCoNLL = CoNLLToTSV.getCoNLLSentences(args[0]);
		for (SentenceData09 sent : convertedCoNLL) {
			SentenceRules.convertSentence(sent);
		}
		
		if (args.length > 2 && args[2].equalsIgnoreCase("msd")) {
			convertToMSD(convertedCoNLL);
		}
		
		CoNLLTool.writeCoNLL(convertedCoNLL, args[1]);
	}
}
