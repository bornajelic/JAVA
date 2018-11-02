package pmf.phy.observer;

public class SquareValue implements IntegerStorageObserver {

	
	@Override
	public void valueChanged(IntegerStorage istorage) {
		//tu se ta vrijednost kvadrira
		System.out.println("Provided new value : " + istorage.getValue() + " ,square is " + istorage.getValue()*istorage.getValue());
		
		
	}

}
