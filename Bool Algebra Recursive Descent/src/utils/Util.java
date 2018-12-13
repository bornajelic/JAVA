package utils;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import Nodes.Node;

public class Util {

	/*
	 * method creates a boolean table of thruth
	 */

	public static void forEach(List<String> variables,
			Consumer<boolean[]> consumer) {

		int numOfCombinations = (int) Math.pow(2, variables.size());

		for (int i = 0; i < numOfCombinations; i++) {
			boolean[] aCombination = row(addPadding(Integer.toBinaryString(i)));
			consumer.accept(aCombination);
		}
	}

	/*
	 * Method creates all variable combinations, using ExpressionEvaluator to
	 * calculate the value of a function and if this value matches the value
	 * passed as third argument it adds this combination in Set of combinations
	 */

	public static Set<boolean[]> filterAssignments(List<String> variables,
			Node expression, boolean expressionValue) {
		Set<boolean[]> set = new LinkedHashSet<boolean[]>();
		ExpressionEvaluator eval = new ExpressionEvaluator(variables);

		forEach(variables, values -> {
			eval.setValues(values);
			expression.accept(eval);
			if (eval.getResult() == expressionValue) {
				set.add(values);
			}
		});
		return set;
	}

	public static Set<Integer> toSumOfMinterms(List<String> variables, Node expression) {
		return ProductORSum(variables, expression, true);
	}
	
	public static Set<Integer> toSumOfMaxterms(List<String> variables, Node expression) {
		return ProductORSum(variables, expression, false);
	}
	
	private static Set<Integer> ProductORSum(List<String> variables,Node expression,boolean expressionValue) {
		
		Set<Integer> positions = new LinkedHashSet<Integer>();
		
		Set<boolean[]> set = filterAssignments(variables, expression, expressionValue);
		
		Iterator <boolean[]> it = set.iterator();
		
		while(it.hasNext()) {
			positions.add(booleanArrayToInt(it.next()));
		}
		return positions;
	}
	
	/*
	 * Method returns a position of a given value in a logical table
	 */
	public static int booleanArrayToInt(boolean[] values) { 
		
		int pos = 0;
		for (int i = 0; i < values.length; i++) {
			if(values[i]){
				pos += Math.pow(2, values.length - 1 - i);
			}
				
		}
		
		return pos;
	}
	
	/*
	 * Helper method for forEach-method it adds padding to binary numbers
	 */
	public static String addPadding(String str) {

		StringBuilder sb = new StringBuilder();
		if (str.length() == 3) {
			return str;
		} else {
			int i = str.length();

			while (i < 3) {
				sb.append(0);
				i++;
			}
			sb.append(str);
		}
		return sb.toString();
	}

	/*
	 * helper method for forEach-method returns a boolean array
	 */
	public static boolean[] row(String s) {
		boolean[] bArray = new boolean[s.length()];
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++) {
			switch (c[i]) {
			case '0':
				bArray[i] = false;
				break;
			case '1':
				bArray[i] = true;
				break;
			}
		}

		return bArray;
	}
}
