package pmf.phy.db;
 //greater of equals, equals,notequals,like
public class ComparisonOperators {

	public static final IComparisonOperator LESS = (s1,s2) -> s1.compareTo(s2) < 0;
	
	public static final IComparisonOperator LESS_OR_EQUALS = (s1,s2) -> s1.compareTo(s2) <= 0;
	
	public static final IComparisonOperator GREATER = (s1,s2) -> s1.compareTo(s2) > 0;
	
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1,s2) -> s1.compareTo(s2) >= 0 ;
	
	public static final IComparisonOperator EQUALS = ((value1, value2) -> value1.compareTo(value2) == 0);
	
	public static final IComparisonOperator NOT_EQUALS = (s1,s2) -> s1.compareTo(s2) != 0;
	
	/*
	 * LIKE moze imati '*' simbol koji se moze samo jednom pojaviti
	 */
	public static final IComparisonOperator LIKE = (s1,s2) -> {
		
		short i1 = (short) s1.chars().filter(ch -> ch =='*').count();//koliko puta se pojavi '*' znak
		short i2 = (short) s2.chars().filter(ch -> ch =='*').count();
		int wildcard = s2.indexOf("*");
		System.out.println(wildcard);
		if((i1 == 0 || i1 == 1)  && (i2 == 0 || i2 == 1)) {
			
			if(wildcard == 1 ) {
				char c = s2.charAt(0);
				if(s1.charAt(0) == c) {
					return true;
				}
			}
			
			StringBuilder sb = new StringBuilder();
			char[] array = s2.toCharArray();
			
			for(int i = 0 ; i< array.length; i++) {
				if(array[i] == '*') {
					continue;
				}
				else {
					sb.append(array[i]);
				}
			}
			if(s1.equals(sb.toString())) {
				return true;
			}
			else {
				return false;
			}
			
			
			
		}
		else {
			throw new IllegalAccessError("Budem implementirao posebnu iznimku kasnije, ovo je za ComparisonOperatore");
		}
		
	};
	

	public static void main(String[] args) {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		System.out.println(oper.satisfied("Zagreb", "Aba*")); // false
		System.out.println(oper.satisfied("AAA", "AA*AA")); // false
		System.out.println(oper.satisfied("AAAA", "AA*AA"));
		System.out.println(oper.satisfied("AAAA", "A*"));
		System.out.println(oper.satisfied("A", "A*"));
		
		
		System.out.println("\nGREATER");
		IComparisonOperator oper2 = ComparisonOperators.GREATER;
		System.out.println(oper2.satisfied("BO", "AO")); //true
		System.out.println(oper2.satisfied("Jelic", "Ivic")); //true
		System.out.println(oper2.satisfied("Delic", "Jelic"));//false
		
		System.out.println("\nLESS");
		IComparisonOperator oper3 = ComparisonOperators.LESS;
		System.out.println(oper3.satisfied("Delic", "Ivic"));
		System.out.println(oper3.satisfied("001", "003"));
		
		System.out.println("\nGREATER OR EQUALS\n");
		IComparisonOperator oper4 = ComparisonOperators.GREATER_OR_EQUALS;
		System.out.println(oper4.satisfied("Delic", "Delic"));
		System.out.println(oper4.satisfied("Delic", "Jelic"));
		
		System.out.println("\nEQUALS");
		
		IComparisonOperator oper5 = ComparisonOperators.EQUALS;
		
		System.out.println(oper5.satisfied("!", "!"));
		System.out.println(oper5.satisfied("BOKI", "BOKI"));
		System.out.println(oper5.satisfied("tin", "tit"));
		
		
		System.out.println("\nNO TEQUALS\n");
		
		IComparisonOperator oper6 = ComparisonOperators.NOT_EQUALS;
		System.out.println(oper6.satisfied("!", "!"));
		System.out.println(oper6.satisfied("boki", "boku"));
		
		System.out.println("\nLESS OR EQUALS\n");
		IComparisonOperator oper7 = ComparisonOperators.LESS_OR_EQUALS;
		
		System.out.println(oper7.satisfied("Boki", "Boki")); 
		System.out.println(oper7.satisfied("Roki", "Boki")); 
		System.out.println(oper7.satisfied("Doki", "Boki")); 
		
		
		
		
		
		
		
		
		
		
		
		
	} 
	
	
	
	
}
