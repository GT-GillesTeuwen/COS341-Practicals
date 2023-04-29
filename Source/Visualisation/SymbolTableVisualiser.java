package Visualisation;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;
import Scoping.SybmbolTable;

import java.awt.geom.*;

public class SymbolTableVisualiser extends JPanel {
    Graphics2D g2;
    SybmbolTable symbolTable;

    public SymbolTableVisualiser(SybmbolTable symbolTable) throws Exception {
        this.symbolTable = symbolTable;
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
        g2.drawString("null", 40, 40);

    }

    private void addTable() {
        String[] cols = { "ID", "SCOPE ID", "TYPE", "NAME" };
        String[][] data = new String[symbolTable.getScopeTable().size() - 1][4];

        int i = 0;
        for (Integer id : symbolTable.getScopeTable().keySet()) {
            if (id != symbolTable.getMainScope()) {
                data[i][0] = id + "";
                data[i][1] = symbolTable.getScopeTable().get(id).getAtts()[0];
                data[i][2] = symbolTable.getScopeTable().get(id).getAtts()[1];
                data[i][3] = symbolTable.getScopeTable().get(id).getAtts()[2];
                i++;
            }

        }

        JTable table = new JTable(data, cols);
        table.setFont(new Font("Arial", Font.BOLD, 20));
        table.setRowHeight(table.getRowHeight() + 10);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        table.setRowSorter(sorter);

        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        JScrollPane p = new JScrollPane(table);
        p.setPreferredSize(new Dimension(1500, 500));
        this.add(p);
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
    }

}
