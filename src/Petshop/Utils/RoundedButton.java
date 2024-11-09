package Petshop.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {

    // Construtor que recebe o rótulo do botão
    public RoundedButton(String label) {
        super(label);
        setContentAreaFilled(false);  // Impede que o fundo padrão seja pintado
        setFocusPainted(false);       // Remove a borda de foco ao clicar
        setBorderPainted(false);      // Remove a borda padrão do botão
        setOpaque(false);             // Garante que o fundo não seja desenhado
    }

    // Método para desenhar o botão
    @Override
    protected void paintComponent(Graphics g) {
        // Cria um gráfico 2D
        Graphics2D g2 = (Graphics2D) g.create();

        // Habilita anti-aliasing para um desenho mais suave
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define a cor do fundo do botão (personalizada)
        g2.setColor(new Color(30, 10, 60));  // Cor personalizada

        // Desenha o fundo com bordas arredondadas
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));  // 30 é o raio da borda

        // Define a cor da fonte como branca
        g2.setColor(Color.WHITE);

        // Define a fonte, caso deseje ajustar o tipo ou tamanho
        g2.setFont(getFont());

        // Desenha o texto no centro do botão
        FontMetrics fm = g2.getFontMetrics();
        String text = getText();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();

        g2.drawString(text, x, y);

        // Libera recursos gráficos após o uso
        g2.dispose();
    }

    // Método para desenhar a borda (não será usado neste caso, pois queremos que o botão não tenha borda)
    @Override
    protected void paintBorder(Graphics g) {
        // Não desenha borda
    }

    @Override
    public Dimension getPreferredSize() {
        // Retorna o tamanho preferido para o botão
        Dimension preferredSize = super.getPreferredSize();
        return new Dimension(preferredSize.width + 20, preferredSize.height + 20); // Adiciona algum espaço para bordas arredondadas
    }
}

