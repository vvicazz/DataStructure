package com.akash.graph.api;

import java.util.Set;

public interface Graph<N, E> {

	void addEdge(N srcData, N destData, E edgeData);

	void addNode(N nodeData);

	void removeEdge(N src, N dest);

	void removeNode(N nodeData);

	void removeGraph();

	E findEdge(N src, N dest);

	Set<N> findAdjacentVertices(N node);

	Set<N> getAllVertices();

	int getNumOfVertices();

	int getNumOfEdges();

	boolean existPath(N src, N dest);

	boolean isCyclic();

	boolean isStronglyConnected();

	boolean isComplete();

	boolean isDirected();
	
	boolean Bipartite();
	
	int getNumberOfConnectedComponents();
}