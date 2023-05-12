package Nodes.AssignmnetStrategies;

import java.awt.Color;

import Exceptions.InvalidNumvarAssignmentException;
import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;
import Nodes.HaltChecking.HaltChecker;

public class CheckASSIGN extends AssignmentCheckingStrategy {
    @Override
    public void handle(nNode node) throws InvalidNumvarAssignmentException {
        // Handle constant for NUMVAR
        if (node.getChildren()[1].getDisplayName().equals("0.00")
                || node.getChildren()[1].getDisplayName().equals("POS")
                || node.getChildren()[1].getDisplayName().equals("NEG")) {
            Node.s.giveValue(node.getChildren()[0].getId());
            return;
        }

        // Handle constant for BOOLVAR
        if (node.getChildren()[1].getDisplayName().equals("T") || node.getChildren()[1].getDisplayName().equals("F")) {
            Node.s.giveValue(node.getChildren()[0].getId());
            return;
        }

        // Handle Numvar:=Numvar
        if (node.getChildren()[1].getDisplayName().equals("NUMVAR")) {
            if (Node.s.numvarHasValue(node.getChildren()[1].getId())) {

                Node.s.giveValue(node.getChildren()[0].getId());
                return;
            } else {
                node.setSubtreeColour(Color.RED, false, false);
                throw new InvalidNumvarAssignmentException(((nNode) node.getChildren()[0]).getData(),
                        ((nNode) node.getChildren()[1]).getData());
            }
        }

        // Handle Numvar:=NumExpr
        if (node.getChildren()[1].getDisplayName().equals("NUMEXPR")) {
            try {
                if (isValid(node.getChildren()[1], ((nNode) node.getChildren()[0]).getData())) {

                    Node.s.giveValue(node.getChildren()[0].getId());

                    return;
                }
            } catch (InvalidNumvarAssignmentException e) {
                node.setSubtreeColour(new Color(255, 200, 0), false, false);
                throw new InvalidNumvarAssignmentException(((nNode) node.getChildren()[0]).getData(),
                        ((nNode) node.getChildren()[1]).getDisplayName());
            }

        }
    }

    public boolean isValid(Node node, String originalAssign) throws InvalidNumvarAssignmentException {
        if (node.getDisplayName().equals("0.00") || node.getDisplayName().equals("POS")
                || node.getDisplayName().equals("NEG")) {
            return true;
        }

        if (node.getDisplayName().equals("NUMVAR")) {
            if (Node.s.numvarHasValue(node.getId())) {
                return true;
            } else {
                node.setSubtreeColour(Color.RED, false, false);
                throw new InvalidNumvarAssignmentException(originalAssign,
                        "Expression using " + ((nNode) node).getData());
            }
        }

        if (node.getDisplayName().equals("NUMEXPR")) {
            return isValid(((nNode) node).getChildren()[0], originalAssign)
                    && isValid(((nNode) node).getChildren()[1], originalAssign);
        }

        return false;
    }
}
