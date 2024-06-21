package com.mycompany.taskmanager.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBarUI extends BasicScrollBarUI {

    // Define colors for thumb, track, and track background
    private final Color thumbColor = new Color(100, 100, 100);
    private final Color trackColor = new Color(240, 240, 240);
    private final Color trackBorderColor = new Color(200, 200, 200);

    // Override methods to customize scrollbar painting
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.setColor(thumbColor);
        ((Graphics2D) g).fill(thumbBounds);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(trackColor);
        ((Graphics2D) g).fill(trackBounds);
        g.setColor(trackBorderColor);
        ((Graphics2D) g).draw(trackBounds);
    }
}