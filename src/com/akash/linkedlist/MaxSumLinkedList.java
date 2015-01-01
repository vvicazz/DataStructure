package com.akash.linkedlist;

public class MaxSumLinkedList {

//	http://www.geeksforgeeks.org/maximum-sum-linked-list-two-sorted-linked-lists-common-nodes/
	
	public static void main(String args[]) {

		MyLinkedList l1 = new MyLinkedList();
		l1.add(1);
		l1.add(3);
		l1.add(30);
		l1.add(90);
		l1.add(120);
		l1.add(240);
		l1.add(511);

		MyLinkedList l2 = new MyLinkedList();
		l2.add(0);
		l2.add(3);
		l2.add(12);
		l2.add(32);
		l2.add(90);
		l2.add(125);
		l2.add(240);
		l2.add(249);

		MyLinkedList l3 = getMaxSumLinkedList(l1, l2);
		System.out.println(l3);
	}

	private static MyLinkedList getMaxSumLinkedList(MyLinkedList list1,
			MyLinkedList list2) {

		MyLinkedList result = new MyLinkedList();
		Node node1 = list1.getRoot();
		Node node2 = list2.getRoot();
		Node intersectPoint1 = node1;
		Node intersectPoint2 = node2;

		while (node1 != null && node2 != null) {
			int sum1 = 0;
			int sum2 = 0;

			// stop when either number matches or any list is over
			while (node1 != null && node2 != null
					&& node1.getValue() != node2.getValue()) {
				if (node1.getValue() > node2.getValue()) {
					sum2 = sum2 + node2.getValue();
					node2 = node2.getNext();
				} else {
					sum1 = sum1 + node1.getValue();
					node1 = node1.getNext();
				}
			}

			// When last element of both list are same
			if (node1 == null && node2 == null) {
				break;
			}

			// after crossing last matching node
			if (node1 == null) {
				while (node2 != null) {
					sum2 = sum2 + node2.getValue();
					node2 = node2.getNext();
				}
			} else if (node2 == null) {
				while (node1 != null) {
					sum1 = sum1 + node1.getValue();
					node1 = node1.getNext();
				}
			}

			// copy list which has max sum to result list
			if (sum1 >= sum2) {
				addSpecifiedElements(result, intersectPoint1, node1);
			} else {
				addSpecifiedElements(result, intersectPoint2, node2);
			}

			if (node1 != null) {
				node1 = node1.getNext();
				intersectPoint1 = node1;
			}
			if (node2 != null) {
				node2 = node2.getNext();
				intersectPoint2 = node2;
			}
		}

		return result;
	}

	private static void addSpecifiedElements(MyLinkedList finalList,
			Node start, Node end) {

		Node node = start;
		while (node != end) {
			finalList.add(node.getValue());
			node = node.getNext();
		}
		if (end != null) {
			finalList.add(end.getValue());
		}
	}
}