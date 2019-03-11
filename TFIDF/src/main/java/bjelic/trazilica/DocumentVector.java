package bjelic.trazilica;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DocumentVector {
	
	

	private Map<String,Double> IDF;

	private String fileName;
	
	private List<Double> queryTFIDF;
	
	private Double cosineSimilarity;
	
	
	

	public DocumentVector(String fileName, Map<String,Double> IDF) {

		this.IDF = IDF;
		this.fileName = fileName;

	}
	
	

	public Map<String,Double> getTFIDF() {
		return IDF;
	}

	public String getFileName() {
		return fileName;
	}
	
	public List<Double> getQueryTFIDF() {
		return this.queryTFIDF;
	}
	
	/*
	 * tu ce se trebati igrati jer moram vidjeti oce li biti ista velicina kada izbacim zaustavne rijeci.. no brrijem da je ok
	 */
	
	public void cosineSimilarity(Map<String,Double> queryTF) { //arumenti imaju istu duljinu
		/*for(Map.Entry<String, Double> e : this.IDF.entrySet()) {
			System.out.println(e.getKey() + " -> " + e.getValue());
		}*/
		
		Map<String, Double> map = new LinkedHashMap<String, Double>();
		for(Map.Entry<String, Double> entry : queryTF.entrySet()) {
			
			double d;
			if(this.IDF.get(entry.getKey()) == null) {
				d = 0;
			}
			else {
				d = this.IDF.get(entry.getKey());
			}
			//System.out.println("dodajem : queryTF-> "+ entry.getKey()+ " " + entry.getValue() + " * IDF -> " + d + "od rijeci " +this.IDF.get(entry.getKey()) );
			map.put(entry.getKey(), (entry.getValue() * d));
		}
		
		/*System.out.println("Printanje TFIDF za dokument");
		for(Map.Entry<String, Double> e1 : map.entrySet()) {
			System.out.println(e1.getKey() + " ---> " + e1.getValue());
		}*/
		//System.out.println("ZAVRSIO\n");
		
		
		
		double dotProduct = 0.0;
		double unitLenghtQuery = 0.0;
		double unitLenghtDocument = 0.0;
		
		for(Map.Entry<String, Double> e : queryTF.entrySet()) {
			dotProduct += e.getValue() * map.get(e.getKey());
			unitLenghtQuery += Math.pow(e.getValue(), 2);
			unitLenghtDocument +=Math.pow(map.get(e.getKey()), 2);
		}
		unitLenghtQuery = Math.sqrt(unitLenghtQuery);
		unitLenghtDocument = Math.sqrt(unitLenghtDocument);
		//System.out.println(dotProduct + " / " + unitLenghtDocument + " * " + unitLenghtQuery);
	
		if(unitLenghtDocument == 0) {
			this.cosineSimilarity = 0.0;
		} else {
			this.cosineSimilarity =  (dotProduct/(unitLenghtDocument * unitLenghtQuery));
		}
		
		
	}
	
	public Double getCosineSimilarity() {
		return this.cosineSimilarity;
	}
	
}
