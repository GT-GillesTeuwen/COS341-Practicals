package Nodes.AssignmnetStrategies;

import java.awt.Color;
import java.util.ArrayList;

import Exceptions.InvalidConditionException;
import Exceptions.InvalidOutputException;
import Exceptions.InvalidVarAssignmentException;
import Exceptions.ProcedureNotDeclaredException;
import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;
import Nodes.HaltChecking.HaltChecker;

public class CheckLOOP extends AssignmentCheckingStrategy {
    @Override
    public void handle(nNode node)
            throws InvalidVarAssignmentException, ProcedureNotDeclaredException, InvalidOutputException,
            InvalidConditionException {

        if (node.getChildren()[0].getDisplayName().equals("CMPR")) {
            isCmprValid((nNode) node.getChildren()[0], "Loop condition");
        }
        if (node.getChildren()[0].getDisplayName().equals("LOGIC")) {
            isLogicValid((nNode) node.getChildren()[0], "Loop condition");
        }
        Node.enterLoopScope();
        AssignmentCheckingStrategy algo = new CheckALGO();
        algo.handle((nNode) node.getChildren()[1]);
        Node.exitLoopScope();
    }

    private boolean isCmprValid(Node node, String data)
            throws InvalidVarAssignmentException, InvalidConditionException {
        return isNumExprValid(((nNode) node).getChildren()[0], data)
                && isNumExprValid(((nNode) node).getChildren()[1], data);
    }

    public boolean isLogicValid(Node node, String originalAssign)
            throws InvalidVarAssignmentException, InvalidConditionException {
        if (node.getDisplayName().equals("T") || node.getDisplayName().equals("F")) {
            return true;
        }

        if (node.getDisplayName().equals("BOOLVAR")) {
            if (Node.s.varHasValue(node.getId())) {
                return true;
            } else {
                node.setSubtreeColour(Color.RED, false, false);
                throw new InvalidConditionException(originalAssign, ((nNode) node).getData());
            }
        }

        if (node.getDisplayName().equals("LOGIC")) {
            if (((nNode) node).getChildren().length == 1) {
                return isLogicValid(((nNode) node).getChildren()[0], originalAssign);
            } else {
                return isLogicValid(((nNode) node).getChildren()[0], originalAssign)
                        && isLogicValid(((nNode) node).getChildren()[1], originalAssign);
            }

        }

        return false;
    }

    public boolean isNumExprValid(Node node, String originalAssign)
            throws InvalidVarAssignmentException, InvalidConditionException {
        if (node.getDisplayName().equals("0.00") || node.getDisplayName().equals("POS")
                || node.getDisplayName().equals("NEG")) {
            return true;
        }

        if (node.getDisplayName().equals("NUMVAR")) {
            if (Node.s.varHasValue(node.getId())) {
                return true;
            } else {
                node.setSubtreeColour(Color.RED, false, false);
                throw new InvalidConditionException(originalAssign, ((nNode) node).getData());
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
