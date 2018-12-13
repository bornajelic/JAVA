package utils;

import java.util.Arrays;

public class ForEachTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Util.forEach(Arrays.asList("A", "B", "C", "D"),
				values -> System.out.println(Arrays.toString(values).replaceAll("true", "1").replaceAll("false", "0")));

	}

}
