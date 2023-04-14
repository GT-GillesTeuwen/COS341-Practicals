
import java.awt.geom.*;
import java.util.Map;

public class DrawableNode {
    public static final int SIZE = 30;
    Ellipse2D ellipse2d;
    Node node;
    Node[] children;

    public DrawableNode(int initialX, int initialY, Node node) {
        this.ellipse2d = new Ellipse2D.Double(initialX, initialY, SIZE, SIZE);
        this.node = node;
        if(node instanceof nNode){
            this.children=((nNode)(node)).getChildren();
        }else{
            this.children=new Node[0];
        }
    }

    public Ellipse2D getEllipse2d() {
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
}
