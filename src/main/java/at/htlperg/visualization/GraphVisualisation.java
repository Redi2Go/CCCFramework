package at.htlperg.visualization;

import at.htlperg.algebra.Veci;
import at.htlperg.graph.GraphEdge;
import at.htlperg.graph.GraphNode;
import at.htlperg.observable.IObserver;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

// Based on https://graphstream-project.org/doc/Tutorials/Getting-Started/1.0/
public class GraphVisualisation extends SingleGraph {
    public static final Color DEFAULT_TEXT_COLOR = Color.GRAY;
    public static final int DEFAULT_NODE_SIZE = 40;
    public static final Color DEFAULT_NODE_COLOR = Color.BLACK;

    private static final String EDGE_CSS_TEMPLATE =
            """
                    edge#%s {
                        text-size: 10px;
                        text-background-mode: rounded-box;
                        text-background-color: #%s;
                        text-color: #%s;
                        text-size: %dpx;
                        arrow-shape: arrow;
                        arrow-size: 10px;
                        fill-color: #%s;
                    }
                    """;
    private static final String NODE_CSS_TEMPLATE =
            """
                    node#%s {
                        text-background-mode: rounded-box;
                        text-background-color: #%s;
                        text-color: #%s;
                        text-size: 20px;
                        size: %dpx;
                        fill-color: #%s;
                    }
                    """;

    private final Object haltSynchronizationObject = new Object();
    private final boolean autoLayout;
    private final JSpinner playbackTimeout;
    private final JButton toggleButton;
    private boolean halted = true;

    private final Map<GraphNode, IObserver> nodeObservers = new HashMap<>();
    private final Map<GraphEdge<?>, IObserver> edgeObservers = new HashMap<>();

    public GraphVisualisation(boolean autoLayout) {
        super("Main");

        this.autoLayout = autoLayout;

        System.setProperty("org.graphstream.ui", "swing");

        Viewer viewer = display(autoLayout);
        ViewPanel viewPanel = (ViewPanel) viewer.getDefaultView(); // ViewPanel is the view for gs-ui-swing
//        ((JFrame) SwingUtilities.getWindowAncestor(viewPanel)).setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        this.toggleButton = new JButton(halted ? "Play" : "Stop");
        this.toggleButton.addActionListener(e -> {
            halted = !halted;
            this.toggleButton.setText(halted ? "Play" : "Stop");

            if (!halted) {
                synchronized (haltSynchronizationObject) {
                    haltSynchronizationObject.notifyAll();
                }
            }
        });

        this.playbackTimeout = new JSpinner(new SpinnerNumberModel(0, 0, 2000, 100));

        viewPanel.add(this.toggleButton);
        viewPanel.add(this.playbackTimeout);

        viewPanel.resizeFrame(1280, 720);
    }

    public void addEdge(GraphEdge<?> graphEdge) {
        if (getEdge(graphEdge.getIdentifier()) != null)
            return;

        Edge edge = addEdge(graphEdge.getIdentifier(), graphEdge.getFromNode().getIdentifier(), graphEdge.getToNode().getIdentifier(), true);

        edgeObservers.put(graphEdge, null);
        edgeObservers.replace(graphEdge, graphEdge.getPresentationSubject().attachAndFire(() -> {
            edge.setAttribute("ui.label", graphEdge.getPresentationSubject().getLabel() + "");
            this.updateStyleSheet();
            this.onModelChange();
        }));
    }

    public void removeEdge(GraphEdge<?> graphEdge) {
        if (getEdge(graphEdge.getIdentifier()) == null)
            return;

        removeEdge(graphEdge.getIdentifier());

        graphEdge.getValue().detach(edgeObservers.remove(graphEdge));
    }

    public void addNode(GraphNode graphNode) {
        if (getNode(graphNode.getIdentifier()) != null)
            return;

        Node node = addNode(graphNode.getIdentifier());

        nodeObservers.put(graphNode, null);
        nodeObservers.replace(graphNode, graphNode.getPresentationSubject().attachAndFire(() -> {
            node.setAttribute("ui.label", graphNode.getPresentationSubject().getLabel());

            Veci position = graphNode.getPresentationSubject().getPosition();
            if (position != null && !autoLayout)
                node.setAttribute("xy", position.x(), position.y());

            this.updateStyleSheet();
            this.onModelChange();
        }));
    }

    public void removeNode(GraphNode graphNode) {
        if (getNode(graphNode.getIdentifier()) == null)
            return;

        removeNode(graphNode.getIdentifier());

        graphNode.getPresentationSubject().detach(nodeObservers.remove(graphNode));
    }

    private void onModelChange() {
        try {
            synchronized (haltSynchronizationObject) {
                if (halted)
                    haltSynchronizationObject.wait();
                else if (!playbackTimeout.getValue().equals(0))
                    haltSynchronizationObject.wait((Integer) playbackTimeout.getValue());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateStyleSheet() {
        StringBuilder builder = new StringBuilder();

        for (GraphNode graphNode : nodeObservers.keySet()) {
            builder.append(buildNodeStylesheet(graphNode.getIdentifier(), graphNode.getPresentationSubject())).append('\n');
        }

        for (GraphEdge<?> graphEdge : edgeObservers.keySet())
            builder.append(buildEdgeStylesheet(graphEdge.getIdentifier(), graphEdge.getPresentationSubject())).append('\n');

        setAttribute("stylesheet", builder.toString());
    }

    private String buildNodeStylesheet(String nodeId, PresentationSubject presentationSubject) {
        return NODE_CSS_TEMPLATE.formatted(
                nodeId,
                toHexColor(getContrastColor(presentationSubject.getTextColor())),
                toHexColor(presentationSubject.getTextColor()),
                presentationSubject.getNodeSize(),
                toHexColor(presentationSubject.getNodeColor())
        );
    }

    private String buildEdgeStylesheet(String edgeId, PresentationSubject presentationSubject) {
        return EDGE_CSS_TEMPLATE.formatted(
                edgeId,
                toHexColor(getContrastColor(presentationSubject.getTextColor())),
                toHexColor(presentationSubject.getTextColor()),
                presentationSubject.getNodeSize(),
                toHexColor(presentationSubject.getNodeColor())
        );
    }

    private static Color getContrastColor(Color color) {
        int y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
        return y >= 128 ? Color.black : Color.white;
    }

    private static String toHexColor(Color color) {
        return "%02x%02x%02x".formatted(color.getRed(), color.getGreen(), color.getBlue());
    }
}
