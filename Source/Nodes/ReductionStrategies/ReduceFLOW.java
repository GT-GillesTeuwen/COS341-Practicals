package Nodes.ReductionStrategies;

import java.awt.Color;

import Nodes.Node;
import Nodes.nNode;

public class ReduceFLOW extends NodeReductionStrategy {
    public void handle(nNode node) {
        Node[] newChildren = new Node[node.getChildren().length - 3];
        for (int i = 0; i < newChildren.length; i++) {
            newChildren[i] = node.getChildren()[2 * i + 1];
        }
        node.setChildren(newChildren);
    }
}
