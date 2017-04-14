package com.akash.docs.DS.tree;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

//http://www.geeksforgeeks.org/avl-tree-set-2-deletion/
public final class AvlTree<V extends Comparable<V>> implements Serializable {

	private static final long serialVersionUID = 19862L;

	private transient volatile Node<V> rootNode;
	private transient int size = 0;

	private enum INSERTION_ROTATION_TYPE {
		LL, LR, RR, RL;
	}

	public AvlTree() {
	}

	public AvlTree(V rootData) {
		Objects.requireNonNull(rootData);
		rootNode = new Node<>();
		rootNode.setValue(rootData);
		rootNode.setHeight(0);
		size = 1;
	}

	public boolean add(V nodeData) {
		Objects.requireNonNull(nodeData);
		Node<V> newNode = new Node<>();
		newNode.setValue(nodeData);
		if (rootNode == null) {
			rootNode = newNode;
			rootNode.setHeight(0);
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
		newNode.setParent(tempNodeParent);
		size++;
		balanceAvlAfterInsert(newNode);
		return true;
	}

	private void balanceAvlAfterInsert(Node<V> newNode) {
		boolean isRotated = false;
		Node<V> tempNode = newNode;
		while (tempNode != null) {
			updateHeight(tempNode);
			if (tempNode.getHeight() > 1 || tempNode.getHeight() < -1) {
				isRotated = true;
				rotateAfterInsertion(tempNode, getTypeOfInsertRotation(tempNode, newNode));
				break;
			}
			tempNode = tempNode.getParent();
		}
		tempNode = newNode;
		if (isRotated) {
			updateHeight(tempNode);
			tempNode = tempNode.getParent();
		}
	}

	private void rotateAfterInsertion(Node<V> unbalancedParentNode, INSERTION_ROTATION_TYPE rotationType) {
		Node<V> parentTempNode = unbalancedParentNode.getParent();
		Node<V> tempNode = unbalancedParentNode;
		Boolean isLeft = null;
		if (parentTempNode != null) {
			if (parentTempNode.getValue().compareTo(tempNode.getValue()) > 0) {
				isLeft = Boolean.TRUE;
			} else {
				isLeft = Boolean.FALSE;
			}
		}
		switch (rotationType) {
		case LL: {
			Node<V> leftOfTempNode = tempNode.getLeft();
			leftOfTempNode.setParent(parentTempNode);
			if (isLeft == Boolean.FALSE) {
				parentTempNode.setLeft(leftOfTempNode);
			} else if (isLeft == Boolean.TRUE) {
				parentTempNode.setRight(leftOfTempNode);
			}
			leftOfTempNode.setRight(tempNode);
			tempNode.setParent(leftOfTempNode);
			tempNode.setLeft(leftOfTempNode.getRight());
			if (leftOfTempNode.getRight() != null) {
				leftOfTempNode.getRight().setParent(tempNode);
			}
			break;
		}
		case RR: {
			Node<V> rightOfTempNode = tempNode.getRight();
			rightOfTempNode.setParent(parentTempNode);
			if (isLeft == Boolean.FALSE) {
				parentTempNode.setLeft(rightOfTempNode);
			} else if (isLeft == Boolean.TRUE) {
				parentTempNode.setRight(rightOfTempNode);
			}
			rightOfTempNode.setLeft(tempNode);
			tempNode.setParent(rightOfTempNode);
			tempNode.setRight(rightOfTempNode.getLeft());
			if (rightOfTempNode.getLeft() != null) {
				rightOfTempNode.getLeft().setParent(tempNode);
			}
			break;
		}
		case LR: {
			Node<V> leftOfTempNode = tempNode.getLeft();
			Node<V> rightOfTempNode = tempNode.getRight();
			Node<V> nodeOfRotation = tempNode.getLeft().getRight();

			nodeOfRotation.setParent(parentTempNode);
			if (isLeft == Boolean.FALSE) {
				parentTempNode.setLeft(nodeOfRotation);
			} else if (isLeft == Boolean.TRUE) {
				parentTempNode.setRight(nodeOfRotation);
			}

			leftOfTempNode.setRight(nodeOfRotation.getLeft());
			if (nodeOfRotation.getLeft() != null)
				nodeOfRotation.getLeft().setParent(leftOfTempNode);

			nodeOfRotation.setLeft(leftOfTempNode);
			leftOfTempNode.setParent(nodeOfRotation);

			if (rightOfTempNode != null)
				rightOfTempNode.setLeft(nodeOfRotation.getRight());
			if (nodeOfRotation.getRight() != null)
				nodeOfRotation.getRight().setParent(rightOfTempNode);

			if (rightOfTempNode != null)
				rightOfTempNode.setParent(nodeOfRotation);
			nodeOfRotation.setRight(rightOfTempNode);

			break;
		}
		case RL: {
			Node<V> leftOfTempNode = tempNode.getLeft();
			Node<V> rightOfTempNode = tempNode.getRight();
			Node<V> nodeOfRotation = tempNode.getRight().getLeft();

			nodeOfRotation.setParent(parentTempNode);
			if (isLeft == Boolean.FALSE) {
				parentTempNode.setLeft(nodeOfRotation);
			} else if (isLeft == Boolean.TRUE) {
				parentTempNode.setRight(nodeOfRotation);
			}

			if (leftOfTempNode != null)
				leftOfTempNode.setRight(nodeOfRotation.getLeft());
			if (nodeOfRotation.getLeft() != null)
				nodeOfRotation.getLeft().setParent(leftOfTempNode);

			if (leftOfTempNode != null)
				leftOfTempNode.setParent(nodeOfRotation);
			nodeOfRotation.setLeft(leftOfTempNode);

			rightOfTempNode.setLeft(nodeOfRotation.getRight());
			if (nodeOfRotation.getRight() != null)
				nodeOfRotation.getRight().setParent(rightOfTempNode);

			nodeOfRotation.setRight(rightOfTempNode);
			rightOfTempNode.setParent(nodeOfRotation);

			break;
		}
		}
	}

	private INSERTION_ROTATION_TYPE getTypeOfInsertRotation(Node<V> unbalancedParentNode, Node<V> newNode) {
		Queue<Integer> queue = new LinkedList<>();
		while (unbalancedParentNode.getValue().compareTo(newNode.getValue()) != 0) {
			if (unbalancedParentNode.getValue().compareTo(newNode.getValue()) > 0) {
				unbalancedParentNode = unbalancedParentNode.getLeft();
				queue.offer(0);
			} else {
				unbalancedParentNode = unbalancedParentNode.getRight();
				queue.offer(1);
			}
		}
		int firstChild = queue.poll();
		int secondChild = queue.poll();
		int output = firstChild * 1 + secondChild * 2;
		switch (output) {
		case 0:
			return INSERTION_ROTATION_TYPE.LL;
		case 1:
			return INSERTION_ROTATION_TYPE.LR;
		case 2:
			return INSERTION_ROTATION_TYPE.RL;
		case 3:
			return INSERTION_ROTATION_TYPE.RR;
		}
		return null;
	}

	private void updateHeight(Node<V> node) {
		if (node != null) {
			int leftHeight = 0;
			int rightHeight = 0;
			if (node.getLeft() != null) {
				leftHeight = node.getLeft().getHeight();
			}
			if (node.getRight() != null) {
				rightHeight = node.getRight().getHeight();
			}
			node.setHeight(leftHeight - rightHeight);
		}
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
			if (isLeft)
				parentNode.setLeft(null);
			else
				parentNode.setRight(null);
			nodeToDelete.setValue(null);
		} else if (nodeToDelete.getLeft() == null) {
			if (isLeft)
				parentNode.setLeft(nodeToDelete.getRight());
			else
				parentNode.setRight(nodeToDelete.getRight());
			if (nodeToDelete.getRight() != null)
				nodeToDelete.getRight().setParent(parentNode);
			nodeToDelete.setValue(null);
			nodeToDelete.setLeft(null);
			nodeToDelete.setRight(null);
			nodeToDelete.setParent(null);
		} else if (nodeToDelete.getRight() == null) {
			if (isLeft)
				parentNode.setLeft(nodeToDelete.getLeft());
			else
				parentNode.setRight(nodeToDelete.getLeft());
			if (nodeToDelete.getLeft() != null)
				nodeToDelete.getLeft().setParent(parentNode);
			nodeToDelete.setValue(null);
			nodeToDelete.setLeft(null);
			nodeToDelete.setRight(null);
			nodeToDelete.setParent(null);
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
		private int height;
		private Node<V> parent;

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

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public Node<V> getParent() {
			return parent;
		}

		public void setParent(Node<V> parent) {
			this.parent = parent;
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