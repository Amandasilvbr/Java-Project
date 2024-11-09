package Petshop.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.net.URL;

public class Header extends JPanel {
    private static final int PANEL_WIDTH = 800;
    private static final Color TOP_PANEL_COLOR = new Color(30, 10, 60);
    private static final Font TIME_FONT = new Font("Arial", Font.PLAIN, 10);

    public Header(boolean showClock) {
        setPreferredSize(new Dimension(PANEL_WIDTH, 80));
        setBackground(TOP_PANEL_COLOR);
        setLayout(new BorderLayout());

        addHomeIcon();

        if (showClock) {
            addClock();
        }
    }

    private void addHomeIcon() {
        URL homeIconUrl = Petshop.Pages.HomePage.class.getClassLoader().getResource("resources/homeicon.png");
        if (homeIconUrl != null) {
            ImageIcon homeIcon = new ImageIcon(homeIconUrl);
            Image resizedImage = homeIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);

            JLabel homeIconLabel = new JLabel(resizedIcon);
            add(homeIconLabel, BorderLayout.WEST);
        } else {
            System.out.println("Imagem homeicon não encontrada.");
        }
    }

    private void addClock() {
        JLabel clockLabel = new JLabel();
        clockLabel.setFont(TIME_FONT);
        clockLabel.setForeground(Color.WHITE);

        clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        clockLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 10));
        clockLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        add(clockLabel, BorderLayout.EAST);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE d 'de' MMM HH:mm", new Locale("pt", "BR"));
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime now = LocalDateTime.now();
                clockLabel.setText(now.format(formatter));
            }
        });
        timer.start();
    }
}
