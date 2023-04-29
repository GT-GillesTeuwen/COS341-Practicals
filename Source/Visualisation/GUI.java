package Visualisation;

import java.awt.BorderLayout;
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

public class GUI {
    public GUI(Tree tree) throws EmptyTreeException {

        if (tree == null) {
            throw new EmptyTreeException("There is no tree to visualise");
        }
        // Creating our JFrame
        JFrame frame = new JFrame("Tabbed Pane Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        TreeVisualiser v = new TreeVisualiser(tree);
        SymbolTableVisualiser s = new SymbolTableVisualiser(tree.getScopeTable());

        tabbedPane.setBounds(50, 50, 800, 800);
        tabbedPane.add("AST", v);
        tabbedPane.add("Symbol Table", s);
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setSize(400, 150);
        frame.setVisible(true);

    }
}
