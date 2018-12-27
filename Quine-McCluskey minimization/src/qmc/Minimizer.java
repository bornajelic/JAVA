package qmc;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import parser.Parser;
import Nodes.Node;
import utils.Util;

public class Minimizer {

	private Set<Integer> mintermSet; // skup indexa minterma

	private Set<Integer> dontCareSet; // skup indexa dontcareova

	private List<String> variables;

	private Set<Mask> primCover;

	private List<Set<Mask>> minimalForms;

	private static final Logger LOG = Logger.getLogger("qmc");

	/*
	 * constructor checks if these two sets overlap
	 */
	public Minimizer(Set<Integer> mintermSet, Set<Integer> dontCareSet,
			List<String> variables) {

		if (!(Collections.disjoint(mintermSet, dontCareSet))) {
			throw new IllegalArgumentException("Sets Not disjoint!");
		}
		/*
		 * te da su predani indeksi legalni za predane varijable (funkcija nad
		 * {A,B,C} ne može imati minterm 42).
		 */
		// Util.toSumOfMinterms(variables, expression) -> to cu provjeriti u
		// mainu, jeli indexi pasu

		this.mintermSet = mintermSet;
		this.dontCareSet = dontCareSet;
		this.variables = variables;
		this.primCover = findPrimaryImplicants();
		this.minimalForms = chooseMinimalCover(primCover);

	}

	/*
	 * comparing n-th group wiht n+1 th group and searching for matched pairs
	 * matched pairs are pairs that are differed only by one variable(number)
	 */
	public Set<Mask> findPrimaryImplicants() {

		List<Set<Mask>> groups = createFirstColumn();
		// System.out.println("GRUPIRANJE GOTOVO\n");
		List<Set<Mask>> tmpGroup = groups;
		List<Set<Mask>> tmpList = new ArrayList<Set<Mask>>();
		Set<Mask> primaryImplicants = new LinkedHashSet<Mask>();

		// Set<Mask> primaryImplicants = new LinkedHashSet<Mask>();

		// System.out.println("GROUPS SIZE = " + groups.size());
		/*
		 * sada moram usporediti setove 0 sa 1 1.pristupi prvom elementu iz
		 * grupe nula 2. pristupi prvom elementu iz grupe 1, suporedi ih, ++
		 */

		int counter = 0;

		boolean match = true;
		while (counter < groups.size() - 2) {
			/*
			 * System.out.println("\nUsli smo u sljedeci krug!");
			 * System.out.println("KORAK " + counter);
			 * System.out.println("tmpGroup.size = " + tmpGroup.size());
			 */
			for (int i = 0; i < tmpGroup.size() - 1; i++) {
				// System.out.println("tmpGroup.size() = " + tmpGroup.size() +
				// " i = " + i);

				Set<Mask> s = tmpGroup.get(i);
				Iterator<Mask> it1 = s.iterator(); // iterira po n setu
				Set<Mask> tmpSet = new LinkedHashSet<Mask>();

				while (it1.hasNext()) { // pokazujen na prvi u n setu

					Mask mask = it1.next(); // vraca masku
					match = false;
					// System.out.println("SIDRO -> " + mask.toString());
					// System.out.println(tmpGroup.get(1));
					Iterator<Mask> it2 = tmpGroup.get(i + 1).iterator(); // greska
																			// u
																			// step2

					while (it2.hasNext()) { // pokazujem na prvi u n+1setu

						Mask tmpMask = it2.next();
						// System.out.println("  --usporedjujem sa "
						// + tmpMask.toString());
						Optional<Mask> optional = tmpMask.combineWith(mask);

						/*
						 * TRAZIM match.. ako imam match stavi ga u novu grupu
						 */

						if (optional.isPresent()) {
							match = true;
							if (!(optional.get().isCombined())) { // AKO NIJE
																	// MATCH
								// System.out.println("BINGO!");

								tmpSet.add(optional.get());
								// System.out.println("Nasao primarni implikant -> "
								// + optional.get().toString());
								LOG.log(Level.FINEST,
										"Primary implicant found: "
												+ optional.get().toString());

							}

						}

					}

					if (match == false) {
						// System.out.println("za masku " + mask.toString() +
						// " nema matcha, on je PRIM APL");
						if (!mask.isDontCare()) {
							primaryImplicants.add(mask);
						}

					}

				}
				if (!tmpSet.isEmpty()) {
					tmpList.add(tmpSet);
				}

				// boundary condition, there is one last element, and that
				// element hasnt been compared with (because it is the only one
				// left)
				// set collection doesnt have a .get() method, so I've converted
				// it to list
				if (tmpSet.size() == 1) {
					ArrayList<Mask> last = new ArrayList<Mask>(tmpSet);
					primaryImplicants.add(last.get(0));
				}
				/*
				 * for (Mask m : tmpSet) { System.out.println(m.toString()); }
				 * System.out.println("ZAvrsio sa grupom!");
				 * System.out.println("\n====================================="
				 * );
				 */

			}
			// System.out.println("ZAVRSIO JE KRUG, TMP.LIST.SIZE = "
			// + tmpList.size());
			// tu se treba resetirato tmpGroup
			// reseting collections
			tmpGroup = new ArrayList<Set<Mask>>();
			tmpGroup.addAll(tmpList);
			tmpList = new ArrayList<Set<Mask>>();
			// tmpList = new ArrayList<Set<Mask>>();
			// tmpGroup = tmpList;
			if (!tmpGroup.isEmpty()) {
				LOG.log(Level.FINE, "*****************************");
				for (Set<Mask> s : tmpGroup) {
					for (Mask m : s) {
						LOG.log(Level.FINE, m.toString());
					}
				}
				LOG.log(Level.FINE, "*****************************");
			}

			/*
			 * int x = 0; for (Set<Mask> s : tmpGroup) {
			 * System.out.println("Grupa  " + x); System.out.println(s); x++; }
			 */

			counter++;

		} // dosao do kraja, dobio listu setova koji imaju primarne implikante
		/*
		 * System.out.println("\n SKUP PRIMARNIH APLIKANATA \n"); for(Mask m :
		 * primaryImplicants) { System.out.println(m.toString()); }
		 */

		logPrimaryImplicants(primaryImplicants);
		return primaryImplicants;
	}

