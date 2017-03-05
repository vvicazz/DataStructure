package com.akash.graph.api.two;

import java.io.Serializable;
import java.util.Set;

public interface WeightedGraph<N, E extends Comparable<E>> extends Serializable {

	boolean isDirected();

	boolean addEdge(N source, N destination, E weight);

	Set<WeightedEdge<N, E>> getEdges(N source);

	Set<WeightedEdge<N, E>> getOutEdges(N source);

	Set<WeightedEdge<N, E>> getInEdges(N source);

	Set<N> getAllNodes();

	Set<WeightedEdge<N, E>> getAllEdges();

	WeightedGraph<N, E> mst(WeightedGraph<N, E> graph);
}