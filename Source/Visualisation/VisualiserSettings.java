package Visualisation;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Nodes.Node;
import Nodes.nNode;
import Nodes.tNode;

import java.awt.geom.*;

public class VisualiserSettings extends JPanel {
    TreeVisualiser tv;

    public VisualiserSettings(TreeVisualiser tv) {
        this.tv = tv;
        addSlider();
    }

    private void addSlider() {
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // JLabel nodeSizeLabel = new JLabel("Node Size");
        // sliderPanel.add(nodeSizeLabel);
        // JSlider nodeSizeSlider = new JSlider(20, 60, 30);
        // sliderPanel.add(nodeSizeSlider);
        // JLabel nodeSizeValue = new JLabel(DrawableNode.SIZE + "");
        // sliderPanel.add(nodeSizeValue);

        // nodeSizeSlider.addChangeListener(new ChangeListener() {
        // public void stateChanged(ChangeEvent event) {
        // int value = nodeSizeSlider.getValue();
        // DrawableNode.SIZE = value;
        // nodeSizeValue.setText(value + "");
        // tv.repaint();
        // }
        // });

        JLabel fontSizeLabel = new JLabel("Font Size");
        sliderPanel.add(fontSizeLabel);
        JSlider fontSizeSlider = new JSlider(6, 20, tv.FONT_SIZE);
        sliderPanel.add(fontSizeSlider);
        JLabel fontSizeValue = new JLabel(tv.FONT_SIZE + "");
        sliderPanel.add(fontSizeValue);

        fontSizeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                int value = fontSizeSlider.getValue();
                tv.FONT_SIZE = value;
                fontSizeValue.setText(value + "");
                tv.repaint();
            }
        });

        this.add(sliderPanel);
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
    }

}
