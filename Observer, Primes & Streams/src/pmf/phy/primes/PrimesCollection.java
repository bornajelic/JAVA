package pmf.phy.primes;

import java.util.Iterator;


public class PrimesCollection implements Iterable<Integer> {
	
	
	private int limit;
	
	public PrimesCollection(int limit) {
		this.limit = limit;
	}
	
	public int getLimit() {
		return this.limit;
	}

	@Override
	public Iterator<Integer> iterator() {
		// TODO Auto-generated method stub
		return new IteratorPrime();
	}
		
	private class IteratorPrime implements Iterator<Integer> {
		
		private int currentPrime = 2;
		private int number = 2;
		private int primeCounter = 1;
		@Override
		
		public boolean hasNext() {
			if(this.primeCounter <= limit) {
				return true;
			}
			else 
				return false;
		}

		@Override
		public Integer next() {
			
			
			while(primeCounter <= limit) {
				System.out.println("\nPOKUSAJ -> " + primeCounter + "\n");
				prime();
				break;
			}
			
			return currentPrime;
		}
		
	private void prime() {
		
		for(int i = this.number ; i > 0; i++){
			System.out.println("Testiram za " + number);
			int brojac = 0;
			for(int j = 2 ; j < i ; j++) {
				if(i % j == 0) {
					brojac++;
					break;
				}
				
			}
			if(brojac == 0) {// i je prime, vrati ga
				System.out.println("Nasao sam prime  " + i);
				this.currentPrime = i;
				this.primeCounter++;
				
				
				this.number++;
				return;
			}

			
			
			System.out.println("nisam nasao\n");
			this.number++;
		
			
		}
		return;
	}
	
		

}
}
