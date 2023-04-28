package Nodes.Strategies;

import java.awt.Color;
import java.util.ArrayList;

import Nodes.Node;
import Nodes.nNode;

public class ReduceALGO extends NodeReductionStrategy {
    public void handle(nNode node) {
        ArrayList<Node> children = new ArrayList<>();
        for (int i = 0; i < node.getChildren().length; i++) {
            if (node.getChildren()[i].getDisplayName().equals("SEQ")) {
                nNode algoNode = (nNode) ((nNode) node.getChildren()[i]).getChildren()[0];
                if (algoNode.getDisplayName().equals("ALGO")) {
                    for (int j = 0; j < algoNode.getChildren().length; j++) {
                        children.add(algoNode.getChildren()[j]);
                    }
                } else {
                    children.add(algoNode);
                }

            } else {
                children.add(node.getChildren()[i]);
                System.out.println(i);
            }
        }
        Node[] newChildren = new Node[children.size()];
        for (int i = 0; i < newChildren.length; i++) {
            newChildren[i] = children.get(i);
        }
        node.setChildren(newChildren);
    }
}
