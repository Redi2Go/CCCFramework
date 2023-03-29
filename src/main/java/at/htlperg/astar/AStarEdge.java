package at.htlperg.astar;

public class AStarEdge {

    public final double cost;
    public final AStarNode target;

    public AStarEdge(AStarNode targetAStarNode, double costVal) {
        target = targetAStarNode;
        cost = costVal;
    }

}
