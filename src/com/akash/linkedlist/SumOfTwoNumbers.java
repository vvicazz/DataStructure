package com.akash.linkedlist;

/**
 * 	1->2->5->9	<br>
 *     3->6->4 	<br>
 *  ----------  <br>
 *  1->6->2->3 	<br>
 * 
 */
public class SumOfTwoNumbers {

	public static void main(String args[]) {
		MyLinkedList list1 = new MyLinkedList();
		MyLinkedList list2 = new MyLinkedList();
		MyLinkedList list3 = new MyLinkedList();
		list1.add(1);
		list1.add(2);
		list1.add(5);
		list1.add(9);
		list2.add(3);
		list2.add(6);
		list2.add(4);
		sum(list1.getRoot(), list2.getRoot(), list3);
	}

	private static void sum(Node node1, Node node2,
			MyLinkedList list3) {

		
	}
}