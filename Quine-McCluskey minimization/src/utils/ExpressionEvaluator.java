package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import Nodes.BinaryOperatorNode;
import Nodes.ConstantNode;
import Nodes.Node;
import Nodes.NodeVisitor;
import Nodes.UnaryOperatorNode;
import Nodes.VariableNode;

public class ExpressionEvaluator implements NodeVisitor {
	
	private boolean[] values;
	
	private Map<String,Integer> positions;
	
	private Stack<Boolean> stack;
	
	
	
	public ExpressionEvaluator(List<String> variables) {
		
		positions = new HashMap<String, Integer>();
		stack = new Stack<Boolean>();
		values = new boolean[variables.size()];
		//(A,0),(B,1),(C,2)
		
		for(int i = 0; i < variables.size(); i++) {
			positions.put(variables.get(i), i);
		}
		
		
	}

	@Override
	public void visit(ConstantNode node) {
		stack.push(node.getValue());
		
	}

	@Override
	public void visit(VariableNode node) {
		
		try {
			stack.push(values[positions.get(node.getName())]);
		} catch(IllegalStateException e) {
			System.out.println("Map doesnt hold any information!");
		}
		
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		
		node.getChild().accept(this);
		boolean result = node.getOperator().apply(stack.pop());
		stack.push(result);
		
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		
		List<Boolean> list = new ArrayList<Boolean>();
		
		for(Node n : node.getChildren()) {
			n.accept(this);
		}
		
		int i = 0;
		while(i < node.getChildren().size()) {
			list.add(stack.pop());
			i++;
		}
		
		boolean result = node.getOperator().apply(list.get(0), list.get(1)); 
																			
		int j = 2;
		if(j < list.size()) { 
			while(j < list.size()){
				result = node.getOperator().apply(result, list.get(j));
				j++;
			}
		}
		
		stack.push(result);
	
	}
	
	public void start() {
		this.stack.clear();
	}
	
	public boolean getResult() {
		
		if(stack.size() != 1) {
			throw new IllegalStateException("Expected only one element on stack!");
		}
		return stack.pop();
	}
	
	public void setValues(boolean[] b) {
		start();
		this.values = b;
		
	}

}
