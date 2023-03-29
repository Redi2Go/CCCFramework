package at.htlperg.visualization;

import at.htlperg.algebra.Veci;
import at.htlperg.observable.ISubject;

import java.awt.*;

public class PresentationSubject extends ISubject {
    private Color textColor = GraphVisualisation.DEFAULT_TEXT_COLOR;
    private int nodeSize = GraphVisualisation.DEFAULT_NODE_SIZE;
    private Color nodeColor = GraphVisualisation.DEFAULT_NODE_COLOR;
    private String label = "";
    private Veci position = null;

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        notifyObservers();
    }

    public int getNodeSize() {
        return nodeSize;
    }

    public void setNodeSize(int size) {
        this.nodeSize = size;
        notifyObservers();
    }

    public Color getNodeColor() {
        return nodeColor;
    }

    public void setNodeColor(Color nodeColor) {
        this.nodeColor = nodeColor;
        notifyObservers();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        notifyObservers();
    }

    public Veci getPosition() {
        return position;
    }

    public void setPosition(Veci position) {
        this.position = position;
        notifyObservers();
    }
}
