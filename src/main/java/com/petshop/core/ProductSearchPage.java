package com.petshop.core;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableCellRenderer;


public class ProductSearchPage {
    private final JPanel mainPanel; // Painel principal
    private final JTextField searchField; // Campo de pesquisa
    private final JTable resultsTable; // Tabela para exibir resultados

    public ProductSearchPage() {
        // Inicialização do painel principal e configuração do layout
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Layout vertical
        mainPanel.setBackground(new Color(55, 0, 100)); // Cor de fundo roxa escura

        // Criando o painel branco que vai conter os campos de entrada
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS)); // Layout vertical dentro do quadrado branco
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Espaçamento interno
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Alinha o painel ao centro

        // Título da página com padding superior
        JLabel titleLabel = new JLabel("Consulta de Produtos");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); // Altera a cor do título para branco
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Alinha o título ao centro
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Adiciona padding no topo

        // Inicialização do campo de pesquisa com o novo estilo (campo em branco, editável)
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(350, 40)); // Tamanho ajustado
        searchField.setMaximumSize(new Dimension(350, 40)); // Garante o tamanho máximo
        searchField.setForeground(Color.BLACK); // Cor do texto
        searchField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Borda do campo

        // Definir as colunas para a tabela
        String[] columnNames = {"ID", "Nome", "Descrição", "Preço", "Categoria"};

        // Modelo de dados para a tabela (vazio inicialmente)
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Criação da JTable com o modelo de dados
        resultsTable = new JTable(tableModel);
        resultsTable.setFillsViewportHeight(true); // Tabela preenche a área de visualização
        resultsTable.setRowHeight(30); // Altura das linhas
        resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Seleção de uma linha

        // Definir o renderizador para permitir a quebra de linha nas células de descrição
        resultsTable.getColumnModel().getColumn(2).setCellRenderer(new WrapTextRenderer());

        // Ajustar a fonte das células de dados (diminui o tamanho da letra)
        resultsTable.setDefaultRenderer(Object.class, new DataCellRenderer());

        // Adicionando o JScrollPane para tornar a tabela rolável
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setPreferredSize(new Dimension(500, 200));

        // Botão de buscar com estilo
        JButton searchButton = new JButton("Buscar");
        searchButton.setPreferredSize(new Dimension(350, 40)); // Tamanho aumentado
        searchButton.setMaximumSize(new Dimension(350, 40)); // Garante o tamanho máximo seja 350x40
        searchButton.setBackground(new Color(224, 52, 104)); // Cor do botão (rosa escuro)
        searchButton.setForeground(new Color(64, 64, 64)); // Texto do botão em cinza escuro
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o botão

        // Adicionando título, campo de pesquisa, botão e a tabela ao painel principal
        mainPanel.add(Box.createVerticalGlue()); // Preenche o espaço superior
        mainPanel.add(titleLabel); // Adiciona o título
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaçamento entre título e campos
        mainPanel.add(searchPanel); // Adiciona o painel de pesquisa
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaçamento entre pesquisa e tabela
        mainPanel.add(scrollPane); // Adiciona o JScrollPane com a tabela
        mainPanel.add(Box.createVerticalGlue()); // Preenche o espaço inferior

        // Adicionando componentes ao painel de pesquisa
        searchPanel.add(searchField);
        searchPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaçamento entre os campos
        searchPanel.add(searchButton);

        // Ação do botão de busca
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();

                // Exemplo de produtos fictícios para exibição
                String[] productIds = {"123", "456", "789"};
                String[] productNames = {"Produto A", "Produto B", "Produto C"};
                String[] descriptions = {
                        "Excelente produto para o dia a dia.",
                        "Ideal para quem busca qualidade.",
                        "Produto versátil para várias situações."
                };
                double[] prices = {99.99, 149.90, 199.50};
                String[] categories = {"Categoria 1", "Categoria 2", "Categoria 3"};

                // Limpar a tabela antes de adicionar novos resultados
                tableModel.setRowCount(0);

                // Lógica simples para simular a busca de produtos fictícios
                boolean found = false;
                for (int i = 0; i < productIds.length; i++) {
                    if (productIds[i].contains(query)) {  // Busca simples por ID
                        found = true;
                        // Adiciona uma linha na tabela com os dados do produto
                        tableModel.addRow(new Object[]{
                                productIds[i],
                                productNames[i],
                                descriptions[i],
                                "R$ " + prices[i],
                                categories[i]
                        });
                    }
                }
                if (!found) {
                    // Exibir uma janela com a mensagem de que o produto não foi encontrado
                    JOptionPane.showMessageDialog(mainPanel,
                            "Nenhum produto encontrado para o ID: " + query,
                            "Produto Não Encontrado",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                // Após adicionar os dados, ajusta a altura das linhas automaticamente
                adjustRowHeights(resultsTable);
            }
        });
    }

    // Método para ajustar a altura das linhas com base no conteúdo das células
    private void adjustRowHeights(JTable table) {
        for (int row = 0; row < table.getRowCount(); row++) {
            int rowHeight = 0;
            for (int column = 0; column < table.getColumnCount(); column++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                rowHeight = Math.max(comp.getPreferredSize().height, rowHeight);
            }
            table.setRowHeight(row, rowHeight + 10); // Ajusta a altura da linha, considerando margem adicional
        }
    }

    // Classe personalizada para renderizar células com texto que quebra automaticamente
    public static class WrapTextRenderer extends JTextArea implements TableCellRenderer {
        public WrapTextRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value == null ? "" : value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            return this;
        }
    }

    // Renderizador personalizado para ajustar o tamanho da fonte das células de dados
    public static class DataCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            // Configura a fonte das células de dados
            c.setFont(new Font("Arial", Font.PLAIN, 12)); // Fonte menor para as células
            return c;
        }
    }

    public static void main(String[] args) {
        // Criando a janela e configurando a interface
        JFrame frame = new JFrame("Consulta de Produtos");
        frame.setContentPane(new ProductSearchPage().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centraliza a janela
        frame.setVisible(true);
    }
}
