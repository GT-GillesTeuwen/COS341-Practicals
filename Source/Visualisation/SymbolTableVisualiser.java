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
    GUI gui;

    public SymbolTableVisualiser(SybmbolTable symbolTable, GUI gui) {
        this.gui = gui;
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

        String[] cols = { "ID", "SCOPE ID", "TYPE", "NAME", "OTHER USEAGE NODE IDs", "HAS VALUE" };

        int count = 0;
        for (Integer id : symbolTable.getSymbolTable().keySet()) {
            if (id != symbolTable.getMainScope()
                    && !symbolTable.getSymbolTable().get(id).getNode().getDisplayName().equals("CALL")) {
                count++;
            }

        }
        String[][] data = new String[count][6];

        int i = 0;
        for (Integer id : symbolTable.getSymbolTable().keySet()) {
            if (id != symbolTable.getMainScope()
                    && !symbolTable.getSymbolTable().get(id).getNode().getDisplayName().equals("CALL")) {
                data[i][0] = id + "";
                data[i][1] = symbolTable.getSymbolTable().get(id).getAtts()[0];
                data[i][2] = symbolTable.getSymbolTable().get(id).getAtts()[1];
                data[i][3] = symbolTable.getSymbolTable().get(id).getAtts()[2];
                data[i][4] = symbolTable.getSymbolTable().get(id).getAtts()[3];
                data[i][5] = symbolTable.getSymbolTable().get(id).getAtts()[4];
                i++;
            }

        }

        JTable table = new JTable(data, cols);
        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem goToItem = new JMenuItem("Go to node");
        goToItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    System.out.println(table.getValueAt(table.getSelectedRow(), 0));
                    gui.goToNode(Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()));
                }
            }
        });
        popupMenu.add(goToItem);
        table.setComponentPopupMenu(popupMenu);
        table.setFont(new Font("Arial", Font.BOLD, 20));
        table.setRowHeight(table.getRowHeight() + 10);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        table.setRowSorter(sorter);

        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
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
