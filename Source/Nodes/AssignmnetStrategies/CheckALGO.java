package Nodes.AssignmnetStrategies;

import java.awt.Color;

import Exceptions.InvalidConditionException;
import Exceptions.InvalidOutputException;
import Exceptions.InvalidVarAssignmentException;
import Exceptions.ProcedureNotDeclaredException;
import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;
import Nodes.HaltChecking.HaltChecker;

public class CheckALGO extends AssignmentCheckingStrategy {
    @Override
    public void handle(nNode node)
            throws ProcedureNotDeclaredException, InvalidOutputException, InvalidConditionException {
        AssignmentCheckingStrategy assignmentCheckingStrategy = new AssignmentCheckingStrategy();
        for (Node child : node.getChildren()) {
            checkAssignments((nNode) child);
        }
    }

    public void checkAssignments(Node node)
            throws ProcedureNotDeclaredException, InvalidOutputException, InvalidConditionException {
        AssignmentCheckingStrategy assignmentCheckingStrategy = new AssignmentCheckingStrategy();
        switch (node.getDisplayName()) {
            case "PROC":
                assignmentCheckingStrategy = new CheckPROC();
                break;
            case "LOOP":
                assignmentCheckingStrategy = new CheckLOOP();
                break;
            case "BRANCH":
                assignmentCheckingStrategy = new CheckBRANCH();
                break;
            case "ASSIGN":
                assignmentCheckingStrategy = new CheckASSIGN();
                break;
            case "INPUT":
                assignmentCheckingStrategy = new CheckINPUT();
                break;
            case "VALUE":
            case "TEXT":
                assignmentCheckingStrategy = new CheckOUTPUT();
                break;
        }
        try {
            assignmentCheckingStrategy.handle((nNode) node);
        } catch (InvalidVarAssignmentException e) {
            // TODO Auto-generated catch block
            System.out.println(e);
        }
        for (Node nodeC : ((nNode) node).getChildren()) {
            if (!nodeC.isDead() && nodeC instanceof nNode && !node.getDisplayName().equals("LOOP")
                    && !node.getDisplayName().equals("BRANCH")) {
                checkAssignments(nodeC);
            }
        }
    }
}
