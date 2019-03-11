package bjelic.trazilica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class VocDemo {

	public static void main(String[] args) throws IOException {

		long startTime = System.currentTimeMillis();
		Vector v = new Vector("test"); 
		long endTime = System.currentTimeMillis();
		List<DocumentVector> documents = v.getListOfDocuments();

		System.out.println("Vrijeme : " + (endTime - startTime) + "ms");

		System.out.println("Upisi query");

		
		/*
		while (true) {
			if (sc.hasNextLine()) {
				String query = sc.nextLine().toLowerCase();
				if (query.equals("break")) {
					break;
				}
				String[] s = query.split("\\s");

				List<String> queryList = new ArrayList<String>(Arrays.asList(s)); //
				//System.out.println("LISTA PRIJE IZBACIVANJA"); //
				//queryList.stream().forEach(System.out::println);

				Set<String> set = v.getVocabulary().getVocabulary(); // izbacene STOP-rijeci, debilu ovo si glupo
																		// napisao
				Iterator<String> it = queryList.iterator();

				while (it.hasNext()) {
					if (!set.contains(it.next())) {
						it.remove();
					}
				} 
				//System.out.println("LISTA POSLIJE IZBACIVANJA!"); //
				//queryList.stream().forEach(System.out::println);
				Map<String, Double> queryTF = v.calculateTF2(queryList);

				for (DocumentVector document : documents) {
					document.cosineSimilarity(queryTF);
				}

				documents.sort((o1, o2) -> o2.getCosineSimilarity().compareTo(o1.getCosineSimilarity()));

				// 5 najbitnijih dokumenata ili (ukoliko ih je manje) samo one koje daju
				// rezultat
				int counter = 0;
				for (int i = 0; i < documents.size(); i++) {
					
					if (documents.get(i).getCosineSimilarity() != 0) {
						counter++;
						if(counter < 5) {
							System.out.println(documents.get(i).getFileName());
						}else {
							break;
						}
						
					} else {
						continue;
					}
				}
			}

		}

		sc.close();
		*/
		Scanner scanner;
		
		while(true) {
			StringBuilder sb = new StringBuilder();
		 scanner = new Scanner(System.in);
			System.out.print("> ");
			if(scanner.hasNextLine()) {
				String[] entry = scanner.nextLine().toLowerCase().split("\\s+");
				if(entry[0].equals("QUERY".toLowerCase())) {
					for(int i = 1; i < entry.length; i++) {
						sb.append(entry[i]+" ");
					}
					System.out.println("sb---> " +sb.toString());
					
				} else if(entry[0].equals("READ".toLowerCase())) {
					readAFile(entry[1]);
					
				} else if(entry[0].equals("BREAK".toLowerCase())) {
					break;
				} else {
					System.out.println("Nepoznata naredba");
					continue;
				}
			} else {
				break;
			}
			
			String[] s = sb.toString().trim().split("\\s+");
			List<String> queryList = new ArrayList<String>(Arrays.asList(s));
			

			Set<String> set = v.getVocabulary().getVocabulary(); // izbacene STOP-rijeci, debilu ovo si glupo
																	// napisao
			Iterator<String> it = queryList.iterator();

			while (it.hasNext()) {
				if (!set.contains(it.next())) {
					it.remove();
				}
			} 
			
			Map<String, Double> queryTF = v.calculateTF2(queryList);

			for (DocumentVector document : documents) {
				document.cosineSimilarity(queryTF);
			}

			documents.sort((o1, o2) -> o2.getCosineSimilarity().compareTo(o1.getCosineSimilarity()));

			// 5 najbitnijih dokumenata ili (ukoliko ih je manje) samo one koje daju
			// rezultat
			int counter = 0;
			for (int i = 0; i < documents.size(); i++) {
				
				if (documents.get(i).getCosineSimilarity() != 0) {
					counter++;
					if(counter < 5) {
						System.out.println(documents.get(i).getFileName());
					}else {
						break;
					}
					
				} else {
					continue;
				}
			}
			
		}
		scanner.close();
		System.out.println("ĆAOOOO LEPI, BILO MI JE DRAGO!");
		
		
	}

	public static void readAFile(String fileName) throws IOException {
		
		File file = null;
		ClassLoader cl = new VocDemo().getClass().getClassLoader();
		
		if(fileName == null) {
			return;
		}
		try {
			file = new File(cl.getResource(fileName).getFile());
		} catch(NullPointerException e) {
			System.out.println("file se nije mogao alocirati, vjerojatno nisi poslao relative root (clanci, tekstovi,test) ili file ne postoji u tom direktoriju");
			return;
		}
		

		if (file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st = null;
			while ((st = br.readLine()) != null) {
				System.out.println(st);
			}
			br.close();
		} else {
			System.out.println("Ne postoji file!");
		}
	}

}
