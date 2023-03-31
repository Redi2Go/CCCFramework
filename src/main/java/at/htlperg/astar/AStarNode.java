package at.htlperg.astar;

public class AStarNode {
    public final String value;
    private double scoreG = 0, scoreF = 0;
    private final double scoreH;

    public AStarEdge[] adjacencies;
    private AStarNode next;

    public AStarNode(String val, double hVal) {
        value = val;
        scoreH = hVal;
    }

    public double getScoreG() {
        return scoreG;
    }

    public void setScoreG(double scoreG) {
        this.scoreG = scoreG;
    }

    public double getScoreH() {
        return scoreH;
    }

    public double getScoreF() {
        return scoreF;
    }

    public void setScoreF(double scoreF) {
        this.scoreF = scoreF;
    }

    public AStarNode getNext() {
        return next;
    }

    public void setNext(AStarNode next) {
        this.next = next;
    }
}
