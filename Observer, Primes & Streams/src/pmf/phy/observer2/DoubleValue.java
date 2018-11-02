package pmf.phy.observer2;

public class DoubleValue implements IntegerStorageObserver{
	
	private int n;
	private int counter;

	public DoubleValue(int n) {
		this.n = n;
		counter = 0;
	}
	
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.printf("%d < %d\n", counter,n);
		if(counter < n){
			
			System.out.println("Double value: " + istorage.getNewValue() * 2);
			counter++;
		}
		
	}
	
	public int getCounter() {
		return this.counter;
	}
	
	public int getN() {
		return this.n;
	}
	

}
