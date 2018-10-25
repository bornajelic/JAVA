package pmf.hashtable;


import java.util.ConcurrentModificationException;
import java.util.Iterator;

/*
 * sa stackowerflow-a
 * 
 * An iterable is a data structure that wants to make its elements accessible to the public. 
 * It does so by implementing a method whose key is Symbol.iterator. That method is a factory for iterators.
 * An iterator is a pointer for traversing the elements of a data structure (think cursors in databases).
 */
public class HashMap<K, V> implements Iterable<HashMap.TableEntry<K, V>> {

	static class TableEntry<K, V> {

		private K key;
		private V value;

		private TableEntry<K, V> next;

		public K getKey() {
			return this.key;
		}

		public V getValue() {
			return this.value;
		}

		public void setValue(V value) {
			this.value = value;
		}
	}

	private int modificationCount;

	private TableEntry<K, V>[] table;

	private int size; // velicina tablice

	private int capacity; // popunjenost tablice

	/**
	 * default konsktruktor
	 */
	public HashMap() {

		this(16);
	}

	/**
	 * Konstruktor prima jedan argument: broj koji predstavlja željeni početni
	 * kapacitet tablice i koji stvara tablicu veličine koja je potencija broja
	 * 2 koja je prva veća ili jednaka predanom broju (npr. ako se zada 30, bira
	 * se 32)
	 */
	@SuppressWarnings("unchecked")
	public HashMap(int number) {

		if (number < 1) {
			throw new IllegalArgumentException("Unos ne smije biti manji od 1");
		}

		if (checkPowerOf2(number)) {
			this.size = number;
		} else {
			this.size = searchPotention(number);
		}

		this.table = new TableEntry[this.size];
		this.capacity = 0;
		this.modificationCount = 0;
	}

	/**
	 * Metoda put pozvana s ključem koji u tablici već postoji ažurira postojeći
	 * par novom vrijednošću; metoda ne dodaje još jedan par s istim ključem ali
	 * drugom vrijednosti. Ako se kao ključ preda null, metoda mora baciti
	 * IllegalArgumentException.
	 * 
	 * Ukoliko je popunjenost >= 75% velicine tablice, tablica se proširuje
	 * 
	 * @param key
	 *            kljuc
	 * @param value
	 *            vrijednost
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key ne smije biti null");
		}

		if (capacity >= (0.75 * size())) {
			table = resizeAndCreateNew(); // tu mora biti isto kao i prije
											// popunjena, ali nova i veca
											// tablica

		}

		if (isKeyInTable(key, this.table)) { // ako je -> overwrite
			int i = hashFunctionIndex((K) key);
			TableEntry<K, V> tmp = table[i];
			while (tmp != null) {
				if (tmp.getKey().equals(key)) {
					tmp.value = value;
					return;
				}
				tmp = tmp.next;
			}

		}

		TableEntry<K, V> novi = new TableEntry<>();
		novi.key = key;
		novi.value = value;
		novi.next = null;
		if (table[hashFunctionIndex(key)] == null) {
			table[hashFunctionIndex(key)] = novi;
		} else {
			TableEntry<K, V> tmp = table[hashFunctionIndex(key)];
			while (tmp.next != null) {
				tmp = tmp.next;
			}
			tmp.next = novi;
		}
		this.capacity++;
		this.modificationCount++;
		return;
	}

	public boolean isKeyInTable(Object key, TableEntry<K, V>[] hash) {
		if (key == null) {
			return false;
		}
		@SuppressWarnings("unchecked")
		int i = hashFunctionIndex((K) key);
		TableEntry<K, V> tmp = hash[i];
		while (tmp != null) {
			if (tmp.getKey().equals(key)) {
				return true;
			}
			tmp = tmp.next;
		}
		return true;

	}

	/**
	 * Metoda kojom prolazimo starom tablicom te svaki element saljemo pomocnom
	 * metodi koja ce prebacivati elemente u novu tablicu sa novim indexhashom
	 * 
	 * @return prosirena tablica
	 */

