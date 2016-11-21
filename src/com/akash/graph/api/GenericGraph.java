package com.akash.graph.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GenericGraph<N, E extends Comparable<E>> implements Graph<N, E> {

	private Set<Node<N, E>> vertices;
	private boolean isDirected;
	volatile private int numOfEdges = 0;

	public static final Integer DEFAULT_VERTICES_SIZE = 16;

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
		if (numOfVertices > 0) {
			this.vertices = new HashSet<>(numOfVertices);
		}
		this.isDirected = isDirected;
	}

	public GenericGraph(Graph<N, E> graph) {
		this(graph.getNumOfVertices(), graph.isDirected());
		Set<N> vertices = graph.getAllVertices();
		vertices.forEach(nodeData -> {
			this.addNode(nodeData);
		});
		for (N tempNodeOuter : vertices) {
			for (N tempNodeInner : vertices) {
				if (!tempNodeOuter.equals(tempNodeInner)) {
					E edgeData = this.findEdge(tempNodeOuter, tempNodeInner);
					if (edgeData != null) {
						this.addEdge(tempNodeOuter, tempNodeInner, edgeData);
					}
				}
			}
		}
	}

	@Override
	public void addEdge(N srcData, N destData, E edgeData) {
		Node<N, E> srcNode = createNode(srcData);
		Node<N, E> destNode = createNode(destData);
		Edge<N, E> edge = doCreateEdge(edgeData, srcNode, destNode, this.isDirected);
		srcNode.addEdge(edge);
		if (!this.isDirected) {
			Edge<N, E> reverseEdge = doCreateEdge(edgeData, destNode, srcNode, this.isDirected);
			destNode.addEdge(reverseEdge);
		}
		numOfEdges++;
	}

	@Override
	public void addNode(N nodeData) {
		createNode(nodeData);
	}

	@Override
	public void removeEdge(N src, N dest) {
		Edge<N, E> edge = null;
		Node<N, E> srcNode = findNode(src);
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
			numOfEdges--;
		}
	}

	@Override
	public void removeNode(N nodeData) {
		removeNode(findNode(nodeData));
	}

	@Override
	public void removeGraph() {
		for (Node<N, E> node : getAllNodes()) {
			removeNode(node);
		}
	}

	@Override
	public Set<N> getAllVertices() {
		return getAllNodes().stream().map(node -> node.getNode()).collect(Collectors.toSet());
	}

	@Override
	public E findEdge(N src, N dest) {
		E edgeData = null;
		Node<N, E> srcNode = findNode(src);
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
		Node<N, E> srcNode = findNode(nodeData);
		for (Edge<N, E> edge : srcNode.getAdjacencyList()) {
			if (edge.getDest() != null) {
				adjacentNodes.add(edge.getDest().getNode());
			}
		}
		adjacentNodes.remove(null);
		return adjacentNodes;
	}

	@Override
	public int getNumOfVertices() {
		return getAllNodes().size();
	}

	@Override
	public int getNumOfEdges() {
		return numOfEdges;
	}

	@Override
	public boolean existPath(N src, N dest) {
		return false;
	}

	@Override
	public boolean isCyclic() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isStronglyConnected() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isComplete() {
		int numOfVertices = getNumOfVertices();
		if (!isDirected()) {
			return getNumOfEdges() == (numOfVertices * (numOfVertices - 1) / 2);
		} else {
			return getNumOfEdges() == (numOfVertices * (numOfVertices - 1));
		}
	}

	@Override
	public boolean isDirected() {
		return Boolean.TRUE.equals(isDirected);
	}

	@Override
	public boolean Bipartite() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getNumberOfConnectedComponents() {
		throw new UnsupportedOperationException();
	}

	private void removeNode(Node<N, E> node) {
		// TODO : different implementations for directed and undirected
	}

	protected Node<N, E> findNode(N nodeData) {
		for (Node<N, E> tempNode : getAllNodes()) {
			if (tempNode != null && Objects.equals(tempNode.getNode(), nodeData)) {
				return tempNode;
			}
		}
		return null;
	}

	protected Node<N, E> createNode(N nodeData) {
		Node<N, E> node = findNode(nodeData);
		if (node == null) {
			node = doCreateNode(nodeData);
			addNodeToVertices(node);
		}
		return node;
	}

	protected Node<N, E> doCreateNode(N nodeData) {
		return new Node<>(nodeData);
	}

	protected Edge<N, E> doCreateEdge(E edgeData, Node<N, E> src, Node<N, E> dest, boolean isDirected) {
		return new Edge<>(edgeData, src, dest, isDirected);
	}

	protected void addNodeToVertices(Node<N, E> node) {
		vertices.add(node);
	}

	protected Set<? extends Node<N, E>> getAllNodes() {
		return vertices;
	}

	protected Set<? extends Node<N, E>> findAdjacentNodes(Node<N, E> srcNode) {
		Set<Node<N, E>> adjacentNodes = new HashSet<>();
		for (Edge<N, E> edge : srcNode.getAdjacencyList()) {
			adjacentNodes.add(edge.getDest());
		}
		adjacentNodes.remove(null);
		return adjacentNodes;
	}

	@Override
	public String toString() {
		return "Vertices = [" + getAllNodes() + "] , isDirected : " + isDirected();
	}

	static class Node<N, E> {

		private N node;
		volatile private Set<Edge<N, E>> adjacencyList;

		Node(N node) {
			if (node == null)
				throw new NullPointerException();
			this.node = node;
		}

		public N getNode() {
			return node;
		}

		public void setNode(N node) {
			this.node = node;
		}

		public synchronized void addEdge(Edge<N, E> edge) {
			doAddEdge(edge);
		}

		public synchronized Set<? extends Edge<N, E>> getAdjacencyList() {
			return doGetAdjacencyList();
		}

		protected void doAddEdge(Edge<N, E> edge) {
			if (adjacencyList == null) {
				adjacencyList = Collections.newSetFromMap(new ConcurrentHashMap<>());
			}
			adjacencyList.add(edge);
		}

		protected Set<? extends Edge<N, E>> doGetAdjacencyList() {
			if (adjacencyList == null) {
				return Collections.emptySet();
			}
			return adjacencyList;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((getNode() == null) ? 0 : getNode().hashCode());
			result = prime * result + getAdjacencyList().size();
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
			return Objects.equals(getNode(), other.getNode())
					&& (other.getAdjacencyList().size() == this.getAdjacencyList().size());
		}

		@Override
		public String toString() {
			return getNode().toString() + getAdjacencyList();
		}
	}

	static class Edge<N, E> {

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

		public E getEdgeData() {
			return edgeData;
		}

		public void setEdgeData(E edgeData) {
			this.edgeData = edgeData;
		}

		public Node<N, E> getSrc() {
			return src;
		}

		public void setSrc(Node<N, E> src) {
			this.src = src;
		}

		public Node<N, E> getDest() {
			return dest;
		}

		public void setDest(Node<N, E> dest) {
			this.dest = dest;
		}

		public boolean isDirected() {
			return isDirected;
		}

		public void setDirected(boolean isDirected) {
			this.isDirected = isDirected;
		}

		public void remove() {
			setEdgeData(null);
			setSrc(null);
			setDest(null);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((getEdgeData() == null) ? 0 : getEdgeData().hashCode());
			result = prime * result + ((getSrc() == null) ? 0 : getSrc().hashCode());
			result = prime * result + ((getDest() == null) ? 0 : getDest().hashCode());
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
			if (isDirected()) {
				return Objects.equals(getSrc(), other.getSrc()) && Objects.equals(getDest(), other.getDest())
						&& Objects.equals(getEdgeData(), other.getEdgeData());
			} else {
				return ((Objects.equals(getSrc(), other.getSrc()) && Objects.equals(getDest(), other.getDest())) || (Objects
						.equals(getSrc(), other.getDest()) && Objects.equals(getDest(), other.getSrc())))
						&& Objects.equals(getEdgeData(), other.getEdgeData());
			}
		}

		@Override
		public String toString() {
			return "{src : " + getSrc().getNode().toString() + "-----" + getEdgeData().toString() + " -----> dest : "
					+ getDest().getNode().toString() + " , isDirected : " + isDirected() + " }";
		}
	}
}