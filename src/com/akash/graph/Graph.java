package com.akash.graph;

public class Graph {
	
	private int numberOfVertices;
	private int numberOfEdges;
	private Edge[] edges;
		
	public int getNumberOfVertices() {
		return numberOfVertices;
	}
	public void setNumberOfVertices(int numberOfVertices) {
		this.numberOfVertices = numberOfVertices;
	}
	public int getNumberOfEdges() {
		return numberOfEdges;
	}
	public void setNumberOfEdges(int numberOfEdges) {
		this.numberOfEdges = numberOfEdges;
	}
	public Edge[] getEdges() {
		return edges;
	}
	public void setEdges(Edge[] edges) {
		this.edges = edges;
	}

	class Edge {
		private String source;
		private String destination;
		private int weight;
		
		public String getSource() {
			return source;
		}
		public void setSource(String source) {
			this.source = source;
		}
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		public int getWeight() {
			return weight;
		}
		public void setWeight(int weight) {
			this.weight = weight;
		}
	}
}