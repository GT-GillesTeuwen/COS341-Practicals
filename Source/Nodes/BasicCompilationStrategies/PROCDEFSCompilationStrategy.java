package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class PROCDEFSCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {
        String doneCode = "";
        for (Node child : node.getChildren()) {

            BasicCompilationStrategy basicCompilationStrategy = new BasicCompilationStrategy();
            switch (child.getDisplayName()) {
                case "PROC":
                    basicCompilationStrategy = new PROCCompilationStrategy();
                    break;
                default:
                    break;
            }
            doneCode += "\n" + basicCompilationStrategy.handle((nNode) child);
        }
        return doneCode;
    }

}
