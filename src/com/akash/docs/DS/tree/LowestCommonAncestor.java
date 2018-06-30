package com.akash.docs.DS.tree;

/**
 * multiple approaches: <br>
 * 1. findPath(Node,Node,List<Node>) -> <br>
 * recursively find full path of a node in a tree and capture it in a list. <br>
 * time = O(n) <br>
 * memory = O(n) <br>
 * 2. do any traversal on tree and store parent of each node in a hashmap <br>
 * time = O(n) <br>
 * memory = O(n) <br>
 * 3. Every node should return a status object of two boolean variables <br>
 * every node should tell if its subtree(including itself), contains both nodes
 * <br>
 */
public class LowestCommonAncestor {

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
		one.setLeft(two);
		one.setRight(three);
		two.setLeft(four);
		two.setRight(five);
		three.setLeft(six);
		three.setRight(seven);
		four.setLeft(eight);
		four.setRight(nine);
		LowestCommonAncestor obj = new LowestCommonAncestor();
		Node node1 = eight;
		Node node2 = four;
		obj.findLca(one, node1, node2, new NodeStatus(false, false));
		if (obj.LCA != null) {
			System.out.println("Lca of " + node1 + " and " + node2 + " = " + obj.LCA);
		} else {
			System.out.println("Lca of " + node1 + " and " + node2 + " = null");
		}
	}

	private static class NodeStatus {
		private boolean containsN1;
		private boolean containsN2;

		NodeStatus(boolean containsN1, boolean containsN2) {
			this.containsN1 = containsN1;
			this.containsN2 = containsN2;
		}
	}

	private Node LCA = null;

	private boolean checkIfBothNodesFound(NodeStatus obj1, NodeStatus obj2) {
		return (obj1.containsN1 && obj2.containsN2) || (obj1.containsN2 && obj2.containsN1);
	}

	private NodeStatus currentWithParentStatus(NodeStatus obj1, NodeStatus obj2) {
		return new NodeStatus(obj1.containsN1 || obj2.containsN1, obj1.containsN2 || obj2.containsN2);
	}

	public NodeStatus findLca(Node root, Node node1, Node node2, NodeStatus parentStatus) {
		NodeStatus currentStatus = new NodeStatus(false, false);
		if (node1 == null || node2 == null) {
			return currentStatus;
		}
		if (!parentStatus.containsN1 && root.getValue() == node1.getValue()) {
			currentStatus.containsN1 = true;
		} else if (!parentStatus.containsN2 && root.getValue() == node2.getValue()) {
			currentStatus.containsN2 = true;
		}
		if (checkIfBothNodesFound(parentStatus, currentStatus))
			return currentStatus;

		if (root.getLeft() != null) {
			NodeStatus childStatus = findLca(root.getLeft(), node1, node2,
					currentWithParentStatus(currentStatus, parentStatus));
			if (childStatus.containsN1)
				currentStatus.containsN1 = childStatus.containsN1;
			if (childStatus.containsN2)
				currentStatus.containsN2 = childStatus.containsN2;
			if (checkIfBothNodesFound(currentStatus, childStatus)) {
				if (LCA == null) {
					LCA = root;
				}
				return currentStatus;
			}
			if (checkIfBothNodesFound(currentWithParentStatus(currentStatus, parentStatus), childStatus)) {
				return currentStatus;
			}
		}

		if (root.getRight() != null) {
			NodeStatus childStatus = findLca(root.getRight(), node1, node2,
					currentWithParentStatus(currentStatus, parentStatus));
			if (childStatus.containsN1)
				currentStatus.containsN1 = childStatus.containsN1;
			if (childStatus.containsN2)
				currentStatus.containsN2 = childStatus.containsN2;
			if (checkIfBothNodesFound(currentStatus, childStatus)) {
				if (LCA == null) {
					LCA = root;
				}
				return currentStatus;
			}
			if (checkIfBothNodesFound(currentWithParentStatus(currentStatus, parentStatus), childStatus)) {
				return currentStatus;
			}
		}
		return currentStatus;
	}

	private static class Node {
		private Node left;
		private Node right;
		private int value;

		Node(int value) {
			this.value = value;
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

		public int getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "" + value;
		}
	}
}