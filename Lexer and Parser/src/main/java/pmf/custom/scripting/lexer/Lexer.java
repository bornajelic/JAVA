package pmf.custom.scripting.lexer;

public class Lexer {

	private char[] data;
	private Token token;

	private int currentIndex;
	public LexerState state;
	

	/**
	 * Konstruktor; ne prihvaca null vrijednosti, pretvara string u
	 * charArray,inicijalizira LexerState na Basic; poziva metodu nextToken();
	 * 
	 * LexerState.BASIC se postavlja kada se cita obican tekst; Kada dodjemo do
	 * specijalnog znaka $, sljedi promjena u EXTENDED
	 * 
	 * @param text
	 *            String koji prestavlja unos
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
		
	}
	
	
	public Token nextToken() {
		
		
		skipBlanks();
		
		if (currentIndex >= data.length || data.equals("")) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		
		if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {// {$ ili {$=
			
			switchState(); // bio je BASIC, SADA JE EXTENDED

			StringBuilder sb_openTAG = new StringBuilder();
			sb_openTAG.append(data[currentIndex++]);
			sb_openTAG.append(data[currentIndex++]); 

			token = new Token(TokenType.OPEN_TAG, sb_openTAG.toString());
			return token;

		}	

		/*
		 * ako se vec pojavio ulazni TAG {$, MOZE se i zatvoriti
		 */

		if (data[currentIndex] == '$'
				&& data[currentIndex + 1] == '}') {

			StringBuilder sb_closeTAG = new StringBuilder();
			sb_closeTAG.append(data[currentIndex++]);
			sb_closeTAG.append(data[currentIndex++]);
		
			switchState(); // izasli smo iz TAG-a, sad prelazimo u
							// LexerState.BASIC
			token = new Token(TokenType.CLOSE_TAG, sb_closeTAG.toString());
			return token;
		}

		

		if (this.state == LexerState.BASIC) { // tu citam text obicni

			StringBuilder sb = new StringBuilder();
	
			while (currentIndex < data.length && data[currentIndex] != '{') {
				
				if(data[currentIndex] == '\\') {
					switch(data[currentIndex + 1]) {
					case '\\' :
						sb.append('\\');
						break;
					case '{':
						sb.append('{');
						break;
					default:
						throw new LexerException("Escape in string!");
						
					}
				}
				else {
					sb.append(data[currentIndex]);
					currentIndex++;
				}
	
				}
			
			token = new Token(TokenType.WORD, sb.toString());
			return token;
		}

		/*
		 * tu ulazim u tag , obradjujem IF(
		 * FUNCTION,VARIABLE,OPERATOR,INTEGER,DOUBLE I STRING --->
		 * LexerState.EXTENDED) else FOR;END
		 */
		else {
			
			if (Character.isLetter(data[currentIndex])){
				StringBuilder word = new StringBuilder();
				while(data[currentIndex] != ' '){
					if(data[currentIndex] == '\\') {
						word.append(data[currentIndex + 1]);
						currentIndex += 2;
					}else {
						word.append(data[currentIndex++]);
					}
					
				}
				token = getWordType(word.toString());
				return token;
			}
			
			if (data[currentIndex] == '@'){
				currentIndex++;
				if (currentIndex >= data.length || !Character.isLetter(data[currentIndex])) throw new LexerException("FUNCTION NAME");
				StringBuilder fname = new StringBuilder();
				fname.append(data[currentIndex++]);
				while(data[currentIndex] != ' '){
					fname.append(data[currentIndex++]);
				}
				token = new Token(TokenType.FUNCTION, fname.toString());
				return token;
			}
			
			if (data[currentIndex] == '+' || data[currentIndex] == '-'
					|| data[currentIndex] == '/' || data[currentIndex] == '*'
					|| data[currentIndex] == '^') {
				token = new Token(TokenType.OPERATOR, data[currentIndex++]);
				return token;
			}
			
			if (data[currentIndex] == '='){
				token = new Token(TokenType.ECHO, data[currentIndex++]);
				return token;
			}
			
	
			// int ili double

			if (Character.isDigit(data[currentIndex])) {
				StringBuilder sb = new StringBuilder();
				while (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') {
					sb.append(data[currentIndex++]);
				}
				String ulaz = sb.toString();
				try {
					if (ulaz.contains(".")) { // dec broj
						double d = Double.parseDouble(ulaz);
						token = new Token(TokenType.DOUBLE, d);
					} else {
						int i = Integer.parseInt(ulaz);
						token = new Token(TokenType.INT, i);
					}
					return token;
				} catch (NumberFormatException e) {
					throw new LexerException(e.getMessage());
				}

			}

			// string
			if (data[currentIndex] == '"') {
				currentIndex++;
				StringBuilder sb = new StringBuilder();

				while (currentIndex < data.length && data[currentIndex] != '"') {
					sb.append(data[currentIndex++]);
				}
				// \"Joe \\\"Long\\\" Smith\"  --> "Joe \"Long\" Smith"
				token = new Token(TokenType.EL_STRING, stringEscape(sb.toString()));
				return token;
				
				/*
				 * ako je prvi znak\ idi na sljedece slovo, 
				 */
			}
			throw new LexerException("INVALID");
		}
	}
	
	
	
	/**
	 * Metoda provjerava jeli zadani string jednak FOR,ECHO,END, ako nije onda je VARIAble
	 * @param str
	 * @return token
	 */
	private Token getWordType(String str){
		
		if (str.toUpperCase().equals("FOR")){
			return new Token(TokenType.FOR, "FOR");
		}
		
		
		if (str.toUpperCase().equals("END")){
			
			return new Token(TokenType.END, "END");
		}
	
		return new Token(TokenType.VARIABLE, str.toString());
	}

	/**
	 * Pomocna metoda koja rijesava uvjet escape-anja
	 * @param str
	 * @return string sa rijesenim escape-anjem
	 */
	
	private String stringEscape(String str) {
		StringBuilder sb = new StringBuilder();
		char out[] = str.toCharArray();
		int i = 0;
		
		while(out[i] != ' ') {
			if(out[i] == '\\') {
				sb.append(out[i + 1]);
				i += 2;
			}else {
				sb.append(out[i++]);
			}
		}
		
		return sb.toString();
	}
	/**
	 * Metoda vraca token;
	 * 
	 * @return token
	 */
	public Token getToken() {
		return this.token;
	}
	/**
	 * Metoda mijenja stanje BASIC <--> EXTENDED kada got se pozove
	 */

	private void switchState() {
		if (this.state == LexerState.BASIC) {
			this.state = LexerState.EXTENDED;
		} else {
			this.state = LexerState.BASIC;
		}
	}

	/**
	 * Metoda preskace praznine sve dok ne dodjemo do slova/broja
	 */
	private void skipBlanks() {

		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}
	}


}
