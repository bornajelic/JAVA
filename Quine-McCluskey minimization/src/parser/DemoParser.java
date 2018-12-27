package parser;

import java.util.Arrays;
import java.util.List;

import Nodes.Node;
import lexer.LexerException;
import utils.ExpressionEvaluator;
import utils.ExpressionTreePrinter;
import utils.Util;
import utils.VariablesGetter;

public class DemoParser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// these queries are valid
		String[] expressions = new String[] { "0", "tRue", "Not a", "A aNd b",
				"a or b", "a xoR b", "A and b * c", "a or b or c",
				"a xor b :+: c", "not not a", "a or b xor c and d",
				"a or b xor c or d", "a xor b or c xor d",
				"(a + b) xor (c or d)", "(d or b) xor not (a or c)",
				"(c or d) mor not (a or b)", "not a not b", "a and (b or d)",
				"a and (b or c)", "a and 1",
				"(false or b) xor not (a or c or h)" };

		for (String expr : expressions) {
			System.out.println("==================================");
			System.out.println("Izraz: " + expr);
			System.out.println("==================================");

			try {
				System.out.println("Tree:");
				Parser parser = new Parser(expr);
				parser.getExpression().accept(new ExpressionTreePrinter());
			} catch (ParserException ex) {
				System.out.println("Iznimka: " + ex.getClass() + " - "
						+ ex.getMessage());
			}
			System.out.println();
		}

		System.out.println("==========FINAL===============");

		System.out.println("\nVariables getter\n");

		String[] expressions2 = new String[] { "0", "tRue", "Not a", "A aNd b",
				"a or b", "a xoR b", "A and b * c", "a or b or c",
				"a xor b :+: c", "not not a", "a or b xor c and d",
				"a or b xor c or d", "a xor b or c xor d",
				"(a + b) xor (c or d)", "(d or b) xor not (a or c)",
				"(c or d) mor not (a or b)", "not a not b", "a and (b or g)",
				"a and (b or c)", "a and 1", "(a * b) xor not (d and c and g)" };

		for (String expr : expressions2) {
			System.out.println("==================================");
			System.out.println("Izraz: " + expr);
			System.out.println("==================================");

			try {
				System.out.println("Variables");
				Parser parser2 = new Parser(expr);
				VariablesGetter getter = new VariablesGetter();
				parser2.getExpression().accept(getter);
				System.out.println(getter.getVariable());
			} catch (ParserException | LexerException ex) {
				System.out.println("Iznimka: " + ex.getClass() + " - "
						+ ex.getMessage());
			}
			
		}
		 //-------------------------//
		
		System.out.println("ExpressionEvaluator\n");

		Node expression = new Parser("A and b or C").getExpression();

		VariablesGetter getter = new VariablesGetter();
		expression.accept(getter);

		List<String> variables = getter.getVariable();

		ExpressionEvaluator eval = new ExpressionEvaluator(variables);

		Util.forEach(variables, values -> {
			eval.setValues(values);
			expression.accept(eval);
			System.out.println(Arrays.toString(values).replaceAll("true", "1").replaceAll("false", "0") + " ==> "
					+ (eval.getResult() ? "1" : "0"));
		});
	
		
		System.out.println("\nSET\n");
		Node expression2 = new Parser("(A and b) or C").getExpression();

		VariablesGetter getter2 = new VariablesGetter();
		expression2.accept(getter2);

		List<String> variables2 = getter2.getVariable();
		for (boolean[] values : Util.filterAssignments(variables2, expression2, true)) {
			System.out.println(Arrays.toString(values).replaceAll("true", "1").replaceAll("false", "0"));
		}
		
	}

}
