package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Nodes.Node;
import parser.Parser;

public class ForEachTest2 {

	public static void main(String[] args) {
		
		Node expression = new Parser("A and b or C").getExpression();

		VariablesGetter getter = new VariablesGetter();
		expression.accept(getter);

		List<String> variables = getter.getVariable();
		System.out.println("Mintermi f(" + variables + "): " + Util.toSumOfMinterms(variables, expression));

		List<String> variables2 = new ArrayList<>(variables);
		Collections.reverse(variables2);
		System.out.println("Mintermi f(" + variables2 + "): " + Util.toSumOfMinterms(variables2, expression));

		System.out.println(Util.booleanArrayToInt(new boolean[]{false,false,true,true}));
	}

}
