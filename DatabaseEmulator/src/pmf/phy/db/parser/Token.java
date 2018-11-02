package pmf.phy.db.parser;

public class Token {

	private Object value;
	
	private TokenType tokenType;
	
	
	public Token(TokenType type, Object value) {
		
		this.value = value;
		this.tokenType = type;
	}
	
	public Object getValue() {
		return this.value;
	}
	
	public TokenType getType() {
		return this.tokenType;
	}
	
	public String toString() {//za test
		return getType().name() + ", " + (getValue() != null ? getValue().toString() : "NULL" );
	}
	
	
	public String ispisTokena() {
		return getValue().toString();
	}
	
}
