package com.akash.graph.api.two;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WeightedGraphImpl<N, E extends Comparable<E>> implements WeightedGraph<N, E> {

	private static final long serialVersionUID = 23461L;

	private boolean directed;
	private transient Set<N> nodes;
	private transient Map<N, Set<WeightedEdge<N, E>>> edges;
	private transient Map<N, Set<WeightedEdge<N, E>>> inEdges;
	private transient Map<N, Set<WeightedEdge<N, E>>> outEdges;

	WeightedGraphImpl(boolean directed) {
		this.directed = directed;
		nodes = new HashSet<>();
		if (this.directed) {
			inEdges = new ConcurrentHashMap<>();
			outEdges = new ConcurrentHashMap<>();
		} else {
			edges = new ConcurrentHashMap<>();
		}
	}

	WeightedGraphImpl(boolean directed, Set<N> nodes) {
		this.directed = directed;
		this.nodes = nodes;
		if (this.directed) {
			inEdges = new ConcurrentHashMap<>();
			outEdges = new ConcurrentHashMap<>();
		} else {
			edges = new ConcurrentHashMap<>();
		}
	}

	@Override
	public boolean isDirected() {
		return directed;
	}

	@Override
	public boolean addEdge(N source, N destination, E weight) {
		boolean edge1Added = false;
		boolean edge2Added = false;
		nodes.add(source);
		nodes.add(destination);
		if (!isDirected()) {
			edge1Added = addEdge(source, destination, weight, this.edges);
			edge2Added = addEdge(destination, source, weight, this.edges);
		} else {
			edge1Added = addEdge(source, destination, weight, this.outEdges);
			edge2Added = addEdge(destination, source, weight, this.inEdges);
		}
		return edge1Added || edge2Added;
	}

	private boolean addEdge(N source, N destination, E weight, Map<N, Set<WeightedEdge<N, E>>> edges) {
		boolean edgeAdded = false;
		WeightedEdge<N, E> edge = new WeightedEdgeImpl<N, E>(source, destination, weight, isDirected());
		synchronized (edges) {
			Set<WeightedEdge<N, E>> adjacentEdges = edges.get(source);
			if (adjacentEdges == null) {
				adjacentEdges = new HashSet<>();
			}
			edgeAdded = adjacentEdges.add(edge);
			edges.put(source, adjacentEdges);
		}
		return edgeAdded;
	}

	@Override
	public Set<WeightedEdge<N, E>> getEdges(N source) {
		Set<WeightedEdge<N, E>> adjacentEdges;
		if (!isDirected()) {
			adjacentEdges = edges.get(source);
		} else {
			adjacentEdges = outEdges.get(source);
		}
		if (adjacentEdges == null) {
			adjacentEdges = new HashSet<>();
		}
		return Collections.unmodifiableSet(adjacentEdges);
	}

	@Override
	public Set<WeightedEdge<N, E>> getOutEdges(N source) {
		if (isDirected()) {
			Set<WeightedEdge<N, E>> adjacentEdges = outEdges.get(source);
			if (adjacentEdges == null) {
				adjacentEdges = new HashSet<>();
			}
			return Collections.unmodifiableSet(adjacentEdges);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Set<WeightedEdge<N, E>> getInEdges(N source) {
		if (isDirected()) {
			Set<WeightedEdge<N, E>> adjacentEdges = inEdges.get(source);
			if (adjacentEdges == null) {
				adjacentEdges = new HashSet<>();
			}
			return Collections.unmodifiableSet(adjacentEdges);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Set<N> getAllNodes() {
		return Collections.unmodifiableSet(nodes);
	}

	@Override
	public Set<WeightedEdge<N, E>> getAllEdges() {
		Set<WeightedEdge<N, E>> edges = new HashSet<>();
		for (N node : getAllNodes()) {
			edges.addAll(getEdges(node));
		}
		return Collections.unmodifiableSet(edges);
	}

	/** KRUSKAL ALGO IMPLEMENTATION , UNION FIND DATA STRUCTURE ***/

	private static class FindUnionSet<N> {
		private transient N node;
		private transient int rank;
		private transient FindUnionSet<N> parent;

		FindUnionSet(N node, int rank, FindUnionSet<N> parent) {
			this.node = node;
			this.rank = rank;
			this.parent = parent;
		}

		public N getNode() {
			return node;
		}

		public int getRank() {
			return rank;
		}

		public void setRank(int rank) {
			this.rank = rank;
		}

		public FindUnionSet<N> getParent() {
			return parent;
		}

		public void setParent(FindUnionSet<N> parent) {
			this.parent = parent;
		}
	}

	@Override
	public WeightedGraph<N, E> mst(WeightedGraph<N, E> graph) {
		if (graph.isDirected() == true) {
			throw new UnsupportedOperationException();
		}
		WeightedGraph<N, E> mstGraph = new WeightedGraphImpl<>(graph.isDirected(), graph.getAllNodes());
		Map<N, FindUnionSet<N>> fuSets = new HashMap<>(graph.getAllNodes().size());
		for (N node : graph.getAllNodes()) {
			fuSets.put(node, new FindUnionSet<>(node, 1, null));
		}
		for (WeightedEdge<N, E> edge : sortEdges(graph)) {
			N srcParent = find(fuSets, edge.getSource());
			N destParent = find(fuSets, edge.getDestination());
			if (!destParent.equals(srcParent)) {
				union(fuSets, srcParent, destParent);
				mstGraph.addEdge(edge.getSource(), edge.getDestination(), edge.getWeight());
			}
		}
		return mstGraph;
	}

	private List<WeightedEdge<N, E>> sortEdges(WeightedGraph<N, E> graph) {
		Set<WeightedEdge<N, E>> edges = graph.getAllEdges();
		List<WeightedEdge<N, E>> sortedEdges = new ArrayList<>(edges);
		Collections.sort(sortedEdges);
		return sortedEdges;
	}

	private N find(Map<N, FindUnionSet<N>> fuSets, N node) {
		FindUnionSet<N> fuSet = fuSets.get(node);
		if (fuSet.getParent() != null) {
			N parentNode = find(fuSets, fuSet.getParent().getNode());
			fuSet.setParent(fuSets.get(parentNode));
			return parentNode;
		}
		return fuSet.getNode();
	}

	private void union(Map<N, FindUnionSet<N>> fuSets, N src, N dest) {
		N srcParent = find(fuSets, src);
		N destParent = find(fuSets, dest);
		FindUnionSet<N> fuSrc = fuSets.get(srcParent);
		FindUnionSet<N> fuDest = fuSets.get(destParent);
		if (fuSrc.getRank() > fuDest.getRank()) {
			fuDest.setParent(fuSrc);
			fuSrc.setRank(fuSrc.getRank() + fuDest.getRank());
		} else {
			fuSrc.setParent(fuDest);
			fuDest.setRank(fuSrc.getRank() + fuDest.getRank());
		}
	}
}