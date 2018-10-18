package pmf.custom.scripting.elems;

/**
 * Klasa predstavlja element stringa, atribut je tipa string
 * @author barto
 *
 */
public class ElementString extends Element {

	private String value;
	
	public ElementString(String value) {
		this.value = value;
	}

	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return this.value;
	}
	
	
}
