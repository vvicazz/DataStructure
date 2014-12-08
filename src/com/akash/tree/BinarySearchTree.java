package com.akash.tree;

/**
 * All operations in BST <br> 
 * 1. Adding new element <br> 
 * 2. Search an element <br> 
 * 3. Remove an element <br> 
 * 
 * will take a time complexity = height of the tree <br> 
 * 
 * Avg case   : height = log(n)
 * Worst case : height = n
 * 
 * @author Akash Sharma
 *
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable> extends AbstractBinaryTree<T> {

	public BinarySearchTree(T t) {
		super(t);
	}

	public void addNode(T t) {
		addNode(getRoot(), t);
	}
	
	private void addNode(Node<T> node, T t) {
		if(node != null) {
			if(node.getValue().compareTo(t) < 0) {
				Node<T> tempNode = node.getRight();
				if(tempNode==null) {
					tempNode = createNode(t);
					node.setRight(tempNode);
				}
				else
					addNode(node.getRight(), t);
			}
			else {
				Node<T> tempNode = node.getLeft();
				if(tempNode==null) {
					tempNode = createNode(t);
					node.setLeft(tempNode);
				}
				else
					addNode(node.getLeft(), t);
			}
		}
	}

	@Override
	public boolean delete(T t) {
		Node<T> parentNode = searchNode(getRoot(), t);
		if(parentNode != null) {
			if(parentNode.getValue().compareTo(t) < 0) 
				deleteNode(parentNode, parentNode.getRight(), false);
			else 
				deleteNode(parentNode, parentNode.getLeft(), true);
			return true;
		}
		return false;
	}
	
	/**
	 * We have to find inorder successor for the node to delete if it has two childs.
	 * @param parentNode
	 * @param nodeToDelete
	 * @param isLeft
	 */
	private void deleteNode(Node<T> parentNode, Node<T> nodeToDelete, boolean isLeft) {
		if(nodeToDelete.getLeft()==null && nodeToDelete.getRight()==null) {
			if(isLeft)
				parentNode.setLeft(null);
			else
				parentNode.setRight(null);
		}
		else if(nodeToDelete.getLeft()!=null && nodeToDelete.getRight()==null) {
			if(isLeft)
				parentNode.setLeft(nodeToDelete.getLeft());
			else
				parentNode.setRight(nodeToDelete.getLeft());
		}
		else if(nodeToDelete.getRight()!=null && nodeToDelete.getLeft()==null) {
			if(isLeft)
				parentNode.setLeft(nodeToDelete.getRight());
			else
				parentNode.setRight(nodeToDelete.getRight());
		}
		else {
			Node<T> tempNode1 = nodeToDelete.getRight();
			if(tempNode1!=null){
				Node<T> leftNode = tempNode1;
				while(leftNode != null && leftNode.getLeft() != null) {
					tempNode1 = leftNode;
					leftNode = leftNode.getLeft();
				}
				nodeToDelete.setValue(leftNode.getValue());
				tempNode1.setLeft(null);
			}
		}
	}
	
	public Node<T> find(T t) {
		return searchNode(getRoot(), t);
	}
	
	private Node<T> searchNode(Node<T> node, T t) {
		Node<T> tempNode = null;
		if(node != null) {
			if(node.getValue().compareTo(t) < 0) {
				tempNode = node.getRight();
				if(tempNode==null) 
					tempNode = node;
				else
					tempNode = searchNode(tempNode, t);
			}
			else {
				tempNode = node.getLeft();
				if(tempNode==null) 
					tempNode = node;
				else
					tempNode = searchNode(tempNode, t);
			}
		}
		return tempNode;
	}
}
