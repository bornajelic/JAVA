package bjelic.trazilica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/*
 * Stvara se rijecnik svih rijeci koje se nalaze u skupu dokumenata
 * Provjerava se svaka rijec i ukoliko ta rijec nije "zaustavna" (nebitna)
 * stavja se u listu
 */
public class Vocabulary {

	private Set<String> vocabulary; // bez zaustavnih rijeci

	private Set<String> entireVocabulary;
	
	private File[] fileArray;
	
	

	/*
	 * konstruktor prima String varijablu koja daje ime direktorija u kojemu su
	 * fileovi vraca vokabular rijeci s time da se izbacuju zaustavne rijeci
	 * (zamjenice i slicno) te vraca polje svih fileova
	 */
	public Vocabulary(String resourceFolder) {

		try {
			this.entireVocabulary = entireVocabulary(resourceFolder);
			this.vocabulary = noStopWordsVocabulary(resourceFolder);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.getStackTrace();
			System.out.println("Greska u stvaranju rijecnika!");
		}

		this.fileArray = getResourceFolderFiles(resourceFolder);

	}
	private Set<String> entireVocabulary(String resourceFolder) throws FileNotFoundException {
		
		List<String> listOfWords = new ArrayList<String>();
		for (File f : getResourceFolderFiles(resourceFolder)) {

			listOfWords.addAll(fileToListOfWords(f));
		}
		Set<String> set = new HashSet<String>(listOfWords);
		return set;
	}

	private Set<String> noStopWordsVocabulary(String resourceFolder) throws FileNotFoundException {

		List<String> listOfWords = new ArrayList<String>();
		List<String> stopFileList = new ArrayList<String>();
		Set<String> setOfWords = new HashSet<String>();

		/*
		 * loadanje file-a koji sadrzi stop rijeci te ubacivanje tih rijeci u stopFile
		 * -listu
		 */

		String fileName = "hrvatski_stoprijeci.txt";
		ClassLoader cl = new VocDemo().getClass().getClassLoader();
		File file = new File(cl.getResource(fileName).getFile());
		Scanner sc = new Scanner(file);
		while (sc.hasNext()) {
			stopFileList.add(sc.next().toLowerCase());
		}
		sc.close(); // stopFileList sadrzi sve zaustavne rijeci, ovo je dobro

		for (File f : getResourceFolderFiles(resourceFolder)) {

			listOfWords.addAll(fileToListOfWords(f));
		}
		// System.out.println("Ukupan broj riejeci bez provjere " + listOfWords.size());
		// // ovo je ok

		for (String word : listOfWords) {

			if (!stopFileList.contains(word)) {
				setOfWords.add(word);
			}
		}

		return setOfWords;

	}

	/*
	 * Metoda pristupa resource folderu i vraca polje file-ova
	 */

	private File[] getResourceFolderFiles(String folder) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource(folder);
		String path = url.getPath();

		return new File(path).listFiles();

	}

	/*
	 * Metoda ulazi u zaseban file, mice sve interpunkcijske znakove i vraca listu
	 * preostalih rijeci iz filea (SA ponavljanjIMA),sada je sa ponavljanjem
	 */

	List<String> fileToListOfWords(File f) throws FileNotFoundException {

		Scanner sc = new Scanner(f, "UTF-8");

		String txt = sc.useDelimiter("\\A").next().toLowerCase();
		String[] s = txt.replaceAll("^[.,\\s]+", "").split("[.,\\s]+");
		sc.close(); // ovo je dobro
		List<String> list = Arrays.asList(s);
		
		return list;
	}

	/*
	 * Metoda cita file, salje se relative path tipa tekstovi/eclipse.txt ili
	 * clanci/blabla.txt
	 */
	public void readAFile(String fileName) throws IOException {
		ClassLoader cl = new VocDemo().getClass().getClassLoader();

		File file = new File(cl.getResource(fileName).getFile());

		if (file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st = null;
			while ((st = br.readLine()) != null) {
				System.out.println(st);
			}
			br.close();
		} else {
			System.out.println("moras upisati direktorij!!!");
		}
	}

	public  String file2String(File f) throws FileNotFoundException {
		Scanner sc = new Scanner(f, "UTF-8");
		String txt = sc.useDelimiter("\\A").next();
		sc.close();
		return txt;
	}

	public Set<String> getVocabulary() {
		return this.vocabulary;
	}

	public File[] getFileArray() {
		return this.fileArray;
	}

	public Set<String> getEntireVocabulary() {
		return this.entireVocabulary;
	}
}
