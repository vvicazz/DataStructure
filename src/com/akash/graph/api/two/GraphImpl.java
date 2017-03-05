package com.akash.graph.api.two;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GraphImpl<N> implements Graph<N> {

	private static final long serialVersionUID = 1234L;
	private boolean directed;
	
	transient private Set<N> nodes;
	transient private Map<N, Set<Edge<N>>> edges;
	transient private Map<N, Set<Edge<N>>> inEdges;
	transient private Map<N, Set<Edge<N>>> outEdges;

	GraphImpl(boolean directed) {
		this.directed = directed;
		nodes = new HashSet<>();
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
	public boolean addEdge(N source, N destination) {
		boolean edge1Added = false;
		boolean edge2Added = false;
		nodes.add(source);
		nodes.add(destination);
		if (!isDirected()) {
			edge1Added = addEdge(source, destination, this.edges);
			edge2Added = addEdge(destination, source, this.edges);
		} else {
			edge1Added = addEdge(source, destination, this.outEdges);
			edge2Added = addEdge(destination, source, this.inEdges);
		}
		return edge1Added || edge2Added;
	}

	private boolean addEdge(N source, N destination, Map<N, Set<Edge<N>>> edges) {
		boolean edgeAdded = false;
		Edge<N> edge = new EdgeImpl<N>(source, destination, isDirected());
		synchronized (edges) {
			Set<Edge<N>> adjacentEdges = edges.get(source);
			if (adjacentEdges == null) {
				adjacentEdges = new HashSet<>();
			}
			edgeAdded = adjacentEdges.add(edge);
			edges.put(source, adjacentEdges);
		}
		return edgeAdded;
	}

	@Override
	public Set<Edge<N>> getEdges(N source) {
		Set<Edge<N>> adjacentEdges;
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
	public Set<Edge<N>> getOutEdges(N source) {
		if (isDirected()) {
			Set<Edge<N>> adjacentEdges = outEdges.get(source);
			if (adjacentEdges == null) {
				adjacentEdges = new HashSet<>();
			}
			return Collections.unmodifiableSet(adjacentEdges);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Set<Edge<N>> getInEdges(N source) {
		if (isDirected()) {
			Set<Edge<N>> adjacentEdges = inEdges.get(source);
			if (adjacentEdges == null) {
				adjacentEdges = new HashSet<>();
			}
			return Collections.unmodifiableSet(adjacentEdges);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public boolean hasEdge(N source, N destination) {
		Edge<N> edge = new EdgeImpl<N>(source, destination, isDirected());
		if (isDirected()) {
			Set<Edge<N>> adjacentEdges = edges.get(source);
			if (adjacentEdges != null) {
				return adjacentEdges.contains(edge);
			}
		} else {
			Set<Edge<N>> adjacentEdges = inEdges.get(source);
			if (adjacentEdges != null) {
				return adjacentEdges.contains(edge);
			}
		}
		return false;
	}

	@Override
	public Set<N> getAllNodes() {
		return Collections.unmodifiableSet(nodes);
	}
}