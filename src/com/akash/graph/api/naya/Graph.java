package com.akash.graph.api.naya;

import java.io.Serializable;
import java.util.Set;

public interface Graph<N> extends Serializable {

	boolean isDirected();

	boolean addEdge(N source, N destination);

	Set<Edge<N>> getEdges(N source);

	boolean hasEdge(N source, N destination);

	Set<Edge<N>> getOutEdges(N source);

	Set<Edge<N>> getInEdges(N source);

	Set<N> getAllNodes();
}