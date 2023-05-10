package Nodes.ReductionStrategies;

import Nodes.Node;
import Nodes.nNode;

public class ReduceASSIGN extends NodeReductionStrategy {
    public void handle(nNode node) {
        Node[] newChildren = new Node[2];
        newChildren[0] = node.getChildren()[0];
        newChildren[1] = node.getChildren()[2];
        node.setChildren(newChildren);
    }
}
