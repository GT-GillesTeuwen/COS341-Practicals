package Nodes.HaltCheckingStrategies;

import java.awt.Color;

import Exceptions.ProcedureNotDeclaredException;
import Nodes.nNode;
import Nodes.tNode;
import Nodes.HaltChecking.HaltChecker;

public class HaltCheckALGO extends HaltCheckingStrategy {
    @Override
    public boolean handle(nNode node) throws ProcedureNotDeclaredException {
        boolean halts = false;
        // Check for halt
        HaltChecker haltChecker = new HaltChecker();
        int i;
        for (i = 0; i < node.getChildren().length; i++) {
            if (node.getChildren()[i] instanceof tNode) {
                haltChecker.handleHALT((tNode) node.getChildren()[i]);
                halts = true;
                break;
            }
            if (node.getChildren()[i].getDisplayName().equals("BRANCH")) {
                if (haltChecker.handleBRANCH((nNode) node.getChildren()[i])) {
                    halts = true;
                    break;
                }
            }
            if (node.getChildren()[i].getDisplayName().equals("CALL")) {
                if (haltChecker.handleCall((nNode) node.getChildren()[i])) {
                    halts = true;
                    break;
                }
            }
        }
        i++;
        for (; i < node.getChildren().length; i++) {
            node.getChildren()[i].setSubtreeColour(Color.GRAY, false, true);
        }
        return halts;
    }
}
