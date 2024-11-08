package Petshop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;

public class HomePage {
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 700;
    private static final Color TOP_PANEL_COLOR = new Color(30, 10, 60);
    private static final Color GRADIENT_COLOR_1 = new Color(60, 26, 127);
    private static final Color GRADIENT_COLOR_2 = new Color(60, 26, 127);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 12);
    private static final Font TIME_FONT = new Font("Arial", Font.PLAIN, 10);

    private final JPanel mainPanel;

    public HomePage() {
        mainPanel = createMainPanel();
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        panel.setBorder(BorderFactory.createEmptyBorder());

        panel.add(createTopPanel(), BorderLayout.NORTH);
        panel.add(createLeftPanel(), BorderLayout.WEST);

        JPanel contentPanel = createContentPanel();
        ArrayList<JPanel> cardList = populateContentPanelWithCards(contentPanel);
        setCardsEvents(cardList);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private ArrayList<JPanel> populateContentPanelWithCards(JPanel contentPanel) {
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 10, 0);


        String[][] extraCards = {
                {"Calendário", "Verifique o calendário", "calendario.png"},
                {"Cirurgia", "Marque uma cirurgia", "cirurgia.png"},
                {"Consulta", "Marque uma consulta", "consulta.png"},
                {"Evento", "Marque um evento", "evento.png"},
                {"Tutor", "Cadastre um tutor", "tutor.png"},
                {"Pets", "Cadastre um pet", "pets.png"},
        };

        ArrayList<JPanel> cardList = new ArrayList<>();

        for (int i = 0; i < extraCards.length; i++) {
            gbc.gridx = i % 2;
            gbc.gridy = i / 2;


            String imagePath = extraCards[i][2];
            JPanel card = createCards(extraCards[i][0], extraCards[i][1], 20, 10, 5, 10, imagePath);

            card.setPreferredSize(new Dimension(200, 100));

            contentPanel.add(card, gbc);

            cardList.add(i, card);
        }

        return cardList;
    }

    //Adds click events to cards
    private void setCardsEvents(ArrayList<JPanel> cardList) {
        for (JPanel card : cardList) {
            int i = cardList.indexOf(card);
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (i) {
                        case 0:
                            //Add re-direct to Calendario page
                            JOptionPane.showMessageDialog(null, "Evento Calendário");
                            break;
                        case 1:
                            //Add re-direct to Cirurgia page
                            JOptionPane.showMessageDialog(null, "Evento Cirurgia");
                            break;
                        case 2:
                            //Add re-direct to Consulta page
                            JOptionPane.showMessageDialog(null, "Evento Consulta");
                            break;
                        case 3:
                            //Add re-direct to Evento page
                            JOptionPane.showMessageDialog(null, "Evento Evento");
                            break;
                        case 4:
                            //Add re-direct to Tutor page
                            JOptionPane.showMessageDialog(null, "Evento Tutor");
                            break;
                        case 5:
                            //Add re-direct to Pets page
                            JOptionPane.showMessageDialog(null, "Evento Pets");
                            break;
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    card.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });
        }
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, 0));
                g2d.fillRoundRect(0, getHeight() - 5, getWidth(), 5, 5, 5);
            }
        };
        topPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 80));
        topPanel.setBackground(TOP_PANEL_COLOR);
        topPanel.setLayout(new BorderLayout());

        addHomeIconToTopPanel(topPanel);

        return topPanel;
    }

    private void addHomeIconToTopPanel(JPanel topPanel) {
        URL homeIconUrl = getClass().getClassLoader().getResource("resources/homeicon.png");
        if (homeIconUrl != null) {
            ImageIcon homeIcon = new ImageIcon(homeIconUrl);
            Image resizedImage = homeIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);

            JLabel homeIconLabel = new JLabel(resizedIcon);
            topPanel.add(homeIconLabel, BorderLayout.WEST);
        } else {
            System.out.println("Imagem homeicon não encontrada.");
        }
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(250, PANEL_HEIGHT));

        JPanel agendaPanel = createAgendaPanel();
        JScrollPane scrollPane = new JScrollPane(agendaPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        leftPanel.add(scrollPane);
        return leftPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        return contentPanel;
    }

    private JPanel createCards(String title, String time, int topMargin, int leftMargin, int bottomMargin, int rightMargin, String imagePath) {
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };

        cardPanel.setLayout(new GridBagLayout());
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        if (imagePath != null) {
            URL imageUrl = getClass().getClassLoader().getResource("resources/" + imagePath);
            if (imageUrl != null) {
                ImageIcon imageIcon = new ImageIcon(imageUrl);
                Image image = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(image));

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 0, 0, 10);
                cardPanel.add(imageLabel, gbc);
            } else {
                System.out.println("Imagem não encontrada: " + imagePath);
            }
        }

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel titleLabel = createLabel(title, LABEL_FONT);
        titleLabel.setBorder(new EmptyBorder(0, 10, 2, 10));
        textPanel.add(titleLabel);

        JLabel timeLabel = createLabel(time, new Font("Arial", Font.PLAIN, 10));
        timeLabel.setBorder(new EmptyBorder(0, 10, 2, 10));
        textPanel.add(timeLabel);

        gbc.gridx = 1;
        gbc.gridy = 0;
        cardPanel.add(textPanel, gbc);

        cardPanel.setMaximumSize(new Dimension(200, 80));

        return cardPanel;
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JPanel createAgendaPanel() {
        JPanel agendaPanel = new JPanel();
        agendaPanel.setLayout(new BoxLayout(agendaPanel, BoxLayout.Y_AXIS));
        agendaPanel.setOpaque(false);

        JLabel agendaTitle = new JLabel("Agenda do Dia");
        agendaTitle.setFont(TITLE_FONT);
        agendaTitle.setForeground(Color.WHITE);
        agendaTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        agendaPanel.add(Box.createVerticalStrut(15));
        agendaPanel.add(agendaTitle);
        agendaPanel.add(Box.createVerticalStrut(20));

        String[][] events = {
                {"Consulta com o Dr. Abulebebe", "10:00 AM - 11:00 AM"},
                {"Evento de vacinação", "2:00 PM - 3:00 PM"},
                {"Consulta com a Dra. Ana", "4:00 PM - 5:00 PM"},
                {"Consulta com a Dra. Ana", "4:00 PM - 5:00 PM"},
                {"Consulta com a Dra. Ana", "4:00 PM - 5:00 PM"},
                {"Consulta com a Dra. Ana", "4:00 PM - 5:00 PM"},
                {"Consulta com a Dra. Ana", "4:00 PM - 5:00 PM"},
                {"Consulta com a Dra. Ana", "4:00 PM - 5:00 PM"},
                {"Consulta com a Dra. Ana", "4:00 PM - 5:00 PM"},
                {"Consulta com a Dra. Ana", "4:00 PM - 5:00 PM"},
        };

        for (String[] event : events) {
            agendaPanel.add(createCards(event[0], event[1], 20, 10, 5, 10, null));
            agendaPanel.add(Box.createVerticalStrut(11));
        }

        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                GradientPaint gradientPaint = new GradientPaint(0, 0, GRADIENT_COLOR_1, width, height, GRADIENT_COLOR_2);
                g2d.setPaint(gradientPaint);
                g2d.fillRect(0, 0, width, height);
            }
        };
        gradientPanel.setLayout(new BorderLayout());
        gradientPanel.setOpaque(false);
        gradientPanel.add(agendaPanel, BorderLayout.CENTER);

        return gradientPanel;
    }

    public static void start() {
        JFrame frame = new JFrame("Home Page");
        frame.setContentPane(new HomePage().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
