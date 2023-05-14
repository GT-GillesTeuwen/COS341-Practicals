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

public class CheckCALL extends AssignmentCheckingStrategy {
    @Override
    public void handle(nNode node)
            throws InvalidVarAssignmentException, ProcedureNotDeclaredException, InvalidOutputException,
            InvalidConditionException {
        nNode procNode = Node.s.getProc(node.getData(), node.getId());
        AssignmentCheckingStrategy algo = new CheckALGO();
        algo.handle((nNode) procNode.getChildren()[0]);

    }

}
