package Nodes.ReductionStrategies;

import java.awt.Color;

import Nodes.Node;
import Nodes.nNode;

public class ReduceLOGIC extends NodeReductionStrategy {
    public void handle(nNode node) {
        String data = "";
        switch (node.getChildren()[0].getDisplayName()) {
            case "^(":
                data = "and";
                break;
            case "v(":
                data = "or";
                break;
            case "!(":
                data = "not";
                break;
            default:
                data = "impossible";
                break;
        }
        Node[] newChildren;
        if (data.equals("not")) {
            newChildren = new Node[1];
            newChildren[0] = node.getChildren()[1];
        } else {

            newChildren = new Node[2];
            newChildren[0] = node.getChildren()[1];
            newChildren[1] = node.getChildren()[3];

        }
        node.setChildren(newChildren);
        node.setData(data);
    }
}
