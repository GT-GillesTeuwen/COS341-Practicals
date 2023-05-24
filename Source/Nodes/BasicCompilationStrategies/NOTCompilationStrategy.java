package Nodes.BasicCompilationStrategies;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

public class NOTCompilationStrategy extends BasicCompilationStrategy {
    public String handle(nNode node) {

        String curvar = "";
        String was1 = BasicCompilationStrategy.getNextLabel("not");
        String end = BasicCompilationStrategy.getNextLabel("end");
        String code = "";
        switch (node.getChildren()[0].getDisplayName()) {
            case "T":
                curvar = BasicCompilationStrategy.getNextTempVar();
                code += "LET " + curvar + "=1\n";
                break;
            case "F":
                curvar = BasicCompilationStrategy.getNextTempVar();
                code += "LET " + curvar + "=0\n";
                break;
            case "BOOLVAR":
                curvar = BasicCompilationStrategy.getNextTempVar();
                code += "LET " + curvar + "=" + new BOOLVARCompilationStrategy().handle((nNode) node.getChildren()[0])
                        + "\n";
                break;
            case "LOGIC":
                code += new LOGICCompilationStrategy().handle((nNode) node.getChildren()[0]);
                String prevVar = BasicCompilationStrategy.getCurTempVar();
                curvar = BasicCompilationStrategy.getNextTempVar();
                code += "LET " + curvar + "=" + prevVar + "\n";
                break;

            default:
                break;
        }
        String resVar = BasicCompilationStrategy.getNextTempVar();
        code += "IF " + curvar + "=1" + " THEN GOTO " + was1 + "\n";
        code += "LET " + resVar + "=1\n";
        code += "GOTO " + end + "\n";
        code += "REM " + was1 + "\n";
        code += "LET " + resVar + "=0\n";
        code += "REM " + end + "\n";

        return code;
    }

}
