package qmc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.*;



public class Sranje {
	
	

	public static void main(String[] args) {
		
	
		Set<Integer> minterms = new HashSet<>(Arrays.asList(0,1,3,10,11,14,15));
		Set<Integer> dontcares = new HashSet<>(Arrays.asList(4,6));

		Minimizer m = new Minimizer(minterms, dontcares, Arrays.asList("A","B","C","D"));
		
		m.findPrimaryImplicants();
		
		System.out.println(m.getPrimCover());
		
		
	}
}
