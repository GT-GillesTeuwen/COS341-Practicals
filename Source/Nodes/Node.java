package Nodes;

import java.awt.Color;

public class Node {
    protected String displayName;
    protected int id;
    protected static int idCount = 0;
    protected Color color;

    public Node(String displayName) {
        this.displayName = displayName;
        id = idCount++;
        this.color = Color.BLACK;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Color getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public Node reduceOneStepDerivations() {
        return null;
    }

    public Node reduceType() {
        return null;
    }
}
