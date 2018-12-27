package Nodes;

import java.util.List;
import java.util.function.BinaryOperator;

/*
 * Constructor accepts name of an operator, list of references that represent
 * operands(minimum of 2 and more)
 */
public class BinaryOperatorNode implements Node {

	private String name;

	private List<Node> children;

	private BinaryOperator<Boolean> operator;

	public BinaryOperatorNode(String name, List<Node> children,
			BinaryOperator<Boolean> operator) {
		super();
		this.name = name;
		this.children = children;
		this.operator = operator;
	}

	public String getName() {
		return name;
	}

	public List<Node> getChildren() {
		return children;
	}

	public BinaryOperator<Boolean> getOperator() {
		return operator;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}
	
	public String toString(){
		String str = "";
		str += "name=" + name + "\n";
		str += "children= [" + "\n";
		for(Node n : children){
			str += n.toString() + ",\n";	}
		str += "]";
		return str;
	}

}
