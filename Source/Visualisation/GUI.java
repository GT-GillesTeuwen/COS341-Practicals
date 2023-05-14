package Visualisation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Exceptions.EmptyTreeException;
import Nodes.Node;
import Nodes.nNode;
import Scoping.SymbolTable;

public class GUI {
    Tree tree;
    TreeVisualiser treeVisualiser;

    SymbolTable sybmbolTable;
    SymbolTableVisualiser symbolTableVisualiser;

    JTabbedPane tabs;

    public GUI(Tree tree) throws EmptyTreeException {

        if (tree == null) {
            throw new EmptyTreeException("There is no tree to visualise");
        }
        this.tree = tree;
        // Creating our JFrame
        JFrame frame = new JFrame("Tabbed Pane Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        TreeVisualiser v = new TreeVisualiser(tree);
        this.treeVisualiser = v;

        this.sybmbolTable = tree.getScopeTable();
        SymbolTableVisualiser s = new SymbolTableVisualiser(this.sybmbolTable, this);
        this.symbolTableVisualiser = s;

        tabbedPane.setBounds(50, 50, 800, 800);
        tabbedPane.add("AST", v);
        tabbedPane.add("Symbol Table", s);
        this.tabs = tabbedPane;
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setSize(600, 500);
        frame.setVisible(true);

    }

    public void goToNode(int nodeID) {

        DrawableNode drawableNode = treeVisualiser.getDrawableNodeByID(nodeID);
        if (drawableNode == null) {
            return;
        }
        tabs.setSelectedIndex(0);
        Node node = drawableNode.getNode();
        nNode nodeN = (nNode) (node);
        tree.resetNodeColours();
        nodeN.setSelfColour(Color.decode("0xe39607"), true);
        treeVisualiser.goTo(drawableNode);
    }
}
