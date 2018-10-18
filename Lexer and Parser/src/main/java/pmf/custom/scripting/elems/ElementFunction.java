package pmf.custom.scripting.elems;

public class ElementFunction extends Element {

	private String name;
	
	public ElementFunction(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	
}
