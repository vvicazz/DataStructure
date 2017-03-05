package com.akash.graph.api.one;

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
	public int hashCode() {
		int prime = 31;
		int hashCode = 17;
		hashCode += prime * this.getDestination().hashCode();
		hashCode += prime * this.getSource().hashCode();
		return hashCode;
	}

	@Override
	public String toString() {
		StringBuffer content = new StringBuffer();
		content.append("{").append(getSource()).append(" -> ").append(getDestination()).append("}");
		return content.toString();
	}

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

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object ob) {
			if (ob == null) {
				return false;
			}
			if (ob.getClass().getName().equals(DirectedEdge.class)) {
				DirectedEdge<N> edge = (DirectedEdge<N>) ob;
				return (edge.getSource().equals(this.getSource()) && edge.getDestination()
						.equals(this.getDestination()));
			}
			return false;
		}
	}

	private static final class UndirectedEdge<N> extends Edge<N> {
		UndirectedEdge(N source, N destination) {
			super(source, destination);
		}

		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object ob) {
			if (ob == null) {
				return false;
			}
			if (ob.getClass().getName().equals(UndirectedEdge.class)) {
				UndirectedEdge<N> edge = (UndirectedEdge<N>) ob;
				return (edge.getSource().equals(this.getSource()) && edge.getDestination()
						.equals(this.getDestination()))
						|| (edge.getSource().equals(this.getDestination()) && edge.getDestination().equals(
								this.getSource()));
			}
			return false;
		}
	}
}