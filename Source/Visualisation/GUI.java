package Visualisation;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GUI {
    public GUI(Tree tree) throws Exception {
        // Creating our JFrame
        JFrame frame = new JFrame("Tabbed Pane Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        TreeVisualiser v = new TreeVisualiser(tree);
        SymbolTableVisualiser s = new SymbolTableVisualiser(tree.getScopeTable());
        JPanel p3 = new JPanel();
        tabbedPane.setBounds(50, 50, 800, 800);
        tabbedPane.add("AST", v);
        tabbedPane.add("Scope Table", s);
        tabbedPane.add("Three", p3);
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setSize(400, 150);
        frame.setVisible(true);

    }
}
