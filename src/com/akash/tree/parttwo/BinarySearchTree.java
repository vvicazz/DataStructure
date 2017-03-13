package com.akash.tree.parttwo;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public final class BinarySearchTree<V extends Comparable<V>> implements Serializable {

	private static final long serialVersionUID = 42145L;
	private transient volatile Node<V> rootNode;
	private transient int size = 0;

	public BinarySearchTree() {
	}

	public BinarySearchTree(V rootData) {
		Objects.requireNonNull(rootData);
		rootNode = new Node<>();
		rootNode.setValue(rootData);
		size = 1;
	}

	public boolean add(V nodeData) {
		Objects.requireNonNull(nodeData);
		Node<V> newNode = new Node<>();
		newNode.setValue(nodeData);
		if (rootNode == null) {
			rootNode = newNode;
			size++;
			return true;
		}
		Node<V> tempNodeParent = null;
		Node<V> tempNode = rootNode;
		boolean isLeft = false;
		while (tempNode != null) {
			int compareResult = tempNode.getValue().compareTo(nodeData);
			if (compareResult == 0) {
				return false;
			}
			if (compareResult > 0) {
				tempNodeParent = tempNode;
				tempNode = tempNode.getLeft();
				isLeft = true;
			} else {
				tempNodeParent = tempNode;
				tempNode = tempNode.getRight();
				isLeft = false;
			}
		}
		if (isLeft) {
			tempNodeParent.setLeft(newNode);
		} else {
			tempNodeParent.setRight(newNode);
		}
		size++;
		return true;
	}

	public boolean delete(V nodeData) {
		Objects.requireNonNull(nodeData);
		if (rootNode == null) {
			return false;
		}
		Node<V> tempNodeParent = search(nodeData);
		Node<V> nodeToDelete = null;
		if (tempNodeParent == null) {
			return false;
		} else if (tempNodeParent.getLeft().getValue().compareTo(nodeData) == 0) {
			nodeToDelete = tempNodeParent.getLeft();
			deleteNode(nodeToDelete, tempNodeParent, true);
		} else {
			nodeToDelete = tempNodeParent.getRight();
			deleteNode(nodeToDelete, tempNodeParent, false);
		}
		size--;
		return true;
	}

	private void deleteNode(Node<V> nodeToDelete, Node<V> parentNode, boolean isLeft) {
		if (nodeToDelete.getLeft() == null && nodeToDelete.getRight() == null) {
			nodeToDelete.setValue(null);
			if (isLeft)
				parentNode.setLeft(null);
			else
				parentNode.setRight(null);
		} else if (nodeToDelete.getLeft() == null) {
			nodeToDelete.setValue(null);
			if (isLeft)
				parentNode.setLeft(nodeToDelete.getRight());
			else
				parentNode.setRight(nodeToDelete.getRight());
		} else if (nodeToDelete.getRight() == null) {
			nodeToDelete.setValue(null);
			if (isLeft)
				parentNode.setLeft(nodeToDelete.getLeft());
			else
				parentNode.setRight(nodeToDelete.getLeft());
		} else {
			boolean isLeftInorderSucceser = false;
			Node<V> inorderSucceserParent = nodeToDelete;
			Node<V> inorderSucceser = nodeToDelete.getRight();
			while (inorderSucceser.getLeft() != null) {
				isLeftInorderSucceser = true;
				inorderSucceserParent = inorderSucceser;
				inorderSucceser = inorderSucceser.getLeft();
			}
			nodeToDelete.setValue(inorderSucceser.getValue());
			deleteNode(inorderSucceser, inorderSucceserParent, isLeftInorderSucceser);
		}
	}

	public boolean find(V nodeData) {
		Objects.requireNonNull(nodeData);
		return search(nodeData) != null;
	}

	/**
	 * It will return parent for data if data exists
	 * 
	 * @param data
	 * @return
	 */
	private Node<V> search(V nodeData) {
		Objects.requireNonNull(nodeData);
		Node<V> tempNodeParent = null;
		Node<V> tempNode = rootNode;
		while (tempNode != null) {
			int compareResult = tempNode.getValue().compareTo(nodeData);
			if (compareResult == 0) {
				return tempNodeParent;
			} else if (compareResult > 0) {
				tempNodeParent = tempNode;
				tempNode = tempNode.getLeft();
			} else {
				tempNodeParent = tempNode;
				tempNode = tempNode.getRight();
			}
		}
		return null;
	}

	public String getInOrderTraversal() {
		StringBuffer nodesContent = new StringBuffer();
		doInOrderTraversal(rootNode, nodesContent);
		if (nodesContent.length() > 0) {
			nodesContent.deleteCharAt(nodesContent.length() - 1);
		}
		return nodesContent.toString();
	}

	private void doInOrderTraversal(Node<V> node, StringBuffer nodesContent) {
		if (node != null) {
			doInOrderTraversal(node.getLeft(), nodesContent);
			nodesContent.append(node.getValue()).append(",");
			doInOrderTraversal(node.getRight(), nodesContent);
		}
	}

	private static class Node<V extends Comparable<V>> {
		private Node<V> left;
		private Node<V> right;
		private V value;

		public Node<V> getLeft() {
			return left;
		}

		public void setLeft(Node<V> left) {
			this.left = left;
		}

		public Node<V> getRight() {
			return right;
		}

		public void setRight(Node<V> right) {
			this.right = right;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}
	}

	private void writeObject(java.io.ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeInt(size);
		internalWriteEntriesInOrder(rootNode, s);
	}

	private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		int size = s.readInt();
		this.size = size;
		for (int i = 0; i < size; i++) {
			@SuppressWarnings("unchecked")
			V data = (V) s.readObject();
			add(data);
		}
	}

	private void internalWriteEntriesInOrder(Node<V> node, java.io.ObjectOutputStream s) throws IOException {
		if (node != null) {
			internalWriteEntriesInOrder(node.getLeft(), s);
			s.writeObject(node.getValue());
			internalWriteEntriesInOrder(node.getRight(), s);
		}
	}
}