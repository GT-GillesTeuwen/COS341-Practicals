package Nodes.HaltChecking;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class HaltChecker {
    public boolean handle(Node node){
        return false;
    }

    public boolean handleBRANCH(nNode node){
        boolean haltInIF=false;
        boolean haltInElse=false;
        for (Node child : node.getChildren()) {
            if(child.getDisplayName().equals("ALGO")){
                haltInIF=handleALGO((nNode)child);
            }
            if(child.getDisplayName().equals("ELSE")){
                haltInElse=handleALGO((nNode)((nNode)child).getChildren()[0]);
            }
        }
        return haltInIF && haltInElse;
    }

    public boolean handleLOOP(nNode node){
        boolean haltInAll=true;
        for (Node child : node.getChildren()) {
            if(child.getDisplayName().equals("ALGO")){
                haltInAll=haltInAll&&handleALGO((nNode)child);
            }
        }
        return haltInAll;
    }

    public boolean handleALGO(nNode node){
        boolean haltInAlgo=false;
        for (Node child : node.getChildren()) {
            if(child.getDisplayName().equals("BRANCH")){
                if(handleBRANCH((nNode)child)){
                    return true;
                }
            }
            // if(child.getDisplayName().equals("LOOP")){
            //     if(handleLOOP((nNode)child)){
            //         return true;
            //     }
            // }
            if(child.getDisplayName().equals("h")){
                return true;
            }
        }
        return false;
    }

    public boolean handleHALT(tNode node){
        return node.getDisplayName().equals("h");
    }

    public boolean handlePROC(nNode node){
        return handleALGO((nNode)(node.getChildren()[0]));
    }
}
