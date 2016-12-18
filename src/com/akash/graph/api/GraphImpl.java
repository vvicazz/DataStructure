package com.akash.graph.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.AtomicLongMap;

public class GraphImpl<N> implements Graph<N> {

	private boolean isDirected;
	private boolean allowsSelfLoops;
	private AtomicInteger edgeCount = new AtomicInteger(0);
	private Set<N> nodes = new HashSet<>();
	private Map<N, Set<Edge<N>>> edges = new ConcurrentHashMap<>();
	private Map<N, Set<Edge<N>>> inEdges = new ConcurrentHashMap<>();
	private Map<N, Set<Edge<N>>> outEdges = new ConcurrentHashMap<>();

	private AtomicLongMap<N> degree = AtomicLongMap.create();
	private AtomicLongMap<N> inDegree = AtomicLongMap.create();
	private AtomicLongMap<N> outDegree = AtomicLongMap.create();

	private GraphImpl(boolean isDirected, boolean allowsSelfLoops) {

	}

	public static class GraphImplBuilder<N> {
		private boolean isDirected;
		private boolean allowsSelfLoops;

		public GraphImplBuilder(boolean isDirected, boolean allowsSelfLoops) {
			this.isDirected = isDirected;
			this.allowsSelfLoops = allowsSelfLoops;
		}

		public GraphImpl<N> build() {
			return new GraphImpl<N>(this.isDirected, this.allowsSelfLoops);
		}
	}

	@Override
	public synchronized boolean addEdge(N source, N destination) {
		// TODO : apply lock on the comparison of source and destination
		nodes.add(source);
		nodes.add(destination);
		boolean edgeAdded = false;
		if (isDirected()) {
			Edge<N> edge = Edge.of(this, source, destination);
			Set<Edge<N>> outAdjacentEdges = outEdges.get(source);
			if (outAdjacentEdges == null) {
				outAdjacentEdges = new HashSet<>();
			}
			edgeAdded = outAdjacentEdges.add(edge);
			outEdges.put(source, outAdjacentEdges);
			Set<Edge<N>> inAdjacentEdges = inEdges.get(destination);
			if (inAdjacentEdges == null) {
				inAdjacentEdges = new HashSet<>();
			}
			edgeAdded = inAdjacentEdges.add(edge);
			inEdges.put(destination, inAdjacentEdges);
			outDegree.incrementAndGet(source);
			inDegree.incrementAndGet(destination);
		} else {
			Edge<N> edge = Edge.of(this, source, destination);
			Set<Edge<N>> adjacentEdges = edges.get(source);
			if (adjacentEdges == null) {
				adjacentEdges = new HashSet<>();
			}
			edgeAdded = adjacentEdges.add(edge);
			edges.put(source, adjacentEdges);

			Edge<N> reverseEdge = Edge.of(this, destination, source);
			Set<Edge<N>> reverseAdjacentEdges = edges.get(destination);
			if (reverseAdjacentEdges == null) {
				reverseAdjacentEdges = new HashSet<>();
			}
			edgeAdded = reverseAdjacentEdges.add(reverseEdge);
			edges.put(destination, reverseAdjacentEdges);
			degree.incrementAndGet(source);
			degree.incrementAndGet(destination);
		}
		if (edgeAdded) {
			edgeCount.incrementAndGet();
		}
		return edgeAdded;
	}

	@Override
	public synchronized boolean deleteEdge(N source, N destination) {
		// TODO : apply lock on the comparison of source and destination
		boolean edgeRemoved = false;
		if (isDirected()) {
			Edge<N> edge = Edge.of(this, source, destination);
			Set<Edge<N>> outAdjacentEdges = outEdges.get(source);
			if (outAdjacentEdges != null) {
				edgeRemoved = outAdjacentEdges.remove(edge);
				outEdges.put(source, outAdjacentEdges);
			}
			Set<Edge<N>> inAdjacentEdges = inEdges.get(destination);
			if (inAdjacentEdges != null) {
				edgeRemoved = inAdjacentEdges.remove(edge);
				inEdges.put(destination, inAdjacentEdges);
			}
			outDegree.decrementAndGet(source);
			inDegree.decrementAndGet(destination);
		} else {
			Edge<N> edge = Edge.of(this, source, destination);
			Set<Edge<N>> adjacentEdges = edges.get(source);
			if (adjacentEdges != null) {
				edgeRemoved = adjacentEdges.remove(edge);
				edges.put(source, adjacentEdges);
			}

			Edge<N> reverseEdge = Edge.of(this, destination, source);
			Set<Edge<N>> reverseAdjacentEdges = edges.get(destination);
			if (reverseAdjacentEdges != null) {
				edgeRemoved = reverseAdjacentEdges.remove(reverseEdge);
				edges.put(destination, reverseAdjacentEdges);
			}
			degree.decrementAndGet(source);
			degree.decrementAndGet(destination);
		}
		if (edgeRemoved) {
			edgeCount.decrementAndGet();
		}
		return edgeRemoved;
	}

	@Override
	public Set<N> nodes() {
		return Collections.unmodifiableSet(nodes);
	}

	@Override
	public Set<Edge<N>> allEdges() {
		if (nodes().size() == 0) {
			return Collections.emptySet();
		} else if (isDirected()) {
			Set<Edge<N>> edges = new HashSet<>();
			for (N node : nodes()) {
				edges.addAll(outAdjacentEdges(node));
			}
			return Collections.unmodifiableSet(edges);
		} else {
			Set<Edge<N>> edges = new HashSet<>();
			for (N node : nodes()) {
				edges.addAll(adjacentEdges(node));
			}
			return Collections.unmodifiableSet(edges);
		}
	}

	@Override
	public Set<Edge<N>> adjacentEdges(N node) {
		if (isDirected()) {
			throw new UnsupportedOperationException("NOT_AVAILABLE_ON_DIRECTED");
		}
		Set<Edge<N>> adjacentEdges = edges.get(node);
		if (adjacentEdges != null) {
			return Collections.unmodifiableSet(adjacentEdges);
		}
		return Collections.emptySet();
	}

	@Override
	public Set<Edge<N>> inAdjacentEdges(N node) {
		if (!isDirected()) {
			throw new UnsupportedOperationException("NOT_AVAILABLE_ON_UNDIRECTED");
		}
		Set<Edge<N>> inAdjacentEdges = inEdges.get(node);
		if (inAdjacentEdges != null) {
			return Collections.unmodifiableSet(inAdjacentEdges);
		}
		return Collections.emptySet();
	}

	@Override
	public Set<Edge<N>> outAdjacentEdges(N node) {
		if (!isDirected()) {
			throw new UnsupportedOperationException("NOT_AVAILABLE_ON_UNDIRECTED");
		}
		Set<Edge<N>> outAdjacentEdges = outEdges.get(node);
		if (outAdjacentEdges != null) {
			return Collections.unmodifiableSet(outAdjacentEdges);
		}
		return Collections.emptySet();
	}

	@Override
	public int degree(N node) {
		return adjacentEdges(node).size();
	}

	@Override
	public int inDegree(N node) {
		return inAdjacentEdges(node).size();
	}

	@Override
	public int outDegree(N node) {
		return outAdjacentEdges(node).size();
	}

	@Override
	public boolean isDirected() {
		return isDirected;
	}

	@Override
	public boolean isAllowsSelfLoops() {
		return allowsSelfLoops;
	}

	@Override
	public String toString() {
		return Arrays.toString(allEdges().toArray());
	}
}