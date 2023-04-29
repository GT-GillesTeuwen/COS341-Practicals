package Scoping;

import java.awt.Color;
import java.util.HashMap;

import javax.print.attribute.IntegerSyntax;

import Nodes.Node;
import Nodes.nNode;

public class SybmbolTable {
    private HashMap<Integer, Attributes> scopeTable;
    private int currentScope;
    private int mainScope;

    public SybmbolTable() {
        scopeTable = new HashMap<>();
        currentScope = -1;
    }

    public void add(Node node) {
        scopeTable.put(node.getId(), new Attributes(currentScope, node));
    }

    public void add(Node node, int scope) {
        if (!varExitsInTable((nNode) node)) {
            scopeTable.put(node.getId(), new Attributes(scope, node));
        }
    }

    private boolean varExitsInTable(nNode node) {
        for (Attributes a : scopeTable.values()) {
            if ((a.getNode().getDisplayName().contains("VAR")
                    || a.getNode().getDisplayName().contains("STRINGV"))
                    && ((nNode) a.getNode()).getData().equals(node.getData())) {
                return true;
            }
        }
        return false;
    }

    public boolean inScope(String procName, int nodeID, Node node) {
        int callScope = scopeTable.get(nodeID).getScopeID();
        // Check if parent of the current scope (recursion)
        if (scopeTable.get(callScope).getNode().getDisplayName().equals("PROC")
                && ((nNode) (scopeTable.get(callScope).getNode())).getData().equals(procName)) {
            scopeTable.get(callScope).setCalled(true);
            return true;
        }

        // Check if defined in the current scope
        for (Attributes a : scopeTable.values()) {
            if (a.getScopeID() == callScope && a.getNode().getDisplayName().equals("PROC")
                    && ((nNode) a.getNode()).getData().equals(procName)) {
                a.setCalled(true);
                return true;
            }
        }

        // Check if sibling in parent
        int parentScope = scopeTable.get(callScope).getScopeID();
        for (Attributes a : scopeTable.values()) {
            if (a.getScopeID() == parentScope && a.getNode().getDisplayName().equals("PROC")
                    && ((nNode) a.getNode()).getData().equals(procName)) {
                a.setCalled(true);
                return true;
            }
        }
        ((nNode) node).setSubtreeColour(Color.RED);
        System.out.println("Procedure not defined:\n\tNode ID: " + nodeID + ".\n\tProcdure "
                + procName + " is not declared in the scope of procedure "
                + ((nNode) scopeTable.get(callScope).getNode()).getData());
        return false;
    }

    public boolean unusedProcedures() {
        boolean unused = false;
        for (Attributes a : scopeTable.values()) {
            if (a.getNode().getDisplayName().equals("PROC") && !a.isCalled()) {
                System.out
                        .println(((nNode) a.getNode()).getData() + " in scope " + a.getScopeID() + " is never called");
                ((nNode) (a.getNode())).setSubtreeColour(Color.LIGHT_GRAY);
                unused = true;
            }
        }
        return unused;
    }

    public void setCurrentScope(Node root) {
        if (currentScope == -1) {
            mainScope = root.getId();
            Attributes.mainScope = root.getId();
        }
        if (!procedureNameExistsInScope((nNode) root)) {
            this.currentScope = root.getId();
        } else {
            int scopeIDConflict = scopeTable.get(root.getId()).getScopeID();
            System.out.println("Ambiguous procedure name:\n\tNode ID: " + root.getId() + ".\n\tProcdure "
                    + ((nNode) root).getData() + " already declared in scope of procedure "
                    + ((nNode) scopeTable.get(scopeIDConflict).getNode()).getData());
        }
    }

    public int getMainScope() {
        return mainScope;
    }

    public boolean procedureNameExistsInScope(nNode root) {
        // Can't exist already in empty table
        if (scopeTable.size() == 0) {
            return false;
        }
        // Check if same as parent
        if (((nNode) scopeTable.get(currentScope).getNode()).getData().equals(root.getData())) {
            return true;
        }
        // Check if same as sibling
        for (Attributes a : scopeTable.values()) {
            if (a.getScopeID() == currentScope && a.getNode().getDisplayName().equals("PROC") && a.getNode() != root) {
                if (((nNode) a.getNode()).getData().equals(root.getData())) {
                    return true;
                }
            }
        }
        return false;
    }

    public HashMap<Integer, Attributes> getScopeTable() {
        return scopeTable;
    }

}
