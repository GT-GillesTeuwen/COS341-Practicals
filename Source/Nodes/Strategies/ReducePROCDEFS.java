package Nodes.Strategies;

import java.awt.Color;

import Nodes.Node;
import Nodes.nNode;

public class ReducePROCDEFS extends NodeReductionStrategy {
    public void handle(nNode node) {
        Node[] newChildren = new Node[node.getChildren().length - 1];
        for (int i = 1; i < node.getChildren().length; i++) {
            newChildren[i - 1] = node.getChildren()[i];
        }
        node.setChildren(newChildren);
        for (int i = 0; i < node.getChildren().length; i++) {
            if (node.getChildren()[i].getDisplayName().equals("PROCDEFS")) {
                node.getChildren()[i] = ((nNode) (node.getChildren()[i])).getChildren()[0];
            }
        }

    }
}
