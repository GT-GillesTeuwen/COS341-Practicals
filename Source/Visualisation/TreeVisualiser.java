package Visualisation;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import Exceptions.EmptyTreeException;
import Nodes.Node;
import Nodes.tNode;

import java.awt.geom.*;

public class TreeVisualiser extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    Graphics2D g2;
    DrawableNode[] squares;
    Color colour;
    Tree t;
    int zoom;
    boolean done = false;

    public int FONT_SIZE = 15;

    double offsetX, offsetY;
    DrawableNode selected;

    boolean dragging = false;

    public TreeVisualiser(Tree tree) {
        zoom = 0;
        t = tree;
        squares = new DrawableNode[t.getAllNodes().size()];
        int i = 0;
        for (int j = 0; j < t.getNodesPerLevel().length; j++) {
            for (int j2 = 0; j2 < t.getNodesPerLevel()[j]; j2++) {
                squares[i] = new DrawableNode(1600 / (t.getNodesPerLevel()[j] + 1) * (j2 + 1) + j, j * 80,
                        t.getAllNodes().get(i));
                squares[i].initialY = j * 60;
                i++;
            }
        }
        setDrawableChildren();
        setDrawableParents();
        setParents();

        colour = Color.BLACK;

        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        this.requestFocus();
    }

    private void setParents() {
        for (DrawableNode drawableNode : squares) {
            for (Node child : drawableNode.getChildren()) {
                DrawableNode childNode = getDrawableState(child);
                childNode.setParent(drawableNode.getNode());
            }
        }
    }

    private void setDrawableParents() {
        for (DrawableNode drawableNode : squares) {
            for (Node child : drawableNode.getChildren()) {
                DrawableNode childNode = getDrawableState(child);
                childNode.setDrawableParent(drawableNode);
            }
        }
    }

    private void setDrawableChildren() {
        for (DrawableNode drawableNode : squares) {
            DrawableNode[] drawableChildren = new DrawableNode[drawableNode.getChildren().length];
            int i = 0;
            for (Node child : drawableNode.getChildren()) {
                drawableChildren[i] = getDrawableState(child);
                i++;
            }
            drawableNode.setDrawableChildren(drawableChildren);

        }
    }

    public void forceDirectedArrange(double forceThreshold, int maxIterations) throws InterruptedException {
        System.out.println("begin");
        int t = 0;
        Force maxForce;
        do {
            maxForce = new Force(0, 0);
            Force[] totalForces = new Force[squares.length];
            for (int i = 0; i < squares.length; i++) {
                totalForces[i] = calculateForceForNode(squares[i]);
                if (totalForces[i].getMagnitude() > maxForce.getMagnitude()) {
                    maxForce = totalForces[i];
                }
            }
            for (int i = 0; i < squares.length; i++) {
                applyForceToNode(squares[i], totalForces[i], cooling(t));
                repaint();
            }
            t = t + 1;
            repaint();

        } while (t < maxIterations && maxForce.getMagnitude() > forceThreshold);
    }

    private void applyForceToNode(DrawableNode drawableNode, Force force, double cooling) {
        double chX = force.getxComponent() * cooling + drawableNode.getEllipse2d().getMinX();
        double chY = force.getyComponent() * cooling + drawableNode.getEllipse2d().getMinY();
        drawableNode.getEllipse2d().setFrame(chX, chY,
                drawableNode.getEllipse2d().getHeight(),
                drawableNode.getEllipse2d().getWidth());
    }

    private Force calculateForceForNode(DrawableNode node) {
        ArrayList<Force> allForces = new ArrayList<>();
        for (int j = 0; j < squares.length; j++) {
            allForces.add(calculateRepForceBetweenTwoNodes(node, squares[j]));
        }
        for (int i = 0; i < node.getChildren().length; i++) {
            DrawableNode child = getDrawableState(node.getChildren()[i]);
            allForces.add(calculateAttrForceBetweenTwoNodes(node, child));
        }
        if (node.getParent() != null) {
            DrawableNode parent = getDrawableState(node.getParent());
            allForces.add(calculateAttrForceBetweenTwoNodes(node, parent));
        }
        return new Force(allForces);
    }

    private Force calculateRepForceBetweenTwoNodes(DrawableNode n1, DrawableNode n2) {
        if (n1 == n2) {
            return new Force(0, 0);
        }

        double xDiff = (n1.getEllipse2d().getCenterX() - n2.getEllipse2d().getCenterX());
        if (xDiff > 0 && xDiff < DISTLIMIT) {
            xDiff = DISTLIMIT;
        }
        if (xDiff < 0 && xDiff > -DISTLIMIT) {
            xDiff = -DISTLIMIT;
        }
        double xComponent = (1.0 / xDiff);
        if (xDiff == 0) {
            xComponent = 0;
        }

        double yDiff = n1.getEllipse2d().getCenterY() - n2.getEllipse2d().getCenterY();
        if (yDiff > 0 && yDiff < DISTLIMIT) {
            yDiff = DISTLIMIT;
        }
        if (yDiff < 0 && yDiff > -DISTLIMIT) {
            yDiff = -DISTLIMIT;
        }
        double yComponent = (1.0 / yDiff);
        if (yDiff == 0) {
            yComponent = 0;
        }

        return new Force(600 * xComponent, 600 * yComponent);
    }

    double DISTLIMIT = 1;

    private Force calculateAttrForceBetweenTwoNodes(DrawableNode n1, DrawableNode n2) {
        if (n1 == n2) {
            return new Force(0, 0);
        }

        double xDiff = (n1.getEllipse2d().getCenterX() - n2.getEllipse2d().getCenterX());
        if (xDiff > 0 && xDiff < DISTLIMIT) {
            xDiff = DISTLIMIT;
        }
        if (xDiff < 0 && xDiff > -DISTLIMIT) {
            xDiff = -DISTLIMIT;
        }
        double xComponent = -(xDiff);
        if (xDiff == 0) {
            xComponent = 0;
        }

        double yDiff = n1.getEllipse2d().getCenterY() - n2.getEllipse2d().getCenterY();
        if (yDiff > 0 && yDiff < DISTLIMIT) {
            yDiff = DISTLIMIT;
        }
        if (yDiff < 0 && yDiff > -DISTLIMIT) {
            yDiff = -DISTLIMIT;
        }
        double yComponent = -(yDiff);
        if (yDiff == 0) {
            yComponent = 0;
        }
        return new Force(0.1 * xComponent, 0.1 * yComponent);
    }

    public double cooling(int t) {
        return Math.exp(((-t) * 1.0) / 10.0);
    }

    public int getDepth() {
        return t.getNodesPerLevel().length * 62;
    }

    public void paintComponent(Graphics g) {
        if (!done) {
            done = true;
            resetPositions();
        }

        BasicStroke dashed = new BasicStroke(2.0f);

        if (zoom < -5) {

            dashed = new BasicStroke(1.0f);
        } else if (zoom > 5) {
            dashed = new BasicStroke(3.0f);
        }

        super.paintComponent(g);
        g2 = (Graphics2D) g;
        g2.setStroke(dashed);
        g2.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
        g2.setColor(colour);
        for (int i = 0; i < squares.length; i++) {
            // Draw state circle
            squares[i].draw(g2, FONT_SIZE);
        }
        for (int i = 0; i < squares.length; i++) {
            g2.setColor(squares[i].getNode().getColor());
            // Draw state name
            g2.drawString(squares[i].getNode().getDisplayNameWithID(),
                    (int) squares[i].getEllipse2d().getCenterX()
                            - (int) (g2.getFontMetrics().stringWidth(squares[i].getNode().getDisplayNameWithID()) / 2),
                    (int) squares[i].getEllipse2d().getCenterY() + 5);
            int c = 0;

            // cater for arrow to initial node

            // Draw transitions
            for (Node op : squares[i].getChildren()) {

                DrawableNode dNode = getDrawableState(op);
                int d = 10;
                int h = 10;
                if (zoom < -5) {
                    d = 5;
                    h = 5;
                } else if (zoom > 5) {
                    d = 15;
                    h = 15;
                }
                drawArrowLine(g2, squares[i].getEllipse2d().getCenterX(),
                        squares[i].getEllipse2d().getMaxY(),
                        dNode.getEllipse2d().getCenterX(),
                        dNode.getEllipse2d().getMinY(), d,
                        h);

            }

        }

    }

    private void drawArrowLine(Graphics2D g, double x1, double y1, double x2, double y2, int d, int h) {

        int midX = (int) ((x1 + x2) / 2);
        int midY = (int) ((y1 + y2) / 2);

        double dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double sin = dy / D, cos = dx / D;

        dx = x2 - midX;
        dy = y2 - midY;
        D = Math.sqrt(dx * dx + dy * dy);
        sin = dy / D;
        cos = dx / D;
        double xm = D - d, xn = xm, ym = h, yn = -h, x;

        x = xm * cos - ym * sin + midX;
        ym = xm * sin + ym * cos + midY;
        xm = x;

        x = xn * cos - yn * sin + midX;
        yn = xn * sin + yn * cos + midY;
        xn = x;

        int[] xpoints = { (int) x2, (int) xm, (int) xn };
        int[] ypoints = { (int) y2, (int) ym, (int) yn };

        // g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

        Path2D path = new Path2D.Double();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        g.draw(path);

        g.fillPolygon(xpoints, ypoints, 3);
    }

    @Override
    public void mouseDragged(MouseEvent ev) {
        repaint();
        if (dragging) {
            double mx = ev.getX();
            double my = ev.getY();

            double x1 = mx - offsetX;
            double y1 = my - offsetY;
            if (selected != null) {

                selected.getEllipse2d().setFrame(x1, y1, selected.getEllipse2d().getWidth(),
                        selected.getEllipse2d().getHeight());
                repaint();
            } else {
                for (DrawableNode drawableNode : squares) {
                    double chX = x1 + drawableNode.getEllipse2d().getMinX();
                    double chY = y1 + drawableNode.getEllipse2d().getMinY();
                    drawableNode.getEllipse2d().setFrame(chX, chY,
                            drawableNode.getEllipse2d().getWidth(),
                            drawableNode.getEllipse2d().getHeight());
                }
                offsetX = mx;
                offsetY = my;
            }
            // selected = new Rectangle2D.Double(x1, y1, selected.getHeight(),
            // selected.getWidth());

        }

    }

    @Override
    public void mousePressed(MouseEvent ev) {
        double mx = ev.getX();
        double my = ev.getY();

        this.selected = getClickedShape(ev);

        if (selected != null) {
            double x1 = selected.getEllipse2d().getMinX();
            double y1 = selected.getEllipse2d().getMinY();

            if (mx > selected.getEllipse2d().getMinX() && mx < selected.getEllipse2d().getMaxX()
                    && my > selected.getEllipse2d().getMinY()
                    && my < selected.getEllipse2d().getMaxY()) {
                dragging = true;
                offsetX = mx - x1;
                offsetY = my - y1;
            }
        } else {
            dragging = true;
            offsetX = mx;
            offsetY = my;
        }
        repaint();
    }

    public DrawableNode getClickedShape(MouseEvent ev) {
        for (int j = 0; j < squares.length; j++) {
            if (squares[j].getEllipse2d().contains(getMousePosition())) {

                return squares[j];
            }
        }

        return null;
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        dragging = false;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if (SwingUtilities.isRightMouseButton(e)) {
                VisualiserSettings n = new VisualiserSettings(this);
                JFrame frame = new JFrame("Settings");
                frame.setContentPane(n);
                frame.setLocation(e.getX(), e.getY());
                frame.setVisible(true);
                frame.setSize(300, 100);
                return;
            }

            if (selected == null) {
                return;
            }
            NodeVisualiser n = new NodeVisualiser(selected);
            JFrame nodeFrame = new JFrame("Node");
            nodeFrame.setContentPane(n);
            nodeFrame.setLocation(e.getX(), e.getY());
            nodeFrame.setVisible(true);
            nodeFrame.setSize(250, 200);
            n.repaint();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public static void main(String args[]) {

    }

    public DrawableNode getDrawableState(Node n) {
        for (int i = 0; i < squares.length; i++) {
            if (squares[i].getNode() == n) {
                return squares[i];
            }
        }
        System.out.println("Could not find drawable node");
        return null;
    }

    public void resetPositions() {
        placeNode(squares[0]);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // try {
        // for (int i = 0; i < squares.length; i++) {
        // forceDirectedArrange(5, 2);
        // repaint();
        // new Thread().wait(1000);
        // }
        // } catch (InterruptedException e1) {
        // // TODO Auto-generated catch block
        // e1.printStackTrace();
        // }
        int notches = e.getWheelRotation();
        double mx = e.getX();
        double my = e.getY();

        for (DrawableNode drawableNode : squares) {
            double newX = drawableNode.getEllipse2d().getMinX();
            double newY = drawableNode.getEllipse2d().getMinY();

            // Zoom out
            if (notches > 0 && zoom > -14) {
                if (zoom <= 0) {
                    double adjustX = (drawableNode.getEllipse2d().getMinX() - mx) * 0.1;
                    double adjustY = (drawableNode.getEllipse2d().getMinY() - my) * 0.11;

                    double[] adjusts = { adjustX, adjustY };
                    drawableNode.pushAdjust(adjusts);
                    newX = ((drawableNode.getEllipse2d().getMinX()) - adjustX);
                    newY = ((drawableNode.getEllipse2d().getMinY()) - adjustY);
                } else {
                    double[] adjustsments = drawableNode.popAdjust();
                    newX = ((drawableNode.getEllipse2d().getMinX()) - adjustsments[0]);
                    newY = ((drawableNode.getEllipse2d().getMinY()) - adjustsments[1]);
                }

                double newHeight = drawableNode.getEllipse2d().getHeight() - (notches * 2);
                drawableNode.getEllipse2d().setFrame(newX, newY, drawableNode.getEllipse2d().getWidth(), newHeight);
            }
            // Zoom in
            if (notches < 0 && zoom < 14) {
                if (zoom >= 0) {
                    double adjustX = (drawableNode.getEllipse2d().getMinX() - mx) * 0.1;
                    double adjustY = (drawableNode.getEllipse2d().getMinY() - my) * 0.11;

                    double[] adjusts = { adjustX, adjustY };
                    drawableNode.pushAdjust(adjusts);
                    newX = ((drawableNode.getEllipse2d().getMinX()) + adjustX);
                    newY = ((drawableNode.getEllipse2d().getMinY()) + adjustY);
                } else {
                    double[] adjustsments = drawableNode.popAdjust();
                    newX = ((drawableNode.getEllipse2d().getMinX()) + adjustsments[0]);
                    newY = ((drawableNode.getEllipse2d().getMinY()) + adjustsments[1]);
                }

                double newHeight = drawableNode.getEllipse2d().getHeight() - (notches * 2);
                drawableNode.getEllipse2d().setFrame(newX, newY, drawableNode.getEllipse2d().getWidth(), newHeight);
            }

        }
        if (notches < 0 && zoom < 14) {
            FONT_SIZE++;
            zoom++;
        }
        if (notches > 0 && zoom > -14) {
            FONT_SIZE--;
            zoom--;
        }
        System.out.println(zoom);
        repaint();
    }

    private DrawableNode getLeftOnLevel(DrawableNode drawableNode) {
        for (int i = 0; i < squares.length; i++) {
            if (squares[i] == drawableNode) {
                if (i > 0 && squares[i - 1].initialY == drawableNode.initialY) {
                    return squares[i - 1];
                } else {
                    return null;
                }
            }
        }

        return null;
    }

    private void adjustClipping(DrawableNode drawableNode) {
        DrawableNode leftOnLevel = getLeftOnLevel(drawableNode);
        if (leftOnLevel == null) {
            return;
        }
        if (leftOnLevel.getEllipse2d().getMaxX() > drawableNode.getEllipse2d().getMinX() - 60) {
            double adjust = (leftOnLevel.getEllipse2d().getMaxX() + drawableNode.getEllipse2d().getWidth() + 60)
                    - drawableNode.getEllipse2d().getMinX();
            adjustNodeAndChildren(drawableNode, adjust);
        }
    }

    private void adjustNodeAndChildren(DrawableNode drawableNode, double adjust) {
        for (DrawableNode child : drawableNode.drawableChildren) {
            adjustNodeAndChildren(child, adjust);
        }
        drawableNode.getEllipse2d().setFrame(drawableNode.getEllipse2d().getMaxX() + adjust, drawableNode.initialY,
                drawableNode.getEllipse2d().getWidth(),
                drawableNode.getEllipse2d().getHeight());
    }

    private void placeNode(DrawableNode node) {
        if (node.drawableChildren.length > 0) {
            double avgX = 0;
            for (DrawableNode drawableNode : node.drawableChildren) {
                placeNode(drawableNode);
                avgX += drawableNode.getEllipse2d().getMinX();
            }
            avgX /= node.drawableChildren.length;
            node.getEllipse2d().setFrame(avgX, node.initialY, node.getEllipse2d().getWidth(),
                    node.getEllipse2d().getHeight());

        } else {
            DrawableNode leftOnLevel = getLeftOnLevel(node);
            if (leftOnLevel == null) {
                DrawableNode leftOnParentLevel = getLeftOnLevel(node.drawableParent);
                if (leftOnParentLevel == null) {

                    node.getEllipse2d().setFrame(0, node.initialY, node.getEllipse2d().getWidth(),
                            node.getEllipse2d().getHeight());
                } else {
                    node.getEllipse2d().setFrame(
                            leftOnParentLevel.getEllipse2d().getMaxX() + node.getEllipse2d().getWidth()
                                    + 55,
                            node.initialY, node.getEllipse2d().getWidth(),
                            node.getEllipse2d().getHeight());
                }
            } else {
                node.getEllipse2d().setFrame(
                        leftOnLevel.getEllipse2d().getMaxX() + node.getEllipse2d().getWidth() + 55,
                        node.initialY, node.getEllipse2d().getWidth(),
                        node.getEllipse2d().getHeight());
            }

        }
        adjustClipping(node);
    }

    public DrawableNode getDrawableNodeByID(int nodeID) {
        for (DrawableNode drawableNode : squares) {
            if (drawableNode.getNode().getId() == nodeID) {
                return drawableNode;
            }
        }
        return null;
    }

    public void goTo(DrawableNode toGoNode) {
        double toGoX = toGoNode.getEllipse2d().getMinX() - this.getWidth() / 2;
        double toGoY = toGoNode.getEllipse2d().getMinY() - this.getHeight() / 2;

        for (DrawableNode drawableNode : squares) {
            double chX = drawableNode.getEllipse2d().getMinX() - toGoX;
            double chY = drawableNode.getEllipse2d().getMinY() - toGoY;
            drawableNode.getEllipse2d().setFrame(chX, chY,
                    drawableNode.getEllipse2d().getWidth(),
                    drawableNode.getEllipse2d().getHeight());
        }
    }

}
