package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class PROCCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {
        String doneCode = "REM " + node.getData() + " " + Node.s.getNameFromID(node.getId());
        for (Node child : node.getChildren()) {

            BasicCompilationStrategy basicCompilationStrategy = new BasicCompilationStrategy();
            switch (child.getDisplayName()) {
                case "ALGO":
                    basicCompilationStrategy = new ALGOCompilationStrategy();
                    break;
                case "PROCDEFS":
                    basicCompilationStrategy = new PROCDEFSCompilationStrategy();
                    break;
                default:
                    break;
            }
            doneCode += "" + basicCompilationStrategy.handle((nNode) child);
        }
        doneCode += "\nRETURN";
        return doneCode;
    }

}
