package pmf.custom.scripting.nodes;

import pmf.custom.scripting.elems.Element;
import pmf.custom.scripting.elems.ElementVariable;

public class ForLoopNode extends Node {

	private ElementVariable variable;
	
	private Element startExpression;
	
	private Element endExpression;
	
	private Element stepExpression;
	
	
	
	public ForLoopNode(ElementVariable variable, Element start,Element middle, Element end) {
		
		this.variable = variable;
		this.endExpression = end;
		this.stepExpression = middle;
		this.startExpression = start;
	}



	public ElementVariable getVariable() {
		return variable;
	}



	public Element getStartExpression() {
		return startExpression;
	}



	public Element getEndExpression() {
		return endExpression;
	}



	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(variable.asText() + " " + startExpression.asText() + " " + endExpression.asText() + " " + stepExpression.asText() + "\n");
		sb.append("FOR BODY\n");
		for(Node n : getChildren()){
			sb.append(n.toString() + "\n");
		}
		sb.append("END FOR BODY\n");
		return sb.toString();
	}
}
