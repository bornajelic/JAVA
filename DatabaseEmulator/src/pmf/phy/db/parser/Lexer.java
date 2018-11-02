package pmf.phy.db.parser;

public class Lexer {

	
	private char[] data;
	
	private Token token;
	
	private int currentIndex;
	
	
	
	public Lexer(String text){
		
		if(text == null) {
			throw new LexerException();
		}
		this.data = text.toCharArray();
		this.currentIndex = 0;
	}
	
	public Token nextToken() {
		
		skipBlanks();
		
		if(currentIndex >= data.length || data.equals("")) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		if(data[currentIndex] == '"') {
			StringBuilder sb = new StringBuilder();
			currentIndex++; //sad sam na prvom znaku poslije "
			while(currentIndex < data.length && data[currentIndex] != '"') {
				sb.append(data[currentIndex++]);
			}
			currentIndex++;
			token = new Token(TokenType.STR_LITERAL, sb.toString());
			return token;
		}
		
		if(Character.isLetter(data[currentIndex])) {
			StringBuilder sb = new StringBuilder();
			while((Character.isLetter(data[currentIndex]))  && currentIndex <  data.length) {//rubni uvjet za kraj upita ne valja
				sb.append(data[currentIndex++]);
			}
		
			token = checkWord(sb.toString());
			return token;
		
		}
		
		if(data[currentIndex] == '<' || data[currentIndex] == '>' || data[currentIndex] == '=' || data[currentIndex] == '!'){
			StringBuilder sb = new StringBuilder();
			
			while(currentIndex < data.length && !(Character.isLetter(data[currentIndex])) && !(Character.isDigit(data[currentIndex])) && data[currentIndex] != ' ' && data[currentIndex] != '"') {
				sb.append(data[currentIndex++]);
				
			}
			token = checkOperator(sb.toString());
			return token;
			
			
		}
		
	
		
		throw new LexerException();
		
	}
	private Token checkOperator(String s) {
		switch(s) {
		case "<":
			token = new Token(TokenType.OPERATOR, s);
			break;
		case ">":
			token = new Token(TokenType.OPERATOR, s);
			break;
		case "<=":
			token = new Token(TokenType.OPERATOR, s);
			break;
		case ">=":
			token = new Token(TokenType.OPERATOR, s);
			break;
		case "!=":
			token = new Token(TokenType.OPERATOR, s);
			break;
		case "=":
			token = new Token(TokenType.OPERATOR, s);
			break;
		default:
			throw new ParserException("Nedopusten operator");
			
		}
		return token;
	}
	
	private Token checkWord(String s) {

		switch(s.toUpperCase()) {
		case "AND" :
			token = new Token(TokenType.AND, s);
			break;
		case "JMBAG" :
			token = new Token(TokenType.KEYWORD, s);
			break;
		case "FIRSTNAME" :
			token = new Token(TokenType.KEYWORD, s);
			break;
		case "LASTNAME" :
			token = new Token(TokenType.KEYWORD, s);
			break;
		case "FINALGRADE" :
			token = new Token(TokenType.KEYWORD, s);
			break;
		case "QUERY" :
			token = new Token(TokenType.KEYWORD, s);
			break;
		case "LIKE" :
			token = new Token(TokenType.OPERATOR, s);
			break;
		default: 
			throw new LexerException("Samo and, JMBAG,FIRSTNAME,LASTNAME,FINALGRADE dozvoljeni entry-ji!");
		}
		return token;
	}
	
	
	public Token getToken(){
		return this.token;
	}
	
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
				currentIndex++;
				continue;
			}
			break;
		}

	}
	
	public static void main(String[] args) {
		//Lexer lexer = new Lexer("QUERY jmbag and \"upit\" > AND like \"upit\" <"); //moras imati space na kraju?? -> samo kod rijeci
		Lexer lexer = new Lexer("  query     firstName>=\"A\"   and firstName != \"C\" and lastName LIKE \"B*ć\" and jmbag > \"0000000002\"");
		//Lexer lexer = new Lexer ("jmbag>\"upit\"");
		//Lexer lexer = new Lexer("query jmbag = \"0123456789\" and ");
		
		while(lexer.nextToken().getType() != TokenType.EOF) {
			System.out.println(lexer.getToken().toString());

		}
		System.out.println(lexer.getToken().toString());
			
	}
}
