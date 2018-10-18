package pmf.custom.scripting.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

public class LexerTest {

	////@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");
		assertNotNull("TOken was expected but null was returned",lexer.nextToken());
	}
	
	////@Test(expected = IllegalArgumentException.class) 
	public void testNullInput() {
		//must throw
		
		new Lexer(null);
	}
	
	////@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");

		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}

	////@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must
		// return each time what nextToken returned...
		Lexer lexer = new Lexer("");

		Token token = lexer.nextToken();
		//System.out.println(token.toString());
		Token token1 = lexer.getToken();
		//System.out.println(token1.toString());
		Token token2 = lexer.getToken();
		//System.out.println(token2.toString());
		assertEquals("getToken returned different token than nextToken.", token, token1);
		assertEquals("getToken returned different token than nextToken.", token, token2);
	}
	
	////@Test(expected = LexerException.class) //treba biti plavi, ovo prolazi
	public void testReadAferEOF() {
		Lexer lexer = new Lexer("");
		lexer.nextToken();
		//System.out.println(lexer.getToken());
		lexer.nextToken();
		//System.out.println(lexer.getToken());

	}
	
	//////@Test
	public void testNoActualContent() {
		Lexer lexer = new Lexer("    \r\n\t   ");
		Token t = lexer.nextToken();
		System.out.println(t.toString());
		assertEquals("Input had no content. Lexer should generated only EOF token.", TokenType.EOF,
				t.getType());
	}
	
	
	
	////@Test(expected = LexerException.class)
	public void testInvalidEscape() {
		Lexer lexer = new Lexer("   \\a    ");

		// will throw!
		lexer.nextToken();
	}

	
	
	
	
	@Test
	public void noviTest(){
		Lexer lexer = new Lexer("JA sam borna 23 {$ FOR i 1 10.24 1 $} This is {$= i $} th ");
		while(lexer.nextToken().getType() != TokenType.EOF){
			System.out.println(lexer.getToken().toString());
			//if (lexer.getToken().getType() == TokenType.OPEN_TAG) lexer.state = LexerState.EXTENDED;
			//if (lexer.getToken().getType() == TokenType.CLOSE_TAG) lexer.state = LexerState.BASIC;
		}
	}
	
	
	
	//@Test
	public void proba(){ 
		
		String str = "\\borna";
		char[] b = str.toCharArray();
		for(int i = 0; i < b.length; i++) System.out.println(b[i]);
	}
	
	
}