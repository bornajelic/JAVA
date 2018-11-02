package pmf.phy.observer2;

public class SquareValue implements IntegerStorageObserver {

	
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		//tu se ta vrijednost kvadrira
		System.out.println("Provided new value : " + istorage.getNewValue() + " ,square is " + istorage.getNewValue()*istorage.getNewValue());
		
		
	}

}
