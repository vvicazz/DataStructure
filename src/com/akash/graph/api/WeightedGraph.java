package com.akash.graph.api;

public interface WeightedGraph<N, E> extends Graph<N> {

	E getEdge(N src, N dest);
}