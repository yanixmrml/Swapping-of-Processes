/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.longhas.services;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author YANIX_MRML
 */

/*
 * MemorySimulationFrame.java
 * Author : LONGHAS, MARK RYAN M.
 * Created on Oct 3, 2010, 10:33:35 PM
 */

public class ColorRenderer extends JLabel
                           implements TableCellRenderer {

    private boolean isBordered;

    public ColorRenderer(boolean isBordered) {
        this.isBordered = isBordered;
        setOpaque(true); //MUST do this for background to show up.
    }

    public Component getTableCellRendererComponent(
                            JTable table, Object color,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {
        Color newColor = (Color)color;
        setBackground(newColor);
        if (isBordered) {
            if (isSelected) {
                setBorder(new javax.swing.border.LineBorder(Color.WHITE, 1, true));

            } else {
                setBorder(new javax.swing.border.LineBorder(Color.WHITE, 1, true));
            }
        }
        return this;
    }
}
