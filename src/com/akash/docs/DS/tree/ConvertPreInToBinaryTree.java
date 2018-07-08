package com.akash.docs.DS.tree;

import java.util.LinkedList;
import java.util.Queue;

class ConvertPreInToBinaryTree {

	private int in[];
	private int pre[];

	public static void main(String args[]) {
		ConvertPreInToBinaryTree ob = new ConvertPreInToBinaryTree();
		int inOrder[] = { 4, 2, 5, 1, 6, 3, 7 };
		int preOrder[] = { 1, 2, 4, 5, 3, 6, 7 };
		Node root = ob.create(inOrder, preOrder);
		ob.printNodesByLevel(root);
	}

	public Node create(int in[], int pre[]) {
		this.in = in;
		this.pre = pre;
		return create(0, in.length - 1, 0, pre.length - 1);
	}

	// Approach : find root node in Inorder array,
	// then find right most node in left subtree in Inorder array
	// search that element in PreOrder
	//
	// Similar approach can be used to create binary tree
	// using Inorder and Postorder traversal
	// find root in inorder,then find left most node in right subtree
	// search that element in Post order
	//
	// worst case time complexity will be O(n2), for example when tree is left
	// skewed
	private Node create(int inStart, int inEnd, int preStart, int preEnd) {
		if (inEnd > inStart && preEnd > preStart) {
			int rootData = pre[preStart];
			Node root = newNode(rootData);
			int inorderRootIndex = search(in, rootData, inStart, inEnd);

			int rightMostInLeftSubtreeIndex = preStart;
			// inorderRootIndex == inStart , no left subtree tree
			if (inorderRootIndex != inStart) {
				int rightMostInLeftSubtree = in[inorderRootIndex - 1];
				rightMostInLeftSubtreeIndex = search(pre, rightMostInLeftSubtree, preStart + 1, preEnd);
				root.left = create(inStart, inorderRootIndex - 1, preStart + 1, rightMostInLeftSubtreeIndex);
			}

			// inorderRootIndex == inEnd , no right subtree tree
			if (inorderRootIndex != inEnd) {
				root.right = create(inorderRootIndex + 1, inEnd, rightMostInLeftSubtreeIndex + 1, preEnd);
			}
			return root;
		} else if (preEnd == preStart) {
			return newNode(pre[preStart]);
		}
		return null;
	}

	private int search(int arr[], int data, int start, int end) {
		for (int i = start; i <= end; i++) {
			if (arr[i] == data) {
				return i;
			}
		}
		return -1;
	}

	private Node newNode(int data) {
		return new Node(data);
	}

	private static class Node {
		private Node left;
		private Node right;
		private int value;

		Node(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "" + value;
		}
	}

	public void printNodesByLevel(Node root) {
		Queue<Node> queue = new LinkedList<Node>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			int nodeCount = queue.size();
			while (nodeCount > 0) {
				nodeCount--;
				Node temp = queue.poll();
				System.out.print(temp.value + " ");
				if (temp.left != null) {
					queue.offer(temp.left);
				}
				if (temp.right != null) {
					queue.offer(temp.right);
				}
			}
			System.out.println();
		}
	}
}
