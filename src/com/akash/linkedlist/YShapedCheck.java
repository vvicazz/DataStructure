package com.akash.linkedlist;

//program for checking y shaped linked list :
//1.calculate length of both list : L1,L2
//2.if any of L1 or L2 is zero then return
//3.calculate difference of 2
//4.traverse longer linked list with pointer ahead of shorter list with the difference in size.

public class YShapedCheck<T> {

	public boolean verifyYShaped(MyLinked<T> list1, MyLinked<T> list2) {

		int length1 = list1.length();
		int length2 = list2.length();
		if (length1 == 0 || length2 == 0)
			return false;

		int diff = length1 - length2;
		MyLinked.Node<T> ptr1 = list1.getHead();
		MyLinked.Node<T> ptr2 = list2.getHead();
		if (diff > 0) {
			while (diff > 0) {
				ptr1 = ptr1.getNext();
				diff--;
			}
		} else if (diff < 0) {
			diff = diff * -1;
			while (diff > 0) {
				ptr2 = ptr2.getNext();
				diff--;
			}
		}

		while (ptr1 != null && ptr2 != null) {
			if (ptr1.equals(ptr2))
				return true;
			ptr1 = ptr1.getNext();
			ptr2 = ptr2.getNext();
		}
		return false;
	}
}

class MyLinked<T> {

	MyLinked() {
	}

	// head of linked list
	private Node<T> head;

	protected Node<T> getHead() {

		return head;
	}

	static class Node<T> {

		private T data;
		private Node<T> next;

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public Node<T> getNext() {
			return next;
		}

		public void setNext(Node<T> next) {
			this.next = next;
		}

		// Node : override equals() and hashcode() for Node
	}

	public int length() {

		int counter = 0;
		if (head != null) {
			for (Node<T> temp = head; temp != null; temp = temp.getNext()) {
				counter++;
			}
		}
		return counter;
	}

	// Node : override equals() and hashcode() for MyLinked
}