package Nodes.Strategies;

import java.awt.Color;

import Nodes.Node;
import Nodes.nNode;

public class ReducePROC extends NodeReductionStrategy {
    public void handle(nNode node) {
        String data = "";
        int i = 0;
        while (!node.getChildren()[i].getDisplayName().equals("{")) {
            data += node.getChildren()[i].getDisplayName();
            i++;
        }
        i++;
        node.setData(data);
        Node[] newChildren = new Node[node.getChildren().length - i - 1];
        for (int j = 0; j < newChildren.length; j++) {
            newChildren[j] = node.getChildren()[j + i];
        }
        node.setChildren(newChildren);
    }
}
