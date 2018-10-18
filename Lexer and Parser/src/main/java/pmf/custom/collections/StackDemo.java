package pmf.custom.collections;

import java.util.Arrays;




public class StackDemo {
	/*
public static void main(String[] args) {
		
		ObjectStack stack = new ObjectStack();
		
		
		String s = "  231*+9-";
		String unos = s.replaceAll("\\s+","");
		
		System.out.println(unos);

		char[] array = unos.toCharArray();
		
	
		System.out.println(array.length);
		
		System.out.println(Arrays.toString(array)); //sada imam char polje od 7 elemenata
		
	
		System.out.println("STACK");
		
		
		
		for(int i = 0; i < array.length; i++) {
			if(Character.isDigit(array[i])) {
				stack.push(new Integer(array[i]) - '0'); 
				
				
			}
			else {
				int val1 = (int)stack.pop();
				int val2 = (int)stack.pop();
				
				switch(array[i]) {
					
				case '+' : stack.push(val1 + val2);
				break;
				case '-' : stack.push(val2 - val1);
				break;
				case '*' : stack.push(val1 * val2);
				break;
				case '/' : stack.push(val2 / val1);
				break;
				
				}
			}
		}
	
		System.out.println("vrijednost na stacku je = " + stack.pop()); 
		
	} */
	
	public static void main(String [] args) {
		ObjectStack stack = new ObjectStack();
		stack.push("FOR");
		stack.push("{");
		stack.push("i");
		stack.push(10);
		stack.push(16);
		stack.push(1);
		stack.push("}");
		
		stack.print();
		stack.pop();
		stack.pop();
		stack.pop();
		
		System.out.println("Stanje na stacku nakon POP:" );
		stack.print();
		System.out.println("Dodajem na stack element X");
		stack.push("X");
		stack.print();
		
		
	}

}
