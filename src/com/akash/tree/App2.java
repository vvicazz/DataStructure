package com.akash.tree;

public class App2 {

	public static void main(String[] args) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>(50);
		bst.addNode(20);
		bst.addNode(60);
		bst.addNode(40);
		bst.addNode(45);
		bst.addNode(85);
		
		//PreOrder traversal of a BST is sorted array
		bst.printPreOrder(bst.getRoot());
	}

}
