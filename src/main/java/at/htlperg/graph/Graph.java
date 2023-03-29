package at.htlperg.graph;

import at.htlperg.observable.ObservedList;
import at.htlperg.visualization.GraphVisualisation;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private final GraphVisualisation graphVisualisation;
    private final Map<Object, GraphNode> nodes = new HashMap<>();

    public Graph(boolean visualization) {
        this.graphVisualisation = visualization ? new GraphVisualisation(true) : null;
    }

    public <T> GraphEdge<T> addEdge(GraphEdge<T> edge) {
        edge.getFromNode().getEdgeType(edge.getType()).add(edge);
        edge.getToNode().getEdgeType(edge.getType()).add(edge);

        graphVisualisation.addEdge(edge);

        return edge;
    }

    public void removeEdge(GraphEdge<?> edge) {
        edge.getFromNode().getEdgeType(edge.getIdentifier()).remove(edge);
        edge.getToNode().getEdgeType(edge.getIdentifier()).remove(edge);

        graphVisualisation.removeEdge(edge);
    }

    public GraphNode addNode(Object key, GraphNode node) {
        nodes.put(key, node);
        graphVisualisation.addNode(node);

        for (ObservedList<GraphEdge<?>> edges : node.getEdges())
            edges.forEach(graphVisualisation::addEdge);

        return node;
    }

    public void removeNode(GraphNode node) {
        nodes.remove(node);
        graphVisualisation.removeNode(node);

        for (ObservedList<GraphEdge<?>> edges : node.getEdges())
            edges.forEach(graphVisualisation::removeEdge);
    }

    public GraphNode getNode(Object key) {
        return nodes.get(key);
    }
}