	private void logPrimaryImplicants(Set<Mask> primaryImplicants) {
		if (primaryImplicants.size() == 0) {
			LOG.log(Level.SEVERE, "NO primary implicants!");
			return;
		}
		LOG.log(Level.FINE, "\n");
		LOG.log(Level.FINE, "All primary implicants");
		for (Mask m : primaryImplicants) {
			LOG.log(Level.FINE, m.toString());
		}
	}

	public List<String> getMinimalFormsAsString() {
		List<String> returnList = new ArrayList<>();

		for (Set<Mask> set : minimalForms) {
			StringBuilder sb = new StringBuilder();
			for (Mask mask : set) {
				for (int i = 0; i < mask.size(); i++) {
					if (mask.getValueAt(i) == 1) {
						sb.append(variables.get(i) + " ");
						sb.append("AND ");
					} else if (mask.getValueAt(i) == 0) {
						sb.append("NOT " + variables.get(i) + " ");
						sb.append("AND ");
					}
				}
				if (sb.length() >= 4) {
					sb.setLength(sb.length() - 4);
				}
				sb.append("OR ");
			}
			if (sb.length() >= 3) {
				sb.setLength(sb.length() - 3);
			}
			returnList.add(sb.toString());
		}
		return returnList;
	}

	/*
	 * helper method used for creating groups in method findPrimaryImplicants
	 */
	private List<Set<Mask>> createFirstColumn() {

		List<Set<Mask>> list = new ArrayList<Set<Mask>>();
		Mask maskMinterm;
		Mask maskDontCare;

		for (int i = 0; i <= this.variables.size(); i++) {
			Set<Mask> set = new LinkedHashSet<Mask>();
			Iterator<Integer> it = this.mintermSet.iterator();
			Iterator<Integer> it2 = this.dontCareSet.iterator();

			// iteration trough mintermSet
			while (it.hasNext()) {
				maskMinterm = new Mask(it.next(), this.variables.size(), false);
				// System.out.println("Obradjujem minterm : " +
				// maskMinterm.toString());
				if (maskMinterm.countOfOnes() == i) {
					set.add(maskMinterm);
				}
			}

			// iteration trough dontCareSet
			while (it2.hasNext()) {
				maskDontCare = new Mask(it2.next(), this.variables.size(), true);
				// System.out.println("Obradjujem DONTCARE : " +
				// maskDontCare.toString());
				if (maskDontCare.countOfOnes() == i) {
					set.add(maskDontCare);
				}
			}

			list.add(set);

		}
		LOG.log(Level.INFO, "START");
		LOG.log(Level.FINE, "*****************************");
		for (Set<Mask> s : list) {
			for (Mask m : s) {
				LOG.log(Level.FINE, m.toString());
			}

		}

		LOG.log(Level.FINE, "*****************************");

		return list;
	}

