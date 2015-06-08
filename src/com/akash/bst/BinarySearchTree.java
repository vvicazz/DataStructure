package com.akash.bst;

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
		updateheight();
	}
	
	
//	http://stackoverflow.com/questions/575772/the-best-way-to-calculate-the-height-in-a-binary-search-tree-balancing-an-avl
//	http://stackoverflow.com/questions/2597637/finding-height-in-binary-search-tree
	private void updateheight() {
		
		if(root==null) {
			return;
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