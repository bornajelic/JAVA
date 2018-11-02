package pmf.phy.db;

public class FieldValueGetters {
	
	//record je instanca jednog studenta sa njegovim atrubutima

	public static final IFieldValueGetter FIRST_NAME = (record) -> record.getFirstName();
	
	public static final IFieldValueGetter LAST_NAME = (record) -> record.getLastName(); 
	
	public static final IFieldValueGetter JMBAG = (record) -> record.getJmbag();
	
	
	
	
	
	public static void main(String[] args) {
		StudentRecord record = new StudentRecord("0035177046", "Jelic", "Borna", 5);
		System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
		System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
		System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));
		
		
	}
	
	
}
