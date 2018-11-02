package pmf.phy.db.parser;

import java.util.ArrayList;
import java.util.List;

import pmf.phy.db.ComparisonOperators;
import pmf.phy.db.ConditionalExpression;
import pmf.phy.db.FieldValueGetters;
import pmf.phy.db.IComparisonOperator;
import pmf.phy.db.IFieldValueGetter;

public class Parser {

	ArrayList<ConditionalExpression> array; // kad se pojavi and, ubacujem sve
											// prije

	Lexer lexer;

	public Parser(String document) {
		if (document == null) {
			throw new ParserException();
		}
		lexer = new Lexer(document);
		array = new ArrayList<ConditionalExpression>();
		this.parse();
	}

	public void parse() {
		
		Token tmp = lexer.nextToken();
		if(!(tmp.ispisTokena().toUpperCase().equals("QUERY"))) {
			throw new ParserException();
			
		}else {
			while (lexer.nextToken().getType() != TokenType.EOF) {
				
				if (lexer.getToken().getType() == TokenType.KEYWORD) {
					
					ConditionalExpression expression = exprParse();
					array.add(expression); // ili je sljedeci kraj ili end
					continue;
					
				} else if (lexer.getToken().getType() == TokenType.AND) {
					continue;
				} else {
					throw new ParserException();
				}

			}
		}
		
	}



	private ConditionalExpression exprParse() {
		ArrayList<Token> tokenList = new ArrayList<Token>();

		IFieldValueGetter field;
		String str;
		IComparisonOperator operator;

		short counter = 0;
		while (counter < 3) {
			tokenList.add(lexer.getToken());
			lexer.nextToken();
			counter++;
		}

		if (tokenList.size() < 3 || tokenList.size() > 4) {
			throw new ParserException("Netocan broj elemenata za expression");
		}

		if (tokenList.get(0).getType() == TokenType.KEYWORD) {

			switch (tokenList.get(0).ispisTokena().toUpperCase()) {
			case "JMBAG":
				field = FieldValueGetters.JMBAG;
				break;
			case "FIRSTNAME":
				field = FieldValueGetters.FIRST_NAME;
				break;
			case "LASTNAME":
				field = FieldValueGetters.LAST_NAME;
				break;
			default:
				throw new ParserException("Greska u FieldValueGetterima");
			}
		} else {
			throw new ParserException("Prvi element condEXPR mora biti keyword");
		}

		// System.out.println(tokenList.get(1).ispisTokena());
		if (tokenList.get(1).getType() == TokenType.OPERATOR) {

			switch (tokenList.get(1).ispisTokena().toUpperCase()) {
			case "<":
				operator = ComparisonOperators.LESS;
				break;
			case ">":
				operator = ComparisonOperators.GREATER;
				break;
			case "<=":
				operator = ComparisonOperators.LESS_OR_EQUALS;
				break;
			case ">=":
				operator = ComparisonOperators.GREATER_OR_EQUALS;
				break;
			case "!=":
				operator = ComparisonOperators.NOT_EQUALS;
				break;
			case "=":
				operator = ComparisonOperators.EQUALS;
				break;
			case "LIKE":
				operator = ComparisonOperators.LIKE;
				break;
			default:
				throw new ParserException("Krivi comparisonOperatori");
			}

		} else {
			throw new ParserException(
					"Drugi element condEXPR mora biti operators");
		}

		if (tokenList.get(2).getType() == TokenType.STR_LITERAL) {
			str = tokenList.get(2).ispisTokena(); //
		} else {
			throw new ParserException(
					"Treci element condEXPR mora biti strLiteral");
		}

		ConditionalExpression expr = new ConditionalExpression(field, str,
				operator);

		
		   short j = 0; while (j < 3) {
		  System.out.println(tokenList.get(j).toString()); j++;
		  }
		 
		 
		 
		

		return expr;
	}
	
	public String getQueriedJMBAG() {
		if (isDirectQuery()) {
			String s = array.get(0).getStrLiteral();
			return s;
		} else {
			throw new IllegalStateException();
		}
	}

	public List<ConditionalExpression> getQuery() {

		return array;
	}

	public boolean isDirectQuery() {
		if (this.array.size() == 1) {
			ConditionalExpression cExpr = array.get(0);
			
			return cExpr.getField().equals(FieldValueGetters.JMBAG) && cExpr.getOperator().equals(ComparisonOperators.EQUALS);
		}
		return false;
	}

	/*
	public static void main(String[] args) {
		System.out.println("mejn\n");
		Parser qp1 = new Parser(" query jmbag = \"0032\" ");
		System.out.println();
		Parser qp3 = new Parser("query lastname = \"000002332\" ");
		System.out.println();
		Parser p = new Parser("  query     firstName>=\"A\"   and firstName != \"C\" and lastName LIKE \"B*ć\" and jmbag > \"0000000002\"");
		
		
	System.out.println("isDirectQuery(): " + qp1.isDirectQuery()); 
		System.out.println("qp3 isDirectQuery(): " + qp3.isDirectQuery()); 
		System.out.println("jmbag was: " + qp1.getQueriedJMBAG()); 
		System.out.println("size: " + p.getQuery().size());
		
	}*/
}
