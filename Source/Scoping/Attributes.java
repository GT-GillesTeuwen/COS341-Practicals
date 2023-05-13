package Scoping;

import java.util.ArrayList;

import Nodes.Node;
import Nodes.nNode;

public class Attributes {
    private int scopeID;
    private Node node;
    private boolean called;
    public static int mainScope;
    private ArrayList<Integer> otherUsages;
    private boolean hasValue;
    private String halts;

    public Attributes(int scopeID, Node node) {
        this.otherUsages = new ArrayList<>();
        this.scopeID = scopeID;
        this.node = node;
        this.hasValue = false;
        this.halts = "Maybe";
    }

    public void setHasValue(boolean hasValue) {
        this.hasValue = hasValue;
    }

    public void setHalts(String h) {
        this.halts = h;
    }

    public String getHalts() {
        return halts;
    }

    public boolean isHasValue() {
        return hasValue;
    }

    public void setCalled(boolean called) {
        this.called = called;
    }

    public boolean isProcCall(String procName) {
        return node.getDisplayName().equals("CALL") && ((nNode) node).getData().equals(procName);
    }

    public void addOtherUsage(Integer nodeID) {
        this.otherUsages.add(nodeID);
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
        String[] atts = new String[5];
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

        if (otherUsages.size() == 0) {
            atts[3] = "No other usages";
        } else {
            atts[3] = "";
            for (int i = 0; i < otherUsages.size(); i++) {
                atts[3] += otherUsages.get(i).toString();
                if (i < otherUsages.size() - 1) {
                    atts[3] += ", ";
                }
            }
        }
        if (atts[1].equals("Declare Procedure")) {
            atts[4] = "N/A";
        } else {
            atts[4] = isHasValue() + "";
        }

        return atts;

    }
}
