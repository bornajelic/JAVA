package pmf.custom.scripting.elems;

/**
 * Klasa predstavlja element double-a, atribut predstavlja vrijednost varijable tipa double;
 * @author Borna Jelic (barto je ime accounta na racunalu, ukoliko se ikad pojavi)
 *
 */
public class ElementConstantDouble extends Element{

	private double value;
	
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return String.valueOf(value);
	}
	
	
}
