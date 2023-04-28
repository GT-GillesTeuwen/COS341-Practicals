package Nodes.Strategies;

import java.awt.Color;

import Nodes.Node;
import Nodes.nNode;

public class ReduceINT extends NodeReductionStrategy {
    public void handle(nNode node) {
        String data = "";
        for (int i = 0; i < node.getChildren().length; i++) {
            if (node.getChildren()[i].getDisplayName().equals("DIGITS")) {
                data += ((nNode) node.getChildren()[i]).getData();
            } else {
                data += node.getChildren()[i].getDisplayName();
            }

        }
        node.setData(data);
        node.setChildren(new Node[0]);
        node.setColor(Color.BLUE);
    }
}
