package Petshop.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import Petshop.Pages.Home;
import Petshop.Utils.Hover;

public class Footer extends JPanel {

    private static final int PANEL_WIDTH = 700;

    // Method now returns a JPanel
    public JPanel createFooterPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(PANEL_WIDTH, 50));
        setBackground(new Color(30, 10, 60));

        // Home icon on the left margin
        JButton homeButton = new JButton();
        homeButton.setIcon(createFooterIcon());
        homeButton.setContentAreaFilled(false);
        homeButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 15));

        // Adding hover effect to change the cursor to hand
        Hover.addHandCursorOnHover(homeButton);

        // Action for the home button
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Closes the current window and opens the HomePage
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(homeButton);
                currentFrame.dispose();
                Home.start();
            }
        });

        // Adds the home icon to the footer
        add(homeButton, BorderLayout.WEST);

        return this;
    }

    // Creates the home icon
    private static ImageIcon createFooterIcon() {
        // Trying to load the home icon using getClass().getClassLoader()
        URL homeIconUrl = Footer.class.getClassLoader().getResource("resources/home.png");

        // Check if the file was loaded correctly
        if (homeIconUrl != null) {
            ImageIcon homeIcon = new ImageIcon(homeIconUrl);
            Image resizedImage = homeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } else {
            System.out.println("Home image not found.");
            return new ImageIcon(); // Returns a blank image if not found
        }
    }
}
