package Nodes;

import java.awt.Color;
import java.util.ArrayList;

import Nodes.Strategies.ReduceNUMVAR;
import Nodes.Strategies.ReducePROC;
import Nodes.Strategies.NodeReductionStrategy;
import Nodes.Strategies.ReduceASSIGN;

public class nNode extends Node {
    private boolean allowComments = false;
    private Node[] children;
    private String data;

    public nNode(String displayName, Node[] children) {
        super(displayName);
        this.children = children;
        data = "";
    }

    public Node[] getChildren() {
        return children;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setChildren(Node[] nodes) {
        this.children = nodes;
    }

    public Node reduceOneStepDerivations() {
        for (int i = 0; i < children.length; i++) {
            children[i] = children[i].reduceOneStepDerivations();
        }
        children = reworkChildren();
        if (children.length == 0) {
            return null;
        }
        if (children.length == 1) {
            return children[0];
        }
        return this;

    }

    public Node reduceType() {
        for (int i = 0; i < children.length; i++) {
            children[i].reduceType();
        }
        NodeReductionStrategy reductionStrategy = new NodeReductionStrategy();
        switch (this.displayName) {
            case "NUMVAR":
                reductionStrategy = new ReduceNUMVAR();
                break;
            case "ASSIGN":
                reductionStrategy = new ReduceASSIGN();
                break;

            case "PROC":
                reductionStrategy = new ReducePROC();
                break;

            default:
                break;
        }
        reductionStrategy.handle(this);
        return this;

    }

    private Node[] reworkChildren() {
        ArrayList<Node> validChildren = new ArrayList<>();
        for (int i = 0; i < children.length; i++) {
            if (children[i] != null && !children[i].getDisplayName().equals("ε")
                    && !(!allowComments && children[i].getDisplayName().equals("COMMENT"))) {
                validChildren.add(children[i]);
            }
        }
        Node[] updatedChildren = new Node[validChildren.size()];
        for (int i = 0; i < validChildren.size(); i++) {
            updatedChildren[i] = validChildren.get(i);
        }
        return updatedChildren;
    }
}
