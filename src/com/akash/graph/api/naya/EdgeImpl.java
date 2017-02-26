package com.akash.graph.api.naya;

import java.util.Objects;

public class EdgeImpl<N> implements Edge<N> {

	private static final long serialVersionUID = 1912536L;
	
	transient private int hash = 0;
	private N source;
	private N destination;
	private boolean directed;

	EdgeImpl(N source, N destination, boolean directed) {
		Objects.requireNonNull(source, "source cannot be null");
		Objects.requireNonNull(destination, "destination cannot be null");
		this.source = source;
		this.destination = destination;
		this.directed = directed;
	}

	@Override
	public boolean isDirected() {
		return directed;
	}

	@Override
	public N getSource() {
		return source;
	}

	@Override
	public N getDestination() {
		return destination;
	}

	@Override
	public int hashCode() {
		if (hash == 0) {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((destination == null) ? 0 : destination.hashCode());
			result = prime * result + ((source == null) ? 0 : source.hashCode());
			hash = result;
		}
		return hash;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Edge))
			return false;
		Edge other = (Edge) obj;
		if (Objects.equals(destination, other.getDestination()) && Objects.equals(source, other.getSource())) {
			return true;
		}
		if (!isDirected()) {
			return (Objects.equals(source, other.getDestination()) && Objects.equals(destination, other.getSource()));
		}
		return false;
	}
}