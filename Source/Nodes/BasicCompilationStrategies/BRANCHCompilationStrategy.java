package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class BRANCHCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {
        String code = "";
        String checkLbl = BasicCompilationStrategy.getNextLabel("StartIF");
        String trueLbl = BasicCompilationStrategy.getNextLabel("CondTrue");
        String endLbl = BasicCompilationStrategy.getNextLabel("EndIF");
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
        code += "IF " + BasicCompilationStrategy.getCurTempVar() + "=1 THEN GOTO " + trueLbl + "";
        if (node.getChildren().length == 3) {
            code += new ALGOCompilationStrategy().handle((nNode) (((nNode) node.getChildren()[2]).getChildren()[0]))
                    + "";
        }
        code += "\nGOTO " + endLbl + "\n";
        code += "REM " + trueLbl + "";
        code += new ALGOCompilationStrategy().handle((nNode) node.getChildren()[1]) + "";
        code += "\nREM " + endLbl + "";
        return code;
    }

}
