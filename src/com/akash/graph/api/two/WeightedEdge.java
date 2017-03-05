package com.akash.graph.api.two;

public interface WeightedEdge<N, E extends Comparable<E>> extends Edge<N>, Comparable<WeightedEdge<N, E>> {

	E getWeight();
}