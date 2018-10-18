package pmf.custom.scripting.parser;

import pmf.custom.scripting.elems.*;
import pmf.custom.scripting.nodes.*;
import pmf.custom.scripting.lexer.*;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Implementacija parsera
 * 
 * @author Borna Jelić
 * 
 */
public class Parser {

	Stack<Node> stack;

	ArrayList<Element> array;

	Lexer lexer;

	/**
	 * stablo koje predstavlja parsirani program (element prestavlja
	 * "root node")
	 */
	private DocumentNode documentNode;

	/**
	 * Konstruktor
	 * 
	 * @param token
	 *            token izvornog koda
	 * @throws ParserException
	 *             u slucaju pogreske pri parsiranju
	 * @throws LexerException
	 *             u slucaju pogreske pri tokenizaciji
	 */
	public Parser(String document) {
		if (document == null) {
			throw new ParserException();
		}

		lexer = new Lexer(document);
		this.documentNode = new DocumentNode(); // tu mi nesto nije jasno
		stack = new Stack<>();
		this.parse();
	}

	public Stack<Node> getStack() {
		return stack;
	}

	private void parse() {
		/*
		 * svaki puta kada se pojavi WORD ili ECHO, stvori tmp node i dodaj ga
		 * kao dijete ovoga koji je zadnji na stacku (stack.peek) ako dodje na
		 * for tag stvori novi node koji je djete zadnjem na staku i onda dodaj
		 * taj node na stak
		 */

		stack.push(documentNode); // ovo je kao root

		while (lexer.nextToken().getType() != TokenType.EOF) {

			Node tmp = stack.peek();
			// System.out.println(tmp.toString());

			if (lexer.getToken().getType() == TokenType.WORD) {

				Node text = new TextNode(lexer.getToken().getValue().toString());
				tmp.addChildNode(text);
				continue;
			}

			if (lexer.getToken().getType() == TokenType.OPEN_TAG) {

				lexer.nextToken(); // krecem na varijablu
				if (lexer.getToken().getType() == TokenType.FOR) {
					ForLoopNode forLoop = forParse();
					tmp.addChildNode(forLoop);
					stack.push(forLoop); // sada ce sva djeca biti od //
											// forLoop-nodea
					continue;
				}

				if (lexer.getToken().getType() == TokenType.END) {
					stack.pop();
					lexer.nextToken(); // close tag
					continue;

				}

				if (lexer.getToken().getType() == TokenType.ECHO) {

					Node echoNode = new EchoNode(echoMethod());
					tmp.addChildNode(echoNode);
					continue;
				}
			}

			if (lexer.getToken().getType() == TokenType.EOF) {
				break;
			}

		}

	}

	private ForLoopNode forParse() {

		ArrayList<Token> tokenList = new ArrayList<>();
		
		
		while (lexer.nextToken().getType() != TokenType.CLOSE_TAG) {
			tokenList.add(lexer.getToken()); 
		}

		if (tokenList.size() < 3 || tokenList.size() > 4) {
			throw new ParserException("Premalo elemenata u For petlji");
		}

		if (tokenList.get(0).getType() != TokenType.VARIABLE) {
			throw new ParserException("FOR LOOP VARIABLE");
		}
		ElementVariable var = new ElementVariable(tokenList.get(0).getValue()
				.toString());
		
		
		Element initNumber, limitNumber, stepNumber;
		
		
		
		if (tokenList.get(1).getType() == TokenType.INT) {
			initNumber = new ElementConstantInteger((int) tokenList.get(1)
					.getValue());
		} else if (tokenList.get(1).getType() == TokenType.DOUBLE) {
			initNumber = new ElementConstantDouble((double) tokenList.get(1)
					.getValue());
		} else {
			throw new ParserException("FOR LOOP INIT VARIABLE");
		}

		if (tokenList.get(2).getType() == TokenType.INT) {
			limitNumber = new ElementConstantInteger((int) tokenList.get(2)
					.getValue());
		} else if (tokenList.get(2).getType() == TokenType.DOUBLE) {
			limitNumber = new ElementConstantDouble((double) tokenList.get(2)
					.getValue());
		} else {
			throw new ParserException("FOR LOOP LIMIT VARIABLE");
		}

		if (tokenList.get(3).getType() == TokenType.INT) {
			stepNumber = new ElementConstantInteger((int) tokenList.get(3)
					.getValue());
		} else if (tokenList.get(3).getType() == TokenType.DOUBLE) {
			stepNumber = new ElementConstantDouble((double) tokenList.get(3)
					.getValue());
		} else {
			throw new ParserException("FOR LOOP STEP VARIABLE");
		}

		return new ForLoopNode(var, initNumber, limitNumber, stepNumber);
	}
	
	

