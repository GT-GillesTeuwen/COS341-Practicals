package Nodes.Strategies;

import java.awt.Color;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class ReducePROC extends NodeReductionStrategy {
    public void handle(nNode node) {
        String data = "";
        int i = 0;
        while (!node.getChildren()[i].getDisplayName().equals("{")) {
            if (node.getChildren()[i] instanceof tNode) {

                data += node.getChildren()[i].getDisplayName();
            } else {
                data += ((nNode) node.getChildren()[i]).getData();
            }
            i++;
        }
        i++;
        node.setData(data);
        Node[] newChildren = new Node[node.getChildren().length - i - 1];
        for (int j = 0; j < newChildren.length; j++) {
            newChildren[j] = node.getChildren()[j + i];
        }
        node.setChildren(newChildren);
        // Prune PROGR nodes
        if (node.getChildren().length == 1 && node.getChildren()[0].getDisplayName().equals("PROGR")) {
            node.setChildren(((nNode) (node.getChildren()[0])).getChildren());
        }
    }
}
