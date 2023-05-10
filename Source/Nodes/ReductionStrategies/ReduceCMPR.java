package Nodes.ReductionStrategies;

import java.awt.Color;

import Nodes.Node;
import Nodes.nNode;

public class ReduceCMPR extends NodeReductionStrategy {
    public void handle(nNode node) {
        String data = "";
        switch (node.getChildren()[0].getDisplayName()) {
            case "E(":
                data = "equal";
                break;
            case ">(":
                data = "greater than";
                break;
            case "<(":
                data = "less than";
                break;
            default:
                data = "impossible";
                break;
        }
        Node[] newChildren = new Node[2];
        newChildren[0] = node.getChildren()[1];
        newChildren[1] = node.getChildren()[3];
        node.setChildren(newChildren);
        node.setData(data);
    }
}
