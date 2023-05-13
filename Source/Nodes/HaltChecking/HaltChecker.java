package Nodes.HaltChecking;

import java.awt.Color;

import Exceptions.ProcedureNotDeclaredException;
import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class HaltChecker {
    public boolean handle(Node node) {
        return false;
    }

    public boolean handleBRANCH(nNode node) throws ProcedureNotDeclaredException {
        boolean haltInIF = false;
        boolean haltInElse = false;
        for (Node child : node.getChildren()) {
            if (child.getDisplayName().equals("ALGO")) {
                haltInIF = handleALGO((nNode) child);
            }
            if (child.getDisplayName().equals("ELSE")) {
                haltInElse = handleALGO((nNode) ((nNode) child).getChildren()[0]);
            }
        }
        return haltInIF && haltInElse;
    }

    public boolean handleLOOP(nNode node) throws ProcedureNotDeclaredException {
        boolean haltInAll = true;
        for (Node child : node.getChildren()) {
            if (child.getDisplayName().equals("ALGO")) {
                haltInAll = haltInAll && handleALGO((nNode) child);
            }
        }
        return haltInAll;
    }

    public boolean handleALGO(nNode node) throws ProcedureNotDeclaredException {
        boolean halts = false;
        // Check for halt
        int i;
        for (i = 0; i < node.getChildren().length; i++) {
            if (node.getChildren()[i] instanceof tNode) {
                handleHALT((tNode) node.getChildren()[i]);
                halts = true;
                break;
            }
            if (node.getChildren()[i].getDisplayName().equals("BRANCH")) {
                if (handleBRANCH((nNode) node.getChildren()[i])) {
                    halts = true;
                    break;
                }
            }
            if (node.getChildren()[i].getDisplayName().equals("CALL")) {

                nNode proc = Node.s.getProc(((nNode) node.getChildren()[i]).getData(), node.getChildren()[i].getId());
                if (!Node.s.isProcAnalysed(proc.getId()).equals("Checking")) {
                    boolean b = handleCall((nNode) node.getChildren()[i]);
                    if (b) {
                        halts = true;
                        break;
                    }

                }
            }
        }
        i++;
        for (; i < node.getChildren().length; i++) {
            node.getChildren()[i].setSubtreeColour(Color.GRAY, false, true);
        }
        return halts;
    }

    public boolean handleHALT(tNode node) {
        return node.getDisplayName().equals("h");
    }

    public boolean handlePROC(nNode node) throws ProcedureNotDeclaredException {
        return handleALGO((nNode) (node.getChildren()[0]));
    }

    public boolean handleCall(nNode nNode) throws ProcedureNotDeclaredException {

        nNode proc = Node.s.getProc(nNode.getData(), nNode.getId());
        if (Node.s.isProcAnalysed(proc.getId()).equals("Yes")) {
            return true;
        }
        if (Node.s.isProcAnalysed(proc.getId()).equals("No")) {
            return false;
        }
        Node.s.setProcAnalysed(proc.getId(), "Checking");

        boolean halts = handleALGO((nNode) proc.getChildren()[0]);

        if (halts) {
            Node.s.setProcAnalysed(proc.getId(), "Yes");
        } else {
            Node.s.setProcAnalysed(proc.getId(), "No");
        }
        return halts;
    }
}
