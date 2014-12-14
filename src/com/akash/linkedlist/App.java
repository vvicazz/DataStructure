package com.akash.linkedlist;

public class App {

	public static void main(String[] args) {
		MyLinkedList list = new MyLinkedList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		System.out.println(list);
//		list.reverse();
		
		MyLinkedList list2 = new MyLinkedList();
		list2.add(11);
		list2.add(12);
		list2.add(13);
		list2.add(14);
		list2.add(15);
		System.out.println(list2);
		
		list = MyLinkedList.mergeList(list, list2);
		System.out.println(list);
	}
}