package com.akash.graph.api.two;


public interface WeightedEdge<N, E> extends Edge<N> {

	E getWeight();
}