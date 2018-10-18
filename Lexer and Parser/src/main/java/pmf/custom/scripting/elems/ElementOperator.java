package pmf.custom.scripting.elems;

public class ElementOperator extends Element {

	private String symbol;
	
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return this.symbol;
	}
	
	
}
