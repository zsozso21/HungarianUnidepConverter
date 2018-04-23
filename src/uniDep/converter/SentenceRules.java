package uniDep.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uniDep.util.DepTools;
import hu.u_szeged.pos.converter.CoNLLFeaturesToMSD;
import is2.data.SentenceData09;

public class SentenceRules {

	private static Map<String, String> newLabels;
	private static Map<String, String> remainingLabels;

	static {
		newLabels = new HashMap<String, String>();
		newLabels.put("ROOT", addUniDepLabel("root"));
		newLabels.put("SUBJ", addUniDepLabel("nsubj"));
		newLabels.put("AUX", addUniDepLabel("aux"));
		newLabels.put("DET", addUniDepLabel("det"));
		newLabels.put("NEG", addUniDepLabel("neg"));
		newLabels.put("OBJ", addUniDepLabel("dobj"));
		newLabels.put("PUNCT", addUniDepLabel("punct"));
		newLabels.put("NE", addUniDepLabel("name"));
		newLabels.put("NUM", addUniDepLabel("compound"));
		newLabels.put("PREVERB", addUniDepLabel("compound:preverb"));	
		newLabels.put("QUE", addUniDepLabel("advmod:que"));	

		remainingLabels = new HashMap<String, String>();
		remainingLabels.put("CONJ", addUniDepLabel("cc"));
		remainingLabels.put("MODE", addUniDepLabel("advmod:mode"));
		remainingLabels.put("INF", addUniDepLabel("xcomp"));
		remainingLabels.put("DAT", addUniDepLabel("iobj"));
		remainingLabels.put("APPEND", addUniDepLabel("parataxis"));
		remainingLabels.put("OBL", addUniDepLabel("nmod:obl"));
		remainingLabels.put("TLOCY", addUniDepLabel("advmod:tlocy"));
		remainingLabels.put("PRED", addUniDepLabel("dep"));
		remainingLabels.put("ROOT-VAN-CONJ", addUniDepLabel("cc"));
		remainingLabels.put("LOCY", addUniDepLabel("advmod:locy"));
		remainingLabels.put("TO", addUniDepLabel("advmod:to"));
		remainingLabels.put("TFROM", addUniDepLabel("advmod:tfrom"));
		remainingLabels.put("ATT-VAN-CONJ", addUniDepLabel("cc"));
		remainingLabels.put("FROM", addUniDepLabel("advmod:tfrom"));
		remainingLabels.put("COORD-VAN-CONJ", addUniDepLabel("cc"));
		remainingLabels.put("COORD-VAN-PUNCT", addUniDepLabel("punct"));
		remainingLabels.put("COORD-VAN-OBL", addUniDepLabel("nmod:obl"));
		remainingLabels.put("ROOT-VAN-TFROM", addUniDepLabel("advmod:tfrom"));
		remainingLabels.put("ATT-VAN-LOCY", addUniDepLabel("advmod:locy"));
		remainingLabels.put("ATT-VAN-COORD", addUniDepLabel("cc"));
		remainingLabels.put("APPEND-VAN-PUNCT", addUniDepLabel("punct"));
		remainingLabels.put("APPEND-VAN-OBL", addUniDepLabel("nmod:obl"));
		remainingLabels.put("TTO", addUniDepLabel("advmod:tto"));
		remainingLabels.put("ROOT-VAN-LOCY", addUniDepLabel("advmod:locy"));
		remainingLabels.put("ROOT-VAN-COORD", addUniDepLabel("cc"));
		remainingLabels.put("ROOT-VAN-ATT-VAN-TFROM", addUniDepLabel("advmod:tfrom"));
		remainingLabels.put("ROOT-VAN-ATT-VAN-CONJ", addUniDepLabel("cc"));
		remainingLabels.put("ROOT-VAN-APPEND", addUniDepLabel("parataxis"));
		remainingLabels.put("ATT-VAN-OBL", addUniDepLabel("nmod:obl"));
		remainingLabels.put("APPEND-VAN-APPEND", addUniDepLabel("parataxis"));
	}

	private static String addUniDepLabel(String label) {
		return "U_" + label.toUpperCase();
	}

