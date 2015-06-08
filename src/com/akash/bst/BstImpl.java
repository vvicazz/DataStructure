package com.akash.bst;

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
	}
}