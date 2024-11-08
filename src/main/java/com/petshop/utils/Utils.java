package com.petshop.utils;

import javax.swing.*;
import java.awt.*;

public class Utils {
    public static Image getLogoImage() {
        ImageIcon logo = new ImageIcon(ClassLoader.getSystemResource("icon.png"));
        return logo.getImage();
    }

    public static void defaultPage(JFrame frame) {
        frame.setIconImage(getLogoImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
