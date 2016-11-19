package com.akash.graph.api;

public class WeightLessGraph<N> extends GenericGraph<N, EdgeLength> {

}

class EdgeLength implements Comparable<EdgeLength> {

	public static EdgeLength FIXED_LENGTH = new EdgeLength();

	private EdgeLength() {

	}

	@Override
	public int compareTo(EdgeLength o) {
		return 0;
	}
}