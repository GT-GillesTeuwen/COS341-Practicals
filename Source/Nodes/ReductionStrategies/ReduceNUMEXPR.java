package Nodes.ReductionStrategies;

import Nodes.Node;
import Nodes.nNode;

public class ReduceNUMEXPR extends NodeReductionStrategy {
    public void handle(nNode node) {
        String data = "";
        switch (node.getChildren()[0].getDisplayName()) {
            case "a(":
                data = "add";
                break;
            case "m(":
                data = "multiply";
                break;
            case "d(":
                data = "divide";
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
