package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class ASSIGNCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {
        String code = "";

        // Before part
        BasicCompilationStrategy basicCompilationStrategy = new BasicCompilationStrategy();
        switch (node.getChildren()[1].getDisplayName()) {
            case "LOGIC":
                basicCompilationStrategy = new LOGICCompilationStrategy();
                break;
            case "CMPR":
                basicCompilationStrategy = new CMPRCompilationStrategy();
                break;
            default:
                break;
        }
        if ((node.getChildren()[1]) instanceof tNode) {
            code += basicCompilationStrategy.handle((tNode) (node.getChildren()[1]));
        }
        if ((node.getChildren()[1]) instanceof nNode) {
            code += basicCompilationStrategy.handle((nNode) (node.getChildren()[1]));
        }

        // Assign part
        code += "LET " + ((nNode) (node.getChildren()[0])).getData() + "=";

        // After part
        basicCompilationStrategy = new BasicCompilationStrategy();
        switch (node.getChildren()[1].getDisplayName()) {
            case "POS":
                basicCompilationStrategy = new POSCompilationStrategy();
                break;
            case "T":
                basicCompilationStrategy = new TCompilationStrategy();
                break;
            case "F":
                basicCompilationStrategy = new FCompilationStrategy();
                break;
            case "NEG":
                basicCompilationStrategy = new NEGCompilationStrategy();
                break;
            case "STRI":
                basicCompilationStrategy = new STRICompilationStrategy();
                break;
            case "NUMEXPR":
                basicCompilationStrategy = new NUMEXPRCompilationStrategy();
                break;
            case "NUMVAR":
                basicCompilationStrategy = new NUMVARCompilationStrategy();
                break;
            case "0.00":
                basicCompilationStrategy = new ZEROCompilationStrategy();
                break;
            case "LOGIC":
                code += BasicCompilationStrategy.getCurTempVar();
                break;
            case "CMPR":
                code += BasicCompilationStrategy.getCurTempVar();
                break;
            default:
                break;
        }
        if ((node.getChildren()[1]) instanceof tNode) {
            code += basicCompilationStrategy.handle((tNode) (node.getChildren()[1]));
        }
        if ((node.getChildren()[1]) instanceof nNode) {
            code += basicCompilationStrategy.handle((nNode) (node.getChildren()[1]));
        }

        return code;
    }

}
