package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class INPUTCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {
        String code = "INPUT ";
        BasicCompilationStrategy basicCompilationStrategy = new BasicCompilationStrategy();
        switch (node.getChildren()[0].getDisplayName()) {
            case "NUMVAR":
                basicCompilationStrategy = new NUMVARCompilationStrategy();
                break;
            default:
                break;
        }
        if ((node.getChildren()[0]) instanceof tNode) {
            code += "\"Input value for " + basicCompilationStrategy.handle((tNode) (node.getChildren()[0])) + "\"; "
                    + basicCompilationStrategy.handle((tNode) (node.getChildren()[0]));
        }
        if ((node.getChildren()[0]) instanceof nNode) {
            code += "\"Input value for " + basicCompilationStrategy.handle((nNode) (node.getChildren()[0])) + "\"; "
                    + basicCompilationStrategy.handle((nNode) (node.getChildren()[0]));
        }

        return code;
    }

}
