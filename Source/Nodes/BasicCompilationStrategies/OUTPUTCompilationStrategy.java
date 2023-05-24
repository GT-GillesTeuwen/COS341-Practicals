package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class OUTPUTCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {
        String code = "PRINT ";
        BasicCompilationStrategy basicCompilationStrategy = new BasicCompilationStrategy();
        switch (node.getChildren()[0].getDisplayName()) {
            case "NUMVAR":
                basicCompilationStrategy = new NUMVARCompilationStrategy();
                break;
            case "STRINGV":
                basicCompilationStrategy = new STRICompilationStrategy();
                break;
            default:
                break;
        }
        if ((node.getChildren()[0]) instanceof tNode) {
            code += basicCompilationStrategy.handle((tNode) (node.getChildren()[0]));
        }
        if ((node.getChildren()[0]) instanceof nNode) {
            code += basicCompilationStrategy.handle((nNode) (node.getChildren()[0]));
        }

        return code;
    }

}
