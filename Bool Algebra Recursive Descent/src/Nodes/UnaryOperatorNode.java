package Nodes;

import java.util.function.UnaryOperator;
/*
 * Unary operations are modeled by UnaryOperatorNode.
 * Constructor accepts name of an operator, a reference on a node that
 * represents an operand, and strategy of operator which is acting upon operand
 * 
 */


public class UnaryOperatorNode implements Node {

	private String name;
	
	private Node child; //reference on operand
	
	/*
	 * represents an operation on a single operant that produces a result
	 * of the same type as its operand
	 * 
	 * This is a specialization of a Function for the calse where the 
	 * operand and result are of the same type
	 * 
	 * 
	 * if lexer has found *,+,!, :+:, these are operators
	 */
	private UnaryOperator<Boolean> operator;
	
	
	public UnaryOperatorNode(String name, Node child, UnaryOperator<Boolean> operator) {
		this.name = name;
		this.child = child;
		this.operator = operator;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Node getChild() {
		return this.child;
	}
	
	public UnaryOperator<Boolean> getOperator() {
		return this.operator;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
		
	}
	
	public String toString(){
		String str = "";
		str += "name="+name+"\n";
		str += "Node="+child.toString()+"\n";
		str += "";
		return str;
	}
}
