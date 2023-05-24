package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class ZEROCompilationStrategy extends BasicCompilationStrategy {
    public String handle(tNode node) {
        return "0.00";
    }

}
