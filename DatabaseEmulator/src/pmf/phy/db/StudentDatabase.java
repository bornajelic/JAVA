package pmf.phy.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentDatabase {

	List<StudentRecord> internalList = new ArrayList<StudentRecord>();
	HashMap<String, StudentRecord> hMap = new HashMap<>();
	List<StudentRecord> finalList = new ArrayList<StudentRecord>();

	/**
	 * Konstruktoru cemo poslati file koji ce biti "razbijen" u listu stringova
	 * Stvaramo internu listu studentskih zapisa
	 * 
	 * @param inputFile
	 */
	public StudentDatabase(List<String> inputFile) {
		for (int i = 0; i < inputFile.size(); i++) {
			String[] str = inputFile.get(i).split("\t");
			StudentRecord noviInput = new StudentRecord(str[0], str[1], str[2],
					Integer.parseInt(str[3]));
			internalList.add(noviInput);
			hMap.put(str[0], noviInput);
		}
	}

	public StudentRecord forJMBAG(String jmbag) {
		return hMap.get(jmbag);
	}

	/**
	 * Metoda prolazi kroz internalList, zove accepts metodu , ako je true,
	 * pohrani zapis u finalList
	 * 
	 * @param filter
	 *            Referenca na objekt koji je instanca IFilter interface-a
	 * @return finalList
	 */
	public List<StudentRecord> filter(IFilter filter) { 
		/*
		 * predajem metodi listu conditionalExpressions-a (upit)
		 * 
		 */
		for (int i = 0; i < internalList.size(); i++) {
			if (filter.accepts(internalList.get(i))) {
				finalList.add(internalList.get(i));
			}
		}
		return finalList;
	}
}
