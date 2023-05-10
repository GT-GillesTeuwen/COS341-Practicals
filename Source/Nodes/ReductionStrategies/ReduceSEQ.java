package Nodes.ReductionStrategies;

import java.awt.Color;

import Nodes.Node;
import Nodes.nNode;

public class ReduceSEQ extends NodeReductionStrategy {
    public void handle(nNode node) {
        Node[] newChildren = new Node[1];
        newChildren[0] = node.getChildren()[1];
        node.setChildren(newChildren);
    }
}
