package pmf.custom.scripting.lexer;



public class Token {

	
	private Object value; //vrijednost tokena
	
	private TokenType tokenType; // vrsta tokena 
	
	/**
	 * Konstruktor
	 * @param type vrsta tokena
	 * @param value vrijednost tokena
	 */
	public Token(TokenType type, Object value) {
			
		
		this.value = value;
		this.tokenType = type;
		
	}
	
	/**
	 * Vraca vrijednost tokena
	 * @return vrijednost tokena
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Vraca tip tokena
	 * @return tip tokena
	 */
	public TokenType getType() {
		return this.tokenType;
	}
	
	public String toString(){
		return getType().name() + ", " + (getValue() != null ? getValue().toString() : "NULL");
	}
	
	public String ispisTokena() {
		return getValue().toString();
	}
	
	
}
