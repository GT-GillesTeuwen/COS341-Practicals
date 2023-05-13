package Nodes.AssignmnetStrategies;

import java.awt.Color;
import java.util.ArrayList;

import Exceptions.InvalidVarAssignmentException;
import Exceptions.ProcedureNotDeclaredException;
import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;
import Nodes.HaltChecking.HaltChecker;

public class CheckPROC extends AssignmentCheckingStrategy {
    @Override
    public void handle(nNode node) throws InvalidVarAssignmentException, ProcedureNotDeclaredException {
        // Check for halt
        HaltChecker haltChecker = new HaltChecker();
        AssignmentCheckingStrategy a = new CheckALGO();
        a.handle((nNode) (node.getChildren()[0]));
        if (haltChecker.handlePROC(node)) {
            killAfterAllCalls(node.getData());
        }

    }

    private void killAfterAllCalls(String data) throws ProcedureNotDeclaredException {

        for (Integer id : Node.s.getIDsOfProcCalls(data)) {
            nNode par = Node.t.getParentOfNode(id);
            par.killAfterAllCall(data);
        }
    }
}
