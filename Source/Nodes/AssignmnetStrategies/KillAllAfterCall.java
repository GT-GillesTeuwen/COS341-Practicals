package Nodes.AssignmnetStrategies;

import java.awt.Color;

import Nodes.nNode;

public class KillAllAfterCall extends AssignmentCheckingStrategy{
    private String data;
    public KillAllAfterCall(String data){
        this.data=data;
    }
    public void handle(nNode node){
        int i;
        for (i = 0; i < node.getChildren().length; i++) {
            if(node.getChildren()[i].getDisplayName().equals("CALL")){
                if(((nNode)node.getChildren()[i]).getData().equals(data)){
                    break;
                }
            }
        }
        i++;
        for (; i < node.getChildren().length; i++) {
            node.getChildren()[i].setSubtreeColour(Color.GRAY, false,true);
        }
    }
}
