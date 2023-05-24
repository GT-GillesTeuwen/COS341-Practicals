package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class NUMVARCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {
        return node.getData();

    }

}
