package com.dkdk.component;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image image = null;

    public BackgroundPanel(Image image) {
        this.image = image;
    }

    //绘制背景
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
