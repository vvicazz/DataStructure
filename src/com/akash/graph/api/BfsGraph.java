package com.akash.graph.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BfsGraph<N, E extends Comparable<E>> extends GenericGraph<N, E> {

	private Set<BfsNode<N, E>> vertices;

	private enum NodeColor {
		WHITE, GRAY, BLACK;
	}

	public BfsGraph() {
		this(GenericGraph.DEFAULT_VERTICES_SIZE, Boolean.FALSE);
	}

	public BfsGraph(boolean isDirected) {
		this(GenericGraph.DEFAULT_VERTICES_SIZE, isDirected);
	}

	public BfsGraph(Graph<N, E> graph) {
		super(graph);
	}

	public BfsGraph(Integer numOfVertices) {
		this(numOfVertices, Boolean.FALSE);
	}

	public BfsGraph(Integer numOfVertices, boolean isDirected) {
		super(0, isDirected);
		this.vertices = new HashSet<>(numOfVertices);
	}

	public void executeBfs(N src) {

		if (src == null)
			throw new NullPointerException();
		Queue<BfsNode<N, E>> queue = new LinkedList<>();
		BfsNode<N, E> srcNode = findNode(src);
		if (srcNode != null) {
			srcNode.setColor(NodeColor.GRAY);
			srcNode.setDistance(0);
			srcNode.setParent(null);
			queue.offer(srcNode);
			while (!queue.isEmpty()) {
				BfsNode<N, E> bfsNode = queue.poll();
				if (bfsNode != null) {
					for (BfsNode<N, E> tempNode : findAdjacentNodes(bfsNode)) {
						if (tempNode.getColor() == NodeColor.WHITE) {
							tempNode.setColor(NodeColor.GRAY);
							tempNode.setDistance(bfsNode.getDistance() + 1);
							tempNode.setParent(bfsNode);
							queue.offer(tempNode);
						}
					}
					bfsNode.setColor(NodeColor.BLACK);
				}
			}
		}
	}

	@Override
	public boolean existPath(N src, N dest) {

		if(Objects.equals(src, dest)) {
			return true;
		}
		Queue<BfsNode<N, E>> queue = new LinkedList<>();
		BfsNode<N, E> srcNode = findNode(src);
		if (srcNode != null) {
			srcNode.setColor(NodeColor.GRAY);
			srcNode.setDistance(0);
			srcNode.setParent(null);
			queue.offer(srcNode);
			while (!queue.isEmpty()) {
				BfsNode<N, E> bfsNode = queue.poll();
				if (bfsNode != null) {
					for (BfsNode<N, E> tempNode : findAdjacentNodes(bfsNode)) {
						if (Objects.equals(tempNode.getNode(), dest)) {
							return true;
						}
						if (tempNode.getColor() == NodeColor.WHITE) {
							tempNode.setColor(NodeColor.GRAY);
							tempNode.setDistance(bfsNode.getDistance() + 1);
							tempNode.setParent(bfsNode);
							queue.offer(tempNode);
						}
					}
					bfsNode.setColor(NodeColor.BLACK);
				}
			}
		}
		return false;
	}

	@Override
	public int getNumberOfConnectedComponents() {
		// TODO :
		return 0;
	}

	@Override
	protected void addNodeToVertices(Node<N, E> node) {
		BfsNode<N, E> obj = (BfsNode<N, E>) node;
		vertices.add(obj);
	}

	@Override
	protected Set<? extends Node<N, E>> getAllNodes() {
		return vertices;
	}

	@Override
	protected BfsNode<N, E> findNode(N nodeData) {
		return (BfsNode<N, E>) super.findNode(nodeData);
	}

	@Override
	protected BfsEdge<N, E> doCreateEdge(E edgeData, Node<N, E> src, Node<N, E> dest, boolean isDirected) {
		BfsNode<N, E> destObj = (BfsNode<N, E>) dest;
		BfsNode<N, E> srcObj = (BfsNode<N, E>) src;
		return new BfsEdge<>(edgeData, srcObj, destObj, isDirected);
	}

	@Override
	protected BfsNode<N, E> doCreateNode(N nodeData) {
		return new BfsNode<>(nodeData);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Set<BfsNode<N, E>> findAdjacentNodes(Node<N, E> srcNode) {
		return (Set<BfsNode<N, E>>) super.findAdjacentNodes(srcNode);
	}

	@Override
	public String toString() {
		return super.toString();
	}

	static class BfsNode<N, E> extends GenericGraph.Node<N, E> {

		private NodeColor color = NodeColor.WHITE;
		private Integer distance = Integer.MAX_VALUE;
		private Node<N, E> parent;
		volatile private Set<BfsEdge<N, E>> bfsAdjacencyList;

		BfsNode(N node) {
			super(node);
		}

		protected void doAddEdge(Edge<N, E> edge) {
			if (bfsAdjacencyList == null) {
				bfsAdjacencyList = Collections.newSetFromMap(new ConcurrentHashMap<>());
			}
			BfsEdge<N, E> bfsEdge = (BfsEdge<N, E>) edge;
			bfsAdjacencyList.add(bfsEdge);
		}

		protected Set<? extends Edge<N, E>> doGetAdjacencyList() {
			if (bfsAdjacencyList == null) {
				return Collections.emptySet();
			}
			return bfsAdjacencyList;
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
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

		@Override
		public String toString() {
			return super.toString();
		}
	}

	static class BfsEdge<N, E> extends GenericGraph.Edge<N, E> {

		private BfsNode<N, E> src;
		private BfsNode<N, E> dest;

		BfsEdge(E edgeData, BfsNode<N, E> src, BfsNode<N, E> dest, boolean isDirected) {
			super(edgeData, null, null, isDirected);
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

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

		@Override
		public String toString() {
			return super.toString();
		}
	}
}