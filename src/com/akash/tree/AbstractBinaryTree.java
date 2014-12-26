package com.akash.tree;

public abstract class AbstractBinaryTree<T extends Comparable> {

	public AbstractBinaryTree(T t) {
		setRoot(createNode(t));
	}

	private Node<T> root;

	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	public Node<T> createNode(T t) {
		Node<T> node = new Node<T>();
		node.setLeft(null);
		node.setRight(null);
		node.setValue(t);
		return node;
	}

	/**
	 * The implementation class can implement this method in its own way.
	 * There could many ways to delete a node from a tree.
	 * 
	 * @param t
	 * @return
	 */
	abstract public boolean delete(T t);

	public void printPreOrder(Node<T> root) {
		if (root != null) {
			printPreOrder(root.getLeft());
			System.out.print(root.getValue() + "	");
			printPreOrder(root.getRight());
		}
	}

	public void printInOrder(Node<T> root) {
		if (root != null) {
			System.out.print(root.getValue() + "	");
			printInOrder(root.getLeft());
			printInOrder(root.getRight());
		}
	}

	public void printPostOrder(Node<T> root) {
		if (root != null) {
			printPostOrder(root.getLeft());
			printPostOrder(root.getRight());
			System.out.print(root.getValue() + "	");
		}
	}

	class Node<Type extends Comparable> {

		private Node<Type> left;
		private Node<Type> right;
		private Type value;
		
		//See AvlTree
		private Integer nodeHeight; 

		public Node<Type> getLeft() {
			return left;
		}

		public void setLeft(Node<Type> left) {
			this.left = left;
		}

		public Node<Type> getRight() {
			return right;
		}

		public void setRight(Node<Type> right) {
			this.right = right;
		}

		public Type getValue() {
			return value;
		}

		public void setValue(Type value) {
			this.value = value;
		}

		public Integer getNodeHeight() {
			return nodeHeight;
		}

		public void setNodeHeight(Integer nodeHeight) {
			this.nodeHeight = nodeHeight;
		}
	}
}