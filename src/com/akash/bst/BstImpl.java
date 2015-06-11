package com.akash.bst;

import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeMap;

public class BstImpl {

	public static void main(String args[]) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		bst.add(30);
		bst.add(20);
		bst.add(10);
		bst.add(5);
		bst.add(3);
		bst.add(70);
		bst.add(50);
		bst.printSorted();
		
		Stack<Integer> st = new Stack<Integer>();
		st.add(10);
		st.add(20);
		st.pop();
		st.push(25);
		st.add(50);
		
		//We can pass a comparator in constructor and compare values
		//OR
		//if no comparator defined, then elements are sorted acc to their order of insert
		PriorityQueue<BstImpl> pq = new PriorityQueue<BstImpl>();
		pq.add(new BstImpl());
		pq.add(new BstImpl());
		pq.add(new BstImpl());
		pq.add(new BstImpl());
		
		TreeMap<BstImpl,BstImpl> tm;
	}
}