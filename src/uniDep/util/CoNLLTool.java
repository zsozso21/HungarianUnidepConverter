package uniDep.util;

import is2.data.SentenceData09;
import is2.io.CONLLWriter09;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoNLLTool {
	public static List<List<CoNLL09Word>> readCoNLL09(String fileName){
		List<List<CoNLL09Word>> sentenceList = new ArrayList<List<CoNLL09Word>>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			String line;
			List<CoNLL09Word> sentence = new ArrayList<CoNLL09Word>();
			sentenceList.add(sentence);
			while((line = reader.readLine()) != null) {
				if (line.length() > 0) {
					sentence.add(new CoNLL09Word(line));
				} else {
					//új mondat
					sentence = new ArrayList<CoNLL09Word>();
					sentenceList.add(sentence);
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
		return sentenceList;
	}
	
	public static void writeCoNLL(List<SentenceData09> sentenceList, String fileName){
		try {
			CONLLWriter09 writer = new CONLLWriter09(fileName);
			
			for (SentenceData09 sentence : sentenceList) {
					writer.write(sentence, true);
			}
			writer.finishWriting();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  
  public static void writeCoNLLFromWordList(List<List<CoNLL09Word>> sentenceList, String fileName){
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

      for (List<CoNLL09Word> sentence : sentenceList) {
        for (CoNLL09Word word : sentence) {
          writer.write(word + "\t_\t_\n");
        }
        writer.write("\n");
      }
      
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void writeCoNLLFromWordList0Root(List<List<CoNLL09Word>> sentenceList, String fileName){
	    try {
	      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

	      for (List<CoNLL09Word> sentence : sentenceList) {
	        for (CoNLL09Word word : sentence) {
	          writer.write(word.toString0Root() + "\t_\t_\n");
	        }
	        writer.write("\n");
	      }
	      
	      writer.flush();
	      writer.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	
	public static SentenceData09 CloneConllSentence(SentenceData09 sentence) {
		SentenceData09 newSentence = new SentenceData09();
		
		newSentence.id = sentence.id == null ? null : sentence.id.clone();
		newSentence.forms = sentence.forms == null ? null : sentence.forms.clone();
		newSentence.lemmas = sentence.lemmas == null ? null : sentence.lemmas.clone();
		newSentence.plemmas = sentence.plemmas == null ? null : sentence.plemmas.clone();
		newSentence.heads = sentence.heads == null ? null : sentence.heads.clone();
		newSentence.pheads = sentence.pheads == null ? null : sentence.pheads.clone();
		newSentence.labels = sentence.labels == null ? null : sentence.labels.clone();
		newSentence.plabels = sentence.plabels == null ? null : sentence.plabels.clone();
		newSentence.gpos = sentence.gpos == null ? null : sentence.gpos.clone();
		newSentence.ppos = sentence.ppos == null ? null : sentence.ppos.clone();
		newSentence.feats = sentence.feats == null ? null : sentence.feats.clone();
		newSentence.sem = sentence.sem == null ? null : sentence.sem.clone();
		newSentence.semposition = sentence.semposition == null ? null : sentence.semposition.clone();
		newSentence.arg = sentence.arg == null ? null : sentence.arg.clone();
		newSentence.argposition = sentence.argposition == null ? null : sentence.argposition.clone();
		newSentence.fillp = sentence.fillp == null ? null : sentence.fillp.clone();
		newSentence.ofeats = sentence.ofeats == null ? null : sentence.ofeats.clone();
		newSentence.pfeats = sentence.pfeats == null ? null : sentence.pfeats.clone();
		
		return newSentence;
	}
	
	
}
