package pmf.custom.scripting.elems;


/**
 * Klasa predstavlja varijablu elementa, ima jedan atribut, a to je ime funkcije!
 * 
 * @author Borna Jelic
 *
 */
public class ElementVariable extends Element {

	private String name;
	
	/**
	 * Konstruktor
	 * @param name  ime funkcije
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	
	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return this.name;
	}

	
	
	
	
}
