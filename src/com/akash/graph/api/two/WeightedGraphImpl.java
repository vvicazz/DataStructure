package com.akash.graph.api.two;

public class WeightedGraphImpl<N, E> extends GraphImpl<N> implements WeightedGraph<N, E> {

	private static final long serialVersionUID = 23461L;

	WeightedGraphImpl(boolean directed) {
		super(directed);
	}
}