	Element[] echoMethod() { // {$= i i * @sin "0.000" @decfmt $}

		array = new ArrayList<>();
		while (lexer.nextToken().getType() != TokenType.CLOSE_TAG) {
			Token t = lexer.getToken();
			switch (t.getType()) {
			case VARIABLE:
				array.add(new ElementVariable(t.ispisTokena()));
				break;
			case OPERATOR:
				array.add(new ElementOperator(t.ispisTokena()));
				break;
			case FUNCTION:
				array.add(new ElementFunction(t.ispisTokena()));
				break;
			case EL_STRING:
				array.add(new ElementString(t.ispisTokena()));
				break;
			case INT:
				array.add(new ElementConstantInteger((int) t.getValue()));
				break;
			case DOUBLE:
				array.add(new ElementConstantDouble((double) t.getValue()));
				break;
			default:
				throw new ParserException(
						"ne podrzan token u {$= segment $} segmentu");
			}
		}
		Element[] elements = new Element[array.size()];

		for (int i = 0; i < array.size(); i++) {
			elements[i] = array.get(i);
			// System.out.println(elements[i].toString());
		}

		return elements;

	}

	/**
	 * Metoda ide (Node) arraylisti u kojoj su spremljeni nodovi i prolazimo po
	 * toj listi
	 * 
	 * @param document
	 *            text koji je pokrenut u main metodi ili neki unos tipa String
	 */
	public void createOriginalBody(DocumentNode document) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < document.getSize()) {
			if (document.getChild(i) instanceof TextNode) {
				TextNode text = (TextNode) document.getChild(i);
				sb.append(text.getText() + " ");
				i++;
			} else if (document.getChild(i) instanceof ForLoopNode) {
				ForLoopNode forLoop = (ForLoopNode) document.getChild(i);
				sb.append(forPrint(forLoop));
				i++;
			} else if (document.getChild(i) instanceof EchoNode) {
				EchoNode echo = (EchoNode) document.getChild(i);
				sb.append(echoPrint(echo.getElements()));
				i++;
			} else {
				throw new ParserException(
						"Greska u createOriginalBody, ne postoje dobri Nodovi");
			}

		}

	}
	
	public DocumentNode getDocumentNode(){
		return this.documentNode;
	}
	

	/**
	 * tokeni su npr for,i,10,5,100, prepostavljam da su svi unutra
	 * 
	 * @return
	 */
	public String forPrint(ForLoopNode forLoop) {

		StringBuilder sb = new StringBuilder("FOR ( ");

		sb.append(forLoop.getVariable().asText() + " "
				+ forLoop.getStartExpression().asText() + " "
				+ forLoop.getStepExpression().asText() + " "
				+ forLoop.getEndExpression().asText() + ") "); // imam FOR( i

		return sb.toString();
	}

	/**
	 * saljem polje elements[] u kojemu se nalaze svi elementi echo-a, stavljam
	 * razmake izmedju svakog elementa
	 * 
	 * @param elements
	 * @return elementi iz elements[], ali poslije svakog elementa je dodan
	 *         razmak
	 */
	public String echoPrint(Element[] elements) {

		StringBuilder sb = new StringBuilder();
		for (Element value : elements) {
			sb.append(value).append(" ");
		}
		return sb.toString();
	}
}
