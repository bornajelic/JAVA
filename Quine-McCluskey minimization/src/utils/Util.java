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

	/*
	 * returns a row number in table for boolean[] values
	 */

	public static int booleanArrayToInt(boolean[] values) {
		int number = 0;
		int potention = 0;
		for (int i = values.length - 1; i >= 0; i--) {
			if (values[i]) {
				number += Math.pow(2, potention);
			}
			potention++;
		}
		return number;
	}

	/*
	 * returns minterms
	 */
	public static Set<Integer> toSumOfMinterms(List<String> variables,
			Node expression) {
		return mintermOrMaxterm(variables, expression, true);
		
	}

	/*
	 * return maxterms
	 */

	public static Set<Integer> toProductOfMaxterms(List<String> variables,
			Node expression) {
		return mintermOrMaxterm(variables, expression, false);
	}

	/*
	 * helper method for toSumOfMinterms || toSumOfMaxterms
	 */

	public static Set<Integer> mintermOrMaxterm(List<String> variables,
			Node expression, boolean expressionValue) {

		Set<boolean[]> tmp = filterAssignments(variables, expression,
				expressionValue);

		Set<Integer> set = new LinkedHashSet<Integer>();

		Iterator<boolean[]> it = tmp.iterator();

		while (it.hasNext()) {
			set.add(booleanArrayToInt(it.next()));
		}

		return set;
	}

	public static byte[] indexToByteArray(int x, int n) {

		boolean positive = true;
		byte[] array = new byte[n];
		int position = n - 1;

		if (x < 0) {
			positive = false;
			x = -x;
		}

		while (x !=0) { // || position >= 0
			if (position < 0) {
				return array;
			}
			array[position--] = (byte) (x % 2);
			x = x / 2;
			
			
		}

		if (position != 0) {
			// add padding
			int i = 0;
			while (i < position) {
				array[i++] = 0;
			}
		} // stvorim array

		if (positive) {
			return array;
		} else {

			byte[] complement = new byte[n];
			for (int j = 0; j < array.length; j++) {
				complement[j] = (byte) (array[j] == 0 ? 1 : 0);
			}

			byte[] one = new byte[complement.length];
			one[complement.length - 1] = 1;
			byte[] result = addBinary(complement, one);
			return result;
		}

	}

	/*
	 * helper method for indexToByteArray, adds two binary numbers represented
	 * as byte arrays
	 */
	private static byte[] addBinary(byte[] complement, byte[] one) {

		byte[] result = new byte[complement.length];
		boolean carry = false;

		for (int i = complement.length - 1; i >= 0; i--) {

			int foo = complement[i] + one[i] + (carry ? 1 : 0);
			switch (foo) {
			case 1:
				result[i] = 1;
				carry = false;
				break;
			case 2:
				result[i] = 0;
				carry = true;
				break;
			case 3:
				result[i] = 1;
				carry = true;
				break;

			}
		}
		return result;
	}

	/*
	 * helper method for ForEach-method, adds 0s where needed
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
