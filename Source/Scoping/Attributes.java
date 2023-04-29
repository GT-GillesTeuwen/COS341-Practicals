package Scoping;

import Nodes.Node;

public class Attributes {
    private int scopeID;
    private Node node;
    private boolean called;

    public Attributes(int scopeID, Node node) {
        this.scopeID = scopeID;
        this.node = node;
    }

    public void setCalled(boolean called) {
        this.called = called;
    }

    public Node getNode() {
        return node;
    }

    public boolean isCalled() {
        return called;
    }

    public int getScopeID() {
        return scopeID;
    }

    public void setScopeID(int scopeID) {
        this.scopeID = scopeID;
    }

    @Override
    public String toString() {
        if (scopeID == 0) {
            return "{scopeID:Global}";
        }
        return "{scopeID:" + scopeID + "}";
    }
}
