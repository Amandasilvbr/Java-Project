package Petshop.Pages;

import Petshop.Utils.Hover;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import javax.swing.border.AbstractBorder;

import static javax.swing.JFrame.*;

public class Login {
    private final JPanel mainPanel;
    private final JTextField emailField;
    private final JPasswordField passwordField;

    public Login() {
        // Main panel with custom gradient
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(60, 26, 127);
                Color color2 = new Color(25, 118, 210);
                GradientPaint gradientPaint = new GradientPaint(0, 0, color1, width, height, color2);
                g2d.setPaint(gradientPaint);
                g2d.fillRect(0, 0, width, height);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false); // Keep content panel transparent to show gradient
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 50, 50));

        // LoginPanel with rounded border
        JPanel loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Suaviza bordas
                g2d.setColor(Color.WHITE); // Cor de fundo do painel
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Bordas arredondadas com raio de 20px
            }
        };
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(new Color(255, 255, 255, 0)); // Fundo branco com transparência
        loginPanel.setPreferredSize(new Dimension(350, 300)); // Tamanho fixo para o painel de login
        loginPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.setBorder(new RoundBorder(20, new Color(222, 219, 219))); // Aplicando a borda arredondada

        // Title loginPanel
        JLabel titleLabel = new JLabel("Bem-vindo ao sistema OttoPet!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle loginPanel
        JLabel subtitleLabel = new JLabel("Digite os seus dados de acesso no campo abaixo.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.LIGHT_GRAY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Email loginPanel
        emailField = new JTextField("Digite seu e-mail");
        emailField.setPreferredSize(new Dimension(250, 40));
        emailField.setMaximumSize(new Dimension(250, 40));
        emailField.setForeground(new Color(169, 169, 169));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setBorder(BorderFactory.createLineBorder(new Color(222, 219, 219), 1));

        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("Digite seu e-mail")) {
                    emailField.setText("");
                    emailField.setForeground(Color.GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("Digite seu e-mail");
                    emailField.setForeground(new Color(169, 169, 169));
                }
            }
        });

        // Password loginPanel
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 40));
        passwordField.setMaximumSize(new Dimension(250, 40));
        passwordField.setForeground(Color.GRAY);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(222, 219, 219), 1));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setEchoChar('\0');
        passwordField.setText("Digite sua senha");
        passwordField.setForeground(new Color(169, 169, 169));

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("Digite sua senha")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar('*');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("Digite sua senha");
                    passwordField.setForeground(new Color(169, 169, 169));
                    passwordField.setEchoChar('\0');
                }
            }
        });

        // Button loginPanel
        JButton loginButton = new JButton("Entrar");
        loginButton.setPreferredSize(new Dimension(250, 40));
        loginButton.setMaximumSize(new Dimension(250, 40));
        loginButton.setForeground(new Color(64, 64, 64));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        Hover.addHandCursorOnHover(loginButton);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (email.equals("a") && password.equals("a")) {
                    SwingUtilities.getWindowAncestor(mainPanel).dispose();
                    new Home().start();
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciais incorretas");
                }
            }
        });

        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(loginPanel);
        contentPanel.add(Box.createVerticalGlue());

        // Add fields in the box
        loginPanel.add(emailField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(passwordField);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(loginButton);

        // Cat image login page
        URL imageUrl = getClass().getClassLoader().getResource("resources/gato.png");
        if (imageUrl != null) {
            ImageIcon catIcon = new ImageIcon(imageUrl);
            Image img = catIcon.getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH);
            catIcon = new ImageIcon(img);

            JLabel catLabel = new JLabel(catIcon);
            JPanel catPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            catPanel.setOpaque(false);
            catPanel.add(catLabel);
            loginPanel.add(catPanel);
        } else {
            System.err.println("Imagem não encontrada");
        }

        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    // Custom round border class
    class RoundBorder extends AbstractBorder {
        private final int radius;
        private final Color borderColor;

        public RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.borderColor = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(borderColor);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 5, 5, 5); // Espaçamento da borda
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Page");
        frame.setContentPane(new Login().mainPanel);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
