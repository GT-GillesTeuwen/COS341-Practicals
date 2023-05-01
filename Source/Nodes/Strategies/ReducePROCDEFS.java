package Nodes.Strategies;

import java.awt.Color;
import java.util.ArrayList;

import Nodes.Node;
import Nodes.nNode;

public class ReducePROCDEFS extends NodeReductionStrategy {
    public void handle(nNode node) {
        Node[] newChildren = new Node[node.getChildren().length - 1];
        for (int i = 1; i < node.getChildren().length; i++) {
            newChildren[i - 1] = node.getChildren()[i];
        }
        node.setChildren(newChildren);
        ArrayList<Node> newChilrenFromChildren = new ArrayList<>();
        for (int i = 0; i < newChildren.length; i++) {
            if (newChildren[i].getDisplayName().equals("PROC")) {
                newChilrenFromChildren.add(newChildren[i]);
            }

        }
        for (int i = 0; i < node.getChildren().length; i++) {
            if (node.getChildren()[i].getDisplayName().equals("PROCDEFS")) {
                nNode procdefChild = (nNode) node.getChildren()[i];
                for (int j = 0; j < procdefChild.getChildren().length; j++) {
                    if (procdefChild.getChildren()[j].getDisplayName().equals("PROC")) {
                        newChilrenFromChildren.add(procdefChild.getChildren()[j]);
                    }
                }
            }
        }

        newChildren = new Node[newChilrenFromChildren.size()];
        for (int i = 0; i < newChildren.length; i++) {
            newChildren[i] = newChilrenFromChildren.get(i);
        }
        node.setChildren(newChildren);

    }
}
