package lexer;

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
	
	public String toString(){
		return getValue().toString();
	}
	//custom toString, printing paris of type - value
	public String ispisTokena() {
		return getType().name() + ", " + (getValue() != null ? getValue().toString() : "NULL");
	}

}
