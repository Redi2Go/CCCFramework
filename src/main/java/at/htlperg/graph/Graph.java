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

if (graphVisualisation != null)
        graphVisualisation.addEdge(edge);

        return edge;
    }

    public void removeEdge(GraphEdge<?> edge) {
        edge.getFromNode().getEdgeType(edge.getIdentifier()).remove(edge);
        edge.getToNode().getEdgeType(edge.getIdentifier()).remove(edge);

        if (graphVisualisation != null)
        graphVisualisation.removeEdge(edge);
    }

    public GraphNode addNode(Object key, GraphNode node) {
        nodes.put(key, node);
        if (graphVisualisation != null)
        graphVisualisation.addNode(node);

        if (graphVisualisation != null)
        for (ObservedList<GraphEdge<?>> edges : node.getEdges())
            edges.forEach(graphVisualisation::addEdge);

        return node;
    }

    public void removeNode(GraphNode node) {
        nodes.remove(node);

        if (graphVisualisation != null)
        graphVisualisation.removeNode(node);

        if (graphVisualisation != null)
        for (ObservedList<GraphEdge<?>> edges : node.getEdges())
            edges.forEach(graphVisualisation::removeEdge);
    }

    public GraphNode getNode(Object key) {
        return nodes.get(key);
    }
}