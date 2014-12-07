package com.akash.tree;

public abstract class AbstractBinaryTree<T> {

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

	abstract public Node<T> addLeftNode(Node<T> node, T t);

	abstract public Node<T> addRightNode(Node<T> node, T t);

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

	class Node<Type> {

		private Node<Type> left;
		private Node<Type> right;
		private Type value;

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
	}
}