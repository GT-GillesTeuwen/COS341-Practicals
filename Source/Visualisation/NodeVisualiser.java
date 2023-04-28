package Visualisation;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

import java.awt.geom.*;

public class NodeVisualiser extends JPanel {
    Graphics2D g2;
    DrawableNode drawableNode;
    Color colour;
    Node node;

    public NodeVisualiser(DrawableNode drawableNode) throws Exception {
        this.drawableNode = drawableNode;
        this.node = drawableNode.getNode();
        colour = Color.BLACK;
        setFocusable(true);
        this.requestFocus();
        this.addTable();
    }

    public void paintComponent(Graphics g) {
        final BasicStroke dashed = new BasicStroke(2.0f);

        super.paintComponent(g);

        g2 = (Graphics2D) g;
        g2.setStroke(dashed);
        g2.setFont(new Font("Arial", Font.PLAIN, 9));
        g2.setColor(colour);
        g2.drawString("null", 40, 40);

    }

    private void addTable() {
        String[] cols = { "Attribute", "Value" };
        String[][] data;
        if (node instanceof tNode) {
            tNode curNode = (tNode) node;
            data = new String[3][2];

            data[0][0] = "ID";
            data[0][1] = curNode.getId() + "";

            data[1][0] = "Display Name";
            data[1][1] = curNode.getDisplayName() + "";

            data[2][0] = "Parent ID";
            data[2][1] = drawableNode.getParent().getId() + "";

        } else {
            nNode curNode = (nNode) node;
            data = new String[5][2];

            data[0][0] = "ID";
            data[0][1] = curNode.getId() + "";

            data[1][0] = "Display Name";
            data[1][1] = curNode.getDisplayName() + "";

            data[2][0] = "Data";
            data[2][1] = curNode.getData() + "";

            data[3][0] = "Children IDs";
            String children = "";
            for (int i = 0; i < curNode.getChildren().length; i++) {
                children += curNode.getChildren()[i].getId();
                if (i < curNode.getChildren().length - 1) {
                    children += ",";
                }
            }
            data[3][1] = children;

            data[4][0] = "Parent ID";
            data[4][1] = drawableNode.getParent().getId() + "";
        }
        JTable table = new JTable(data, cols);
        JPanel p = new JPanel();
        p.add(table);
        table.setPreferredSize(new Dimension(200, data.length * 20));
        this.setSize(200, data.length * 20);
        System.out.println(table.getWidth() + " " + table.getHeight());
        this.add(p);
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
    }

}
