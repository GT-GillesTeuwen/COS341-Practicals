package Nodes.AssignmnetStrategies;

import java.awt.Color;

import Exceptions.InvalidOutputException;
import Exceptions.InvalidVarAssignmentException;
import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;
import Nodes.HaltChecking.HaltChecker;

public class CheckOUTPUT extends AssignmentCheckingStrategy {
    @Override
    public void handle(nNode node) throws InvalidVarAssignmentException, InvalidOutputException {
        if (!Node.s.varHasValue(node.getChildren()[0].getId())) {
            node.setSubtreeColour(Color.RED, false, false);
            throw new InvalidOutputException(((nNode) node.getChildren()[0]).getData());
        }
    }
}
