package Nodes;

public class Node {
    protected String displayName;
    protected int id;
    protected static int idCount = 0;

    public Node(String displayName) {
        this.displayName = displayName;
        id = idCount++;
    }

    public String getDisplayName() {
        return displayName;
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
