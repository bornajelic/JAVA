package pmf.custom.collections;

/**
 * Collection klasa sa svojstvenim metodama
 * @author Borna Jelić PMF
 *
 */

public class Collection {
	
	/**
	 * Defaultni konstruktor
	 */

	protected Collection() {
		
	}
	
	/**
	 * Provjerava jeli prazna kolekcija
	 * @return True ako je prazna, false u suprotnom
	 */
	
	
	boolean isEmpty() {
		if(size() == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Metoda racuna broj elemenata(objekata) 
	 * @return broj od trenutno spremljenih objekata
	 */
	
	int size() {
		return 0;
	}
	
	/**
	 * Dodaje elemente u kolekciju
	 * @param value
	 */
	
	
	void add(Object value) {
		
	}
	
	/**
	 * Provjerava postoji li trazeni element u kolekciji
	 * @param value
	 * @return true ako element postoji, false u suprotnom
	 */
	
	boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Ukoliko postoji zadana vrijednost u kolekciji, brise ju
	 * @param value
	 * @return true ako je nadjena zadana vrijednost i brise ju,
	 * false ako nije nadjena zadana vrijednost
	 */
	
	public boolean remove(Object value) {
		return false;
	}
	
	
	/**
	 * Metoda alocira polje objekata sa velicinom zadane kolekcije te ju
	 * puni elementima, nikad ne vraca null
	 * @return Object Array od zadane kolekcije
	 */
	
	
	Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Metoda zove processor metodu Processor klase za svaki element zadane
	 * kolekcije
	 * @param processor
	 */
	
	void forEach(Processor processor) {
		
	}
	
	/**
	 * Metoda daje sve elemente zadane kolekcije novoj kolekciji
	 * @param other
	 */
	
	
	void addAll(Collection other) {
		class processor extends Processor {
			public void process(Object value){
				 add(value);
			 }
			
		}
		other.forEach(new processor());
	}
	
	/**
	 * Metoda brise sve elemente iz kolekcije
	 */
	
	void clear() {
		
	}
}
