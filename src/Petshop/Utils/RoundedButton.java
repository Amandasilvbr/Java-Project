package Petshop.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {

    // Constructor that takes the button label as a parameter
    public RoundedButton(String label) {
        super(label);
        setContentAreaFilled(false);  // Prevents the default background from being painted
        setFocusPainted(false);       // Removes the focus border when the button is clicked
        setBorderPainted(false);      // Removes the default button border
        setOpaque(false);             // Ensures the background is not painted
    }

    // Method to paint the button (custom design)
    @Override
    protected void paintComponent(Graphics g) {
        // Create a Graphics2D object for more advanced rendering
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable anti-aliasing for smoother drawing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the background color of the button (custom color)
        g2.setColor(new Color(30, 10, 60));  // Custom color

        // Draw the background with rounded corners
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));  // 15 is the border radius

        // Set the font color to white
        g2.setColor(Color.WHITE);

        // Set the font, using the button's font
        g2.setFont(getFont());

        // Draw the button text at the center
        FontMetrics fm = g2.getFontMetrics();
        String text = getText();
        int x = (getWidth() - fm.stringWidth(text)) / 2;  // Calculate the x position to center the text
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();  // Calculate the y position to center the text

        // Draw the text
        g2.drawString(text, x, y);

        // Dispose of the Graphics2D object to release resources
        g2.dispose();
    }

    // Method to paint the border (not used in this case because we want no border)
    @Override
    protected void paintBorder(Graphics g) {
        // Do not draw a border
    }

    // Method to return the preferred size of the button
    @Override
    public Dimension getPreferredSize() {
        // Get the preferred size and return it
        Dimension preferredSize = super.getPreferredSize();
        return new Dimension(preferredSize.width, preferredSize.height);
    }
}
