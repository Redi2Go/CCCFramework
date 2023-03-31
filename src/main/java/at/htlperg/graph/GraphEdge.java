package at.htlperg.graph;

import at.htlperg.observable.ObservedObject;
import at.htlperg.visualization.PresentationSubject;

public class GraphEdge<T> {
    private final PresentationSubject presentationSubject = new PresentationSubject();

    private final GraphNode fromNode, toNode;
    private final ObservedObject<T> value = new ObservedObject<>();
    private final double weight;
    private final String type;

    public GraphEdge(GraphNode fromNode, GraphNode toNode, String type, double weight) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.type = type;
        this.weight = weight;
    }

    public String getIdentifier() {
        return "edge" + hashCode();
    }

    public GraphNode getFromNode() {
        return fromNode;
    }

    public GraphNode getToNode() {
        return toNode;
    }

    public ObservedObject<T> getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public PresentationSubject getPresentationSubject() {
        return presentationSubject;
    }

    public double getWeight() {
        return weight;
    }
}
