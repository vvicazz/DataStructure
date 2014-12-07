package com.akash.tree;


public class App1 {

	public static void main(String args[]) {
		SimpleBinaryTree<Integer> tree = new SimpleBinaryTree<Integer>(2);
		AbstractBinaryTree<Integer>.Node<Integer> node1 = tree.addLeftNode(tree.getRoot(), 3);
		AbstractBinaryTree<Integer>.Node<Integer> node2 = tree.addRightNode(tree.getRoot(), 4);
		
		AbstractBinaryTree<Integer>.Node<Integer> node3 = tree.addLeftNode(node1, 5);
		AbstractBinaryTree<Integer>.Node<Integer> node4 = tree.addRightNode(node1, 6);
		
		AbstractBinaryTree<Integer>.Node<Integer> node5 = tree.addLeftNode(node2, 7);
		AbstractBinaryTree<Integer>.Node<Integer> node6 = tree.addRightNode(node2, 8);
		
		tree.printInOrder(tree.getRoot());
		System.out.println();
		tree.printPreOrder(tree.getRoot());
		System.out.println();
		tree.printPostOrder(tree.getRoot());
	}
}