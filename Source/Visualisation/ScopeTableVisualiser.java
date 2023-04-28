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
import Scoping.ScopeTable;

import java.awt.geom.*;

public class ScopeTableVisualiser extends JPanel {
    Graphics2D g2;
    ScopeTable scopeTable;

    public ScopeTableVisualiser(ScopeTable scopeTable) throws Exception {
        this.scopeTable = scopeTable;
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
        String[] cols = { "ID", "SCOPE" };
        String[][] data = new String[scopeTable.getScopeTable().size()][2];

        int i = 0;
        for (Integer id : scopeTable.getScopeTable().keySet()) {
            data[i][0] = id + "";
            data[i][1] = scopeTable.getScopeTable().get(id).toString();
            i++;
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
