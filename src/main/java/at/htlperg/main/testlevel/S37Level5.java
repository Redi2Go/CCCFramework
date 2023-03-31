package at.htlperg.main.testlevel;

import at.htlperg.algebra.Mat;
import at.htlperg.algebra.Veci;
import at.htlperg.algebra.Veco;
import at.htlperg.graph.Graph;
import at.htlperg.graph.GraphDijkstra;
import at.htlperg.graph.GraphEdge;
import at.htlperg.graph.GraphNode;
import at.htlperg.io.InputReader;
import at.htlperg.io.Level;
import at.htlperg.simplereader.SimpleReader;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class S37Level5 extends Level {
    private final Veci[] deltas = new Veci[]{
            new Veci(-2, 0),
            new Veci(2, 0),

            new Veci(-1, -1),
            new Veci(1, -1),
            new Veci(-1, 1),
            new Veci(1, 1),
    };
    List<Mat<Character>> output = new LinkedList<>();
    public S37Level5() {
        super(5);
    }

    @Override
    public void readLevel(InputReader inputReader, SimpleReader simpleReader) {
        int matrixCount = inputReader.line().asInt();
        for (int i = 0; i < matrixCount; i++) {
            int countWalls = inputReader.line().asInt();
            if(countWalls == 0){
//                output.add(inputReader.matrix().map(s -> {s.toCharArray()}));
            }
            Mat<String> matrix = inputReader.matrix();
            Mat<Character> chars = new Mat<>(matrix.getRows().map(row -> {
                char[] c = row.x().toCharArray();
                List<Character> cs = new ArrayList<>();
                for (char cc : c)
                    cs.add(cc);

                return new Veco<Character>(cs);
            }));

            Map<GraphNode, LinkedList<GraphNode>> test = pathfind(chars);


//            result.forEach((target, path) -> {
//                if (!path.isEmpty() && "edge".equals(target.getValue())) {
//                    b.set(false);
//                }
//            });
        }
    }

//    public void blockPaths(int remaining, )

    public Map<GraphNode, LinkedList<GraphNode>> pathfind(Mat<Character> chars) {
        Graph graph = new Graph(false);
        boolean trapped = false;

//            GraphNode wasp = graph.addNode("W", new GraphNode(List.of("ref")));


        // make border
        chars = makeBorder(chars);
        System.out.println(chars);

        GraphNode wasp = null;
        for (int y = 0; y < chars.getHeight(); y++) {
            for (int x = 0; x < chars.getWidth(y); x++) {
                char c = chars.component(new Veci(x, y));
                if (c == 'O') {
                    graph.addNode(new Veci(x, y).hashCode(), new GraphNode(List.of("ref"))).setValue(new Veci(x, y));
                } else if (c == 'T') {
                    graph.addNode(new Veci(x, y).hashCode(), new GraphNode(List.of("ref"))).setValue("edge");
                } else if (c == 'W') {
                    wasp = graph.addNode(new Veci(x, y).hashCode(), new GraphNode(List.of("ref")));
                    wasp.getPresentationSubject().setNodeColor(Color.YELLOW);
                }
            }
        }

        for (int y = 0; y < chars.getHeight(); y++) {
            for (int x = 0; x < chars.getWidth(y); x++) {
                Character c = chars.component(new Veci(x, y));
                if (c != null && (c == (char)79 || c == 'T' || c == 'W')) {
                    for (Veci delta : deltas) {
                        Character nc = chars.component(new Veci(x, y).add(delta));
                        if (nc == null || (nc != (char)79 && nc != 'T'))
                            continue;

                        graph.addEdge(new GraphEdge<>(graph.getNode(new Veci(x, y).hashCode()), graph.getNode(new Veci(x, y).add(delta).hashCode()), "ref", 1));
                    }
                }
            }
        }

        Map<GraphNode, LinkedList<GraphNode>> result = GraphDijkstra.calculateShortestPathFromSource(wasp, "ref");
        return result;
    }

    public Mat<Character> makeBorder(Mat<Character> chars) {

        ArrayList<Character> topbottom = new ArrayList<>();
        for (int i = 0; i < chars.getWidth() + 2; i++) {
            topbottom.add('T');
        }
        Veco<Character> tobbottomVec = new Veco<>(topbottom);


        ArrayList<Veco<Character>> vecoList = new ArrayList<>();
        vecoList.add(tobbottomVec);
//        for (Veco<Character> row :chars.getRows().toList()) {
//            ArrayList<Character> charList = new ArrayList<>();
//            charList.add('T');
//            charList.add('-');
//            charList.addAll(row.toList());
//            charList.add('-');
//            charList.add('T');
//            vecoList.add(new Veco<Character>(charList));
//        }

        for (int i = 0; i < chars.getRows().length(); i++) {
            ArrayList<Character> charList = new ArrayList<>();
            charList.add('T');
            charList.addAll(chars.getRows().toList().get(i).toList());
            charList.add('T');
            vecoList.add(new Veco<Character>(charList));
        }
        vecoList.add(tobbottomVec);


        Mat<Character> result = new Mat<>(new Veco<Veco<Character>>(vecoList));

//        ArrayList rowList = new ArrayList();
//          for ()
//
//          Veco row = new Veco();
//          row.


//        Mat<Character> withBorder = new Mat<Character>();


        return result;
    }

    @Override
    public List<String> solveLevel() {
        List<String> result = new LinkedList<>();
        result.add(output.size() + "");
        result.add("");
        output.forEach(m-> {
            m.getRows().toList().forEach(row->{
                result.add(row.toString());
            });
        });
        return result;
    }
}
