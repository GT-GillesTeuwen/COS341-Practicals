package Visualisation;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import Nodes.Node;
import Nodes.tNode;

import java.awt.geom.*;

public class TreeVisualiser extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    Graphics2D g2;
    DrawableNode[] squares;
    Color colour;
    Tree t;
    int zoom;

    double offsetX, offsetY;
    DrawableNode selected;

    boolean dragging = false;

    public TreeVisualiser(Tree tree) throws Exception {
        zoom = 0;
        t = tree;
        squares = new DrawableNode[t.getAllNodes().size()];
        int i = 0;
        for (int j = 0; j < t.getNodesPerLevel().length; j++) {
            for (int j2 = 0; j2 < t.getNodesPerLevel()[j]; j2++) {
                squares[i] = new DrawableNode(1600 / (t.getNodesPerLevel()[j] + 1) * (j2 + 1) + j, j * 60,
                        t.getAllNodes().get(i));
                i++;
            }
        }
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
        final BasicStroke dashed = new BasicStroke(2.0f);

        super.paintComponent(g);
        g2 = (Graphics2D) g;
        g2.setStroke(dashed);
        g2.setFont(new Font("Arial", Font.PLAIN, 9));
        g2.setColor(colour);
        for (int i = 0; i < squares.length; i++) {

            // Draw state circle
            g2.setColor(squares[i].node.getColor());
            if (squares[i].getNode() instanceof tNode) {
                g2.setFont(new Font("Arial", Font.PLAIN, 14));
            } else {
                g2.setFont(new Font("Arial", Font.PLAIN, 8));
            }
            g2.draw(squares[i].getEllipse2d());

            // Draw state name
            g2.drawString(squares[i].getNode().getDisplayName(),
                    (int) squares[i].getEllipse2d().getCenterX()
                            - (int) (squares[i].getNode().getDisplayName().length() * 2.5),
                    (int) squares[i].getEllipse2d().getCenterY() + 5);
            int c = 0;

            // cater for arrow to initial node

            // Draw transitions
            for (Node op : squares[i].getChildren()) {

                DrawableNode dNode = getDrawableState(op);

                drawArrowLine(g2, squares[i].getEllipse2d().getCenterX(), squares[i].getEllipse2d().getCenterY(),
                        dNode.getEllipse2d().getCenterX(),
                        dNode.getEllipse2d().getCenterY(), 10,
                        10);

            }

        }

    }

    private void drawArrowLine(Graphics2D g, double x1, double y1, double x2, double y2, int d, int h) {

        int midX = (int) ((x1 + x2) / 2);
        int midY = (int) ((y1 + y2) / 2);

        double dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double sin = dy / D, cos = dx / D;

        // Initial up and left of final
        if (sin > 0 && cos > 0) {
            y1 += (DrawableNode.SIZE / 2);
            y2 -= (DrawableNode.SIZE / 2);
            x2 -= 0;// (DrawableNode.SIZE / 2);
        }

        // Initial up and right of final
        else if (sin > 0 && cos < 0) {
            y1 += (DrawableNode.SIZE / 2);
            y2 -= (DrawableNode.SIZE / 2);
            x2 += 0;// (DrawableNode.SIZE / 2);
        }

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

        if (x1 == x2 && y1 == y2) {
            midY -= 100;
            x1 -= DrawableNode.SIZE / 4;
            x2 += DrawableNode.SIZE / 4;
            y1 -= DrawableNode.SIZE / 2;
            y2 -= DrawableNode.SIZE / 2;

        } else {

        }
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

                selected.getEllipse2d().setFrame(x1, y1, selected.getEllipse2d().getHeight(),
                        selected.getEllipse2d().getWidth());
                repaint();
            } else {
                for (DrawableNode drawableNode : squares) {
                    double chX = x1 + drawableNode.getEllipse2d().getMinX();
                    double chY = y1 + drawableNode.getEllipse2d().getMinY();
                    drawableNode.getEllipse2d().setFrame(chX, chY,
                            drawableNode.getEllipse2d().getHeight(),
                            drawableNode.getEllipse2d().getWidth());
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
        System.out.println("zoom before scroll: " + zoom);
        System.out.println(mx + "  " + my);
        for (DrawableNode drawableNode : squares) {
            double newX = drawableNode.getEllipse2d().getCenterX();
            double newY = drawableNode.getEllipse2d().getCenterY();
            if (notches > 0) {
                if (zoom <= 0) {
                    System.out.println("zoom out from 0");
                    double adjustX = (drawableNode.getEllipse2d().getCenterX() - mx) * 0.1;
                    double adjustY = (drawableNode.getEllipse2d().getCenterY() - my) * 0.11;
                    double[] adjusts = { adjustX, adjustY };
                    drawableNode.pushAdjust(adjusts);
                    newX = ((drawableNode.getEllipse2d().getCenterX()) - adjustX);
                    newY = ((drawableNode.getEllipse2d().getCenterY()) - adjustY);
                } else {
                    double[] adjustsments = drawableNode.popAdjust();
                    newX = ((drawableNode.getEllipse2d().getCenterX()) - adjustsments[0]);
                    newY = ((drawableNode.getEllipse2d().getCenterY()) - adjustsments[1]);
                }

            }
            if (notches < 0) {
                if (zoom >= 0) {
                    double adjustX = (drawableNode.getEllipse2d().getCenterX() - mx) * 0.5;
                    double adjustY = (drawableNode.getEllipse2d().getCenterY() - my) * 0.5;
                    double[] adjusts = { adjustX, adjustY };
                    drawableNode.pushAdjust(adjusts);
                    newX = ((drawableNode.getEllipse2d().getCenterX()) + adjustX);
                    newY = ((drawableNode.getEllipse2d().getCenterY()) + adjustY);
                } else {
                    double[] adjustsments = drawableNode.popAdjust();
                    newX = ((drawableNode.getEllipse2d().getCenterX()) + adjustsments[0]);
                    newY = ((drawableNode.getEllipse2d().getCenterY()) + adjustsments[1]);
                }
            }

            drawableNode.getEllipse2d().setFrame(newX,
                    newY,
                    drawableNode.getEllipse2d().getHeight() - notches * 2,
                    drawableNode.getEllipse2d().getWidth() - notches * 2);
        }
        if (notches < 0) {
            zoom++;
        }
        if (notches > 0) {
            zoom--;
        }
        System.out.println("zoom after scroll: " + zoom);

        repaint();
    }

}
