package Nodes.AssignmnetStrategies;

import Exceptions.InvalidConditionException;
import Exceptions.InvalidOutputException;
import Exceptions.InvalidVarAssignmentException;
import Exceptions.ProcedureNotDeclaredException;
import Nodes.nNode;

public class AssignmentCheckingStrategy {
    public void handle(nNode node)
            throws InvalidVarAssignmentException, ProcedureNotDeclaredException, InvalidOutputException,
            InvalidConditionException {
        // System.out.println("no action for "+node.getDisplayName());
    }

}
