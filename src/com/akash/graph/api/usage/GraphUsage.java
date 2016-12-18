package com.akash.graph.api.usage;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class GraphUsage {

	public static void main(String args[]) {

		/*BfsGraph<String, Integer> graph = new BfsGraph<>(10, false);
		graph.addNode("delhi");
		graph.addNode("goa");
		graph.addNode("kashmir");
		graph.addEdge("delhi", "goa", 12);
		graph.addEdge("delhi", "kashmir", 20);
		graph.addEdge("goa", "kashmir", 20);
		//System.out.println(graph);
		graph.executeBfs("delhi");
		//System.out.println(graph);
*/		
		MutableValueGraph<String, Double> graph2 =
			     ValueGraphBuilder.undirected().allowsSelfLoops(true).build();
			 graph2.putEdgeValue("San Francisco", "San Francisco", 0.0);
			 graph2.putEdgeValue("San Jose", "San Jose", 0.0);
			 graph2.putEdgeValue("San Francisco", "San Jose", 48.4);
			 System.out.println(graph2);
	}
}