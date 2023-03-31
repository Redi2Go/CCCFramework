package at.htlperg.main.testlevel;

import at.htlperg.algebra.Mat;
import at.htlperg.algebra.Veco;
import at.htlperg.graph.Graph;
import at.htlperg.graph.GraphDijkstra;
import at.htlperg.graph.GraphEdge;
import at.htlperg.graph.GraphNode;
import at.htlperg.io.InputReader;
import at.htlperg.io.Level;
import at.htlperg.simplereader.SimpleReader;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Level42 extends Level {
    private Graph graph;

    private Veco<Economist> economists;

    public Level42() {
        super(42);
    }

    @Override
    public void readLevel(InputReader inputReader, SimpleReader simpleReader) {

//        SimpleReader testing
//        simpleReader.getLines();
//        List<Integer> test = simpleReader.getSplitLine(2, Integer.class, " ");
//        int test2 = test.get(2)+ test.get(3);
//        int exactpos = simpleReader.getExactPosition(2, 2, Integer.class, " ");
//        Integer exactposShouldNull = simpleReader.getExactPosition(0, 1, Integer.class, " ");
//
//        simpleReader.get(0);
//
////        AStar testing
//
//        AStarImpl aStar = new AStarImpl();
//        AStarNode n1 = new AStarNode("Lol", 366);
//        AStarNode n2 = new AStarNode("Sheesh", 374);
//        AStarNode n3 = new AStarNode("Lol2", 380);
//        AStarNode n4 = new AStarNode("Sheesh2", 253);
//        AStarNode n5 = new AStarNode("Lol3", 178);
//
//        n1.adjacencies = new AStarEdge[]{
//                new AStarEdge(n3, 10),
//                new AStarEdge(n2, 100)
//        };
//        n3.adjacencies = new AStarEdge[]{
//                new AStarEdge(n5, 10),
//                new AStarEdge(n4, 100)
//        };
//        n2.adjacencies = new AStarEdge[]{
//                new AStarEdge(n1, 10),
//                new AStarEdge(n3, 10),
//                new AStarEdge(n4, 10),
//                new AStarEdge(n5, 10)
//        };
//        n5.adjacencies = new AStarEdge[]{
//
//        };
//        n4.adjacencies = new AStarEdge[]{
//
//        };
//
//        aStar.search(n1, n5);
//        List<AStarNode> path = aStar.printPath(n5);
//        System.out.println("Path: " + path);
//
//        //ComplexReader testing

        Mat<Integer> economistsLines = inputReader.matrix().map(Integer::parseInt);
        Map<Integer, Veco<Integer>> sweetsLines =  inputReader.matrix().map(Integer::parseInt)
                .getRows().toDict(row -> Map.entry(row.x(), row.subVector(1)));

        economists = economistsLines.getRows().map(line -> new Economist(
                line.x(),
                Map.ofEntries(sweetsLines.entrySet().stream().map(entry ->
                        Map.entry(entry.getKey(), entry.getValue().component(line.x() - 1))).toArray(Map.Entry[]::new)),
                line.subVector(1).toList()
        ));

        graph = new Graph(true);

        sweetsLines.keySet().forEach(sweet -> {
            GraphNode graphNode = new GraphNode(List.of("owns", "values"));
            graphNode.getPresentationSubject().setLabel("Sweet " + sweet);

            graph.addNode(sweet, graphNode);
        });

        economists.forEach(economist -> {
            // Create Economist-Node and set initial label and node color
            GraphNode graphNode = new GraphNode(List.of("owns", "values"));
            graphNode.getPresentationSubject().setLabel("Eco " + economist.id);
            // Visualize economists inventory size via the nodes blue color
            graphNode.getPresentationSubject().setNodeColor(new Color(0, Math.min(255, economist.inventory.size() * 50), 0));

            graph.addNode(economist, graphNode);
        });

        economists.forEach(economist -> {
            GraphNode economistNode = graph.getNode(economist);
            economist.inventory.forEach(sweetId -> {
                GraphNode sweetNode = graph.getNode(sweetId);

                GraphEdge<Integer> graphEdge = new GraphEdge<>(economistNode, sweetNode, "owns", 1);
                graphEdge.getPresentationSubject().setLabel("owns");
                graphEdge.getPresentationSubject().setNodeSize(15);
                graphEdge.getPresentationSubject().setNodeColor(
                        new Color(Math.min(255, economist.subjectiveValues.get(sweetId) * 25), 0, 0)
                );

                graph.addEdge(graphEdge);
            });
        });

        Map<GraphNode, LinkedList<GraphNode>> distances = GraphDijkstra.calculateShortestPathFromSource(graph.getNode(economists.x()), "owns");
        System.out.println(distances);
    }

    @Override
    public List<String> solveLevel() {
        return economists.map(this::findBestTrade).toList();
    }

    private String findBestTrade(Economist economist) {
        List<Trade> possibleTrades = new ArrayList<>();

        Veco<Economist> possiblePartners = economists.filter(e -> e != economist);
        for (Economist partner : possiblePartners.toList()) {
            for (int sweetId1 : economist.inventory) {
                for (int sweetId2 : partner.inventory)
                    possibleTrades.add(new Trade(economist, sweetId1, partner, sweetId2));
            }
        }

        return possibleTrades.stream().filter(Trade::isBeneficial).max(new TradeComparator()).orElseThrow().toOutput();
    }

    private static class TradeComparator implements Comparator<Trade> {

        @Override
        public int compare(Trade o1, Trade o2) {
            int c = Comparator.comparingInt(Trade::calcBenefit1).compare(o1, o2);
            if (c != 0)
                return c;

            c = Comparator.<Trade>comparingInt(o -> o.econ2.id).reversed().compare(o1, o2);
            if (c != 0)
                return c;

            c = Comparator.<Trade>comparingInt(o -> o.sweetId1).reversed().compare(o1, o2);
            if (c != 0)
                return c;

            return Comparator.<Trade>comparingInt(o -> o.sweetId2).reversed().compare(o1, o2);
        }
    }

    private record Economist(int id, Map<Integer, Integer> subjectiveValues, List<Integer> inventory) {
    }

    private record Trade(Economist econ1, int sweetId1, Economist econ2, int sweetId2) {
        public boolean isBeneficial() {
            return calcBenefit1() > 0 && calcBenefit2() > 0;
        }

        public int calcBenefit1() {
            return econ1.subjectiveValues.get(sweetId2) - econ1.subjectiveValues.get(sweetId1);
        }

        public int calcBenefit2() {
            return econ2.subjectiveValues.get(sweetId1) - econ2.subjectiveValues.get(sweetId2);
        }

        public String toOutput() {
            return "%d %d %d %d".formatted(econ1.id, sweetId1, econ2.id, sweetId2);
        }
    }
}
