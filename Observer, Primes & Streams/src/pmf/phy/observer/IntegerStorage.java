package pmf.phy.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IntegerStorage {

	private int value;
	
	private int size;
	
	
	public IntegerStorage(int value) {
		this.value = value;
		this.size = 0;
	}
	
	private List<IntegerStorageObserver> observers = new ArrayList<>();
	
	

	
	public void addObserver(IntegerStorageObserver observer) {
		if(!(observers.contains(observer))){
			observers.add(observer);
			size++;
		}
		else {
			System.out.println("Observer vec postoji");
		}
	}
	
	public void removeObserver(IntegerStorageObserver observer) {
		if(observers.contains(observer)){
			//dohvati index di se taj objekt nalazi
			int i = 0;
			for(IntegerStorageObserver o : observers) {
				if(o.equals(observer)) {
					break;
				}
				i++;
			}
			observers.remove(i);
			size--;
			
		}
		else {
			System.out.println("Ne postoji observer u listi");
		}
	}
	
	public void clearObservers() {
			observers.clear();
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		//samo ako je nova vrijednost drugacija od trenutne
		if(this.value != value) {
			this.value = value;
		}
		Iterator<IntegerStorageObserver> it = observers.iterator();
		
		while(it.hasNext()) {
			IntegerStorageObserver o = it.next();
			if(o instanceof DoubleValue) {
				if(((DoubleValue) o).getN() == ((DoubleValue) o).getCounter()) {
					it.remove();
					this.size--;
				}
			}
			o.valueChanged(this);
		}
	
	}
	
	public int getSize() {
		return size;
	}
		
	public boolean contains(IntegerStorageObserver observer) {
		if(observers.contains(observer)) {
			return true;
		}
		
		return false;
	}


	
}
