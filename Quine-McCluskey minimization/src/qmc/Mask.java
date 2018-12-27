package qmc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import utils.Util;

public class Mask {
	
	//mask is an octet array
	
	boolean dontCare; 
	
	byte[] values;
	
	private Set<Integer> indexes; //indexes of minterms
	
	private int hashValue;
	
	private boolean combined;
	
	/*
	 * First constructor takes ordinal number of minterm, no. of variables
	 * in function, and is minterm normal or dont care
	 */
	
	public Mask(int index, int numberOfVariables, boolean dontCare) {
		
		if(numberOfVariables < 1 || index < 0) {
			throw new IllegalArgumentException("numberOfVariables < 1 || index < 0 ");
		}
		//created a set and values
		this.indexes = new TreeSet<Integer>();
		this.indexes.add(index);
		this.values = Util.indexToByteArray(index, numberOfVariables);
		
		this.dontCare = dontCare;
		
		this.indexes = Collections.unmodifiableSet(this.indexes);
		this.hashValue = Arrays.hashCode(this.values);
	}
	
	
	/*
	 * second constructor explicitly accepts a mask( etc.. A'B'CD - > 0011 ),
	 * set of indexes -> ordinal numbers of minterms and flag(dont care)
	 */
	
	public Mask(byte[] values, Set<Integer> indexes, boolean dontCare) {
		
		if(indexes == null || indexes.isEmpty() || values == null) {
			throw new IllegalArgumentException("Null || empty set! "); 
		}
		
		this.indexes = new TreeSet<Integer>();
		this.indexes = indexes;
		this.indexes = Collections.unmodifiableSet(this.indexes);
		this.values = values;
		this.dontCare = dontCare;
		this.hashValue = Arrays.hashCode(this.values);
		
	}
	
	/*
	 * Checks if product is used with another product for creation
	 * of a third product
	 */
	public boolean isCombined() {
		return combined;
	}
	
	public void setCombined(boolean combined) {
		this.combined = combined;
	}
	
	
	public boolean isDontCare() {
		return  this.dontCare;
	}
	
	public Set<Integer> getIndexes() {
		return this.indexes;
	}
	
	
	
	public int countOfOnes() {
		int counter = 0;
		for(int i = 0; i < this.values.length; i++) {
			if(this.values[i] == 1) {
				counter++;
			}
		}
		return counter;
	}
	
	public int size() {
		return this.values.length;
	}
	
	
	public byte getValueAt(int position) {
		if(position < 0 || position > this.size() -1) {
			throw new IndexOutOfBoundsException("Get valueAt error");
		}
		return this.values[position];
	}
	
	/*
	 * original 2101 , other 2001
	 * final 2201
	 */
	public Optional<Mask> combineWith(Mask other) {
		
		if(other.equals(null) || other.size() != this.size()) {
			throw new IllegalArgumentException("error in combineWith method");
		}
		if(isCombinable(other)) {
			//create new value of the two
			byte[] tmp = Arrays.copyOf(values, values.length);
			for(int i = 0; i < tmp.length; i++) {
				if(tmp[i] != other.getValueAt(i)) {
					tmp[i] = 2;
				}
			}
		/*
		 * create a new set, add all elements of original set
		 * and add all elemenets from other seet	
		 */
		Set<Integer> combinedSet = new TreeSet<Integer>();
		combinedSet.addAll(this.indexes);//sada imam set svih prije i kombinacija
		combinedSet.addAll(other.getIndexes());
		
		boolean combinedDontCare = this.dontCare && other.isDontCare();
		this.combined = true;
		other.setCombined(true);
		
		return Optional.of(new Mask(tmp,combinedSet,combinedDontCare));
		}
		return Optional.empty();
	}

	/*
	 * helper method to check if another mask is combinable with
	 * original mask
	 * returns true if difference is <= 1 in positions
	 * 2012 - 2011 is combinable, only one position is different
	 */
	private boolean isCombinable(Mask other) {
		int counter = 0;
		for(int i = 0; i < this.values.length; i++) {
			if(other.getValueAt(i) != values[i]) {
				counter++;
				if(counter > 1) {
					return false;
				}
			}
		}
		return true;
	}


	@Override
	public String toString() {
	//	System.out.println("KOMBINIRANA ? " + isCombined());
		StringBuilder sb = new StringBuilder();
		for(byte b : values) {
		
			if(b == 2) {
				sb.append("-");
			}else {
				sb.append(b);
			}
		}
		sb.append(" ");
		sb.append(isDontCare() ? "D " : ". ");
		sb.append(isCombined() ? "*" : " ");
		sb.append(Arrays.toString(indexes.toArray()));
		
		
		return sb.toString();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hashValue;
		result = prime * result + Arrays.hashCode(values);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mask other = (Mask) obj;
		if (hashValue != other.hashValue)
			return false;
		if (!Arrays.equals(values, other.values))
			return false;
		return true;
	}


	
	
}
