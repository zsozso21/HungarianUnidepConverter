package uniDep.util;

/**
 * CoNNL 2009 formátum 1 sora.
 * 
 * @author zsozso
 * 
 */
public class CoNLL09Word {
	protected String Id;
	protected String Form;
	protected String Lemma;
	protected String PLemma;
	protected String Pos;
	protected String PPos;
	protected String Feat;
	protected String PFeat;
	protected String Head;
	protected String PHead;
	protected String Deprel;
	protected String PDeprel;

	public CoNLL09Word(){
	  
	}
	
	public CoNLL09Word(String row) {
		String[] rowArr = row.split("\t");
		if (rowArr.length >= 12) {
			Id = rowArr[0];
			Form = rowArr[1];
			Lemma = rowArr[2];
			PLemma = rowArr[3];
			Pos = rowArr[4];
			PPos = rowArr[5];
			Feat = rowArr[6];
			PFeat = rowArr[7];
			Head = rowArr[8];
			PHead = rowArr[9];
			Deprel = rowArr[10];
			PDeprel = rowArr[11];
		}
	}

	public CoNLL09Word(String id, String form, String lemma, String pLemma,
			String pos, String pPos, String feat, String pFeat, String head,
			String pHead, String deprel, String pDeprel) {
		Id = id;
		Form = form;
		Lemma = lemma;
		PLemma = pLemma;
		Pos = pos;
		PPos = pPos;
		Feat = feat;
		PFeat = pFeat;
		Head = head;
		PHead = pHead;
		Deprel = deprel;
		PDeprel = pDeprel;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getForm() {
		return Form;
	}

	public void setForm(String form) {
		Form = form;
	}

	public String getLemma() {
		return Lemma;
	}

	public void setLemma(String lemma) {
		Lemma = lemma;
	}

	public String getPLemma() {
		return PLemma;
	}

	public void setPLemma(String pLemma) {
		PLemma = pLemma;
	}

	public String getPos() {
		return Pos;
	}

	public void setPos(String pos) {
		Pos = pos;
	}

	public String getPPos() {
		return PPos;
	}

	public void setPPos(String pPos) {
		PPos = pPos;
	}

	public String getFeat() {
		return Feat;
	}

	public void setFeat(String feat) {
		Feat = feat;
	}

	public String getPFeat() {
		return PFeat;
	}

	public void setPFeat(String pFeat) {
		PFeat = pFeat;
	}

	public String getHead() {
		return Head;
	}

	public void setHead(String head) {
		Head = head;
	}

	public String getPHead() {
		return PHead;
	}

	public void setPHead(String pHead) {
		PHead = pHead;
	}

	public String getDeprel() {
		return Deprel;
	}

	public void setDeprel(String deprel) {
		Deprel = deprel;
	}

	public String getPDeprel() {
		return PDeprel;
	}

	public void setPDeprel(String pDeprel) {
		PDeprel = pDeprel;
	}

	@Override
	public String toString() {
		String head = Head == null ? "_" : Head;
	    String phead = PHead == null ? "_" : PHead;
	    String deprel = Deprel == null ? "_" : Deprel;
	    String pDeprel = PDeprel == null ? "_" : PDeprel;
			return Id + "\t" + Form + "\t" + Lemma + "\t" + PLemma + "\t" + Pos + "\t" + PPos + "\t" + Feat + "\t" + PFeat + "\t" + head + "\t" + phead + "\t" + deprel + "\t" + pDeprel;
	}
	
	public String toString0Root() {
		String head = Head == null ? "0" : Head;
	    String phead = PHead == null ? "0" : PHead;
	    String deprel = Deprel == null ? "ROOT" : Deprel;
	    String pDeprel = PDeprel == null ? "ROOT" : PDeprel;
			return Id + "\t" + Form + "\t" + Lemma + "\t" + PLemma + "\t" + Pos + "\t" + PPos + "\t" + Feat + "\t" + PFeat + "\t" + head + "\t" + phead + "\t" + deprel + "\t" + pDeprel;
		}
}
