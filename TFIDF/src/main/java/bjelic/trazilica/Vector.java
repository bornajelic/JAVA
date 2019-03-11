package bjelic.trazilica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * testiraj svaku metodu
 */

public class Vector {

	private Vocabulary vocabulary;

	private List<DocumentVector> listOfDocuments = new ArrayList<DocumentVector>();
	
	private Map<String,Double> idfMap;
	
	public Vector(String directory) {
		this.vocabulary = new Vocabulary(directory);
		try {
			this.idfMap = calculateIDF(vocabulary.getFileArray());
			//System.out.println(vocabulary.getFileArray().length);
			for(File f : vocabulary.getFileArray()) {
				// prvo tf trebam izracunati
				//System.out.println("\nPromatram file -> " + f.getName() + "\n");
				listOfDocuments.add(documentCreation(f.getName(), idfMap, vocabulary.fileToListOfWords(f)));
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("GRESKA U STVARANJU IDF KOEFICIJENATA ZA VOKABULAR!");
		}
		
		
	}

	private Map<String, Double> calculateIDF(File[] fileArray) throws FileNotFoundException {

		List<String> vocabularyList = new ArrayList<String>(vocabulary.getEntireVocabulary());
		List<String> wordsOfAFile = new ArrayList<String>();
		Map<String,Double> idfMap = new HashMap<String, Double>();
	

		for (String word : vocabularyList) {
			double idf = 0.0;
			// System.out.println("\nnova rijec -> " + word + "\n");
			// za tu rijec otvori dokument1 i provjeri jeli postoji
			for (File f : fileArray) {
				// System.out.println("sada proucavam " + f.getName());
				wordsOfAFile = vocabulary.fileToListOfWords(f);
				if (wordsOfAFile.contains(word)) {
					idf++;
				}

			}
		
			idfMap.put(word, Math.log((double) fileArray.length / idf));
			
			
		}
		
		return idfMap;
	}
	
	/*
	 * saljem ime file-a i mapu/listu idf koeficijenata i lijepim na taj dokument
	 * zadnja varijabla je poslana u obliku funkcije getFile2ListofWords(FIle f)M
	 * zadnja varijabla mora proci prije kroz metodu koja racuna tf vrijednosti za taj file
	 */

	private DocumentVector documentCreation(String fileName, Map<String,Double> idfMap, List<String> fileWords) {
		
		Map<String,Double> map = new HashMap<String, Double>();
		Set<String> fileWordsSet = new LinkedHashSet<String>(fileWords);
		List<String> list = new ArrayList<String>();
		list.addAll(fileWordsSet);
		
		for(int i = 0; i < list.size(); i++) {
			map.put(list.get(i), idfMap.get(list.get(i)));
		}
		/*
		for(Map.Entry<String, Double> e : map.entrySet()) {
			System.out.println(e.getKey() +" " + e.getValue());
		}*/
		return new DocumentVector(fileName, map);
	}
	
	
	public List<Double> calculateTF(List<String> fileWords) {
		
		//System.out.println("Sve rijeci u fajlu");
		//fileWords.stream().forEach(System.out::println);
		
		
		
		Set<String> set = new LinkedHashSet<String>(fileWords);
		List<Double> tfList = new ArrayList<Double>();
		
		
		for(String word : set) {
			//System.out.println("Promatram rijec --------->" + word);
			double tf = 0.0;
			for(int i = 0; i < fileWords.size(); i++) {
				
				if(word.equals(fileWords.get(i))) {
					++tf;
				}	
			}
			tfList.add(tf);
			//System.out.println("Za rijec  ---> " + word + " <--- TF = " + tf);
		}
		
		return tfList;
	}
	
	public  Map<String, Double> calculateTF2(List<String> list) {

		
		
		Map<String, Double> map = new LinkedHashMap<String, Double>();

		for (int i = 0; i < list.size(); i++) {
			map.merge(list.get(i), (double)1, Double::sum);
		}
		/*
		for(Map.Entry<String, Double> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		}*/
		return map;
	}
	
	public List<DocumentVector> getListOfDocuments() {
		return this.listOfDocuments;
	}
	
	public Map<String,Double> getIdfMap() {
		return this.idfMap;
	}
	
	public Vocabulary getVocabulary() {
		return this.vocabulary;
	}
}
