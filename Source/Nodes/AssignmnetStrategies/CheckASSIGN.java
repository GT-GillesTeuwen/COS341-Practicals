package Nodes.AssignmnetStrategies;

import java.awt.Color;

import Exceptions.InvalidVarAssignmentException;
import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;
import Nodes.HaltChecking.HaltChecker;

public class CheckASSIGN extends AssignmentCheckingStrategy {
    @Override
    public void handle(nNode node) throws InvalidVarAssignmentException {
        handleNumvar(node);
        handleBoolvar(node);

    }

    private void handleBoolvar(nNode node) throws InvalidVarAssignmentException {
        // Handle constant for BOOLVAR
        if (node.getChildren()[1].getDisplayName().equals("T") || node.getChildren()[1].getDisplayName().equals("F")) {
            Node.s.giveValue(node.getChildren()[0].getId());
            return;
        }

        // Handle Boolvar:=Boolvar
        if (node.getChildren()[1].getDisplayName().equals("BOOLVAR")) {
            if (Node.s.varHasValue(node.getChildren()[1].getId())) {

                Node.s.giveValue(node.getChildren()[0].getId());
                return;
            } else {
                node.setSubtreeColour(Color.RED, false, false);
                throw new InvalidVarAssignmentException(((nNode) node.getChildren()[0]).getData(),
                        ((nNode) node.getChildren()[1]).getData());
            }
        }

        // Handle Boolvar:=Logic
        if (node.getChildren()[1].getDisplayName().equals("LOGIC")) {
            try {
                if (isLogicValid(node.getChildren()[1], ((nNode) node.getChildren()[0]).getData())) {
                    Node.s.giveValue(node.getChildren()[0].getId());
                    return;
                }
            } catch (InvalidVarAssignmentException e) {
                node.setSubtreeColour(new Color(255, 200, 0), false, false);
                throw e;
            }
        }

        // Handle Boolvar:=CMPR
        if (node.getChildren()[1].getDisplayName().equals("CMPR")) {
            try {
                if (isCmprValid(node.getChildren()[1], ((nNode) node.getChildren()[0]).getData())) {
                    Node.s.giveValue(node.getChildren()[0].getId());
                    return;
                }
            } catch (InvalidVarAssignmentException e) {
                node.setSubtreeColour(new Color(255, 200, 0), false, false);
                throw e;
            }
        }

    }

    private boolean isCmprValid(Node node, String data) throws InvalidVarAssignmentException {
        return isNumExprValid(((nNode) node).getChildren()[0], data)
                && isNumExprValid(((nNode) node).getChildren()[1], data);
    }

    public boolean isLogicValid(Node node, String originalAssign) throws InvalidVarAssignmentException {
        if (node.getDisplayName().equals("T") || node.getDisplayName().equals("F")) {
            return true;
        }

        if (node.getDisplayName().equals("BOOLVAR")) {
            if (Node.s.varHasValue(node.getId())) {
                return true;
            } else {
                node.setSubtreeColour(Color.RED, false, false);
                throw new InvalidVarAssignmentException(originalAssign,
                        "Expression using " + ((nNode) node).getData());
            }
        }

        if (node.getDisplayName().equals("LOGIC")) {
            return isLogicValid(((nNode) node).getChildren()[0], originalAssign)
                    && isLogicValid(((nNode) node).getChildren()[1], originalAssign);
        }

        return false;
    }

    public void handleNumvar(nNode node) throws InvalidVarAssignmentException {
        // Handle constant for NUMVAR
        if (node.getChildren()[1].getDisplayName().equals("0.00")
                || node.getChildren()[1].getDisplayName().equals("POS")
                || node.getChildren()[1].getDisplayName().equals("NEG")) {
            Node.s.giveValue(node.getChildren()[0].getId());
            return;
        }

        // Handle Numvar:=Numvar
        if (node.getChildren()[1].getDisplayName().equals("NUMVAR")) {
            if (Node.s.varHasValue(node.getChildren()[1].getId())) {

                Node.s.giveValue(node.getChildren()[0].getId());
                return;
            } else {
                node.setSubtreeColour(Color.RED, false, false);
                throw new InvalidVarAssignmentException(((nNode) node.getChildren()[0]).getData(),
                        ((nNode) node.getChildren()[1]).getData());
            }
        }

        // Handle Numvar:=NumExpr
        if (node.getChildren()[1].getDisplayName().equals("NUMEXPR")) {
            try {
                if (isNumExprValid(node.getChildren()[1], ((nNode) node.getChildren()[0]).getData())) {
                    Node.s.giveValue(node.getChildren()[0].getId());
                    return;
                }
            } catch (InvalidVarAssignmentException e) {
                node.setSubtreeColour(new Color(255, 200, 0), false, false);
                throw e;
            }
        }
    }

    public boolean isNumExprValid(Node node, String originalAssign) throws InvalidVarAssignmentException {
        if (node.getDisplayName().equals("0.00") || node.getDisplayName().equals("POS")
                || node.getDisplayName().equals("NEG")) {
            return true;
        }

        if (node.getDisplayName().equals("NUMVAR")) {
            if (Node.s.varHasValue(node.getId())) {
                return true;
            } else {
                node.setSubtreeColour(Color.RED, false, false);
                throw new InvalidVarAssignmentException(originalAssign,
                        "Expression using " + ((nNode) node).getData());
            }
        }

        if (node.getDisplayName().equals("NUMEXPR")) {
            boolean op1Valid = isNumExprValid(((nNode) node).getChildren()[0], originalAssign);
            boolean op2Valid = isNumExprValid(((nNode) node).getChildren()[1], originalAssign);

            return op1Valid && op2Valid;
        }

        return false;
    }
}
