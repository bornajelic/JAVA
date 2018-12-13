package Nodes;

/*
 * Variables are moddeled by VariableNode.
 * Constructor accepts name of a variable and stores it
 */
public class VariableNode implements Node{
	
	private String name;
	
	public VariableNode(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
		
	}
	
	public String toString(){
		String str = "";
		str += "name=VARIABLE\n";
		str += "value="+name+"\n";
		str += "";
		return str;
	}

}
