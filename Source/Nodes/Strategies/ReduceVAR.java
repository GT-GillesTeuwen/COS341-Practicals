package Nodes.Strategies;

import java.awt.Color;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class ReduceVAR extends NodeReductionStrategy {
    public void handle(nNode node) {
        String data = "";
        for (int i = 0; i < node.getChildren().length; i++) {
            if (node.getChildren()[i] instanceof tNode) {

                data += node.getChildren()[i].getDisplayName();
            } else {
                data += ((nNode) node.getChildren()[i]).getData();
            }
        }
        node.setData(data);
        node.setChildren(new Node[0]);
        node.setColor(Color.BLUE);
    }
}
