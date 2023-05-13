package Nodes;

import java.awt.Color;
import java.util.Stack;

import Exceptions.InvalidConditionException;
import Exceptions.InvalidOutputException;
import Exceptions.ProcedureNotDeclaredException;
import Scoping.SybmbolTable;
import Visualisation.Tree;

public class Node {
    public static Tree t;
    public static SybmbolTable s;
    protected String displayName;
    protected int id;
    protected static int idCount = 0;
    protected Color color;
    protected Color prevColor;
    protected boolean dead;
    private static Stack<SybmbolTable> allTables = new Stack<>();

    public Node(String displayName) {
        this.displayName = displayName;
        id = idCount++;
        this.color = Color.BLACK;
        this.prevColor = null;
        dead = false;
    }

    public static void enterLoopScope() {
        allTables.push(s);
        s = new SybmbolTable(s);
    }

    public static void exitLoopScope() {
        s = allTables.pop();
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
            throws ProcedureNotDeclaredException, InvalidOutputException, InvalidConditionException {

    }

    public void checkWhereMainHalts() throws ProcedureNotDeclaredException {

    }

}
