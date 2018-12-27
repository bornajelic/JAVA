package Nodes;
/*
 * Constants are modeled with ConstantNode. 
 * Constructor accepts value of a constant and stores it
 */
public class ConstantNode implements Node {

	private boolean value;
	
	
	public ConstantNode(boolean value) {
		this.value = value;
	}
	
	public boolean getValue() {
		return this.value;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
		
	}
	public String toString(){
		String str = "";
		str += "name=CONSTANT\n";
		str += "value="+value+"\n";
		str += "";
		return str;
	}
}
 

