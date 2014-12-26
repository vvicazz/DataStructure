package com.akash.tree;

/**
 * AVL tree makes the binary tree balanced.	<br><br>
 * 
 * Height of Tree   = height of root node = length of longest path from root to leaf	<br>
 * 
 * Height of a Node = length of longest path from that node to leaf	
 * 					= 1 + MAX {
 * 								height of left child ,
 * 								height of right child
 * 								}
 * 
 * 	<br>
 * 
 *  AVL requires heights of left and right child of every node in the tree,
 *   must have a difference of {-1,0,+1} .	<br><br>
 * 
 * Here we maintain height of each node with node itself (as nodeHeight). <br>
 * While adding or deleting a node from tree, we update the height of each node coming in that path.
 * 
 * @author Akash
 * @param <T>
 */
public class AvlTree<T extends Comparable> extends BinarySearchTree<T> {

	public AvlTree(T t) {
		super(t);
		getRoot().setNodeHeight(0);
	}
	
	private void updateHeight(Node<T> node, int updateValue) {
		Integer height=node.getNodeHeight();
		if(height!=null) {
			node.setNodeHeight(height + updateValue);
		}
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
				else {
					addNode(node.getRight(), t);
				}
			}
			else {
				Node<T> tempNode = node.getLeft();
				if(tempNode==null) {
					tempNode = createNode(t);
					node.setLeft(tempNode);
				}
				else {
					addNode(node.getLeft(), t);
				}
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
}