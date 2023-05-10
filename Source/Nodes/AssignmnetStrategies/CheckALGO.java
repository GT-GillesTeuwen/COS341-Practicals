package Nodes.AssignmnetStrategies;

import java.awt.Color;

import Nodes.nNode;
import Nodes.tNode;
import Nodes.HaltChecking.HaltChecker;

public class CheckALGO extends AssignmentCheckingStrategy{
    @Override
    public void handle(nNode node){
        //Check for halt
        HaltChecker haltChecker=new HaltChecker();
        int i;
        for (i = 0; i < node.getChildren().length; i++) {
            if(node.getChildren()[i] instanceof tNode){
                haltChecker.handleHALT((tNode)node.getChildren()[i]);
                break;
            }
            if(node.getChildren()[i].getDisplayName().equals("BRANCH")){
                if(haltChecker.handleBRANCH((nNode)node.getChildren()[i])){
                    break;
                }
            }
            // if(node.getChildren()[i].getDisplayName().equals("LOOP")){
            //     if(haltChecker.handleLOOP((nNode)node.getChildren()[i])){
            //         break;
            //     }
            // }
        }
        i++;
        for (; i < node.getChildren().length; i++) {
            node.getChildren()[i].setSubtreeColour(Color.GRAY, false,true);
        }
    }
}
