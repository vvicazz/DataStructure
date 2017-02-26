package com.akash.graph.api.naya;


public interface WeightedEdge<N, E> extends Edge<N> {

	E getWeight();
}