package com.akash.docs.DS.tree;

import java.util.Stack;

public class PostOrderWithoutRecursion {

	public static void main(String args[]) {
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		Node five = new Node(5);
		Node six = new Node(6);
		Node seven = new Node(7);
		Node eight = new Node(8);
		Node nine = new Node(9);
		Node ten = new Node(10);
		Node eleven = new Node(11);
		one.setLeft(two);
		one.setRight(three);
		two.setLeft(four);
		two.setRight(five);
		four.setRight(eight);
		five.setLeft(nine);
		five.setRight(ten);
		three.setLeft(six);
		three.setRight(seven);
		seven.setLeft(eleven);
		postOrder(one);
	}

	// this algorithm is similar to Inorder traversal without recursion
	// the only difference is : extra if condition in while loop
	// see isRightChildProcessed
	public static void postOrder(Node node) {
		PostOrderPojo pojoNode = new PostOrderPojo(node);
		Stack<PostOrderPojo> stack = new Stack<>();
		stack.push(pojoNode);
		while (pojoNode.getLeft() != null) {
			pojoNode = new PostOrderPojo(pojoNode.getLeft());
			stack.push(pojoNode);
		}
		while (!stack.isEmpty()) {
			pojoNode = stack.peek();
			if (pojoNode.isRightChildProcessed()) {
				pojoNode = stack.pop();
				pojoNode.getNode().process();
			} else {
				pojoNode.processRightChild();
				if (pojoNode.getRight() != null) {
					pojoNode = new PostOrderPojo(pojoNode.getRight());
					stack.push(pojoNode);
					while (pojoNode.getLeft() != null) {
						pojoNode = new PostOrderPojo(pojoNode.getLeft());
						stack.push(pojoNode);
					}
				}
			}
		}
	}

	static class PostOrderPojo {
		private Node node;

		/**
		 * when we check an item at stack top, <br>
		 * its left subtree is already processed <br>
		 * Now we need to process its right subtree <br>
		 * if right subtree is not present or already processed <br>
		 * then pop the node from stack and process it.
		 */
		private boolean isRightChildProcessed;

		PostOrderPojo(Node node) {
			this.node = node;
			isRightChildProcessed = false;
		}

		public Node getNode() {
			return node;
		}

		public void processRightChild() {
			isRightChildProcessed = true;
		}

		public boolean isRightChildProcessed() {
			return isRightChildProcessed;
		}

		public Node getLeft() {
			return getNode().getLeft();
		}

		public Node getRight() {
			return getNode().getRight();
		}
	}

	public static class Node {
		private Node left;
		private Node right;
		private Integer value;

		Node(Integer value) {
			this.value = value;
		}

		public void process() {
			System.out.print(" " + value + " ");
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		public Integer getValue() {
			return value;
		}
	}
}