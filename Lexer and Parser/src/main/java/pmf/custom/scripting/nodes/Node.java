package pmf.custom.scripting.nodes;
import java.util.ArrayList;


public class Node {

	/*
	 * pogledaj kako izgleda default konstrutkor
	 * pogledaj kada se koristi INSTANCEOF
	 * kod moje kolekcije bi bilo
	 * array.get(index) instanceof Node
	 *  i moras castat return
	 */
	 private ArrayList<Node> array = new ArrayList<>();
	 
	 
	 
	 public void addChildNode(Node child) { //ako su protected, ne mogu ih u Parseru pozivati??
		 if(array == null) {
			 array = new ArrayList<>();
		 }
		 array.add(child); 
	 }
	 
	 public ArrayList<Node> getChildren(){
		 return array;
	 }
	 
	 public Node getChild(int index) {
		if(index >= array.size()) {
			throw new IndexOutOfBoundsException();
		}
		return array.get(index);
	}
	 
	 public int getSize() {
		 return this.array.size();
	 }
	 
	 
	
}
