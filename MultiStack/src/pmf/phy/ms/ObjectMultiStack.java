package pmf.phy.ms;

import java.util.EmptyStackException;
import java.util.HashMap;



public class ObjectMultiStack {

	
	
	static class MultiStackEntry {
		
		ValueWrapper value;
		
		MultiStackEntry next;
		
		public ValueWrapper getValue() {
			return this.value;
		}
	
	}
	
	
	private HashMap<String, MultiStackEntry> map;
	
	
	public ObjectMultiStack() {
		this.map = new HashMap<String, MultiStackEntry>(); 
	}
	
	
	
	private MultiStackEntry createNewNode(ValueWrapper valueWrapper) {
		MultiStackEntry node = new MultiStackEntry();
		node.value = valueWrapper;
		node.next = null;
		return node;
	}
	
	public void push(String name, ValueWrapper valueWrapper) {

		if(!(map.containsKey(name))) {
			MultiStackEntry head = createNewNode(valueWrapper);
			map.put(name, head);
			
			return;
		}else {
			MultiStackEntry tmp = map.get(name); //tmp == head; get(key) vraca value 
			while(tmp.next != null) {
				tmp = tmp.next;
			}
			tmp.next = createNewNode(valueWrapper);
		}
	}
	
	public ValueWrapper pop(String name) {
		
		if(!(map.containsKey(name))) {
			throw new EmptyStackException();
		}
		else {
			MultiStackEntry head = map.get(name);
			MultiStackEntry tmp = null;
			MultiStackEntry tmp2 = null;
			MultiStackEntry tmp3 = null;
			if(head.next == null) {
				tmp = head;
				head = null;
				return tmp.value;
			}
			
			
			tmp = head;
			while(tmp.next != null) {
				tmp2 = tmp;
				tmp = tmp.next;
			}
			tmp3 = tmp;
			tmp = null; //ovo je vrijednost koju bries
			tmp2.next = tmp;
			return tmp3.value;
		}
		
	}
	
	public ValueWrapper peek(String name) {
		
		if(!(map.containsKey(name))) {
			throw new EmptyStackException();
		}
		else {
			MultiStackEntry tmp = map.get(name);
			while(tmp.next != null) {
				tmp = tmp.next;
			}
			return tmp.value;
		}
	}
	
	public boolean isEmpty(String name){
		if(map.containsKey(name)) {
			return false;
		}else {
			return true;
		}
	}
	
	
	
	

	
	

}
