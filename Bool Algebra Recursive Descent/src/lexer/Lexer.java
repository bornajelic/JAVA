package lexer;


public class Lexer {

	private char[] data;

	private Token token;

	private int currentIndex;

	public Lexer(String text) {
		if (text == null) {
			throw new LexerException("Need text do process!");
		}
		this.data = text.toCharArray();
		this.currentIndex = 0;

	}

	public Token nextToken() {

		skipBlanks();
		
		if (currentIndex >= data.length || data.equals("")) {
			token = new Token(TokenType.EOF, null);

		}

		// indetifikator NOT
		else if (currentIndex < data.length && Character.isLetter(data[currentIndex])) { // N check
			
			StringBuilder sb = new StringBuilder();

			while (currentIndex < data.length
					&& (Character.isLetter(data[currentIndex])
							|| Character.isDigit(data[currentIndex]) || data[currentIndex] == '_')) {
				sb.append(data[currentIndex++]); 

			}

			token = checkIndetificator(sb.toString());
		}
		
		else if(currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			StringBuilder sb = new StringBuilder();
			
			while(currentIndex < data.length && (Character.isDigit(data[currentIndex]))){
				sb.append(data[currentIndex++]);
			}
			token = checkNumerical(sb.toString());
		}
		
		else if (currentIndex < data.length && data[currentIndex] == '(') {

			token = new Token(TokenType.OPEN_BRACKET, data[currentIndex++]);
		}

		else if (currentIndex < data.length && data[currentIndex] == ')') {
			token = new Token(TokenType.CLOSED_BRACKET, data[currentIndex++]);
		}
		
		else if( currentIndex < data.length  && (data[currentIndex] == '*' || data[currentIndex] == '+' || data[currentIndex] == '!')) {
			StringBuilder sb = new StringBuilder();
			sb.append(data[currentIndex++]);
			token = checkSymbol(sb.toString());
		}
		
		else if(currentIndex < data.length && data[currentIndex] == ':') {
			StringBuilder sb = new StringBuilder();
			sb.append(data[currentIndex++]);
			sb.append(data[currentIndex++]);
			sb.append(data[currentIndex++]);
			
			if(!(sb.toString().equals(":+:"))) {
				throw new LexerException("Ater : expected +:");
			} else {
				token = new Token(TokenType.OPERATOR, "xor");
			}
		}
		
		else {
			throw new LexerException("Error!");
		}
		

		return token;

	}

	public Token getToken() {
		return this.token;
	}

	private void skipBlanks() {

		while (currentIndex < data.length) {
			char c = data[this.currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				this.currentIndex++;
				continue;
			}
			break;
		}
	}

	private Token checkIndetificator(String str) {

		String s = str.toLowerCase();

		if (s.equals("and") || s.equals("xor") || s.equals("or")
				|| s.equals("not")) {

			return new Token(TokenType.OPERATOR, s);

		} else if (s.equals("true")) {
			return new Token(TokenType.CONSTANT, Boolean.TRUE);

		} else if (s.equals("false")) {
			return new Token(TokenType.CONSTANT, Boolean.FALSE);

		} else {
			return new Token(TokenType.VARIABLE, s.toUpperCase());
		}

	}

	private Token checkNumerical(String str) {
		if (str.equals("1")) {
			return new Token(TokenType.CONSTANT, Boolean.TRUE);

		} else if (str.equals("0")) {
			return new Token(TokenType.CONSTANT, Boolean.FALSE);

		} else {
			throw new LexerException("Numerical series can only be 0 or 1");
		}
	}

	private Token checkSymbol(String str) {
		
		switch (str) {

		case "*":
			token = new Token(TokenType.OPERATOR, "and");
			break;
		case "+":
			token = new Token(TokenType.OPERATOR, "or");
			break;
		case "!":
			token = new Token(TokenType.OPERATOR, "not");
			break;
			
		default:
			throw new LexerException("Invalid symbol, expected *,!,+,:+:");
		}

		return token;
	}
}
