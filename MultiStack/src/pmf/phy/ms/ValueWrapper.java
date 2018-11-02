package pmf.phy.ms;


public class ValueWrapper {

	
	
	
	private Object initialValue;
	
	public ValueWrapper(Object initialValue) {
		this.initialValue = initialValue;
	}
	
	public Object getInitialValue() {
		return this.initialValue;
	}
	
	public void setInitialValue(Object o) {
		
			this.initialValue = o;
	}
	
	
	public void add(Object incValue) {
		
		
		Object argument = checkObject(incValue);
		this.initialValue = checkObject(this.initialValue);
		//System.out.println("Trenutna inicjalna vrijendnost " +getInitialValue());
		
		switch(checkOperation(argument, this.initialValue)) {
		
			case 1:
				this.setInitialValue((Integer)getInitialValue() + (Integer)argument);
				break;
			case 2:
				this.setInitialValue((Double)argument + new Double(((Integer)getInitialValue()).intValue()));
				break;
			case 3:
				this.setInitialValue((Double)getInitialValue() + new Double(((Integer)argument).intValue())); 
				break;
			case 4:
				this.setInitialValue((Double)getInitialValue() - (Double)argument);
				break;
			
			default:
				throw new RuntimeException();
		}
		
	}
	
	public void subtract(Object incValue) {
		Object argument = checkObject(incValue);
		this.initialValue = checkObject(this.initialValue);
		//System.out.println("Trenutna inicjalna vrijendnost " +getInitialValue());
		//System.out.println("Trenutni argument : " + argument);
		
		switch(checkOperation(argument, this.initialValue)) {
		
			case 1:
				this.setInitialValue((Integer)getInitialValue() - (Integer)argument);
				break;
			case 2:
				this.setInitialValue(new Double(((Integer)getInitialValue()).intValue()) -(Double)argument );
				break;
			case 3:
				this.setInitialValue((Double)getInitialValue() - new Double(((Integer)argument).intValue())); 
				break;
			case 4:
				this.setInitialValue((Double)getInitialValue() - (Double)argument); 
				break;
			
			default:
				throw new RuntimeException();
		}
		
	}
	
	public void multiply(Object incValue) {
		Object argument = checkObject(incValue);
		this.initialValue = checkObject(this.initialValue);
		//System.out.println("Trenutna inicjalna vrijendnost " +getInitialValue());
		//System.out.println("Trenutni argument : " + argument);
		
		switch(checkOperation(argument, this.initialValue)) {
		
			case 1:
				this.setInitialValue((Integer)getInitialValue() * (Integer)argument);
				break;
			case 2:
				this.setInitialValue(new Double(((Integer)getInitialValue()).intValue()) * (Double)argument );
				break;
			case 3:
				this.setInitialValue((Double)getInitialValue() * new Double(((Integer)argument).intValue())); 
				break;
			case 4:
				this.setInitialValue((Double)getInitialValue() * (Double)argument);
				break;
			
			default:
				throw new RuntimeException();
		}
	}
	
	public void divide(Object incValue) {
		
		Object argument = checkObject(incValue);
		this.initialValue = checkObject(this.initialValue);
		//System.out.println("Trenutna inicjalna vrijendnost " +getInitialValue());
		//System.out.println("Trenutni argument : " + argument);
		
		switch(checkOperation(argument, this.initialValue)) {
		
			case 1:
				this.setInitialValue((Integer)getInitialValue() / (Integer)argument);
				break;
			case 2:
				this.setInitialValue(new Double(((Integer)getInitialValue()).intValue()) / (Double)argument );
				break;
			case 3:
				this.setInitialValue((Double)getInitialValue() / new Double(((Integer)argument).intValue())); 
				break;
			case 4:
				this.setInitialValue((Double)getInitialValue() / (Double)argument);
				break;
			
			default:
				throw new RuntimeException();
		}
	}
	
