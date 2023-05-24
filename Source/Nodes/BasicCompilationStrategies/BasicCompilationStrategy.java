package Nodes.BasicCompilationStrategies;

import Exceptions.InvalidConditionException;
import Exceptions.InvalidOutputException;
import Exceptions.InvalidVarAssignmentException;
import Exceptions.ProcedureNotDeclaredException;
import Nodes.nNode;
import Nodes.tNode;
import javafx.scene.control.Label;

public class BasicCompilationStrategy {

    protected static int LabelCount = 0;
    protected static int TempCount = 0;

    public static String getNextLabel(String prefix) {
        LabelCount++;
        return prefix + LabelCount;
    }

    public static String getCurTempVar() {
        return "t" + TempCount;
    }

    public static String getNextTempVar() {
        TempCount++;
        return "t" + TempCount;
    }

    public String handle(nNode node) {
        // System.out.println("no action for "+node.getDisplayName());
        return "";
    }

    public String handle(tNode node) {
        // System.out.println("no action for "+node.getDisplayName());
        return "";
    }

}
