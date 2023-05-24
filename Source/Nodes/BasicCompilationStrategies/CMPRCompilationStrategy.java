package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class CMPRCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {
        // Start numexpr segment
        String code = "LET " + BasicCompilationStrategy.getNextTempVar() + "=(";

        // First operand
        BasicCompilationStrategy basicCompilationStrategy = chooseStrategy(node.getChildren()[0].getDisplayName());
        if ((node.getChildren()[0]) instanceof tNode) {
            code += basicCompilationStrategy.handle((tNode) (node.getChildren()[0]));
        }
        if ((node.getChildren()[0]) instanceof nNode) {
            code += basicCompilationStrategy.handle((nNode) (node.getChildren()[0]));
        }

        // Operation
        code += getOperationFromNode(node.getData());

        // Second operand
        basicCompilationStrategy = chooseStrategy(node.getChildren()[1].getDisplayName());
        if ((node.getChildren()[1]) instanceof tNode) {
            code += basicCompilationStrategy.handle((tNode) (node.getChildren()[1]));
        }
        if ((node.getChildren()[1]) instanceof nNode) {
            code += basicCompilationStrategy.handle((nNode) (node.getChildren()[1]));
        }

        // End Numexpr segment
        code += ")\n";
        return code;
    }

    private String getOperationFromNode(String nodeOp) {
        switch (nodeOp) {
            case "less than":
                return "<";
            case "greater than":
                return ">";
            case "equal":
                return "=";
            default:
                return "<unknown operation>";
        }
    }

    private BasicCompilationStrategy chooseStrategy(String nodeName) {
        switch (nodeName) {
            case "NUMEXPR":
                return new NUMEXPRCompilationStrategy();
            case "POS":
                return new POSCompilationStrategy();
            case "NEG":
                return new NEGCompilationStrategy();
            case "NUMVAR":
                return new NUMVARCompilationStrategy();
            case "0.00":
                return new ZEROCompilationStrategy();
            default:
                return new BasicCompilationStrategy();
        }
    }

}
