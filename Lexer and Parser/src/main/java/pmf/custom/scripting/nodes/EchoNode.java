package pmf.custom.scripting.nodes;

import pmf.custom.scripting.elems.Element;

public class EchoNode extends Node{

	private Element[] elements;
	
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	
	public Element[] getElements(){
		return this.elements;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Element e : elements){
			s.append(e.asText());
			s.append("\n");
		}
		
		return s.toString();
	}
	
}
