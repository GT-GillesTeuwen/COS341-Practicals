import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.geom.*;

public class TreeVisualiser extends JPanel implements MouseListener, MouseMotionListener {
    Graphics2D g2;
    DrawableNode[] squares;
    Color colour;
    Tree t;

    double offsetX, offsetY;
    DrawableNode selected;

    boolean dragging = false;

    public TreeVisualiser(Tree tree) throws Exception {
        t=tree;
        squares = new DrawableNode[t.getAllNodes().size()];
        int i = 0;
        for (int j = 0; j < t.getNodesPerLevel().length; j++) {
            for (int j2 = 0; j2 < t.getNodesPerLevel()[j]; j2++) {
                squares[i] = new DrawableNode(1600/(t.getNodesPerLevel()[j]+1) * (j2+1)+j, j*60, t.getAllNodes().get(i));
                i++;
            }
        }

        colour = Color.BLACK;

        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.requestFocus();

    }

    public int getDepth(){
        return t.getNodesPerLevel().length*62;
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
            if(squares[i].getNode() instanceof tNode){
                g2.setColor(Color.RED);
                g2.setFont(new Font("Arial", Font.PLAIN, 14));
            }else{
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.PLAIN, 8));
            }
            g2.draw(squares[i].getEllipse2d());
           

            // Draw state name
            g2.drawString(squares[i].getNode().getDisplayName(),
                    (int) squares[i].getEllipse2d().getCenterX() - (int)(squares[i].getNode().getDisplayName().length() * 2.5),
                    (int) squares[i].getEllipse2d().getCenterY() + 5);
            int c = 0;

            // cater for arrow to initial node
           
            // Draw transitions
            for (Node op : squares[i].getChildren()) {
                
                    DrawableNode dNode=getDrawableState(op);
                 
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
            x2 -= 0;//(DrawableNode.SIZE / 2);
        }

        
        // Initial up and right of final
        else if (sin > 0 && cos < 0) {
            y1 += (DrawableNode.SIZE / 2);
            y2 -= (DrawableNode.SIZE / 2);
            x2 += 0;//(DrawableNode.SIZE / 2);
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

        if(x1==x2 && y1==y2){
            midY-=100;
            x1-=DrawableNode.SIZE/4;
            x2+=DrawableNode.SIZE/4;
            y1-=DrawableNode.SIZE/2;
            y2-=DrawableNode.SIZE/2;
            
        }else{
            

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
        if (dragging) {
            double mx = ev.getX();
            double my = ev.getY();

            double x1 = mx - offsetX;
            double y1 = my - offsetY;
            selected.getEllipse2d().setFrame(x1, y1, selected.getEllipse2d().getHeight(),
                    selected.getEllipse2d().getWidth());
            // selected = new Rectangle2D.Double(x1, y1, selected.getHeight(),
            // selected.getWidth());
            repaint();
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
        }

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
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
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

}
