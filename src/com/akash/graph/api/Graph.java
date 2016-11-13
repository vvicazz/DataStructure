package com.akash.graph.api;

import java.util.Set;

public interface Graph<N, E> {

	void createEdge(N srcData, N destData, E edgeData);
	
	void addNode(N nodeData);

	void removeEdge(N src, N dest);

	boolean removeNode(N nodeData);

	E findEdge(N src, N dest);

	Set<N> findAdjacentVertices(N node);

	boolean existPath(N src, N dest);

	boolean isCyclic();

	boolean isConnected();

	boolean isComplete();

	boolean isDirected();
}