package parser;

import java.util.ArrayList;
import java.util.List;

import Nodes.BinaryOperatorNode;
import Nodes.ConstantNode;
import Nodes.Node;
import Nodes.UnaryOperatorNode;
import Nodes.VariableNode;
import lexer.Lexer;
import lexer.LexerException;
import lexer.Token;
import lexer.TokenType;

public class Parser {

	Lexer lexer;

	Node expression;

	// (d or b) xor not (a or c)

	public Parser(String expression) {

		try {
			lexer = new Lexer(expression);
		} catch (LexerException e) {
			System.out.println(e.getStackTrace().toString());
		}

		try {
			lexer.nextToken(); // generated first token
			this.expression = s();

		} catch (LexerException | ParserException p) {
			System.out.println(p.getStackTrace().toString());
		}

	}

	/*
	 * Starting point of recursive descent, this method checks for the end of
	 * file and initiates the whole process
	 */
	private Node s() {

		if (lexer.getToken().getType() == TokenType.EOF) {
			throw new ParserException("NEki eof kurac na pocetku"); 
		}

		else
			return e1();
	}

	private Node e1() {

		List<Node> children = new ArrayList<Node>();
		children.add(e2());

		while (lexer.getToken().getType() != TokenType.EOF
				&& lexer.getToken().getValue().equals("or")) {
			lexer.nextToken();
			children.add(e2());
		}
		if (children.size() == 1) {
			return children.get(0);
		} else {
			return new BinaryOperatorNode("or", children, (a, b) -> a || b);
		}

	}

	private Node e2() {
		
		List<Node> children = new ArrayList<Node>();
		children.add(e3());

		while (lexer.getToken().getType() != TokenType.EOF
				&& lexer.getToken().getValue().equals("xor")) {
			lexer.nextToken();

			children.add(e3());
		}
		if (children.size() == 1) {
			return children.get(0);
		} else {
			return new BinaryOperatorNode("xor", children, (a, b) -> a ^ b);
		}

	}

	private Node e3() {

		List<Node> children = new ArrayList<Node>();
		children.add(e4());

		while (lexer.getToken().getType() != TokenType.EOF
				&& lexer.getToken().getValue().equals("and")) {
			lexer.nextToken();
			children.add(e4());
		}
		if (children.size() == 1) {
			return children.get(0);
		} else {
			return new BinaryOperatorNode("and", children, (a, b) -> a && b);
		}
	}

	private Node e4() {

		if (lexer.getToken().getType() != TokenType.EOF
				&& lexer.getToken().getValue().equals("not")) {
			lexer.nextToken();
			return new UnaryOperatorNode("not", e4(), a -> !a);
		} else {
			return e5();
		}

	}

	private Node e5() {

		if (lexer.getToken().getType() == TokenType.VARIABLE) {
			String name = lexer.getToken().getValue().toString();
			lexer.nextToken();
			return new VariableNode(name);
		} else if (lexer.getToken().getType() == TokenType.CONSTANT) {
			Token t = lexer.getToken();
			lexer.nextToken();
			if (t.getValue().equals(Boolean.TRUE)) {
				return new ConstantNode(true);
			} else {
				return new ConstantNode(false);
			}
		} else { // recursion
			if (lexer.getToken().getType() == TokenType.OPEN_BRACKET) {

				lexer.nextToken();

				Node tmp = s();

				if (lexer.getToken().getType() != TokenType.CLOSED_BRACKET) {
					throw new ParserException("Bracket problem!");
				} else {

					lexer.nextToken();

					return tmp;
				}

			} else {
				throw new ParserException("Parsing error in e5()!");
			}
		}
	}

	public Node getExpression() {
		return this.expression;
	}
}
