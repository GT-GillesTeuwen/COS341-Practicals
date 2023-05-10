package Nodes.AssignmnetStrategies;

import java.awt.Color;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;
import Nodes.HaltChecking.HaltChecker;

public class CheckASSIGN extends AssignmentCheckingStrategy{
    @Override
    public void handle(nNode node){
        //Handle constant for NUMVAR
        if(node.getChildren()[1].getDisplayName().equals("0.00")||node.getChildren()[1].getDisplayName().equals("POS")||node.getChildren()[1].getDisplayName().equals("NEG")){
            Node.s.giveValue(node.getChildren()[0].getId());
            return;
        }

        //Handle constant for BOOLVAR
        if(node.getChildren()[1].getDisplayName().equals("T")||node.getChildren()[1].getDisplayName().equals("F")){
            Node.s.giveValue(node.getChildren()[0].getId());
            return;
        }
    }
}
