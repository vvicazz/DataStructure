package com.akash.graph.api.usage;

import com.akash.graph.api.Graph;
import com.akash.graph.api.GraphImpl.GraphImplBuilder;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class GraphUsage {

	public static void main(String args[]) {

		// guavaExample();
		Graph<String> graph = new GraphImplBuilder<String>(false, true).build();
		graph.addEdge("delhi", "mumbai");
		graph.addEdge("goa", "mumbai");
		graph.addEdge("delhi", "pune");
		graph.addEdge("gurugram", "noida");
		System.out.println(graph);
	}

	private static void guavaExample() {
		MutableValueGraph<String, Double> graph2 = ValueGraphBuilder.undirected().allowsSelfLoops(true).build();
		graph2.putEdgeValue("San Francisco", "San Francisco", 0.0);
		graph2.putEdgeValue("San Jose", "San Jose", 0.0);
		graph2.putEdgeValue("San Francisco", "San Jose", 48.4);
		System.out.println(graph2);
	}
}