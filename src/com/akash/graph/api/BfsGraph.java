package com.akash.graph.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BfsGraph<N, E> extends GenericGraph<N, E> {

	private enum NodeColor {
		WHITE, GRAY, BLACK;
	}

	public BfsGraph() {
		super();
	}

	public BfsGraph(boolean isDirected) {
		super(isDirected);
	}

	public BfsGraph(Graph<N, E> graph) {
		super(graph);
	}

	public BfsGraph(Integer numOfVertices) {
		this(numOfVertices, Boolean.FALSE);
	}

	public BfsGraph(Integer numOfVertices, boolean isDirected) {
		super(numOfVertices, isDirected);
	}

	private Queue<BfsNode<N, E>> queue = new LinkedList<>();

	public void executeBfs(N src) {

		BfsNode<N, E> srcNode = createNode(src);
		srcNode.setColor(NodeColor.GRAY);
		srcNode.setDistance(0);
		srcNode.setParent(null);
		queue.offer(srcNode);
		while (!queue.isEmpty()) {
			BfsNode<N, E> bfsNode = queue.poll();
			if (bfsNode != null) {
				for (BfsNode<N, E> tempNode : findBfsAdjacentNodes(bfsNode)) {
					if (tempNode.getColor() == NodeColor.WHITE) {
						tempNode.setColor(NodeColor.GRAY);
						tempNode.setDistance(tempNode.getDistance() + 1);
						tempNode.setParent(tempNode);
						queue.offer(tempNode);
					}
				}
			}
		}
	}

	Set<BfsNode<N, E>> findBfsAdjacentNodes(BfsNode<N, E> srcNode) {

		Set<BfsNode<N, E>> adjacentNodes = new HashSet<>();
		if (srcNode != null) {
			for (BfsEdge<N, E> edge : srcNode.getBfsAdjacencyList()) {
				adjacentNodes.add(edge.getDest());
			}
			adjacentNodes.remove(null);
		}
		return adjacentNodes;
	}

	BfsNode<N, E> createNode(N nodeData) {
		return (BfsNode<N, E>) super.createNode(nodeData);
	}

	BfsNode<N, E> doCreateNode(N nodeData) {
		return new BfsNode<>(nodeData);
	}

	@Override
	public String toString() {
		return "" + getAllVertices() + " , isDirected : " + isDirected();
	}

	static class BfsNode<N, E> extends GenericGraph.Node<N, E> {

		private NodeColor color = NodeColor.WHITE;
		private Integer distance = Integer.MAX_VALUE;
		private Node<N, E> parent;
		volatile private Set<BfsEdge<N, E>> bfsAdjacencyList;

		BfsNode(N node) {
			super(node);
		}

		synchronized void addEdge(BfsEdge<N, E> edge) {
			if (bfsAdjacencyList == null) {
				bfsAdjacencyList = Collections.newSetFromMap(new ConcurrentHashMap<>());
			}
			bfsAdjacencyList.add(edge);
		}

		synchronized Set<BfsEdge<N, E>> getBfsAdjacencyList() {
			if (bfsAdjacencyList == null) {
				return Collections.emptySet();
			}
			return bfsAdjacencyList;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((getNode() == null) ? 0 : getNode().hashCode());
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
			return Objects.equals(getNode(), other.getNode());
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

		@Override
		public String toString() {
			return "{node : " + getNode().toString() + "}";
		}
	}

	static class BfsEdge<N, E> extends GenericGraph.Edge<N, E> {

		private BfsNode<N, E> src;
		private BfsNode<N, E> dest;

		BfsEdge(E edgeData, BfsNode<N, E> src, BfsNode<N, E> dest, boolean isDirected) {
			super(edgeData, src, dest, isDirected);
			this.setSrc(src);
			this.setDest(dest);
		}

		public BfsNode<N, E> getSrc() {
			return src;
		}

		public void setSrc(BfsNode<N, E> src) {
			this.src = src;
		}

		public BfsNode<N, E> getDest() {
			return dest;
		}

		public void setDest(BfsNode<N, E> dest) {
			this.dest = dest;
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
			return "edge = " + getEdgeData().toString() + " , src = " + getSrc().toString() + " , dest = "
					+ getDest().toString() + " , isDirected = " + isDirected();
		}
	}
}