	private Object checkObject (Object o){
		if(o instanceof Double) {
			return o;
		}
		else if(o == null) {
			o = Integer.valueOf(0);
			return o;
		}
		else if(o instanceof String) {
			//tu pretvori string u int ili double
			
			switch(stringExamination(o.toString())) {
			
			case 1:
				o = Integer.valueOf(o.toString());
				return o;
			case 2:
				o = Double.valueOf(o.toString());
				return o;
			default:
				throw new RuntimeException();
			}
			
		}
		else if(o instanceof Integer) {
			return o;
		}
		else throw new RuntimeException();
	}
	
	
	private int checkOperation(Object o1, Object o2) { // prije ovoga moram provjeriti jeli string
		
		
		
		if(o1 instanceof Integer && o2 instanceof Integer) {
			
			return 1; //vraca se Integer
		}
		else if(o1 instanceof Double && o2 instanceof Integer) {
		
			return 2;//vraca se Double
		}
		else if (o1 instanceof Integer && o2 instanceof Double){
			return 3;
		}
		else if(o1 instanceof Double && o2 instanceof Double) {
			return 4;
		}
		else {
			throw new RuntimeException();
		}
	}
	

	
	public static int stringExamination(String s) {
		int startCounter = 0;
		char[] array = s.toCharArray();
		
		if(array[0] == '-' || array[0] == '+') {
			startCounter++;
		}
		int decimalCounter = 0;	
		int expCounter = 0;
		
		for(int i = startCounter; i <array.length ; i++) {
			//System.out.println("Provjeravam  " +array[i]);
			if(Character.isDigit(array[i])) {
				continue;
			}
			else if(array[i] == '.') {
				decimalCounter++;
				if(decimalCounter > 1) {
					//System.out.println("NIJE VALJAN");
					throw new RuntimeException();
				}
				continue;
			}
			else if(Character.isLetter(array[i])) {
				//System.out.println("Ovaj obradjujem " +array[i]);
				if(array[i] == 'E') {
					expCounter++;
					if(expCounter > 1) {
					//¸¸	System.out.println("NIJE VALJAN");
						throw new RuntimeException();
					}
					continue;
				}
				throw new RuntimeException();
			}
			else
				throw new RuntimeException();
		}
		
		if(decimalCounter == 1) {
			return 2;
		}
		else {
			return 1;
		}
	}
	
	public int numCompare(Object withValue) {
		
		Object argument = checkObject(withValue);
		this.initialValue = checkObject(this.initialValue);
		
		if(this.initialValue == null && argument == null) {
			return 0;
		}
		
		switch(checkOperation(argument, this.initialValue)) {
		
		case 1:
			System.out.println("Tu sam");
			this.setInitialValue((Integer)getInitialValue() - (Integer)argument);
			if(getInitialValue().equals(0)) {
				return 0;
			}
			if((int)getInitialValue() > 1) {
				return 1;
			}
			if((int)getInitialValue() < 1) {
				return -1;
			}
			break;
		case 2:
			this.setInitialValue(new Double(((Integer)getInitialValue()).intValue()) -(Double)argument );
			if(getInitialValue().equals(0)) {
				return 0;
			}
			if((int)getInitialValue() > 1) {
				return 1;
			}
			if((int)getInitialValue() < 1) {
				return -1;
			}
			break;
		case 3:
			this.setInitialValue((Double)getInitialValue() - new Double(((Integer)argument).intValue())); 
			if((double)getInitialValue() == 0) {
				return 0;
			}
			if((double)getInitialValue() > 1) {
				return 1;
			}
			if((double)getInitialValue() < 1) {
				return -1;
			}
			break;
		case 4:
			if((double)getInitialValue() == 0) {
				return 0;
			}
			if((double)getInitialValue() > 1) {
				return 1;
			}
			if((double)getInitialValue() < 1) {
				return -1;
			}
			this.setInitialValue((Double)getInitialValue() - (Double)argument); 
			break;
		
		default:
			throw new RuntimeException();
	}
		
		return 0;
		
	}
	
	public int checkIfNull(Object o1, Object o2) {
		if(o1 == null && o2 != null){
			return 1;
		}
		else if(o1 != null && o2 == null) {
			return 2;
		}
		else if(o1 == null && o2 == null) {
			return 3;
		}
		else
			throw new RuntimeException();
	}

	
	
	public static void main(String[] args) {
		//null, String, Integer, Double
	/*	ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getInitialValue());
		System.out.println(v1.getInitialValue());
		System.out.println(v2.getInitialValue());
		
	
	
		
		
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getInitialValue());
		System.out.println(v5.getInitialValue());
		System.out.println(v6.getInitialValue());
		
		ValueWrapper v3 = new ValueWrapper("1");
		ValueWrapper v4 = new ValueWrapper(Double.valueOf(1.2));
		v3.add(v4.getInitialValue());
		System.out.println(v3.getInitialValue());
		System.out.println(v4.getInitialValue());
		
		
		ValueWrapper v7 = new ValueWrapper("1.3E2");
		ValueWrapper v8 = new ValueWrapper(Double.valueOf(5.2));
		v7.subtract(v8.getInitialValue());
		System.out.println(v7.getInitialValue());
		System.out.println(v8.getInitialValue());
		
		ValueWrapper v9 = new ValueWrapper("1.3E2");
		ValueWrapper v10 = new ValueWrapper(Double.valueOf(5.2));
		v9.multiply(v10.getInitialValue());
		System.out.println(v9.getInitialValue());
		System.out.println(v10.getInitialValue());
		
		ValueWrapper v11 = new ValueWrapper("12");
		ValueWrapper v12 = new ValueWrapper(Integer.valueOf(2));
		v11.divide(v12.getInitialValue());
		System.out.println(v11.getInitialValue());
		System.out.println(v12.getInitialValue());
		
		
		ValueWrapper v13 = new ValueWrapper("12.0");
		ValueWrapper v14 = new ValueWrapper(Integer.valueOf(12));
		
		System.out.println(v13.numCompare(v14.getInitialValue()));*/
	}
}
