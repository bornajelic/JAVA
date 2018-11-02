package pmf.phy.observer2;

public final class IntegerStorageChange {

	/*
	 * read-only properties are 
	 * assigned only once - on the initialization of the instance.
	 */
	
	private IntegerStorage istorage;
	
	private int valueBeforeChange;
	
	private int newValue;
	
	

	public IntegerStorageChange(IntegerStorage istorage, int valueBeforeChange,
			int newValue) {
		super();
		this.istorage = istorage;
		this.valueBeforeChange = valueBeforeChange;
		this.newValue = newValue;
	}

	public int getValueBeforeChange() {
		return this.valueBeforeChange;
	}
	
	public int getNewValue() {
		return this.newValue;
	}
	
	public IntegerStorage getIntegerStorage() {
		return this.istorage;
	}
	
}
