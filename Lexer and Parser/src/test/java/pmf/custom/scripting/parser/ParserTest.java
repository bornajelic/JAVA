package pmf.custom.scripting.parser;

import static org.junit.Assert.*;

import org.junit.Test;


public class ParserTest {
	


	@Test(expected = ParserException.class)
	public void testNullInput() {
		Parser parser = new Parser(null);
		
	}
	
	public void Print() {
		Parser parser = new Parser("Jebo sam ti mamu {$ FOR i 1 2 3 $} {$END$}");
		
	}
	
	
}
