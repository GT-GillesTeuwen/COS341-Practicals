package Scoping;

import java.awt.Color;
import java.util.HashMap;

import javax.print.attribute.IntegerSyntax;
import javax.swing.JOptionPane;

import Exceptions.AmbiguousDeclarationException;
import Exceptions.ProcedureNotDeclaredException;
import Nodes.Node;
import Nodes.nNode;

public class SybmbolTable {
    private HashMap<Integer, Attributes> symbolTable;
    private int currentScope;
    private int mainScope;

    public SybmbolTable() {
        symbolTable = new HashMap<>();
        currentScope = -1;
    }

    public void add(Node node) {
        symbolTable.put(node.getId(), new Attributes(currentScope, node));
    }

    public void add(Node node, int scope) {
        if (!varExitsInTable((nNode) node)) {
            symbolTable.put(node.getId(), new Attributes(scope, node));
        }
    }

    private boolean varExitsInTable(nNode node) {
        for (Attributes a : symbolTable.values()) {
            if ((a.getNode().getDisplayName().contains("VAR")
                    || a.getNode().getDisplayName().contains("STRINGV"))
                    && ((nNode) a.getNode()).getData().equals(node.getData())) {
                return true;
            }
        }
        return false;
    }

    public boolean inScope(String procName, int nodeID, Node node) throws ProcedureNotDeclaredException {
        int callScope = symbolTable.get(nodeID).getScopeID();
        // Check if parent of the current scope (recursion)
        if (symbolTable.get(callScope).getNode().getDisplayName().equals("PROC")
                && ((nNode) (symbolTable.get(callScope).getNode())).getData().equals(procName)) {
            symbolTable.get(callScope).setCalled(true);
            return true;
        }

        // Check if defined in the current scope
        for (Attributes a : symbolTable.values()) {
            if (a.getScopeID() == callScope && a.getNode().getDisplayName().equals("PROC")
                    && ((nNode) a.getNode()).getData().equals(procName)) {
                a.setCalled(true);
                return true;
            }
        }

        // Check if sibling in parent
        int parentScope = symbolTable.get(callScope).getScopeID();
        for (Attributes a : symbolTable.values()) {
            if (a.getScopeID() == parentScope && a.getNode().getDisplayName().equals("PROC")
                    && ((nNode) a.getNode()).getData().equals(procName)) {
                a.setCalled(true);
                return true;
            }
        }
        ((nNode) node).setSubtreeColour(Color.RED, true);
        System.out.println("Procedure not defined:\n\tNode ID: " + nodeID + ".\n\tProcdure "
                + procName + " is not declared in the scope of "
                + ((nNode) symbolTable.get(callScope).getNode()).getData());
        throw new ProcedureNotDeclaredException("Procedure not defined:\n\tNode ID: " + nodeID + ".\n\tProcdure "
                + procName + " is not declared in the scope of "
                + ((nNode) symbolTable.get(callScope).getNode()).getData());

    }

    public boolean unusedProcedures() {
        boolean unused = false;
        for (Attributes a : symbolTable.values()) {
            if (a.getNode().getDisplayName().equals("PROC") && !a.isCalled()) {
                System.out
                        .println(((nNode) a.getNode()).getData() + " in scope " + a.getScopeID()
                                + " is never called. All child nodes will be greyed out in the visual tree");
                ((nNode) (a.getNode())).setSubtreeColour(Color.LIGHT_GRAY, false);
                JOptionPane.showMessageDialog(null,
                        ((nNode) a.getNode()).getData() + " in scope " + a.getScopeID()
                                + " is never called. All child nodes will be greyed out in the visual tree",
                        "Uncalled Procedure", JOptionPane.WARNING_MESSAGE);
                unused = true;
            }
        }
        return unused;
    }

    public void setCurrentScope(Node root) throws AmbiguousDeclarationException {
        if (currentScope == -1) {
            mainScope = root.getId();
            Attributes.mainScope = root.getId();
        }
        if (!procedureNameExistsInScope((nNode) root)) {
            this.currentScope = root.getId();
        } else {
            int scopeIDConflict = symbolTable.get(root.getId()).getScopeID();
            String exString = ("Ambiguous procedure name:\n\tNode ID: " + root.getId() + ".\n\tProcdure "
                    + ((nNode) root).getData() + " already declared in scope of procedure "
                    + ((nNode) symbolTable.get(scopeIDConflict).getNode()).getData());
            throw new AmbiguousDeclarationException(exString);
        }
    }

    public int getMainScope() {
        return mainScope;
    }

    public boolean procedureNameExistsInScope(nNode root) {
        // Can't exist already in empty table
        if (symbolTable.size() == 0) {
            return false;
        }
        // Check if same as parent
        if (((nNode) symbolTable.get(currentScope).getNode()).getData().equals(root.getData())) {
            return true;
        }
        // Check if same as sibling
        for (Attributes a : symbolTable.values()) {
            if (a.getScopeID() == currentScope && a.getNode().getDisplayName().equals("PROC") && a.getNode() != root) {
                if (((nNode) a.getNode()).getData().equals(root.getData())) {
                    return true;
                }
            }
        }
        return false;
    }

    public HashMap<Integer, Attributes> getSymbolTable() {
        return symbolTable;
    }

    public String toHTML() {
        String out = "<!DOCTYPE html> <html><style>table, th, td {border:1px solid black;}</style> <table> <tr><td>NODE ID</td><td>SCOPE</td><td>TYPE</td><td>NAME</td></tr>\n";

        for (Integer id : this.symbolTable.keySet()) {
            if (id != this.getMainScope()) {
                out += "\t<tr>\n";
                out += "\t\t<td>" + id + "</td>\n";
                out += "\t\t<td>" + this.symbolTable.get(id).getAtts()[0] + "</td>\n";
                out += "\t\t<td>" + this.symbolTable.get(id).getAtts()[1] + "</td>\n";
                out += "\t\t<td>" + this.symbolTable.get(id).getAtts()[2] + "</td>\n";
                out += "\t</tr>\n";
            }

        }
        out += "</table>";
        return out;
    }

}
