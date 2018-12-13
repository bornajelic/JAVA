package Nodes;

/*
 * Allows the VISITOR to pass the object so the right operations
 * occur on the right type of object!
 * 
 * accept() is passed the same visitor object but then the method visit()
 * is called using a visitor object!
 * 
 */
public interface Node {

	void accept(NodeVisitor visitor);
}
