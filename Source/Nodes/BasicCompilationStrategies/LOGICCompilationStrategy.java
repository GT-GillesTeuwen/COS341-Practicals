package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class LOGICCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {
        BasicCompilationStrategy basicCompilationStrategy = getOperationFromNode(node);
        return basicCompilationStrategy.handle(node);

    }

    private BasicCompilationStrategy getOperationFromNode(nNode node) {
        switch (node.getData()) {
            case "and":
                return new ANDCompilationStrategy();
            case "or":
                return new ORCompilationStrategy();
            case "not":
                return new NOTCompilationStrategy();
            default:
                return new BasicCompilationStrategy();
        }
    }

}
