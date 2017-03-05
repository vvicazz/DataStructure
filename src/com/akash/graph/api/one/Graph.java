package com.akash.graph.api.one;

import java.util.Set;

public interface Graph<N> {

	Set<N> nodes();

	Set<Edge<N>> allEdges();

	Set<Edge<N>> adjacentEdges(N node);

	Set<Edge<N>> inAdjacentEdges(N node);

	Set<Edge<N>> outAdjacentEdges(N node);

	int degree(N node);

	int inDegree(N node);

	int outDegree(N node);

	boolean isDirected();

	boolean isAllowsSelfLoops();

	boolean deleteEdge(N source, N destination);

	boolean addEdge(N source, N destination);

	// boolean isEdgeAvailable(N source, N destination)
	// boolean removeNode(N node)
}