package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class ANDCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {

        String curvar1 = "";
        String curvar2 = "";
        String false1 = BasicCompilationStrategy.getNextLabel("false");
        String end = BasicCompilationStrategy.getNextLabel("end");
        String code = "";
        switch (node.getChildren()[0].getDisplayName()) {
            case "T":
                curvar1 = BasicCompilationStrategy.getNextTempVar();
                code += "LET " + curvar1 + "=1\n";
                break;
            case "F":
                curvar1 = BasicCompilationStrategy.getNextTempVar();
                code += "LET " + curvar1 + "=0\n";
                break;
            case "BOOLVAR":
                curvar1 = BasicCompilationStrategy.getNextTempVar();
                code += "LET " + curvar1 + "=" + new BOOLVARCompilationStrategy().handle((nNode) node.getChildren()[0])
                        + "\n";
                break;
            case "LOGIC":
                code += new LOGICCompilationStrategy().handle((nNode) node.getChildren()[0]);
                String prevVar = BasicCompilationStrategy.getCurTempVar();
                curvar1 = BasicCompilationStrategy.getNextTempVar();
                code += "LET " + curvar1 + "=" + prevVar + "\n";
                break;

            default:
                break;
        }
        switch (node.getChildren()[1].getDisplayName()) {
            case "T":
                curvar2 = BasicCompilationStrategy.getNextTempVar();
                code += "LET " + curvar2 + "=1\n";
                break;
            case "F":
                curvar2 = BasicCompilationStrategy.getNextTempVar();
                code += "LET " + curvar2 + "=0\n";
                break;
            case "BOOLVAR":
                curvar2 = BasicCompilationStrategy.getNextTempVar();
                code += "LET " + curvar2 + "=" + new BOOLVARCompilationStrategy().handle((nNode) node.getChildren()[1])
                        + "\n";
                break;
            case "LOGIC":
                code += new LOGICCompilationStrategy().handle((nNode) node.getChildren()[1]);
                String prevVar = BasicCompilationStrategy.getCurTempVar();
                curvar2 = BasicCompilationStrategy.getNextTempVar();
                code += "LET " + curvar2 + "=" + prevVar + "\n";
                break;

            default:
                break;
        }
        String resVar = BasicCompilationStrategy.getNextTempVar();
        code += "IF " + curvar1 + "=0" + " THEN GOTO " + false1 + "\n";
        code += "IF " + curvar2 + "=0" + " THEN GOTO " + false1 + "\n";
        code += "LET " + resVar + "=1\n";
        code += "GOTO " + end + "\n";
        code += "REM " + false1 + "\n";
        code += "LET " + resVar + "=0\n";
        code += "REM " + end + "\n";

        return code;
    }

}
