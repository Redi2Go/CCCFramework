package at.htlperg.graph;

import at.htlperg.observable.ObservedList;
import at.htlperg.visualization.PresentationSubject;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphNode {
    private final PresentationSubject presentationSubject = new PresentationSubject();

    private final Map<String, ObservedList<GraphEdge<?>>> edges;

    public GraphNode(List<String> edgeTypes) {
        this.edges = new HashMap<>();
        for (String name : edgeTypes)
            edges.put(name, new ObservedList<>());
    }

    public ObservedList<GraphEdge<?>> getEdgeType(String name) {
        return edges.get(name);
    }

    public Collection<ObservedList<GraphEdge<?>>> getEdges() {
        return edges.values();
    }

    public String getIdentifier() {
        return "node" + hashCode();
    }

    public PresentationSubject getPresentationSubject() {
        return presentationSubject;
    }

    @Override
    public String toString() {
        return getIdentifier();
    }
}
