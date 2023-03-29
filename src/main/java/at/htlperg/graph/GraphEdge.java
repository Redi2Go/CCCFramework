package at.htlperg.graph;

import at.htlperg.observable.ObservedObject;
import at.htlperg.visualization.PresentationSubject;

public class GraphEdge<T> {
    private final PresentationSubject presentationSubject = new PresentationSubject();

    private final GraphNode fromNode, toNode;
    private final ObservedObject<T> value = new ObservedObject<>();
    private final String type;

    public GraphEdge(GraphNode fromNode, GraphNode toNode, String type) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.type = type;
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
}
