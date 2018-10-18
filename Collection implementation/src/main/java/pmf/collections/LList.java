package pmf.collections;

/**
 * Implementacija vezane liste
 * 
 * @author Borna Jelic PMF
 *
 */

public class LList extends Collection{

	private static class ListNode {
		
		ListNode previous;
		ListNode next;
		Object value;
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	/*
	 * Konstruktori
	 * */
	
	/**
	 * Default konstruktor stvara praznu kolekciju
	 */
	
	public LList() {
		
		first = null;
		last = null;
		size = 0;
		
	}
	
	public LList(Collection other) {
		
		first = null;
		last = null;
		size = 0;
		addAll(other);
	}
	
	
	
	//metod
	
	
	void add(Object value) { //end
		
		ListNode newNode = new ListNode();
		newNode.previous = null;
		newNode.next = null;
		newNode.value = value;
		
		
		
		if (first == null) { //ako je prazna
			first = newNode;
			last = first;
			this.size++;
		}
		else {
			
			newNode.previous = last;
			last.next = newNode;
			last = newNode;
			this.size++;
		}
		
	}
	
	Object get(int index) {
		
		if(index <0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode tmp = first;
		int i = 0;
		
		while(i < index) {
			tmp = tmp.next;
			i++;
		}
		return tmp.value;
		
	}
	
	void clear() {
		
		ListNode tmp = first;
		
		while(tmp.next != null) {
			
			tmp = tmp.next;
			tmp.value = null;
			
		}
		first = null;
	}
	
	void insert(Object value, int position) {
		
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode newNode = new ListNode();
		newNode.previous = null;
		newNode.next = null;
		newNode.value = value;
		
		ListNode tmp = last;
		ListNode tmp2;
		
		
			for(int i = size; i > position+1; i--) {
				tmp = tmp.previous;
			}
			
			  tmp2 = tmp.previous;
			  tmp2.next = newNode;
			  newNode.previous = tmp2;
			  newNode.next = tmp;
			  tmp.previous = newNode;
		
			size++;
		
		
		
	}
	
	
	
	int indexOf(Object value) {
		
		ListNode tmp = first;
		int i = 0;
		
		while(tmp.next != null) {
			if(tmp.value == value) {
				break;
			}
			else {
				i++;
			}
		}
		return i;
	}
	
	void remove(int index) {
		
		if(index < 0  || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		ListNode tmp = first;
		ListNode tmp2;
		ListNode tmp3;
		//ako zelim prvi izbrisati
		for(int i = 0; i <= index; i++) {
			tmp = tmp.next;
			this.size--;
		}
		if(tmp.previous == null) {
			first = first.next;
			first.previous = null;
		}
		else if( tmp.next == null) {
			last = last.previous;
			last.next = null;
			size--;
		}
		else {
			tmp2 = tmp.previous;
			tmp3 = tmp.next;
			
			tmp2.next = tmp3;
			tmp3.previous = tmp2;
			size--;
			tmp = null;
			
		}
		
		
		
		
	}

	boolean isEmpty() {
		if(first == null) {
			return true;
		}
		else
			return false;
	}

	int size() {
		
	
		return this.size;
	}
	
	

	boolean contains(Object value) {
		
		
		ListNode tmp = first;
		while(tmp.next != null) {
			if(tmp.value.equals(value)) {
				return true;
			}
			tmp = tmp.next;
		}
		return false;
	}
	
	

	Object[] toArray() {
		Object novi[] = new Object[this.size];
		ListNode tmp = first;
		for(int i = 0; i < size-1; i++) {
			novi[i] = tmp.value;
			tmp = tmp.next;
		}
		return novi;
	}
	
	

	void forEach(Processor processor) {
		
		ListNode tmp = first;
		for(int i = 0; i < size-1; i++) {
			processor.process(tmp.value);
			tmp=tmp.next;
		}
		
	}

	void addAll(Collection other) {
		
		super.addAll(other);
	}
	
	
	
	void printList() {
		
		ListNode tmp = first;
		while(tmp != null) {
			
			String s = "value = " + tmp.value + " , ";
			s += "previous = ";
			
			if (tmp.previous != null) s += tmp.previous.value;
			s += " , next = ";
			
			if (tmp.next != null) s += tmp.next.value;
			
			System.out.println(s);
			tmp = tmp.next;
		}
	}
}
