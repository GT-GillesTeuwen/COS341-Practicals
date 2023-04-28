package Visualisation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;
import Scoping.ScopeTable;

public class Tree {
    private Node root;
    private ArrayList<Node> allNodes;
    private int[] nodesPerLevel;
    private ScopeTable scopeTable;

    public Tree(Node root) throws Exception {
        if (root == null) {
            throw new Exception("Tree Error: No tree to build");
        }
        scopeTable = new ScopeTable();
        allNodes = new ArrayList<>();

        root.reduceOneStepDerivations();
        root.reduceType();
        this.root = root;

        int depth = maxDepth();
        nodesPerLevel = new int[depth];
        nodesPerLevel[0] = 1;
        countLevel(root, 1);
        for (int i = 1; i <= depth; i++) {
            addLevel(root, i);
        }

        scopeTable.setCurrentScope(root);
        scopeTable.add(root);
        setChildrenScopes(root);
        checkCallScopes(root);
        scopeTable.unusedProcedures();
    }

    private void setChildrenScopes(Node root) {
        if (root instanceof nNode) {
            if (root.getDisplayName().equals("PROC")) {
                scopeTable.setCurrentScope(root);
            }
            nNode nodeN = (nNode) root;
            for (int i = 0; i < nodeN.getChildren().length; i++) {
                if (nodeN.getChildren()[i].getDisplayName().contains("VAR")
                        || nodeN.getChildren()[i].getDisplayName().contains("STRINGV")) {
                    scopeTable.add(nodeN.getChildren()[i], 0);
                } else {

                    scopeTable.add(nodeN.getChildren()[i]);
                }
            }
            for (int i = 0; i < nodeN.getChildren().length; i++) {
                setChildrenScopes(nodeN.getChildren()[i]);
            }

        }

    }

    public void checkCallScopes(Node root) {
        if (root instanceof nNode) {

            nNode nodeN = (nNode) root;
            if (root.getDisplayName().equals("CALL")) {
                if (!scopeTable.inScope(nodeN.getData(), nodeN.getId(), nodeN)) {

                }
            }
            for (int i = 0; i < nodeN.getChildren().length; i++) {
                checkCallScopes(nodeN.getChildren()[i]);
            }

        }
    }

    public void addLevel(Node root, int level) {
        if (root instanceof tNode && level == 1) {
            allNodes.add(root);
            return;
        }
        if (root instanceof tNode) {
            return;
        }
        if (level == 1)
            allNodes.add(root);
        else if (level > 1) {
            nNode nRoot = (nNode) (root);
            for (int i = 0; i < nRoot.getChildren().length; i++) {
                addLevel(nRoot.getChildren()[i], level - 1);
            }

        }
    }

    public void countLevel(Node cur, int level) {
        if (cur instanceof nNode) {
            nNode curN = ((nNode) (cur));
            for (Node node : curN.getChildren()) {

                nodesPerLevel[level]++;
            }
            for (Node node : curN.getChildren()) {
                countLevel(node, level + 1);
            }
        } else {
            return;
        }
    }

    public ArrayList<Node> getAllNodes() {
        return allNodes;
    }

    public int[] getNodesPerLevel() {
        return nodesPerLevel;
    }

    public int maxDepth() {
        return getDepth(root);
    }

    private int getDepth(Node node) {
        if (node instanceof tNode) {
            return 1;
        }

        else {
            nNode nodeN = (nNode) node;
            if (nodeN.getChildren().length == 0) {
                return 1;
            }
            /* compute the depth of each subtree */
            int[] depths = new int[nodeN.getChildren().length];
            for (int i = 0; i < depths.length; i++) {
                depths[i] = getDepth(nodeN.getChildren()[i]);
            }
            int max = -1;
            for (int i = 0; i < depths.length; i++) {
                if (depths[i] > max) {
                    max = depths[i];
                }
            }
            return max + 1;
        }
    }

    public String toSpecXML() {
        String xml = "<Tree>\n";
        for (Node node : allNodes) {
            String children = getNodeChildrenXML(node);
            String content = node.getDisplayName().replace(">", "&gt;");
            content = content.replace("<", "&lt;");
            xml += "\t" + "<Node>\n";
            xml += "\t\t" + "<ID>" + node.getId() + "</ID>\n";
            xml += "\t\t" + "<Contents>" + content + "</Contents>\n";
            xml += "\t\t" + "<Children>\n" + children + "\t\t</Children>\n";
            xml += "\t" + "</Node>\n";
        }
        xml += "</Tree>";
        return xml;
    }

    private String getNodeChildrenXML(Node node) {
        if (node instanceof tNode) {
            return "";
        }
        String childXML = "";
        nNode nodeN = (nNode) node;
        for (Node child : nodeN.getChildren()) {
            childXML += "\t\t\t<ChildID>" + child.getId() + "</ChildID>\n";
        }
        return childXML;
    }

    private String nTabs(int n) {
        String out = "";
        for (int i = 0; i < n; i++) {
            out += "\t";
        }
        return out;
    }

    private String childrenIds(nNode node) {
        String out = "";
        for (int i = 0; i < node.getChildren().length; i++) {
            out += node.getChildren()[i].getId();
            if (i < node.getChildren().length - 1) {
                out += ",";
            }
        }
        return out;
    }

    public String toTutorXML() {
        return printNonTerminal((nNode) root, 0);
    }

    public ScopeTable getScopeTable() {
        return scopeTable;
    }

    public String printNonTerminal(nNode node, int indentation) {
        String out = "";
        out += nTabs(indentation);
        out += "<" + node.getDisplayName() + " ID=\"" + node.getId() + "\" Children=\"" + childrenIds(node) + "\">\n";
        for (int i = 0; i < node.getChildren().length; i++) {
            if (node.getChildren()[i] instanceof nNode) {
                out += printNonTerminal(((nNode) node.getChildren()[i]), indentation + 1);
            } else {
                out += printTerminal(((tNode) node.getChildren()[i]), indentation + 1);
            }
        }
        out += nTabs(indentation);
        out += "</" + node.getDisplayName() + ">\n";
        return out;
    }

    private String printTerminal(tNode node, int indentation) {
        String out = "";
        out += nTabs(indentation);
        out += "<Terminal ID=\"" + node.getId() + "\" Children=\"" + "\">"
                + node.getDisplayName().replace(">", "&gt;").replace("<", "&lt;") + "</" + "Terminal" + ">\n";
        return out;
    }
}
