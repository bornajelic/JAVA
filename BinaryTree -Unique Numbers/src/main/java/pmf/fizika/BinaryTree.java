package pmf.fizika;
import java.util.Scanner;

public class BinaryTree {
	
	/**
	 * Implementacija binarnog stabla
	 * (ovaj put bez konstruktora)
	 * @author Borna Jelić PMF
	 *
	 */
	
	static class BstNode {
		BstNode right;
		BstNode left;
		int data;
	}
	
	/**
	 * Metoda alocira novi čvor u memoriji
	 * 
	 * @param data
	 * @return novi alocirani čvor
	 */
	public static BstNode getNewNode(int data) {
		BstNode novi = new BstNode();
		novi.right = null;
		novi.left = null;
		novi.data = data;
		
		return novi;
	}
	/**
	 * Metoda dodaje novi čvor u stablo
	 * 
	 * @param root
	 * @param data
	 * @return root kao pointer na početni cvor da se ne "pogubi" u memoriji
	 */
	
	public static BstNode addNode(BstNode root, int data ) {
		if(root == null) {
			root = getNewNode(data);
			return root;
		}
		else if(data < root.data) {
			root.left = addNode(root.left,data);
		}
		else if(data > root.data){
			root.right = addNode(root.right,data);
		}
		else {
			;
		}
			
		
		return root;
			
	}
	
	/**
	 * Metoda vraća broj čvorova u stablu
	 * 
	 * @param root
	 * @return counter koji predstavlja broj čvorova u stablu
	 */
	public static int treeSize(BstNode root) {
		int counter = 1;
		if(root == null) 
			return 0;
		else {
			counter +=	treeSize(root.left);
			counter +=	treeSize(root.right);
			return counter;
		}
	}
	
	/**
	 * Boolean metoda koja gleda postoji li traženi čvor u stablu
	 * 
	 * @param root
	 * @param data
	 * @return true ukoliko je nađen, u suprotnom, false
	 */
		
	public static boolean containsValue(BstNode root, int data) {
		
		if(root==null) {
			return false;
		}
	
		else if(root.data > data) {
			return containsValue(root.left, data);
		}
		else if(root.data < data){
			return containsValue(root.right, data);
		}
		else
			return true;
		
		 
	}
	
	/**
	 * Metoda koja ispisuje stablo u InOrder postupku
	 * @param root
	 */
	public static void inOrder(BstNode root) {
		
		if(root == null) {
			return;
		}
		else {
			
			inOrder(root.right);
			System.out.println(root.data);
			inOrder(root.left);
		}
		
		
	}
	/**
	 * Metoda koja ispisuje stablo od najmanjg do najvećeg 
	 * @param root
	 *  			
	 */
	
	public static void ispisMinMax(BstNode root) {
		if(root == null)
			return;
		else {
			ispisMinMax(root.left);
			System.out.println(root.data);
			ispisMinMax(root.right);
		}
		
	}
	
	/**
	 * Metoda koja čita sa tipkovnice cijele brojeve te ih pridodaje
	 * binarnom stablu	
	 * @param args
	 */
	
	public static void main(String[] args) {
		
	
		BstNode glava = null;
		Scanner sc = new Scanner(System.in);
		
		
		while(true) {
			System.out.println("Unesite broj");
			
			if(sc.hasNextInt()) {
				
				int i = sc.nextInt();
			
				if(!containsValue(glava, i)){
					glava = addNode(glava, i);
				}
				else {
					System.out.println("Broj vec postoji, preskacem");
				}
			}
			else{
				String s = sc.next();
				if(s.equals("kraj")) {
					inOrder(glava);
					System.out.println();
					ispisMinMax(glava);
					break;
				}
				else{
					System.out.println(s + " nije cijeli broj");
				}
			}
				
		}
		
		sc.close();
		
		
		
		
	}
	
}
