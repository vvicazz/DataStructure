package com.akash.graph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Set of vertices must be tracked there can be various implementations of graph
 * to store edges : 1.keep edge in a Set,treat an edge as single entity,
 * difficult to search an edge 2.keep a Map, key as startNode value as endNode
 * 3.
 * 
 * @author Akash.Sharma
 *
 * maintain list of all nodes in a set
 * Each node will contain a list of adjacency node pointers 
 *
 */
public class MatrixGraph<N, C> {

	int numberOfEdges;

	private Set<Edge<N, C>> edgeList = new HashSet<Edge<N, C>>();

	public boolean addEdge(N startNode, N endNode) {

		Edge<N, C> adge = new Edge<N, C>(startNode, endNode);
		if (edgeList.add(adge)) {
			numberOfEdges++;
			return true;
		}
		return false;
	}

	public boolean isEdgeAvailable(N startNode, N endNode) {

		Edge<N, C> adge = new Edge<N, C>(startNode, endNode);
		return edgeList.contains(adge);
	}

	public boolean deleteEdge(N startNode, N endNode) {

		Edge<N, C> adge = new Edge<N, C>(startNode, endNode);
		if (edgeList.remove(adge)) {
			numberOfEdges--;
			return true;
		}
		return false;
	}

	public static class Edge<N, C> {

		// setting a generic constant
		Edge(N startNode, N endNode) {
			this.startNode = startNode;
			this.endNode = endNode;
		}

		Edge(N startNode, N endNode, C weight) {
			this.startNode = startNode;
			this.endNode = endNode;
			this.weight = weight;
		}

		private N startNode;
		private N endNode;
		private C weight;

		public N getStartNode() {
			return startNode;
		}

		public N getEndNode() {
			return endNode;
		}

		@Override
		public int hashCode() {

			return getStartNode() == null ? 0 : getStartNode().hashCode();
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
				Edge ob1 = (Edge) ob;
				return Objects.equals(ob1.getStartNode(), this.getStartNode())
						&& Objects.equals(ob1.getEndNode(), this.getEndNode());
			}
		}

		@Override
		public String toString() {
			return "Edge[" + getStartNode() + "-->" + getEndNode() + "]";
		}
	}
}