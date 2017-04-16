package com.akash.graph.api.two;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Graphs {

	private Graphs() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 2 edge connected graph problem
	 * 
	 * @param graph
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <N> boolean hasBridgeEdge(Graph<N> graph) {
		Objects.requireNonNull(graph);
		N source = (N) graph.getAllNodes().stream().findAny();
		if (source != null) {
			BridgeResponse<N> bridgeResponse = new BridgeResponse<N>(graph);
			AtomicInteger time = new AtomicInteger(0);
			if (doBridgeEdge(graph, source, time, bridgeResponse)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private static <N> boolean doBridgeEdge(Graph<N> graph, N source, AtomicInteger time,
			BridgeResponse<N> bridgeResponse) {
		bridgeResponse.getVisited().put(source, Boolean.TRUE);
		Integer arrivalTime = time.incrementAndGet();
		bridgeResponse.getArrivalTime().put(source, arrivalTime);
		bridgeResponse.getDbe().put(source, arrivalTime);
		for (Edge<N> edge : graph.getEdges(source)) {
			N tempNode = edge.getDestination();
			if (!bridgeResponse.getVisited().get(tempNode)) {
				bridgeResponse.getParent().put(tempNode, source);
				boolean hasBackEdge = doBridgeEdge(graph, tempNode, time, bridgeResponse);
				bridgeResponse.getDbe().put(source,
						min(bridgeResponse.getDbe().get(tempNode), bridgeResponse.getDbe().get(source)));
				if (bridgeResponse.getDbe().get(tempNode) > bridgeResponse.getArrivalTime().get(source)) {
					System.out.println(source + " --> " + tempNode + " is back edge");
					return true;
				}
				if (hasBackEdge) {
					return true;
				}
			} else if (!tempNode.equals(bridgeResponse.getParent().get(source))) {
				bridgeResponse.getDbe().put(source,
						min(bridgeResponse.getArrivalTime().get(tempNode), bridgeResponse.getDbe().get(source)));
			}
		}
		return false;
	}

	private static int min(int a, int b) {
		return a > b ? b : a;
	}

	private static class BridgeResponse<N> {
		private Map<N, Integer> arrivalTime;
		private Map<N, Boolean> visited;
		private Map<N, Integer> dbe;
		private Map<N, N> parent;

		BridgeResponse(Graph<N> graph) {
			Set<N> nodes = graph.getAllNodes();
			for (N tempNode : nodes) {
				arrivalTime.put(tempNode, 0);
				visited.put(tempNode, Boolean.FALSE);
				dbe.put(tempNode, 0);
				parent.put(tempNode, null);
			}
		}

		public Map<N, Integer> getArrivalTime() {
			return arrivalTime;
		}

		public Map<N, Boolean> getVisited() {
			return visited;
		}

		public Map<N, Integer> getDbe() {
			return dbe;
		}

		public Map<N, N> getParent() {
			return parent;
		}

		@Override
		public String toString() {
			StringBuilder content = new StringBuilder();
			Iterator<Map.Entry<N, Integer>> itr = getArrivalTime().entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<N, Integer> entry = itr.next();
				content.append("{node=").append(entry.getKey()).append(" , visited=");
				content.append(getVisited().get(entry.getKey())).append(", dbe=");
				content.append(getDbe().get(entry.getKey())).append(", parent=");
				content.append(getParent().get(entry.getKey())).append(", arrivalTime=");
				content.append(entry.getValue()).append("}");
			}
			return content.toString();
		}
	}

	@SuppressWarnings("unchecked")
	public static <N> int getConnectedComponents(Graph<N> graph) {
		Objects.requireNonNull(graph);
		int connectedComponents = 0;
		Set<N> nodes = new HashSet<N>(graph.getAllNodes());
		while (nodes.size() > 0) {
			N source = (N) nodes.stream().findAny();
			BfsResponse<N> response = bfs(source, graph);
			Set<N> processedNodes = response.getColor().entrySet().stream().map(entry -> entry.getKey())
					.collect(Collectors.toSet());
			nodes.removeAll(processedNodes);
			connectedComponents++;
		}
		return connectedComponents;
	}

	@SuppressWarnings("unchecked")
	public static <N> boolean isBipartite(Graph<N> graph) {
		Objects.requireNonNull(graph);
		N source = (N) graph.getAllNodes().stream().findAny();
		Objects.requireNonNull(source);
		BfsResponse<N> bfsResponse = new BfsResponse<N>(graph);
		bfsResponse.getColor().put(source, Color.GRAY);
		bfsResponse.getDistance().put(source, 0);
		Queue<N> queue = new LinkedList<>();
		queue.add(source);
		while (queue.isEmpty()) {
			N queueNode = queue.poll();
			for (Edge<N> edge : graph.getEdges(queueNode)) {
				N tempNode = edge.getDestination();
				if (bfsResponse.getDistance().get(queueNode).intValue() == bfsResponse.getDistance().get(tempNode)
						.intValue()) {
					return false;
				}
				if (bfsResponse.getColor().get(tempNode) == Color.WHITE) {
					bfsResponse.getColor().put(tempNode, Color.GRAY);
					bfsResponse.getDistance().put(tempNode, bfsResponse.getDistance().get(queueNode) + 1);
					bfsResponse.getParent().put(tempNode, queueNode);
					queue.add(tempNode);
				}
			}
			bfsResponse.getColor().put(queueNode, Color.BLACK);
		}
		return true;
	}

	public static <N> BfsResponse<N> bfs(N source, Graph<N> graph) {
		Objects.requireNonNull(graph);
		Objects.requireNonNull(source);
		BfsResponse<N> bfsResponse = new BfsResponse<N>(graph);
		bfsResponse.getColor().put(source, Color.GRAY);
		bfsResponse.getDistance().put(source, 0);
		Queue<N> queue = new LinkedList<>();
		queue.add(source);
		while (queue.isEmpty()) {
			N queueNode = queue.poll();
			for (Edge<N> edge : graph.getEdges(queueNode)) {
				N tempNode = edge.getDestination();
				if (bfsResponse.getColor().get(tempNode) == Color.WHITE) {
					bfsResponse.getColor().put(tempNode, Color.GRAY);
					bfsResponse.getDistance().put(tempNode, bfsResponse.getDistance().get(queueNode) + 1);
					bfsResponse.getParent().put(tempNode, queueNode);
					queue.add(tempNode);
				}
			}
			bfsResponse.getColor().put(queueNode, Color.BLACK);
		}
		return bfsResponse;
	}

	public static <N> DfsResponse<N> dfs(N source, Graph<N> graph) {
		Objects.requireNonNull(graph);
		Objects.requireNonNull(source);
		DfsResponse<N> dfsResponse = new DfsResponse<N>(graph);
		AtomicInteger time = new AtomicInteger(0);
		doDfs(graph, source, time, dfsResponse);
		return dfsResponse;
	}

	private static <N> void doDfs(Graph<N> graph, N source, AtomicInteger time, DfsResponse<N> dfsResponse) {

		dfsResponse.getVisited().put(source, Boolean.TRUE);
		dfsResponse.getArrivalTime().put(source, time.incrementAndGet());
		for (Edge<N> edge : graph.getEdges(source)) {
			N tempNode = edge.getDestination();
			if (!dfsResponse.getVisited().get(tempNode)) {
				doDfs(graph, tempNode, time, dfsResponse);
			}
		}
		dfsResponse.getDepartureTime().put(source, time.incrementAndGet());
	}

	@SuppressWarnings("unchecked")
	public static <N> boolean isDag(Graph<N> graph) {
		N source = (N) graph.getAllNodes().stream().findAny();
		DfsResponse<N> dfsResponse = new DfsResponse<N>(graph);
		AtomicInteger time = new AtomicInteger(0);
		return doDag(graph, source, time, dfsResponse);
	}

	private static <N> boolean doDag(Graph<N> graph, N source, AtomicInteger time, DfsResponse<N> dfsResponse) {
		dfsResponse.getVisited().put(source, Boolean.TRUE);
		dfsResponse.getArrivalTime().put(source, time.incrementAndGet());
		for (Edge<N> edge : graph.getEdges(source)) {
			N tempNode = edge.getDestination();
			if (!dfsResponse.getVisited().get(tempNode)) {
				doDfs(graph, tempNode, time, dfsResponse);
			} else if (isBackEdgeInDirectedDfs(tempNode, source, dfsResponse)) {
				return true;
			}
		}
		dfsResponse.getDepartureTime().put(source, time.incrementAndGet());
		return false;
	}

	private static <N> boolean isBackEdgeInDirectedDfs(N u, N v, DfsResponse<N> dfsResponse) {
		return dfsResponse.getVisited().get(u)
				&& dfsResponse.getArrivalTime().get(u).intValue() > dfsResponse.getArrivalTime().get(v).intValue()
				&& dfsResponse.getDepartureTime().get(v).intValue() == -1;
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
			for (N node : graph.getAllNodes()) {
				color.put(node, Color.WHITE);
				distance.put(node, -1);
				parent.put(node, null);
			}
		}

		public Map<N, Color> getColor() {
			return color;
		}

		public Map<N, Integer> getDistance() {
			return distance;
		}

		public Map<N, N> getParent() {
			return parent;
		}

		@Override
		public String toString() {
			StringBuilder content = new StringBuilder();
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
			for (N node : graph.getAllNodes()) {
				visited.put(node, Boolean.FALSE);
				arrivalTime.put(node, -1);
				departureTime.put(node, -1);
			}
		}

		public Map<N, Boolean> getVisited() {
			return visited;
		}

		public Map<N, Integer> getArrivalTime() {
			return arrivalTime;
		}

		public Map<N, Integer> getDepartureTime() {
			return departureTime;
		}

		@Override
		public String toString() {
			StringBuilder content = new StringBuilder();
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