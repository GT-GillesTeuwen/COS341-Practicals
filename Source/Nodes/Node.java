package Nodes;

import java.awt.Color;

public class Node {
    protected String displayName;
    protected int id;
    protected static int idCount = 0;
    protected Color color;
    protected Color prevColor;

    public Node(String displayName) {
        this.displayName = displayName;
        id = idCount++;
        this.color = Color.BLACK;
        this.prevColor = null;
    }

    public void resetColour() {
        if (this.prevColor != null) {
            this.color = prevColor;
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayNameWithID() {
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
