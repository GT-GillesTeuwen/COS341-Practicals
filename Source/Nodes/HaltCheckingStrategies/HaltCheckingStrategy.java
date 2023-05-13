package Nodes.HaltCheckingStrategies;

import Exceptions.InvalidVarAssignmentException;
import Exceptions.ProcedureNotDeclaredException;
import Nodes.nNode;

public class HaltCheckingStrategy {
    public boolean handle(nNode node) throws InvalidVarAssignmentException, ProcedureNotDeclaredException {
        return false;
    }
}
