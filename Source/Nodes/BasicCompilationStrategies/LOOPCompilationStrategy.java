package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class LOOPCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {
        String code = "";
        String checkLbl = BasicCompilationStrategy.getNextLabel("StartLoop");
        String endLbl = BasicCompilationStrategy.getNextLabel("EndLoop");
        // Condition setup
        code += "REM " + checkLbl + "\n";
        BasicCompilationStrategy basicCompilationStrategy = new BasicCompilationStrategy();
        switch (node.getChildren()[0].getDisplayName()) {
            case "LOGIC":
                basicCompilationStrategy = new LOGICCompilationStrategy();
                break;
            case "CMPR":
                basicCompilationStrategy = new CMPRCompilationStrategy();
                break;
            case "BOOLVAR":
                code += "LET " + BasicCompilationStrategy.getNextTempVar() + "=";
                basicCompilationStrategy = new BOOLVARCompilationStrategy();
                break;
            case "T":
                code += "LET " + BasicCompilationStrategy.getNextTempVar() + "=";
                basicCompilationStrategy = new TCompilationStrategy();
                break;
            case "F":
                code += "LET " + BasicCompilationStrategy.getNextTempVar() + "=";
                basicCompilationStrategy = new FCompilationStrategy();
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
        if (code.charAt(code.length() - 1) != '\n') {
            code += "\n";
        }
        code += "IF " + BasicCompilationStrategy.getCurTempVar() + "=0 THEN GOTO " + endLbl + "";
        code += new ALGOCompilationStrategy().handle((nNode) node.getChildren()[1]) + "";
        code += "\nGOTO " + checkLbl + "\n";
        code += "REM " + endLbl + "";
        return code;
    }

}
