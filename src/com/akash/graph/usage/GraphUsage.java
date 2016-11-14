package com.akash.graph.usage;

import com.akash.graph.api.BfsGraph;

public class GraphUsage {

	public static void main(String args[]) {

		BfsGraph<String, Integer> graph = new BfsGraph<>(10, false);
		graph.addNode("delhi");
		graph.addNode("goa");
		graph.addNode("kashmir");
		graph.addEdge("delhi", "goa", 12);
		graph.addEdge("delhi", "kashmir", 20);
		graph.addEdge("goa", "kashmir", 20);
		System.out.println(graph);
		graph.executeBfs("delhi");
		System.out.println(graph);
	}
}