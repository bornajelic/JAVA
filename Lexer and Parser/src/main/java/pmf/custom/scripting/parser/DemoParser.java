package pmf.custom.scripting.parser;

import java.util.Stack;

import pmf.custom.scripting.lexer.Lexer;
import pmf.custom.scripting.lexer.TokenType;
import pmf.custom.scripting.nodes.DocumentNode;
import pmf.custom.scripting.nodes.Node;

public class DemoParser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String docBody = "JA sam borna 23 {$ FOR i 1 10.24 1 $} This is {$= i i @sin $} th {$ END $} borna";
		Parser parser = new Parser(docBody);
		Stack<Node> stack = parser.getStack();
		
		
		while(!stack.empty()){
			Node node = stack.peek();
			
			System.out.println(node.toString());
			for(Node n : node.getChildren()){
				System.out.println(n.toString());
			}
			
			stack.pop();
		}
		
		
	}
	
}
