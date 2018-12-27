package lexer;

public class DemoLexer {

	public static void main(String[] args) {
	
		
		String[] expressions = new String[] { "0", "tRue", "Not a", "A aNd b", "a or b", "a xoR b", "A and b * c",
				"a or b or c", "a xor b :+: c", "not not a", "a or b xor c and d", "a or b xor c or d",
				"a xor b or c xor d", "(a + b) xor (c or d)", "(d or b) xor not (a or c)", "(c or d) mor not (a or b)",
				"not a not b", "a and (b or", "a and (b or c", "(a and re)", "(false or b) xor not (a or c)" };
		
		for (String expr : expressions) {
			System.out.println("==================================");
			System.out.println("Izraz: " + expr);
			System.out.println("==================================");
		
			try {
				System.out.println("Tokenizacija");
				Lexer lexer = new Lexer(expr);
				while(lexer.nextToken().getType() != TokenType.EOF) {
					System.out.println(lexer.getToken().ispisTokena());
				}
				lexer.nextToken();
				System.out.println(lexer.getToken().ispisTokena());
			}catch(LexerException e) {
				
			}
			
		}
	}
}
