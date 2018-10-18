package pmf.lexerAndParser;

public class Lexer {

	private char[] data; //ulazni tekst
 	private Token token; // trenutni token
 	
 	private int currentIndex; // index prvog neobradjenog znaka
 	private LexerState state;
 	
 	/**
 	 * Konstruktor; ne prihvaca null vrijednosti, pretvara string u charArray,inicijalizira LexerState na Basic;
 	 *  poziva metodu nextToken();
 	 * @param text String koji prestavlja unos
 	 */
 	
 	public Lexer(String text) {
 		
 		if(text == null) {
 			throw new IllegalArgumentException();
 		}
 		this.data = text.toCharArray();
 		this.currentIndex = 0;
 		state = LexerState.BASIC;
 		//nextToken();
 	
 	}
 	
 	/**
 	 * Ukoliko postoji, vraća sljedeći token
 	 * @return token
 	 */
 	
 	public Token nextToken() {
 		
 		
 		//ovo se samo prvi puta pokrece
 		if (token != null && token.getValue() != null){
 			//ako je prije utvrdjen kraj, ponovni poziv metode je greska
 	 		if(token.getType() == TokenType.EOF) {
 	 			throw new LexerException("ne smije biti null!");
 	 		}
	 		if(token.getValue().equals('#')) {
	 			switchState();
	 		}
 		}
 		
 		skipBlanks();
 		//ako vise nema znakova u polju(tekstu), kraj programa
 		if(currentIndex >= data.length || data.equals("")) {
 			token = new Token(TokenType.EOF, null);
 			return token;
 		}
 		
 		
 		if(this.state == LexerState.BASIC) {
 			
 			//ako je char znak,trazi rijec u stringu
 	 		if(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\' ) {
 	 		
 	 			StringBuilder sb = new StringBuilder(); 
 	 			while(currentIndex < data.length && ( Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {
 	 				
 	 					//provjera jeli dobar escape sequence .. nije mi se dalo raditi posebnu funkciju ksad sam vec napisao..ali bila bi tipa boolean
 	 					if(data[currentIndex] == '\\') { 
 	 						if (currentIndex +1 >= data.length){ //rubni uvjet za kraj stringa
 	 							token = new Token(TokenType.SYMBOL, "/");
 	 							return token;
 	 						}else{
 	 							if ((Character.isDigit(data[currentIndex+1]) || data[currentIndex +1] == '\\')){
 	 								sb.append(data[currentIndex+1]);
 	 	 							currentIndex +=2;
 	 							}else if (Character.isLetter(data[currentIndex+1])){ //pravilan escape
 	 								char c = data[currentIndex+1];
						 			if(!(c  == 't' ||c  == 'r' ||c  == 'n')){
						 				throw new LexerException("Invalid escape! Letter " + c);
						 			}
 	 							}
 	 						}			
 	 					}else {
	 							sb.append(data[currentIndex]);
	 							currentIndex++;
	 					}
 	 						
 	 					
 	 				
 	 			}
 	 			String povrat = sb.toString();
 	 			token = new Token(TokenType.WORD, povrat);
 	 		
 	 		}
 	 		//pohranio sam BROJ
 	 		else if(Character.isDigit(data[currentIndex])) {
 	 			StringBuilder sb = new StringBuilder();
 	 			
 	 			while(currentIndex < data.length && Character.isDigit(data[currentIndex])) {
 	 				sb.append(data[currentIndex]);
 	 				currentIndex++;
 	 			}
 	 			String broj = sb.toString();
 	 			try{
 	 				long l = Long.parseLong(broj);
 	 				token = new Token(TokenType.NUMBER, l);
 	 			}
 	 			catch (NumberFormatException e) {
 	 				throw new LexerException("broj nije pohranjiv u tip LONG");
 	 			}
 	 		}
 	 		
 	 		//ako nije rijec ili broj, ostaje simbol
 	 		else {
 	 			char c = data[currentIndex];
 	 			token = new Token(TokenType.SYMBOL, c);
 	 			currentIndex++;
 	 		}
 		}
 		// ako je EXTENDED gledaj sve kao rijec
 		else {
 			StringBuilder sb = new StringBuilder();
 			
 			while(currentIndex < data.length && data[currentIndex] != ' ') { // ulaz = "a23/bsser4 ".. idi do razmaka i uzmi to kao WORD
 				sb.append(data[currentIndex]);
 				currentIndex++;
 			}
 			String izlaz = sb.toString();
 			token = new Token(TokenType.WORD, izlaz);
 		}
 		
 		
 		return token;
 	}
 	
 	
 	/**
 	 * Metoda vraca token;
 	 * @return token
 	 */
 	public Token getToken() {
 		return this.token;
 	}


 	 /**
 	  * Metoda preskače praznine u tekstu, traje sve dok postoje uzastopne praznie
 	  */
 	public void skipBlanks() {
 		
 		while(currentIndex < data.length) {
 			char c = data[currentIndex];
 			if( c  == ' ' || c  == '\t' ||c  == '\r' ||c  == '\n') {
 				currentIndex++;
 				continue;
 			}
 			break;
 		}
 
 	}
 	
 	/**
 	 * Metoda postavlja način rada Lexera,inkrementira currentIndex, ukoliko je unos LexerState.BASIC, vraća LexerState.EXTENDED i obrnuto
 	 * @param state LexerState.BASIC ili LexerState.EXTENDED
 	 */
 	public void setState(LexerState state) {
 		if (state == null) throw new IllegalArgumentException();
 		this.state = state;
 	}
 	
 	private void switchState(){
 		if(state == LexerState.BASIC) {
 			state = LexerState.EXTENDED;
 		}
 		else {
 			state = LexerState.BASIC;
 		}
 		
 	}
}

