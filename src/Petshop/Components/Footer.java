package Petshop.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import Petshop.Pages.HomePage;
import Petshop.Utils.Hover;

public class Footer extends JPanel {

    private static final int PANEL_WIDTH = 700;

    // Método agora retorna um JPanel
    public JPanel createFooterPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(PANEL_WIDTH, 50));
        setBackground(new Color(30, 10, 60)); // Cor de fundo similar ao header

        // Ícone de casa na margem esquerda
        JButton homeButton = new JButton();
        homeButton.setIcon(createFooterIcon());
        homeButton.setContentAreaFilled(false);  // Remove o fundo do botão
        homeButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 15));

        // Adicionando o efeito de hover para mudar o cursor para mão
        Hover.addHandCursorOnHover(homeButton);

        // Ação do botão home
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fecha a janela atual e abre a HomePage
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(homeButton);
                currentFrame.dispose();  // Fecha a janela atual
                HomePage.start();  // Chama a HomePage, ou seja, página inicial
            }
        });

        // Adiciona o ícone de casa ao footer
        add(homeButton, BorderLayout.WEST);

        // Agora retorna o próprio JPanel com o footer configurado
        return this;
    }

    // Criar o ícone de casa
    private static ImageIcon createFooterIcon() {
        // Tentando carregar o ícone de casa usando getClass().getClassLoader()
        URL homeIconUrl = Footer.class.getClassLoader().getResource("resources/home.png");

        // Verificar se o arquivo foi carregado corretamente
        if (homeIconUrl != null) {
            ImageIcon homeIcon = new ImageIcon(homeIconUrl);
            Image resizedImage = homeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } else {
            System.out.println("Imagem home não encontrada.");
            return new ImageIcon(); // Retorna uma imagem em branco se não encontrado
        }
    }
}
