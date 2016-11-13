package com.akash.graph.api;

public class BfsGraph<N, E> extends GenericGraph<N, E> {

	private enum NodeColor {
		WHITE, GRAY, BLACK;
	}

	static class BfsNode<N, E> extends GenericGraph.Node<N, E> {

		private NodeColor color = NodeColor.WHITE;
		private Integer distance = Integer.MAX_VALUE;
		private Node<N, E> parent;

		BfsNode(N node) {
			super(node);
		}

		public NodeColor getColor() {
			return color;
		}

		public void setColor(NodeColor color) {
			this.color = color;
		}

		public Integer getDistance() {
			return distance;
		}

		public void setDistance(Integer distance) {
			this.distance = distance;
		}

		public Node<N, E> getParent() {
			return parent;
		}

		public void setParent(Node<N, E> parent) {
			this.parent = parent;
		}
	}
}