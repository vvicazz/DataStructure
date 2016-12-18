package com.akash.graph.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Graphs {

	private Graphs() {
		throw new UnsupportedOperationException();
	}

	public static <N> BfsResponse<N> bfs(N source, Graph<N> graph) {
		BfsResponse<N> bfsResponse = new BfsResponse<N>(graph);
		bfsResponse.getColor().put(source, Color.GRAY);
		bfsResponse.getDistance().put(source, 0);
		Queue<N> queue = new LinkedList<>();
		queue.add(source);
		while (queue.isEmpty()) {
			N node = queue.poll();
			for (Edge<N> edge : graph.adjacentEdges(node)) {
				N tempNode = edge.getDestination();
				if (bfsResponse.getColor().get(tempNode) == Color.WHITE) {
					bfsResponse.getColor().put(tempNode, Color.GRAY);
					bfsResponse.getDistance().put(tempNode, bfsResponse.getDistance().get(node) + 1);
					bfsResponse.getParent().put(tempNode, node);
					queue.add(tempNode);
				}
			}
			bfsResponse.getColor().put(node, Color.BLACK);
		}
		return bfsResponse;
	}

	public static <N> DfsResponse<N> dfs(N source, Graph<N> graph) {
		DfsResponse<N> dfsResponse = new DfsResponse<N>(graph);
		AtomicInteger time = new AtomicInteger(0);
		doDfs(graph, source, time, dfsResponse);
		return dfsResponse;
	}

	private static <N> void doDfs(Graph<N> graph, N source, AtomicInteger time, DfsResponse<N> dfsResponse) {

		dfsResponse.getVisited().put(source, Boolean.TRUE);
		dfsResponse.getArrivalTime().put(source, time.incrementAndGet());
		for (Edge<N> edge : graph.adjacentEdges(source)) {
			N tempNode = edge.getDestination();
			if (!dfsResponse.getVisited().get(tempNode)) {
				doDfs(graph, tempNode, time, dfsResponse);
			}
		}
		dfsResponse.getDepartureTime().put(source, time.incrementAndGet());
	}

	public enum Color {
		WHITE, GRAY, BLACK;
	}

	public static class BfsResponse<N> {
		private Map<N, Color> color;
		private Map<N, Integer> distance;
		private Map<N, N> parent;

		BfsResponse(Graph<N> graph) {
			this.color = new HashMap<>();
			this.distance = new HashMap<>();
			this.parent = new HashMap<>();
			for (N node : graph.nodes()) {
				color.put(node, Color.WHITE);
				distance.put(node, -1);
				parent.put(node, null);
			}
		}

		public Map<N, Color> getColor() {
			return color;
		}

		public void setColor(Map<N, Color> color) {
			this.color = color;
		}

		public Map<N, Integer> getDistance() {
			return distance;
		}

		public void setDistance(Map<N, Integer> distance) {
			this.distance = distance;
		}

		public Map<N, N> getParent() {
			return parent;
		}

		public void setParent(Map<N, N> parent) {
			this.parent = parent;
		}

		@Override
		public String toString() {
			StringBuffer content = new StringBuffer();
			Iterator<Map.Entry<N, Color>> itr = getColor().entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<N, Color> entry = itr.next();
				content.append("{node=").append(entry.getKey()).append(" , parent=");
				content.append(getParent().get(entry.getKey())).append(", distance=");
				content.append(getDistance().get(entry.getKey())).append(", color=");
				content.append(entry.getValue()).append("}");
			}
			return content.toString();
		}
	}

	public static class DfsResponse<N> {
		private Map<N, Boolean> visited;
		private Map<N, Integer> arrivalTime;
		private Map<N, Integer> departureTime;

		DfsResponse(Graph<N> graph) {
			this.visited = new HashMap<>();
			this.arrivalTime = new HashMap<>();
			this.departureTime = new HashMap<>();
			for (N node : graph.nodes()) {
				visited.put(node, Boolean.FALSE);
				arrivalTime.put(node, -1);
				departureTime.put(node, -1);
			}
		}

		public Map<N, Boolean> getVisited() {
			return visited;
		}

		public void setVisited(Map<N, Boolean> visited) {
			this.visited = visited;
		}

		public Map<N, Integer> getArrivalTime() {
			return arrivalTime;
		}

		public void setArrivalTime(Map<N, Integer> arrivalTime) {
			this.arrivalTime = arrivalTime;
		}

		public Map<N, Integer> getDepartureTime() {
			return departureTime;
		}

		public void setDepartureTime(Map<N, Integer> departureTime) {
			this.departureTime = departureTime;
		}

		@Override
		public String toString() {
			StringBuffer content = new StringBuffer();
			Iterator<Map.Entry<N, Integer>> itr = getDepartureTime().entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<N, Integer> entry = itr.next();
				content.append("{node=").append(entry.getKey()).append(" , visited=");
				content.append(getVisited().get(entry.getKey())).append(", arrivalTime=");
				content.append(getArrivalTime().get(entry.getKey())).append(", departureTime=");
				content.append(entry.getValue()).append("}");
			}
			return content.toString();
		}
	}
}