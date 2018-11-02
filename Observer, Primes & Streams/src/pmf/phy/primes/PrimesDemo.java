package pmf.phy.primes;

public class PrimesDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PrimesCollection pc = new PrimesCollection(5);
		for(Integer i : pc) {
			System.out.println(i);
		}
		
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}

}
