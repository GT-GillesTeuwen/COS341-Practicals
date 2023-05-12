package Nodes.AssignmnetStrategies;

import java.awt.Color;

import Exceptions.InvalidVarAssignmentException;
import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;
import Nodes.HaltChecking.HaltChecker;

public class CheckINPUT extends AssignmentCheckingStrategy {
    @Override
    public void handle(nNode node) throws InvalidVarAssignmentException {
        Node.s.giveValue(node.getChildren()[0].getId());
    }
}
