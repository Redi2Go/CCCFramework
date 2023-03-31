package at.htlperg.main;

import at.htlperg.algebra.Veci;
import at.htlperg.graph.Graph;
import at.htlperg.graph.GraphDijkstra;
import at.htlperg.graph.GraphEdge;
import at.htlperg.graph.GraphNode;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainExample {
    public static void main(String[] args) {
        Graph graph = new Graph(true);
        GraphNode graphNode1 = graph.addNode(1, new GraphNode(List.of("ref")));
        GraphNode graphNode2 = graph.addNode(2, new GraphNode(List.of("ref")));
        GraphEdge<Integer> edge = graph.addEdge(new GraphEdge<>(graphNode1, graphNode2, "ref", 1));
        edge.getPresentationSubject().setLabel("-1");

        graphNode1.getPresentationSubject().setLabel("test label");
        graphNode1.getPresentationSubject().setNodeColor(Color.GREEN);
        graphNode1.getPresentationSubject().setTextColor(Color.ORANGE);
        graphNode1.getPresentationSubject().setNodeSize(50);
        edge.getPresentationSubject().setLabel(String.valueOf(32));
        edge.getPresentationSubject().setTextColor(Color.CYAN);
        edge.getPresentationSubject().setNodeColor(Color.GREEN);
        graphNode1.getPresentationSubject().setPosition(new Veci(-1, 0));
        graphNode2.getPresentationSubject().setPosition(new Veci(1, 0));
        edge.getPresentationSubject().setNodeColor(Color.GREEN);
        graph.addNode(3, new GraphNode(List.of("ref")));
        graph.addNode(4, new GraphNode(List.of("ref")));
        graph.addNode(5, new GraphNode(List.of("ref")));
        graph.addNode(6, new GraphNode(List.of("ref")));
        graph.addNode(7, new GraphNode(List.of("ref")));


        graph.addEdge(new GraphEdge<>(graphNode1, graph.getNode(3), "ref", 1));
        graph.addEdge(new GraphEdge<>(graphNode1, graph.getNode(4), "ref", 1));
        graph.addEdge(new GraphEdge<>(graph.getNode(4), graph.getNode(3), "ref", 1));
        graph.addEdge(new GraphEdge<>(graph.getNode(4), graph.getNode(5), "ref", 1));
        graph.addEdge(new GraphEdge<>(graph.getNode(5), graph.getNode(6), "ref", 1));
        graph.addEdge(new GraphEdge<>(graph.getNode(6), graph.getNode(7), "ref", 1));

        for (int i = 1; i < 7; i++) {
            graph.getNode(i).getPresentationSubject().setLabel(graph.getNode(i).toString());
        }

        Map<GraphNode, LinkedList<GraphNode>> result = GraphDijkstra.calculateShortestPathFromSource(graphNode1, "ref");

        System.out.println(result);
    }
}