	public TableEntry<K, V>[] resizeAndCreateNew() {

		int oldSize = this.size;
		this.size = 2 * this.size;
		TableEntry<K, V> tmp;
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] expanded = new TableEntry[this.size];

		for (int i = 0; i < oldSize; i++) {
			tmp = table[i];
			if (tmp == null) {
				continue;
			}

			while (tmp != null) {

				putInNew(tmp.getKey(), tmp.getValue(), expanded);

				tmp = tmp.next;
			}
		}

		return expanded;
	}

	/**
	 * Pomocna metoda koja stavlja elemente u novu tablicu
	 * 
	 * @param key
	 *            kljuc iz poslanog node-a (stara tablica)
	 * @param value
	 *            vrijednost iz poslanog node-a (stara tablica)
	 * @param expanded
	 *            nova tablica
	 */
	public void putInNew(K key, V value, TableEntry<K, V>[] expanded) {
		if (key == null) {
			throw new IllegalArgumentException("Key ne smije biti null");
		}
		// novi node
		TableEntry<K, V> novi = new TableEntry<>();
		novi.key = key;
		novi.value = value;
		novi.next = null;

		if (expanded[hashFunctionIndex(key)] == null) {
			expanded[hashFunctionIndex(key)] = novi;
		} else {
			TableEntry<K, V> tmp = expanded[hashFunctionIndex(key)];
			while (tmp.next != null) {
				tmp = tmp.next;
			}
			tmp.next = novi;
		}

		return;

	}

	/**
	 * Metoda vraca vrijednost za dani kljuc
	 * 
	 * @param key
	 *            , kljuc, dozvoljen null
	 * @return value
	 */
	public V get(Object key) {

		TableEntry<K, V> tmp;
		for (int i = 0; i < size(); i++) {
			tmp = table[i];
			if (tmp == null)
				continue;
			while (tmp != null) {
				if (tmp.getKey().equals(key)) {
					return tmp.getValue();
				} else {
					tmp = tmp.next;
				}
			}
		}
		return null;
	}

	/**
	 * Metoda moze primiti null, odnosno kljuc ne moze biti null, vrijednost
	 * moze
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(Object key) {

		@SuppressWarnings("unchecked")
		int i = hashFunctionIndex((K) key);
		if (table[i] == null) {
			throw new IllegalArgumentException(
					"za unešen kljuc ne postoji puni valjani sloth ");
		}

		TableEntry<K, V> tmp = table[i];

		while (tmp != null) {
			if (tmp.getKey().equals(key)) {
				return true;
			} else {
				tmp = tmp.next;
			}
		}

		return false;
	}

	/**
	 * Metoda dozvoljava null input, vraca true ukoliko je pronadjena ta
	 * vrijednost u tablici
	 */
	public boolean containsValue(Object value) {

		TableEntry<K, V> tmp;
		for (int i = 0; i < this.size; i++) {
			tmp = table[i];
			while (tmp != null) {
				if (tmp.getValue().equals(value)) {
					return true;
				} else {
					tmp = tmp.next;
				}
			}
		}

		return false;
	}

	/**
	 * Metoda remove uklanja iz tablice uređeni par sa zadanim ključem, ako
	 * takav postoji (inače ne radi ništa). Ako se kao ključ preda null, metoda
	 * ne radi ništa jer takav ključ doista ne postoji
	 * 
	 * @param key
	 */

	public void remove(Object key) {
		@SuppressWarnings("unchecked")
		int i = hashFunctionIndex((K) key);
		if (table[i] == null) {
			throw new IllegalArgumentException();
		}

		TableEntry<K, V> tmp = table[i];
		TableEntry<K, V> previous;
		// prvi element
		if (tmp.getKey().equals(key)) {
			tmp = tmp.next;
			table[i] = tmp;
			this.capacity--;
			return;
		}
		while (!(tmp.next.getKey().equals(key)) && tmp.next != null) {
			tmp = tmp.next;
		}
		previous = tmp;
		previous.next = tmp.next.next;
		this.capacity--;
		this.modificationCount++;
		return;
	}

	/**
	 * Metoda racuna hash vrijednost
	 * 
	 * @param key
	 * @param table
	 * @return index u koji se trebaju ubacivi key and value
	 */
	public int hashFunctionIndex(K key) {

		return Math.abs(key.hashCode()) % this.size;

	}

	/**
	 * Metoda provjerava jeli zadani broj potencija broja 2
	 * 
	 * @param broj
	 *            koji predajemo konstruktoru za velicinu tablice
	 * @return true ako je potencija od 2 i obrnuto
	 */
	static boolean checkPowerOf2(int i) {
		return (i != 0) && ((i & (i - 1))) == 0;
	}

	/**
	 * Metoda se poziva ukoliko broj, koji je predan konstruktoru, nije
	 * potencija broja 2, trazi najblizi broj koji je potencija broja 2
	 * 
	 * @param broj
	 *            koji predajemo konstruktoru za velicinu tablice
	 * @return broj koji je potencija broja 2
	 */
	static int searchPotention(int i) {
		i++;
		while (true) {

			if (checkPowerOf2(i)) {
				return i;
			} else {
				i++;
			}
		}
	}

	public int size() {
		return this.size;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		TableEntry<K, V> tmp;
		for (int i = 0; i < this.size(); i++) {
			tmp = table[i];

			if (tmp == null)
				continue;

			while (tmp != null) {
				sb.append("Key " + i + ": " + tmp.getKey() + " ,Value " + i
						+ ": " + tmp.getValue() + " \n ");
				tmp = tmp.next;

			}

		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Metoda brise sve uredjene parove
	 */
	public void clear() {
		TableEntry<K, V> tmp, tmp2;
		for (int i = 0; i < size(); i++) {
			tmp = table[i];
			if (tmp == null)
				continue;

			while (tmp != null) {
				tmp2 = tmp.next;
				remove(tmp.getKey());
				this.capacity--;
				tmp = tmp2;
			}
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		// TODO Auto-generated method stub
		return new MyIterator();
	}
//GREŠKA KOD hasNEXT()!! ona se zove svaki put, ne samo jednom, prepravi sve
	private class MyIterator implements Iterator<HashMap.TableEntry<K, V>> {

		TableEntry<K, V> current = table[0]; //ovo ne moze biti, ne poziva se metoda samo jednom

		private int arrayIndex = 0;
		private int expectedModificationCount = modificationCount;

		@Override
		public boolean hasNext() {
			if (expectedModificationCount < modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (expectedModificationCount > modificationCount) {
				throw new IllegalStateException();
			}

			if (current == null) { // na kraju inkrementiraj arrayIndex i
									// resetirati current
				incrementArrayIndex(); // dobio sam index punog slotha i
										// velicinu liste za taj sloth, CURRENT
										// mi je postavljen na prvi puni sloth
				return true; // automatski ako postoji novni puni sloth, to
								// znaci da postoji element
			}

			// provjeri ove uvjete, nesto sitno ne valja
			if (current.next != null) {
				current = current.next;
				return true;
			} else {// ako je current.next == null, reset currenta na pocetak
					// nove liste za sljedeci puni sloth
				current = null;
				arrayIndex++;
				return false;
			}

		}

		public void incrementArrayIndex() {

			for (int i = arrayIndex; i < size(); i++) {
				if (table[i] == null) {
					arrayIndex++;
					continue;
				}
				current = table[arrayIndex]; // prvi sljedeci puni sloth
				break;

			}

			return;
		}

		/**
		 * Prepostavka da se next() poziva nakon hasNext() tipa-->
		 * while(x.hasNext()) {x.next;}
		 */

		@Override
		public TableEntry<K, V> next() {

			if (hasNext()) {
				return current;
			}

			return null;
		}

		public void remove() {
			if (expectedModificationCount != modificationCount) {
				throw new IllegalStateException();
			}

			HashMap.this.remove(current.getKey());
			expectedModificationCount--;
		}

	}
}
