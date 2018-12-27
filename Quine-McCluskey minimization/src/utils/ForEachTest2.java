package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import Nodes.Node;
import parser.Parser;

public class ForEachTest2 {

	public static void main(String[] args) {
		
		System.out.println("Testing arrayToInt\n");
		System.out.println(Util.booleanArrayToInt(new boolean[]{false,false,false ,false}));
		System.out.println(Util.booleanArrayToInt(new boolean[]{false,false,false,true}));
		System.out.println(Util.booleanArrayToInt(new boolean[]{false,false,true,false}));
		System.out.println(Util.booleanArrayToInt(new boolean[]{false,false,true,true}));
		System.out.println(Util.booleanArrayToInt(new boolean[]{false,true,false,false}));
		System.out.println(Util.booleanArrayToInt(new boolean[]{false,true,false,true}));
		
		
		Node expression = new Parser("A and b or C").getExpression();

		VariablesGetter getter = new VariablesGetter();
		expression.accept(getter);

		List<String> variables = getter.getVariable();
		System.out.println("Mintermi f(" + variables + "): " + Util.toSumOfMinterms(variables, expression));

		List<String> variables2 = new ArrayList<>(variables);
		Collections.reverse(variables2);
		System.out.println("Mintermi f(" + variables2 + "): " + Util.toSumOfMinterms(variables2, expression));
		
		System.out.println("\nTesting indexToByteArray");
		
		System.out.println(Arrays.toString(Util.indexToByteArray(3, 2)));
		System.out.println(Arrays.toString(Util.indexToByteArray(15, 4)));
		System.out.println(Arrays.toString(Util.indexToByteArray(3, 4)));
		System.out.println(Arrays.toString(Util.indexToByteArray(3, 6)));
		System.out.println(Arrays.toString(Util.indexToByteArray(-2, 16)));
		System.out.println(Arrays.toString(Util.indexToByteArray(-2, 32)));
		System.out.println(Arrays.toString(Util.indexToByteArray(19, 4)));
	
	
		
		
	}

}
