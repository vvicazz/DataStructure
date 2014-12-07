package com.akash.tree;


public class SimpleBinaryTree<T> extends AbstractBinaryTree<T> {

	public SimpleBinaryTree(T t) {
		super(t);
	}

	@Override
	public Node<T> addLeftNode(Node<T> node, T t) {
		if (node != null) {
			Node<T> tempNode = createNode(t);
			node.setLeft(tempNode);
			return tempNode;
		}
		return null;
	}

	@Override
	public Node<T> addRightNode(Node<T> node, T t) {
		if (node != null) {
			Node<T> tempNode = createNode(t);
			node.setRight(tempNode);
			return tempNode;
		}
		return null;
	}

	/**
	 * It traverses the tree in InOrder traversal and deletes the first element
	 * that matches the condition.
	 * Returns false if element not found. 
	 * Note: We can only delete a leaf node i.e. whose both left and right are
	 * null.(This is my implementation)
	 */
	@Override
	public boolean delete(T t) {
		Node<T> parentNode = getRoot();
		Node<T>[] nodeArr = matchInOrderTraversal(getRoot(), t, parentNode);
		if(nodeArr[0]==null) {
			return false;
		}
		else if(nodeArr[0]==nodeArr[1]) {
			setRoot(null);
			return false;
		}
		else {
			Node<T> parent = nodeArr[1];
			Node<T> child = nodeArr[0];
			if( parent.getLeft().equals(child) )
				parent.setLeft(null);
			else
				parent.setRight(null);
			return true;
		}
	}

	private Node<T>[] matchInOrderTraversal(Node<T> node, T t, Node<T> parentNode) {
		Node<T> matchedNode = null;
		if (node != null) {
			if (node.getValue().equals(t) && node.getLeft().equals(null)
					&& node.getRight().equals(null)) {
				matchedNode = node;
			} else {
				parentNode = node;
				Node<T>[] nodeArr = matchInOrderTraversal(node.getLeft(), t, parentNode);
				if (nodeArr[0] == null) {
					nodeArr = matchInOrderTraversal(node.getRight(), t, parentNode);
				}
				matchedNode=nodeArr[0];
				parentNode=nodeArr[1];
			}
		}
		return new Node[]{ matchedNode, parentNode };
	}
}