	private List<Set<Mask>> chooseMinimalCover(Set<Mask> primCover) {

		Mask[] implicants = primCover.toArray(new Mask[primCover.size()]);
		Integer[] minterms = mintermSet.toArray(new Integer[mintermSet.size()]);

		// Mapiraj minterm u stupac u kojem se nalazi:
		Map<Integer, Integer> mintermToColumnMap = new HashMap<>();
		for (int i = 0; i < minterms.length; i++) {
			Integer index = minterms[i];
			mintermToColumnMap.put(index, i);
		}// (0,0),(1,1),(3,2),(10,3),(11,4),(14,5),(15,6)

		// Napravi praznu tablicu pokrivenosti:
		boolean[][] table = buildCoverTable(implicants, minterms,
				mintermToColumnMap);
		// Donji redak tablice: koje sam minterme pokrio?
		// automatski postavljen na false
		boolean[] coveredMinterms = new boolean[minterms.length];

		// Pronađi primarne implikante...
		Set<Mask> importantSet = selectImportantPrimaryImplicants(implicants,
				mintermToColumnMap, table, coveredMinterms);
		LOG.log(Level.FINE, "");
		LOG.log(Level.FINE, "Important primary implicants: ");
		for (Mask mask : importantSet) {
			LOG.log(Level.FINE, mask.toString());
		}
		// Izgradi funkciju pokrivenosti:

		List<Set<BitSet>> pFunction = buildPFunction(table, coveredMinterms);
		LOG.log(Level.FINER, "");
		LOG.log(Level.FINER, "p funkcija je:");
		LOG.log(Level.FINER, pFunction.toString());
		// Pronađi minimalne dopune:

		Set<BitSet> minset = findMinimalSet(pFunction);
		LOG.log(Level.FINER, "");
		LOG.log(Level.FINER, "Minimalna pokrivanja još trebaju::");
		LOG.log(Level.FINER, minset.toString());
		// Izgradi minimalne zapise funkcije:

		List<Set<Mask>> minimalForms = new ArrayList<>();
		for (BitSet bs : minset) {
			Set<Mask> set = new LinkedHashSet<>(importantSet);
			bs.stream().forEach(i -> set.add(implicants[i]));
			minimalForms.add(set);
		}

		LOG.log(Level.FINE, "");
		LOG.log(Level.FINE, "Minimalni oblici funkcije su:");

		for (Set<Mask> minForm : minimalForms) {
			LOG.log(Level.FINE, minForm.toString());
		}
		return minimalForms;
	}

	public List<Node> getMinimalFormsAsExpression() {
		List<Node> nodes = new ArrayList<Node>();
		List<String> minimalForms = getMinimalFormsAsString();
		for (String s : minimalForms) {
			Parser parser = new Parser(s);
			nodes.add(parser.getExpression());
		}
		return nodes;
	}

