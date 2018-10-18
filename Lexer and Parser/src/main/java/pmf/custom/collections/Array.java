package pmf.custom.collections;

/**
 * Implementacija ArrayList
 * @author Borna Jelić PMF
 */



public class Array extends Collection {

	private int size; //no. of elements stored
	private int capacity; //koliko elemenata je moguce pohraniti
	private Object [] elements;

	
	/**
	 * Defaultni konstruktor, postavlja default kapacitet na 16;
	 */
	
	
	public Array() {
		
		this(16);
	}
	
	/**
	 * Delegacija od konsturktora(collection,initialCapacity)
	 * @param other
	 */
	
	public Array(Collection other) {
		
		this(other,16);
	}
	
	
	/**
	 * Konstruktor postavlja odredjeni kapacitet te alocira polje tipa Object sa zadanim kapacitetom
	 * @param initialCapacity
	 */
	
	
	public Array(int initialCapacity) {
		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Kapacitet mora biti > 1");
		}
		
		size = 0;
		capacity = initialCapacity;
		elements = new Object[capacity];
		
		
	}
	
	/**
	 * Konstruktor prima neku kolekciju te zadani kapacitet
	 * Isto tako mijenja velicinu te zadane kolekcije ukoliko je to potrebno
	 * 
	 * @param other 
	 * @param initialCapacity
	 */
	
	public Array(Collection other, int initialCapacity) {
		
		
		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Kapacitet mora biti > 1");
		}
		
		size = 0;
		capacity = initialCapacity;
		elements = new Object[capacity];
		
		
		if(other.size() <= capacity) {
			capacity = 2*capacity;
		}
		
		addAll(other);
	}
	
	
	//======= METODE ========//
	
	/**
	 * Metoda provjerava jeli potrebno povecati polje ukoliko je puno,
	 * te stavlja zadani objekt na zadnje mjesto u polju
	 */
	
	public void add(Object value) {
		
		if(value == null) {
			throw new IllegalArgumentException("Nisu dozvoljene null vrijednosti!");
		}
		
		if(this.size == this.capacity) {
			
			Object novi[] = new Object[2*(size)];
			for(int i = 0; i < this.size ; i++) {
				novi[i] = elements[i];
			}
			
			elements = novi;
			capacity = size*2;
			
		}
		
			elements[size] = value;
			this.size++;
	
	
	}
	
	/**
	 * Metoda vraca vrijednost koja se nalazi na odredjenoj poziciji
	 * @param index
	 * @return vrijednost na datoj poziciji
	 */
	
	
	public Object get(int index) {
		
		if(index > size-1 || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		return elements[index];
		
	}
	
	/**
	 * Metoda brise sve elemente iz kolekcije tako da sve upotpuni
	 * null vrijednostima za koje ce se pobrinuti garbage collector
	 * Postavlja se broj elemenata u listi na 0
	 * 
	 */
	
	void clear() {
		
		for(int i = 0 ; i < size ; i ++) {
			elements[i] = null;
		}
		this.size = 0;
	}
	
	
	/**
	 * Metoda dodaje vrijednost na ZADANO mjesto u kolekciji , prije 
	 * dodavanja, poziva metodu resizeIfFull, ne dopusta null vrijednosti
	 * @param value
	 * @param position
	 */
	
	void insert(Object value, int position) {
		
		if(this.size == this.capacity) {
			
			Object novi[] = new Object[2*(size)];
			for(int i = 0; i < this.size ; i++) {
				novi[i] = elements[i];
			}
			
			elements = novi;
			capacity = size*2;
			
		}
		
		if(position < 0 || position > size-1) {
			throw new IndexOutOfBoundsException();
		}
		if(value == null) {
			throw new IllegalArgumentException();
		}
		
		
		
		
		
		for(int i = size-1; i >= position; i--) {
			elements[i+1] = elements[i];
		}
		
		elements[position] = value;
		this.size++;
		
			}
	
	/**
	 * Metoda vraca poziciju u kolekciji na kojoj se nalazi data vrijednost
	 * @param value
	 * @return pozicija u listi ako postoji, -1 ako ne postoji
	 */
	
	int indexOf(Object value) {
		
		for (int i = 0; i < size-1 ; i++) {
			if(elements[i].equals(value)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Metoda brise element iz kolekcije na zadanoj poziciji
	 * @param index 
	 */
	
	void remove(int index) {
		
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		
		for(int i = index+1; i <= size-1 ; i++) {
			elements[i-1] = elements[i];
		}
		elements[size-1] = null;
		size--;
		
		return;
	}
	
	/**
	 * Metoda provjerava jeli polje prazno
	 */

	boolean isEmpty() {
		if(this.size == 0) 
			return true;
		else
			return false;
	}
	
	
	/**
	 * Metoda vraca broj elemenata spremljenih u kolekciji
	 */
	
	public int size() {
		
		return size;
	}
	
	/**
	 * Metoda vraca boolean  sadrzi li kolecija zadanu vrijednost
	 */

	boolean contains(Object value) {
		
		for (int i = 0; i < size-1; i++) {
			if(elements[i].equals(value))
				return true;
		}
		return false;
	}
	
	
	/**
	 * Ukoliko postoji zadana vrijednost u kolekciji, brise ju
	 * @param value
	 * @return true ako je nadjena zadana vrijednost i brise ju,
	 * false ako nije nadjena zadana vrijednost
	 */
	
	public boolean remove(Object value) {
		if (this.contains(value)) {
			this.remove(this.indexOf(value));
			return true;
		}
		return false;
	}
	
	
	/**
	 * Metoda alocira polje objekata sa velicinom zadane kolekcije te ju
	 * puni elementima, nikad ne vraca null
	 * @return Object Array od zadane kolekcije
	 */
	
	Object[] toArray() {
		
		if(size == 0) {
			throw new UnsupportedOperationException();
		}
		Object novi[] = new Object[this.size];
		for(int i = 0; i < this.size-1; i ++) {
			novi[i] = elements[i];
			
		}
		
		return novi;
	}
	
	/**
	 * Metoda zove process medotu iz nadklase Process za svaki
	 * element u kolekciji
	 */

	void forEach(Processor processor) {
		
		for(int i = 0; i < size-1; i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * Metoda iz podklase Collection
	 */
	void addAll(Collection other) {
		// TODO Auto-generated method stub
		super.addAll(other);
	}
	
	void print() {
		int i = 0;
		while(i <= this.size) {
			System.out.println("Element " + i + ":" + elements[i]);
			i++;
		}
	}
	
}
