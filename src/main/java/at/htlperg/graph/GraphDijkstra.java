package at.htlperg.graph;

import java.util.*;

public class GraphDijkstra {
    public static Map<GraphNode, LinkedList<GraphNode>> calculateShortestPathFromSource(GraphNode source, String edgeType) {
        Set<GraphNode> settledNodes = new HashSet<>();
        Set<GraphNode> unsettledNodes = new HashSet<>();

        Map<GraphNode, Double> distances = new HashMap<>();
        distances.put(source, 0D);

        Map<GraphNode, LinkedList<GraphNode>> shortestPaths = new HashMap<>();

        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            GraphNode currentNode = getLowestDistanceNode(distances, unsettledNodes);
            unsettledNodes.remove(currentNode);

            currentNode.getEdgeType(edgeType).forEach(edge -> {
                GraphNode adjacentNode = edge.getToNode();

                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(distances, shortestPaths, adjacentNode, edge.getWeight(), currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            });

            settledNodes.add(currentNode);
        }

        return shortestPaths;
    }

    private static void calculateMinimumDistance(
            Map<GraphNode, Double> distances,
            Map<GraphNode, LinkedList<GraphNode>> shortestPaths,
            GraphNode evaluationNode,
            double edgeWeigh,
            GraphNode sourceNode
    ) {
        double sourceDistance = distances.getOrDefault(sourceNode, (double) Integer.MAX_VALUE);
        if (sourceDistance + edgeWeigh < distances.getOrDefault(evaluationNode, (double) Integer.MAX_VALUE)) {
            distances.put(evaluationNode, sourceDistance + edgeWeigh);
            LinkedList<GraphNode> shortestPath = new LinkedList<>(shortestPaths.getOrDefault(sourceNode, new LinkedList<>()));
            shortestPath.add(sourceNode);
            shortestPaths.put(evaluationNode, shortestPath);
        }
    }

    private static GraphNode getLowestDistanceNode(Map<GraphNode, Double> distances, Set<GraphNode> unsettledNodes) {
        return unsettledNodes.stream().min(Comparator.comparingDouble(node -> distances.getOrDefault(node, (double) Integer.MAX_VALUE))).orElse(null);
    }
}
