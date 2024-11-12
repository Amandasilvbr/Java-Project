package com.petshop.core.utils;

import javax.swing.*;

public class DefaultPage {
    public static void getDefaultConfig (JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
