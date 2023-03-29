package at.htlperg.astar;

public class AStarNode {

    public final String value;
    public double g_scores;
    public final double h_scores;
    public double f_scores = 0;
    public AStarEdge[] adjacencies;
    public AStarNode parent;

    public AStarNode(String val, double hVal) {
        value = val;
        h_scores = hVal;
    }

    public String toString() {
        return value;

    }
}
