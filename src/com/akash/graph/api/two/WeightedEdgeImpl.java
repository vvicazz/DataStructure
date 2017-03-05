package com.akash.graph.api.two;

import java.util.Objects;

public class WeightedEdgeImpl<N, E extends Comparable<E>> extends EdgeImpl<N> implements WeightedEdge<N, E> {

	private static final long serialVersionUID = 129634L;
	private E weight;

	WeightedEdgeImpl(N source, N destination, E weight, boolean directed) {
		super(source, destination, directed);
		Objects.requireNonNull(weight, "weight cannot be null");
		this.weight = weight;
	}

	@Override
	public E getWeight() {
		return weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeightedEdgeImpl other = (WeightedEdgeImpl) obj;
		return Objects.equals(weight, other.getWeight());
	}

	@Override
	public int compareTo(WeightedEdge<N, E> object) {
		return this.getWeight().compareTo(object.getWeight());
	}
}