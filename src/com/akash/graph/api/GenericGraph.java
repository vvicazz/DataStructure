package com.akash.graph.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class GenericGraph<N, E> implements Graph<N, E> {

	private Set<Node<N, E>> vertices;
	private boolean isDirected;

	private static final Integer DEFAULT_VERTICES_SIZE = 16;

	public GenericGraph() {
		this(DEFAULT_VERTICES_SIZE, Boolean.FALSE);
	}

	public GenericGraph(boolean isDirected) {
		this(DEFAULT_VERTICES_SIZE, isDirected);
	}

	public GenericGraph(Integer numOfVertices) {
		this(numOfVertices, Boolean.FALSE);
	}

	public GenericGraph(Integer numOfVertices, boolean isDirected) {
		this.vertices = new HashSet<>(numOfVertices);
		this.isDirected = isDirected;
	}

	@Override
	public void createEdge(N srcData, N destData, E edgeData) {
		Node<N, E> srcNode = createNode(srcData);
		Node<N, E> destNode = createNode(destData);
		Edge<N, E> edge = new Edge<>(edgeData, srcNode, destNode, this.isDirected);
		srcNode.addEdge(edge);
		if (!this.isDirected) {
			Edge<N, E> reverseEdge = new Edge<>(edgeData, destNode, srcNode, this.isDirected);
			destNode.addEdge(reverseEdge);
		}
	}

	@Override
	public void addNode(N nodeData) {
		createNode(nodeData);
	}

	@Override
	public void removeEdge(N src, N dest) {

		Edge<N, E> edge = null;
		Node<N, E> srcNode = createNode(src);
		if (isDirected()) {
			for (Edge<N, E> tempEdge : srcNode.getAdjacencyList()) {
				if (tempEdge.getDest() != null && Objects.equals(tempEdge.getDest().getNode(), dest)) {
					edge = tempEdge;
					break;
				}
			}
		} else {
			Node<N, E> destNode = null;
			for (Edge<N, E> tempEdge : srcNode.getAdjacencyList()) {
				if (tempEdge.getDest() != null && Objects.equals(tempEdge.getDest().getNode(), dest)) {
					destNode = tempEdge.getDest();
					edge = tempEdge;
					break;
				}
			}
			if (destNode != null) {
				Edge<N, E> reverseEdge = null;
				for (Edge<N, E> tempEdge : destNode.getAdjacencyList()) {
					if (tempEdge.getDest() != null && Objects.equals(tempEdge.getSrc().getNode(), src)) {
						reverseEdge = tempEdge;
						break;
					}
				}
				if (reverseEdge != null) {
					reverseEdge.remove();
					destNode.getAdjacencyList().remove(reverseEdge);
				}
			}
		}
		if (edge != null) {
			edge.remove();
			srcNode.getAdjacencyList().remove(edge);
		}
	}

	@Override
	public boolean removeNode(N nodeData) {
		return false;
	}

	@Override
	public E findEdge(N src, N dest) {

		E edgeData = null;
		Node<N, E> srcNode = createNode(src);
		for (Edge<N, E> edge : srcNode.getAdjacencyList()) {
			if (edge.getDest() != null && Objects.equals(edge.getDest().getNode(), dest)) {
				edgeData = edge.getEdgeData();
				break;
			}
		}
		return edgeData;
	}

	@Override
	public Set<N> findAdjacentVertices(N nodeData) {

		Set<N> adjacentNodes = new HashSet<>();
		Node<N, E> srcNode = createNode(nodeData);
		for (Edge<N, E> edge : srcNode.getAdjacencyList()) {
			if (edge.getDest() != null) {
				adjacentNodes.add(edge.getDest().getNode());
			}
		}
		adjacentNodes.remove(null);
		return adjacentNodes;
	}

	Set<Node<N, E>> findAdjacentNodes(N nodeData) {

		Set<Node<N, E>> adjacentNodes = new HashSet<>();
		Node<N, E> srcNode = createNode(nodeData);
		for (Edge<N, E> edge : srcNode.getAdjacencyList()) {
			adjacentNodes.add(edge.getDest());
		}
		adjacentNodes.remove(null);
		return adjacentNodes;
	}

	@Override
	public boolean existPath(N src, N dest) {
		return false;
	}

	@Override
	public boolean isCyclic() {
		return false;
	}

	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public boolean isComplete() {
		return false;
	}

	@Override
	public boolean isDirected() {
		return isDirected;
	}

	private Node<N, E> findNode(N nodeData) {
		Node<N, E> node = new Node<>(nodeData);
		if (vertices.contains(node)) {
			for (Node<N, E> tempNode : vertices) {
				if (Objects.equals(tempNode, node)) {
					node = tempNode;
					return node;
				}
			}
		}
		return null;
	}

	private Node<N, E> createNode(N nodeData) {
		Node<N, E> node = findNode(nodeData);
		if (node == null) {
			node = new Node<>(nodeData);
			vertices.add(node);
		}
		return node;
	}

	private static class Node<N, E> {

		private N node;
		volatile private Set<Edge<N, E>> adjacencyList;

		Node(N node) {
			this.node = node;
		}

		synchronized void addEdge(Edge<N, E> edge) {
			if (adjacencyList == null) {
				adjacencyList = new ConcurrentSkipListSet<>();
			}
			adjacencyList.add(edge);
		}

		public N getNode() {
			return node;
		}

		public Set<Edge<N, E>> getAdjacencyList() {
			if (adjacencyList == null) {
				return Collections.emptySet();
			}
			return adjacencyList;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((node == null) ? 0 : node.hashCode());
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node<N, E> other = (Node<N, E>) obj;
			return Objects.equals(node, other.node);
		}
	}

	private static class Edge<N, E> {

		private E edgeData;
		private Node<N, E> src;
		private Node<N, E> dest;
		private boolean isDirected;

		Edge(E edgeData, Node<N, E> src, Node<N, E> dest, boolean isDirected) {
			this.edgeData = edgeData;
			this.dest = dest;
			this.src = src;
			this.isDirected = isDirected;
		}

		public Node<N, E> getSrc() {
			return src;
		}

		public E getEdgeData() {
			return edgeData;
		}

		public Node<N, E> getDest() {
			return dest;
		}

		public void remove() {
			this.edgeData = null;
			this.src = null;
			this.dest = null;
		}

		@Override
		public int hashCode() {

			final int prime = 31;
			int result = 1;
			result = prime * result + ((edgeData == null) ? 0 : edgeData.hashCode());
			result = prime * result + ((src == null) ? 0 : src.hashCode());
			result = prime * result + ((dest == null) ? 0 : dest.hashCode());
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {

			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Edge<N, E> other = (Edge<N, E>) obj;
			if (isDirected) {
				return Objects.equals(src, other.src) && Objects.equals(dest, other.dest)
						&& Objects.equals(edgeData, other.edgeData);
			} else {
				return ((Objects.equals(src, other.src) && Objects.equals(dest, other.dest)) || (Objects.equals(src,
						other.dest) && Objects.equals(dest, other.src))) && Objects.equals(edgeData, other.edgeData);
			}
		}
	}
}