package pmf.collections;

//koristim AdapterPattern

public class ObjectStack {

	Array stack;
	
	public ObjectStack() {
		
		this.stack = new Array();
	}
	
	
	boolean isEmpty() {
		return stack.isEmpty();
	}
	
	int size() {
		return stack.size();
	}
	
	void push(Object value) {
		
		stack.add(value);
	}
	
	Object pop() {
		
		if(isEmpty()) {
			throw new EmptyStackException("Stack je prazan");
		}
		
		Object tmp = stack.get(stack.size()-1);
		stack.remove(stack.size()-1);
		return tmp;
		
	}
	
	Object peek() {
		
		if(isEmpty()) {
			throw new EmptyStackException("Stack je prazan");
		}
		
		Object tmp = stack.get(stack.size()-1);
		return tmp;
	}
	
	void clear() {
		stack.clear();
	}
	
	
	
}
