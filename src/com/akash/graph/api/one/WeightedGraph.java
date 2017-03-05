package com.akash.graph.api.one;

public interface WeightedGraph<N, E> extends Graph<N> {

	E getEdge(N src, N dest);
}