	private boolean[][] buildCoverTable(Mask[] implicants, Integer[] minterms,
			Map<Integer, Integer> mintermToColumnMap) {

		boolean[][] table = new boolean[implicants.length][minterms.length];
		for (int i = 0; i < implicants.length; i++) {
			for (int j = 0; j < minterms.length; j++) {
				if (implicants[i].getIndexes().contains(minterms[j])) {
					table[i][j] = true;
				} else {
					table[i][j] = false;
				}
			}
		}
		return table;
	}

	/*
	 * ovu metodu doraditi
	 */
	private Set<Mask> selectImportantPrimaryImplicants(Mask[] implicants,
			Map<Integer, Integer> mintermToColumnMap, boolean[][] table,
			boolean[] coveredMinterms) {
		// trazi implikante koji jedini pokrivaju neki midterm
		Set<Mask> essentialImplicants = new LinkedHashSet<Mask>();
		int row = table[0].length;
		int column = table[1].length;

		/*
		 * otvaram tablicu u tablici gledam stupac po stupac kad naidjem na true
		 * oznacim lokaciju ako je lokacija samo jedna onda BINGO na toj i
		 * koordinati gledam koji je implikant i on je primaran
		 */

		int matchCounter = 0;
		Mask importantImplicant = null;
		for (int j = 0; j < column; j++) {
			for (int i = 0; i < row; i++) {
				if (table[j][i]) {
					matchCounter++;
					// zapamti prvi match, ako postoji jos jedan sljedeci,
					// zajebi cijeli stupac
					importantImplicant = implicants[i];

				}
			}
			if (matchCounter == 1) {
				essentialImplicants.add(importantImplicant);
				coveredMinterms[j] = true;
			}

		}

		return essentialImplicants;
	}

	private List<Set<BitSet>> buildPFunction(boolean[][] table,
			boolean[] coveredmMinterms) {

		ArrayList<Set<BitSet>> list = new ArrayList<Set<BitSet>>();

		for (int i = 0; i < coveredmMinterms.length; i++) {
			if (coveredmMinterms[i]) {
				continue;
			}

			Set<BitSet> set = new LinkedHashSet<BitSet>();
			for (int j = 0; j < table.length; j++) {
				if (table[j][i]) {
					BitSet bs = new BitSet();
					bs.set(i);
					set.add(bs);
				}
			}
			list.add(set);
		}
		return list;
	}

	private Set<BitSet> findMinimalSet(List<Set<BitSet>> pFunction) {

		Set<BitSet> returnSet = new LinkedHashSet<>();

		if (pFunction.size() == 0) {
			return returnSet;
		}

		Set<BitSet> firstSet = pFunction.get(0);

		// First we multiply pFunction
		for (int i = 1; i < pFunction.size(); i++) {
			Set<BitSet> secondSet = pFunction.get(i);
			Set<BitSet> productSet = new LinkedHashSet<>();
			for (BitSet b1 : secondSet) {
				for (BitSet b2 : firstSet) {
					BitSet temp = new BitSet();
					temp.set(b1.nextSetBit(0));
					b1.or(b2);
					productSet.add(b1);
					b1 = temp;
				}
			}
			firstSet = productSet;
		}

		LOG.log(Level.FINER, "");
		LOG.log(Level.FINER, "Nakon pretvorbe p-funkcije u sumu produkata:");
		LOG.log(Level.FINER, firstSet.toString());

		// Then find minimal bitset
		int minimalCardinality = 0;

		for (BitSet bitSet : firstSet) {
			if (minimalCardinality == 0) {
				minimalCardinality = bitSet.cardinality();
			} else {
				if (bitSet.cardinality() < minimalCardinality) {
					minimalCardinality = bitSet.size();
				}
			}
		}

		for (BitSet bitSet : firstSet) {
			if (bitSet.cardinality() == minimalCardinality) {
				returnSet.add(bitSet);
			}
		}

		return returnSet;
	}

	public Set<Mask> getPrimCover() {
		return this.primCover;
	}

}
