package Nodes;

import java.awt.Color;
import java.util.Stack;

import Exceptions.InvalidConditionException;
import Exceptions.InvalidOutputException;
import Exceptions.InvalidVarAssignmentException;
import Exceptions.ProcedureNotDeclaredException;
import Scoping.Attributes;
import Scoping.SymbolTable;
import Visualisation.Tree;

public class Node {
    public static Tree t;
    public static SymbolTable s;
    protected String displayName;
    protected int id;
    protected static int idCount = 0;
    protected Color color;
    protected Color prevColor;
    protected boolean dead;
    private static Stack<SymbolTable> allTables = new Stack<>();
    private static Stack<SymbolTable> tempIfTableStack = new Stack<>();

    public Node(String displayName) {
        this.displayName = displayName;
        id = idCount++;
        this.color = Color.BLACK;
        this.prevColor = null;
        dead = false;
    }

    public static void enterLoopScope() {
        allTables.push(s);
        s = new SymbolTable(s);
    }

    public static void exitLoopScope() {
        s = allTables.pop();
    }

    public static void enterIfScope() {
        SymbolTable oldTable = s;
        allTables.push(s);
        SymbolTable ifTable = new SymbolTable(s);
        s = ifTable;
        tempIfTableStack.push(ifTable);
    }

    public static void enterElseScope() {
        SymbolTable oldTable = allTables.pop();
        s = new SymbolTable(oldTable);
        allTables.push(oldTable);
    }

    public static void exitIfScope() {
        s = allTables.pop();
        tempIfTableStack.pop();
    }

    public static void exitIfElseScope() {
        SymbolTable oldTable = allTables.pop();
        SymbolTable ifTable = tempIfTableStack.pop();
        SymbolTable elseTable = s;
        for (Attributes a : oldTable.getSymbolTable().values()) {
            if (!a.isHasValue() && (a.getNode().getDisplayName().contains("VAR")
                    || a.getNode().getDisplayName().contains("STRINGV"))) {
                if (ifTable.varHasValue(a.getNode().getId()) && elseTable.varHasValue(a.getNode().getId())) {
                    a.setHasValue(true);
                }
            }
        }
        s = oldTable;
    }

    public boolean isDead() {
        return dead;
    }

    public void resetColour() {
        if (this.prevColor != null) {
            this.color = prevColor;
        }
    }

    public void setColor(Color color) {

        this.color = color;
    }

    public void setSubtreeColour(Color color, boolean force, boolean killing) {

    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayNameWithID() {
        return displayName;
    }

    public nNode getNodeParent(int id) {
        System.out.println("not this one");
        return null;
    }

    public Color getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public Node reduceOneStepDerivations() {
        return null;
    }

    public Node reduceType() {
        return null;
    }

    public void checkAssignments()
            throws ProcedureNotDeclaredException, InvalidOutputException, InvalidConditionException,
            InvalidVarAssignmentException {

    }

    public void checkWhereMainHalts() throws ProcedureNotDeclaredException, InvalidVarAssignmentException {

    }

}