	private static Set<String> nRALabels = new HashSet<String>(
			Arrays.asList(new String[] { "ATT", "OBL", "MODE", "LOCY", "TLOCY", "TO", "TTO", "FROM", "TFROM" }));

	public static void treeChanges(SentenceData09 sent) {
		convertPredVan(sent);
		convertPred(sent);
		convertPostPosition(sent);
	}

	public static void convertPredVan(SentenceData09 sent) {
		convertPredVan(sent, "VAN", "PRED");
		convertPredVan(sent, "VAN", "SUBJ");
		convertPredVan(sent, "VAN", "ATT");
		convertPredVan(sent, "VAN", "INF");
		convertPredVan(sent, "VAN", "MODE");
		convertPredVan(sent, "VAN", "parataxis");
		convertPredVan(sent, "ELL", "PRED");
		convertPredVan(sent, "ELL", "SUBJ");
		convertPredVan(sent, "ELL", "OBJ");
		convertPredVan(sent, "ELL", "OBL");
	}

	public static void convertPredVan(SentenceData09 sent, String vanEll, String deprel) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			if (label.endsWith(vanEll + "-" + deprel)) {
				sent.labels[i] = label.substring(0, label.length() - 9);
				// int predHead = sent.heads[i];

				for (int j = 0; j < sent.labels.length; j++) {
					// ha a gyereke vagy a vele egy szinten álló tartalmaz
					// VAN-t, akkor
					if ((sent.heads[j] == i || sent.heads[j] == sent.heads[i])
							&& sent.labels[j].contains("-" + vanEll + "-")) {
						sent.labels[j] = sent.labels[j].replaceAll(".*-", "");
					}
				}
			}
		}
	}

	public static void convertPred(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			int vHead = sent.heads[i];

			// TODO: vagy head-1????
			if (label.endsWith("PRED") && sent.gpos[vHead].equals("V")) {
				sent.heads[i] = sent.heads[vHead];
				sent.heads[vHead] = i; // TODO: vagy i-1???

				sent.labels[i] = sent.labels[vHead];
				sent.labels[vHead] = addUniDepLabel("cop"); // TODO: ?????

				for (int j = 0; j < sent.labels.length; j++) {
					if (sent.heads[j] == vHead) {
						sent.heads[j] = i;
					}
				}
			}
		}
	}

	/*
	 * TODO: Ez még nem az igazi
	 */
	public static void convertPostPosition(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			int vHead = sent.heads[i];

			// TODO: vagy head-1????

			// if (label.endsWith("ATT") && sent.gpos[vHead].equals("S")
			// && sent.ofeats[vHead].contains("SubPOS=t")) {
			if (label.endsWith("ATT") && sent.gpos[vHead].equals("S") && sent.ofeats[vHead].contains("SubPOS=t")) {
				sent.heads[i] = sent.heads[vHead];
				sent.heads[vHead] = i; // TODO: vagy i-1???

				sent.labels[i] = sent.labels[vHead];
				sent.labels[vHead] = addUniDepLabel("case"); // TODO: ?????

				for (int j = 0; j < sent.labels.length; j++) {
					if (sent.heads[j] == vHead) {
						sent.heads[j] = i;
					}
				}
			}
		}
	}

	public static void convertIfThen(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			int vHead = sent.heads[i];

			// TODO: vagy head-1????
			if (sent.forms[i].equalsIgnoreCase("ha") && label.equals("CONJ") && sent.gpos[vHead].equals("V")) {
				int ifsChild = -1;
				for (int j = 0; j < sent.labels.length; j++) {
					if (sent.heads[j] == i) {
						ifsChild = j;
						break;
					}
				}

				if (ifsChild > 0) {
					sent.heads[ifsChild] = vHead;
					sent.labels[ifsChild] = addUniDepLabel("advcl");

					sent.heads[i] = ifsChild;
					sent.labels[i] = addUniDepLabel("mark");
				}
			}
		}
	}

	/*
	 * Mari (Conj)-> és (COORD)-> Józsi (Conj)-> és (COORD)-> Peti
	 */
	public static List<List<Integer>> getCoordLists(SentenceData09 sent) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		Set<Integer> usedWords = new HashSet<Integer>();

		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			if (label.equals("COORD") && !usedWords.contains(i)) {
				List<Integer> coordChain = new ArrayList<Integer>();

				int recHead = sent.heads[i];
				// coordChain.add(recHead);
				coordChain.add(i);
				while (recHead > 0 && (sent.labels[recHead].equals("COORD") || sent.labels[recHead].equals("CONJ"))) {
					coordChain.add(0, recHead);
					recHead = sent.heads[recHead];
				}

				List<Integer> coordConjList = DepTools.getSpecificChilds(sent, i, "CONJ");
				coordConjList.addAll(DepTools.getSpecificChilds(sent, i, "COORD"));

				while (coordConjList.size() > 0) {
					if (coordConjList.size() > 1) {
						System.out.println("More than one COORD/CONJ children!");
						System.out.println(sent);
					}
					List<Integer> newCoordConjList = new ArrayList<Integer>();
					for (int nextChild : coordConjList) {
						// int nextChild = coordConjList.get(0);
						coordChain.add(nextChild);

						newCoordConjList.addAll(DepTools.getSpecificChilds(sent, nextChild, "CONJ"));
						newCoordConjList.addAll(DepTools.getSpecificChilds(sent, nextChild, "COORD"));
					}

					coordConjList = newCoordConjList;
				}

				result.add(coordChain);
			}
		}

		return result;
	}

	public static void convertCoord(SentenceData09 sent) {
		List<List<Integer>> coordList = getCoordLists(sent);
		for (List<Integer> coordChain : coordList) {
			for (int i = 0; i < coordChain.size(); i++) {
				int id = coordChain.get(i);
				if (i > 0) {
					sent.heads[id] = sent.heads[coordChain.get(0)];
				}
				if (sent.labels[id].equals("CONJ")) {
					sent.labels[id] = addUniDepLabel("cc");
				}
				if (sent.labels[id].equals("COORD")) {
					sent.labels[id] = addUniDepLabel("conj");
				}
			}
		}
	}

	public static void convertNRAByMorph(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			if (nRALabels.contains(label)) {
				String pos = sent.gpos[i];
				// String feat = sent.ofeats[i];
				if (pos.equals("R")) {
					sent.labels[i] = addUniDepLabel("advmod:" + label.toLowerCase());
				} else if (pos.equals("A")) {
					sent.labels[i] = addUniDepLabel("amod:" + label.toLowerCase());
				} else if (pos.equals("N")) {
					if (label.equals("ATT")) {
						sent.labels[i] = addUniDepLabel("nmod:att");
					} else if (label.equals("OBL")) {
						sent.labels[i] = addUniDepLabel("nmod:obl");
					} else {
						// ?????
						sent.labels[i] = addUniDepLabel("nmod");
					}
				}
			}
		}
	}

	public static void convertPd(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			String pos = sent.gpos[i];
			String feat = sent.ofeats[i];

			if (pos.equals("P") && feat.contains("SubPOS=d")) {
				if (label.equals("ATT")) {
					sent.labels[i] = addUniDepLabel("nmod:att");
				} else if (label.equals("OBL")) {
					sent.labels[i] = addUniDepLabel("nmod:obl");
				} else if (label.equals("OBJ")) {
					sent.labels[i] = addUniDepLabel("nmod:obj");
				} else if (label.equals("DAT")) {
					sent.labels[i] = addUniDepLabel("nmod:dat");
				}
			}
		}
	}

	public static void convertRenames(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			if (newLabels.containsKey(label)) {
				sent.labels[i] = newLabels.get(label);
			}
		}
	}

	public static void convertRemainingLabels(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			if (remainingLabels.containsKey(label)) {
				sent.labels[i] = remainingLabels.get(label);
			}
		}
	}

	public static void convertRemainingAtt(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			if (label.contains("-ELL-")) {
				sent.labels[i] = addUniDepLabel("remnant");
			}
		}
	}

	public static void convertEll(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			String pos = sent.gpos[i];
			if (label.equals("ATT")) {
				if (pos.equals("V")) {
					sent.labels[i] = addUniDepLabel("ccomp");
				} else if (pos.equals("N")) {
					sent.labels[i] = addUniDepLabel("nmod:att");
				} else {
					sent.labels[i] = addUniDepLabel("dep");
				}
			}
		}
	}

	/*
	 * ha van egy Cssp kódú szavunk && CONJ a címkéje akkor a gyerekét* kössük a
	 * Cssp szülőjéhez && a Cssp-t kössük be az eredeti gyerek alá && Cssp új
	 * relációja U_MARK lesz
	 */
	public static void convertCsspConj(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			String pos = sent.gpos[i];
			String feat = sent.ofeats[i];
			CoNLLFeaturesToMSD conll2msd = new CoNLLFeaturesToMSD();
			String reduced = conll2msd.convert(sent.gpos[i], sent.ofeats[i]);

			if (reduced.equals("Cssp") && label.equals("CONJ")) {
				int numChild = 0;
				int childId = -1;
				for (int j = 0; j < sent.labels.length; j++) {
					if (sent.heads[j] == i) {
						++numChild;
						childId = j;
					}

				}
				// csak, ha egy gyereke van a Cssp-nak
				if (numChild == 1) {
					sent.heads[childId] = sent.heads[i];
					sent.heads[i] = childId;
					sent.labels[i] = addUniDepLabel("mark");
				}
			}
		}
	}
	

	
	public static void convertCcConj(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			String pos = sent.gpos[i];
			String feats = sent.ofeats[i];

			if (label.equals("CONJ") && pos.equals("C") && feats.contains("SubPOS=c")) {
				sent.labels[i] = addUniDepLabel("cc");
			}
		}
	}

	/*
	 * ha van egy igénk && ATT a címkéje ha az igének van Rr MSD-kódú gyereke,
	 * akkor az ige címkéje U_ADVCL lesz ha az igének van Pr kezdetű kódú
	 * gyereke, akkor az ige címkéje U_ACL lesz
	 */
	public static void convertVAtt(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			String pos = sent.gpos[i];

			if (pos.equals("V") && label.equals("ATT")) {
				for (int j = 0; j < sent.labels.length; j++) {
					if (sent.heads[j] == i && sent.gpos[j].equals("R") && sent.ofeats[j].contains("SubPOS=r")) {
						sent.labels[i] = addUniDepLabel("advcl");
						break;
					}
					if (sent.heads[j] == i && sent.gpos[j].equals("P") && sent.ofeats[j].contains("SubPOS=r")) {
						sent.labels[i] = addUniDepLabel("acl");
						break;
					}
				}
			}
		}
	}

	/*
	 * ATT címke esetén 1) ha msd.startsWith(M) -> U_AMOD:ATT
	 */
	public static void convertMAtt(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			String pos = sent.gpos[i];

			if (pos.equals("M") && label.equals("ATT")) {
				sent.labels[i] = addUniDepLabel("amod:att");
			}
		}
	}

	/*
	 * DAT címke esetén
	 * 1) ha a szülő csomópont lemmája "tetszik" vagy "illik" -> U_NMOD:OBL 
	 * 2) ha a szülő csomópont msd-je Vmn kezdetű, és ennek a * szülője "kell, szabad, lehet, tilos" -> U_NMOD:OBL 3) else U_IOBJ
	 */
	public static void convertDAT(SentenceData09 sent) {
		for (int i = 0; i < sent.labels.length; i++) {
			String label = sent.labels[i];
			String pos = sent.gpos[i];

			if (pos.equals("M") && label.equals("ATT")) {
				sent.labels[i] = addUniDepLabel("amod:att");
			}
		}
	}

	public static void convertSentence(SentenceData09 sent) {
		treeChanges(sent);
		convertIfThen(sent);
		convertCsspConj(sent);
		convertCcConj(sent);
		convertCoord(sent);
		convertNRAByMorph(sent);
		convertPd(sent);
		convertMAtt(sent);
		convertVAtt(sent);
		convertRenames(sent);
		convertEll(sent);
		convertRemainingAtt(sent);
		convertRemainingLabels(sent);
	}
}
