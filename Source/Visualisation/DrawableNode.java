package Visualisation;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D.Double;
import java.util.Map;
import java.util.Stack;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class DrawableNode {
    public static int SIZE = 30;
    Double ellipse2d;
    Node parent;
    Node node;
    Node[] children;
    Stack<double[]> adjustments = new Stack();

    public DrawableNode(int initialX, int initialY, Node node) {
        this.ellipse2d = new Rectangle2D.Double(initialX, initialY, 1.5 * SIZE, SIZE);
        this.node = node;
        if (node instanceof nNode) {
            this.children = ((nNode) (node)).getChildren();
        } else {
            this.children = new Node[0];
        }
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public void pushAdjust(double[] adjust) {
        adjustments.push(adjust);
    }

    public double[] popAdjust() {
        return adjustments.pop();
    }

    public Rectangle2D getEllipse2d() {
        return ellipse2d;
    }

    public Node getNode() {
        return node;
    }

    public Node[] getChildren() {
        return children;
    }

    public Ellipse2D getFinalRing() {
        return new Ellipse2D.Double(ellipse2d.getMinX() - 5, ellipse2d.getMinY() - 5, SIZE + 10, SIZE + 10);
    }

    public void draw(Graphics2D g2, int FONT_SIZE) {

        g2.setColor(this.node.getColor());
        if (this.getNode() instanceof tNode) {
            g2.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        } else {
            g2.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        }
        this.getEllipse2d().setFrame(this.getEllipse2d().getMinX(), this.getEllipse2d().getMinY(),
                (g2.getFontMetrics().stringWidth(this.getNode().getDisplayName())) + 10, SIZE);
        g2.draw(this.getEllipse2d());
    }
}
