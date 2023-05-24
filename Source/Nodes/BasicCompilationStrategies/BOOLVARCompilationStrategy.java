package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class BOOLVARCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {
        return node.getData();

    }

}
