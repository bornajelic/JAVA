package pmf.hashtable;

import java.util.Iterator;

public class HashDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer> hMap = new HashMap<>(4);
		
		hMap.put("Borna", 4334);
		hMap.put("Borna", 1000);
		
		hMap.put("dfasd", 11);//borna je prvi
		hMap.put("rger", 342);
		hMap.put("erte", 2342);
		hMap.put("jen", 243);
		
		System.out.println("Size : " + hMap.size() + " popunjenost : " + hMap.getCapacity());
		
		hMap.put("dva", 4241124);
		hMap.put("tri", 23); //erte je prvi
		hMap.put("cet", 42244); //rger je prvi
		hMap.put("pet", 676); // dva je prvi
		hMap.put("pet", 500); // dva je prvi
		hMap.put("ses", 4673524);
		hMap.put("sed", 765); //rger je prvi
		hMap.put("os", 76);
		hMap.remove("dva");
		hMap.put("dev", 423454);
		hMap.put("des", 22);//rger je prvi
		
		System.out.println(hMap.toString());
		
		
		System.out.println(hMap.containsKey("ses"));
		
		
	
		System.out.println(hMap.containsKey("sed"));
		
		
		
		System.out.println("Size : " + hMap.size() + " popunjenost : " + hMap.getCapacity());
		
		
			
		}
	}
	


