package com.petshop.core.utils;

import com.petshop.core.pages.Home;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DefaultPage {
    public static void getDefaultConfig (JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        try {
            Image icon = ImageIO.read(DefaultPage.class.getClassLoader().getResource("icon.png"));
            frame.setIconImage(icon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
