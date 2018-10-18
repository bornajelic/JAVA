package pmf.custom.scripting.elems;

/**
 * Klasa predstavlja element integera, atribut predstavlja vrijednost integera 
 * 
 * @author Borna Jelic
 *
 */
public class ElementConstantInteger extends Element{

	private int value;
	
	/**
	 * Osnovni konstruktor
	 * @param value vrijednost elementa
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return String.valueOf(value);
	}
	
	
}
