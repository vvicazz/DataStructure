package com.akash.graph.api.two;

import java.io.Serializable;

public interface Edge<N> extends Serializable {
	
	public N getSource();

	public N getDestination();
	
	boolean isDirected();
}