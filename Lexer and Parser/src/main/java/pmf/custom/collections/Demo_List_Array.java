package pmf.custom.collections;

import java.util.Arrays;

public class Demo_List_Array {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Array col = new Array(2);
		col.add(new Integer(20));
		col.add("New York");
		col.add("San Francisco"); 
		System.out.println(col.contains("New York")); 
		col.remove(1); 
		System.out.println(col.get(1));
		System.out.println(col.size()); 
		col.add("Los Angeles");
		LList col2 = new LList(col);
		class P extends Processor {
		public void process(Object o) {
		System.out.println(o);
		}
		};
		System.out.println("col1 elements:");
		col.forEach(new P());
		System.out.println("col1 elements again:");
		System.out.println(Arrays.toString(col.toArray()));
		System.out.println("col2 elements:");
		col2.forEach(new P());
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray()));
		System.out.println(col.contains(col2.get(1))); 
		System.out.println(col2.contains(col.get(1))); 
		 
		
	

	}

}
