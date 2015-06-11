package com.akash.bst;

//We can take a comparator in constructor and compare values
//OR
//if no comparator defined, then elements are sorted acc to their order of insert
public class BinarySearchTree<E extends Comparable> {

	private Node root;

	BinarySearchTree() {

	}

	public Node getRoot() {
		return root;
	}

	public void add(E e) {

		if (e == null)
			return;
		if (root == null) {
			root = createNode(e);
		} else {
			Node<E> parent = searchParentForNode(root, e);
			if (parent.getValue().compareTo(e) > 0) {
				parent.setLeft(createNode(e));
			} else {
				parent.setRight(createNode(e));
			}
		}
		updateHeight();
	}

	// http://stackoverflow.com/questions/575772/the-best-way-to-calculate-the-height-in-a-binary-search-tree-balancing-an-avl
	// http://stackoverflow.com/questions/2597637/finding-height-in-binary-search-tree
	private void updateHeight() {

		if (root == null) {
			return;
		}
		updateHeightForEachNode(root);
	}

	/*
	 * This method will take O(n) time complexity
	 */
	private int updateHeightForEachNode(Node<E> node) {

		int leftHeight = 0;
		int rightHeight = 0;
		int height = 0;
		if (node.getLeft() == null && node.getRight() == null) {
			node.setNodeHeight(0);
			return 0;
		} else if (node.getLeft() != null && node.getRight() != null) {
			leftHeight = updateHeightForEachNode(node.getLeft());
			rightHeight = updateHeightForEachNode(node.getRight());
			height = leftHeight > rightHeight ? leftHeight + 1
					: rightHeight + 1;
			node.setNodeHeight(height);
			return height;
		} else if (node.getLeft() != null) {
			height = updateHeightForEachNode(node.getLeft()) + 1;
			node.setNodeHeight(height);
			return height;
		} else {
			height = updateHeightForEachNode(node.getRight()) + 1;
			node.setNodeHeight(height);
			return height;
		}
	}

	public boolean contains(E e) {

		if (root == null) {
			return false;
		}
		Node<E> parent = searchParentForNode(root, e);
		if (parent.getValue().compareTo(e) == 0) {
			return true;
		}
		return false;
	}

	private Node searchParentForNode(Node<E> node, E e) {

		Node<E> parentNode = null;
		while (node != null) {
			if (node.getValue().compareTo(e) == 0) {
				if (node.getRight() != null) {
					parentNode = node;
					node = node.getRight();
				} else {
					return parentNode;
				}
			} else if (node.getValue().compareTo(e) > 0) {
				parentNode = node;
				node = node.getLeft();
			} else {
				parentNode = node;
				node = node.getRight();
			}
		}
		return parentNode;
	}

	private Node createNode(E e) {
		Node<E> node = new Node<E>();
		node.setValue(e);
		node.setNodeHeight(0);
		return node;
	}

	public void printSorted() {
		Node<E> tempNode = root;
		if (tempNode == null) {
			return;
		}
		inOrderTraversal(tempNode);
	}

	//we can also make it without recursion
	private void inOrderTraversal(Node<E> node) {
		if (node != null) {
			inOrderTraversal(node.getLeft());
			System.out.println(node.getValue());
			inOrderTraversal(node.getRight());
		}
	}

	private class Node<Type extends Comparable> {

		private Node<Type> left;
		private Node<Type> right;
		private Type value;

		// See AvlTree
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