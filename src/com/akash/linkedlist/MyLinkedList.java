package com.akash.linkedlist;

public class MyLinkedList {

	private Node root;

	public Node getRoot() {
		return root;
	}
	public void setRoot(Node root) {
		this.root = root;
	}
	
	public void add(int value) {
		Node node = getRoot();
		if(node==null) {
			setRoot(createNewNode(value,null));
		}
		else {
			while(node.getNext()!=null) {
				node = node.getNext();
			}
			node.setNext(createNewNode(value,null));
		}
	}
	
	private Node createNewNode(int value, Node next) {
		Node node = new Node();
		node.setValue(value);
		node.setNext(next);
		return node;
	}
	
	public void delete(int value) {
		Node node = getRoot();
		if(node!=null && node.getValue()==value) {
			setRoot(node.getNext());
			return;
		}
		Node tempNode = node;
		node = node.getNext();
		while(node!=null) {
			if(node.getValue()==value) {
				tempNode.setNext(node.getNext());
				node.setNext(null);
				break;
			}
			else {
				tempNode = node;
				node = node.getNext();
			}
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		Node node=getRoot();
		while(node!=null) {
			sb.append(node.getValue());
			node=node.getNext();
			if(node!=null) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	public void reverse() {
		
		//Initialization of 4 Node variables
		Node temp1 = null;
		Node temp2 = getRoot();
		Node temp3 = null;
		Node temp4 = null;
		if(temp2!= null) {
			temp3 = temp2.getNext();
		}
		if(temp3!=null) {
			temp4 = temp3.getNext();
		}
		
		//iterating over Node variables and reversing 2 nodes in one iteration
		while(!(temp2==null || temp3==null)) {
			reverseFirstTwoElements(temp1,temp2,temp3);
			temp1=temp3;
			temp2=temp4;
			if(temp2!= null) {
				temp3 = temp2.getNext();
			}
			if(temp3!=null) {
				temp4 = temp3.getNext();
			}
		}
		
		//Base case condition, N=2m (where m is any integer)
		if(temp2==null) {
			setRoot(temp1);
		}
		
		//Base case condition, N=2m+1 (where m is any integer)
		if(temp3==null) {
			temp2.setNext(temp1);
			setRoot(temp2);
		}
	}
	
	private void reverseFirstTwoElements(Node temp1,Node temp2,Node temp3) {
		temp3.setNext(temp2);
		temp2.setNext(temp1);
	}
}