package Scoping;

import Nodes.Node;
import Nodes.nNode;

public class Attributes {
    private int scopeID;
    private Node node;
    private boolean called;
    public static int mainScope;

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

    public String[] getAtts() {
        String[] atts = new String[3];
        if (scopeID == 0) {
            atts[0] = "GLOBAL";
        } else if (scopeID == mainScope) {
            atts[0] = "MAIN";
        } else {
            atts[0] = scopeID + "";
        }

        if (node.getDisplayName().equals("PROC")) {
            atts[1] = "Declare Procedure";
        } else if (node.getDisplayName().equals("CALL")) {
            atts[1] = "Call Procedure";
        } else if (node.getDisplayName().contains("VAR") || node.getDisplayName().contains("STRINGV")) {
            atts[1] = "Variable";
        }

        atts[2] = ((nNode) node).getData();

        return atts;

    }
}
