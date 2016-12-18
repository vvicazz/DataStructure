package com.akash.graph.api;

import java.util.Objects;

public abstract class Edge<N> {

	private N source;
	private N destination;

	public N getSource() {
		return source;
	}

	public N getDestination() {
		return destination;
	}

	private Edge(N source, N destination) {
		this.destination = destination;
		this.source = source;
	}

	@Override
	public abstract boolean equals(Object ob);

	@Override
	public abstract int hashCode();

	static <N> Edge<N> of(Graph<N> graph, N source, N destination) {
		if (source == null || destination == null) {
			throw new IllegalArgumentException("NULL_VALUE_NOT_ALLOWED");
		}
		if (!graph.isAllowsSelfLoops() && Objects.equals(source, destination)) {
			throw new IllegalArgumentException("SELF_LOOP_IS_NOT_ALLOWED");
		}
		if (graph.isDirected()) {
			return new DirectedEdge<N>(source, destination);
		} else {
			return new UndirectedEdge<N>(source, destination);
		}
	}

	private static final class DirectedEdge<N> extends Edge<N> {
		DirectedEdge(N source, N destination) {
			super(source, destination);
		}

		@Override
		public boolean equals(Object ob) {
			return false;
		}

		@Override
		public int hashCode() {
			return 0;
		}
	}

	private static final class UndirectedEdge<N> extends Edge<N> {
		UndirectedEdge(N source, N destination) {
			super(source, destination);
		}

		@Override
		public boolean equals(Object ob) {
			return false;
		}

		@Override
		public int hashCode() {
			return 0;
		}
	}
}