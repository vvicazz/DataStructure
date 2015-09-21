package com.akash.linkedlist;

public class App {

	public static void main(String[] args) {
		MyLinkedList list = new MyLinkedList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		list.add(10);
		list.add(11);
		list.add(12);
		list.add(13);
		System.out.println(list);
		list.reverInParts(2);
		System.out.println(list);
		
		/*MyLinkedList list2 = new MyLinkedList();
		list2.add(11);
		list2.add(12);
		list2.add(13);
		list2.add(14);
		list2.add(15);
		System.out.println(list2);
		
		list = MyLinkedList.mergeList(list, list2);
		System.out.println(list);
		
		list.printReverse();*/
	}
}