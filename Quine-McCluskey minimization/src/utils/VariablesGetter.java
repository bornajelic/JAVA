package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Nodes.BinaryOperatorNode;
import Nodes.ConstantNode;
import Nodes.Node;
import Nodes.NodeVisitor;
import Nodes.UnaryOperatorNode;
import Nodes.VariableNode;

public class VariablesGetter implements NodeVisitor {

	List<String> variables;
	
	 public VariablesGetter() {
		
		variables = new ArrayList<String>();
	}
	 
	 
	@Override
	public void visit(ConstantNode node) {
		// TODO Auto-generated method stub
		String name = node.getValue() ? "1" : "0";
		if(!(variables.contains(name))) {
			variables.add(name);
		}
		
		variables.add(name);
		
		
	}

	@Override
	public void visit(VariableNode node) {
		// TODO Auto-generated method stub
		if(!(variables.contains(node.getName()))) {
			variables.add(node.getName());
		}
		
		
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		// TODO Auto-generated method stub
		node.getChild().accept(this);
		
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		// TODO Auto-generated method stub
		for(Node n : node.getChildren()) {
			n.accept(this);
		}
		
	}
	
	public List<String> getVariable() {
		Collections.sort(variables);
		return this.variables;
	}

	
}
