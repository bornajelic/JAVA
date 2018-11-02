package pmf.phy.observer;

public class ChangeCounter implements IntegerStorageObserver{

	int counter;
	
	public ChangeCounter() {
		counter = 0;
	}
	
	public int getCounter() {
		return this.counter;
	}
	
	
	@Override
	public void valueChanged(IntegerStorage istorage) {
		this.counter++;
		System.out.println("Number of values changed since tracking : " + this.getCounter());
	}
	

}
