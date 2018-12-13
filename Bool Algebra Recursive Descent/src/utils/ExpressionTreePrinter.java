package utils;
import java.util.Collections;

import Nodes.BinaryOperatorNode;
import Nodes.ConstantNode;
import Nodes.Node;
import Nodes.NodeVisitor;
import Nodes.UnaryOperatorNode;
import Nodes.VariableNode;

public class ExpressionTreePrinter implements NodeVisitor {
	//
	private int spaceIncrement = 1;
	
	
	@Override
	public void visit(ConstantNode node) {
		printingNodes(node.getValue() ? "true" : "false");
	}

	@Override
	public void visit(VariableNode node) {
		
		printingNodes(node.getName());
		
		
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		if(spaceIncrement>=2) {
			spaceIncrement -=2;
		}
		printingNodes(node.getName());
		spaceIncrement += 2;
		node.getChild().accept(this);
		
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		printingNodes(node.getName());
		spaceIncrement += 2;
		for(Node n : node.getChildren()) {
			n.accept(this);
		}
	
	}
	
	void printingNodes(String str) {
		String space = "";
		space = String.join(" ", Collections.nCopies(spaceIncrement, space));
		System.out.println(space + str);
	}

}
