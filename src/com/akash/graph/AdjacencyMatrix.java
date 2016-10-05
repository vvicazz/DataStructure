package com.akash.graph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 
 * maintain list of all nodes in a set <br>
 * Each node will contain a list of Edge containing : <br>
 * ->adjacency node pointer <br>
 * ->weight for edge
 */
public class AdjacencyMatrix<N> {

	private static final Integer ZERO_WEIGHT = new Integer(0);

	private Set<Node<N>> vertices = new HashSet<Node<N>>();
	private int numberOfEdges;

	public boolean addEdge(N startNode, N endNode) {
		Node<N> start = new Node<N>(startNode);
		Node<N> end = new Node<N>(endNode);
		vertices.add(start);
		if (start.addEdge(end)) {
			numberOfEdges++;
			return true;
		}
		return false;
	}

	public boolean isEdgeAvailable(N startNode, N endNode) {
		Node<N> start = new Node<N>(startNode);
		Node<N> end = new Node<N>(endNode);
		if (vertices.contains(startNode)) {
			return start.hasEdge(end);
		}
		return false;
	}

	public boolean deleteEdge(N startNode, N endNode) {
		Node<N> start = new Node<N>(startNode);
		Node<N> end = new Node<N>(endNode);
		if (vertices.contains(startNode) && start.removeEdge(end)) {
			numberOfEdges--;
			return true;
		}
		return false;
	}

	private static class Node<N> {

		private N node;
		private Set<Edge<N>> edges = new HashSet<Edge<N>>();

		Node(N node) {
			if (node == null) {
				throw new NullPointerException();
			}
			this.node = node;
		}

		public N getNode() {
			return node;
		}

		public Set<Edge<N>> getEdges() {
			return edges;
		}

		boolean addEdge(Node<N> endNode) {
			return getEdges().add(new Edge<N>(endNode, ZERO_WEIGHT));
		}

		boolean removeEdge(Node<N> endNode) {
			return getEdges().remove(new Edge<N>(endNode, ZERO_WEIGHT));
		}

		boolean hasEdge(final Node<N> endNode) {
			return findEdge(endNode).isPresent();
		}

		private Optional<Edge<N>> findEdge(final Node<N> endNode) {
			return getEdges().stream().filter(edge -> Objects.equals(endNode, edge)).findFirst();
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + getNode().hashCode();
			return result;
		}

		@Override
		public boolean equals(Object ob) {
			if (ob == null) {
				return false;
			} else if (!ob.getClass().equals(this.getClass())) {
				return false;
			}
			if (ob == this) {
				return true;
			} else {
				Node<N> ob1 = (Node) ob;
				return Objects.equals(ob1.getNode(), this.getNode());
			}
		}

		@Override
		public String toString() {
			return "Node[" + getNode() + "]";
		}

		private static class Edge<N> {

			private Node<N> endNode;
			private Number weight;

			Edge(Node<N> node, Number weight) {
				if (node == null) {
					throw new NullPointerException();
				}
				this.endNode = node;
				this.weight = weight;
			}

			public Node<N> getNode() {
				return endNode;
			}

			public Number getWeight() {
				return weight;
			}

			@Override
			public int hashCode() {
				int result = 17;
				result = 31 * result + getNode().hashCode();
				if (getWeight() != null) {
					result = 31 * result + getWeight().hashCode();
				}
				return result;
			}

			@Override
			public boolean equals(Object ob) {

				if (ob == null) {
					return false;
				} else if (!ob.getClass().equals(this.getClass())) {
					return false;
				}
				if (ob == this) {
					return true;
				} else {
					Edge<N> ob1 = (Edge<N>) ob;
					if (!Objects.equals(ob1.getNode(), this.getNode())) {
						return false;
					} else {
						if (this.getWeight() == null && ob1.getWeight() == null) {
							return true;
						} else {
							return Objects.equals(ob1.getWeight(), this.getWeight());
						}
					}
				}
			}

			@Override
			public String toString() {
				return "Edge[" + getNode() + "--" + getWeight() == null ? "" : getWeight() + "-->" + getNode() + "]";
			}
		}

	}
}