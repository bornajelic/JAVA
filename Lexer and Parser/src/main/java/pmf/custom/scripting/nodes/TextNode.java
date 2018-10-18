package pmf.custom.scripting.nodes;
// enum WORD, neda mi se mjenjati
public class TextNode extends Node {


	private String text;
	
	
	
	public TextNode(String text) {
		super();
		this.text = text;
	}
	
	
	
	public String getText() {
		return this.text;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return text;
	}
}
