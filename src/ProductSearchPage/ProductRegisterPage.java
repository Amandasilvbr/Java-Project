package ProductSearchPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductRegisterPage {
    private JPanel mainPanel; // Painel principal
    private JTextField IDField, nameField, descriptionField, priceField, categoryField; // Campos de produto
    private JButton saveButton, deleteButton; // Botões para salvar e excluir
    private JTable productTable; // Tabela de produtos
    private DefaultTableModel tableModel; // Modelo da tabela para manipulação dos dados

    public ProductRegisterPage() {
        mainPanel = new JPanel(new CardLayout()); // Usar CardLayout para alternar entre as páginas de cadastro e consulta
        JPanel registerPage = createRegisterPage(); // Página de cadastro
        JPanel queryPage = createQueryPage(); // Página de consulta

        mainPanel.add(registerPage, "Cadastro");
        mainPanel.add(queryPage, "Consulta");

        // Exibindo inicialmente a página de cadastro
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, "Cadastro");
    }

    // Página de cadastro de produto
    private JPanel createRegisterPage() {
        JPanel registerPanel = new JPanel(new BorderLayout(20, 20));
        registerPanel.setBackground(new Color(55, 0, 100));
        registerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JPanel productPanel = createProductPanel("Cadastro de Produto", true); // Cadastro com funcionalidade de inserção

        // Botão para alternar para a consulta
        JButton goToQueryPageButton = new JButton("Ir para Consulta");
        goToQueryPageButton.setBackground(new Color(224, 52, 104));
        goToQueryPageButton.setFont(new Font("Arial", Font.BOLD, 14));
        goToQueryPageButton.setForeground(new Color(64, 64, 64));
        goToQueryPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout layout = (CardLayout) mainPanel.getLayout();
                layout.show(mainPanel, "Consulta"); // Alterna para a página de consulta
            }
        });

        registerPanel.add(productPanel, BorderLayout.CENTER);
        registerPanel.add(goToQueryPageButton, BorderLayout.SOUTH);

        return registerPanel;
    }

    // Página de consulta de produtos
    private JPanel createQueryPage() {
        JPanel queryPanel = new JPanel(new BorderLayout(20, 20));
        queryPanel.setBackground(new Color(55, 0, 100));
        queryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        String[] columnNames = {"Selecionar", "ID", "Nome", "Descrição", "Preço", "Categoria"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : super.getColumnClass(columnIndex);
            }
        };
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(productTable);

        // Botão para excluir produtos selecionados
        deleteButton = new JButton("Excluir Selecionados");
        deleteButton.setPreferredSize(new Dimension(250, 40));
        deleteButton.setBackground(new Color(224, 52, 104));
        deleteButton.setForeground(new Color(64, 64, 64));
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowCount = tableModel.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    boolean isSelected = (boolean) tableModel.getValueAt(i, 0);
                    if (isSelected) {
                        tableModel.removeRow(i);
                    }
                }
            }
        });

        // Botão para alternar para a tela de cadastro
        JButton goToRegisterPageButton = new JButton("Ir para Cadastro");
        goToRegisterPageButton.setBackground(new Color(224, 52, 104));
        goToRegisterPageButton.setFont(new Font("Arial", Font.BOLD, 14));
        goToRegisterPageButton.setForeground(new Color(64, 64, 64));
        goToRegisterPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout layout = (CardLayout) mainPanel.getLayout();
                layout.show(mainPanel, "Cadastro"); // Alterna para a página de cadastro
            }
        });

        queryPanel.add(tableScrollPane, BorderLayout.CENTER);
        queryPanel.add(deleteButton, BorderLayout.SOUTH);
        queryPanel.add(goToRegisterPageButton, BorderLayout.NORTH);

        return queryPanel;
    }

    // Painel de cadastro de produto
    private JPanel createProductPanel(String title, boolean isCadastro) {
        JPanel productPanel = new JPanel(new GridBagLayout());
        productPanel.setBackground(new Color(55, 0, 100));
        productPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Título centralizado
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 0;
        productPanel.add(titleLabel, gbc);

        // Rótulos e campos de entrada de dados
        JLabel idLabel = new JLabel("ID do Produto");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        idLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        productPanel.add(idLabel, gbc);

        IDField = new JTextField(20);
        gbc.gridx = 1;
        productPanel.add(IDField, gbc);

        JLabel nameLabel = new JLabel("Nome do Produto");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        productPanel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        productPanel.add(nameField, gbc);

        JLabel descriptionLabel = new JLabel("Descrição");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3;
        productPanel.add(descriptionLabel, gbc);

        descriptionField = new JTextField(20);
        gbc.gridx = 1;
        productPanel.add(descriptionField, gbc);

        JLabel priceLabel = new JLabel("Preço");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4;
        productPanel.add(priceLabel, gbc);

        priceField = new JTextField(20);
        gbc.gridx = 1;
        productPanel.add(priceField, gbc);

        JLabel categoryLabel = new JLabel("Categoria");
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 5;
        productPanel.add(categoryLabel, gbc);

        categoryField = new JTextField(20);
        gbc.gridx = 1;
        productPanel.add(categoryField, gbc);

        // Botão de salvar ou cadastrar produto
        saveButton = new JButton("Salvar Produto");
        saveButton.setPreferredSize(new Dimension(250, 40));
        saveButton.setMaximumSize(new Dimension(250, 40));
        saveButton.setBackground(new Color(224, 52, 104));
        saveButton.setForeground(new Color(64, 64, 64));
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));

        if (isCadastro) {
            // Funcionalidade de cadastro
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Verificar se algum campo está vazio
                    if (IDField.getText().isEmpty() || nameField.getText().isEmpty() || descriptionField.getText().isEmpty() || priceField.getText().isEmpty() || categoryField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.", "Campos vazios", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String id = IDField.getText();
                        String name = nameField.getText();
                        String description = descriptionField.getText();
                        String price = priceField.getText();
                        String category = categoryField.getText();

                        // Adicionar produto na tabela de consulta
                        Object[] row = {false, id, name, description, price, category};
                        tableModel.addRow(row);

                        // Limpar campos
                        IDField.setText("");
                        nameField.setText("");
                        descriptionField.setText("");
                        priceField.setText("");
                        categoryField.setText("");
                    }
                }
            });
        }

        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 6;
        productPanel.add(saveButton, gbc);

        return productPanel;
    }

    public static void main(String[] args) {
        // Criando a janela e configurando a interface
        JFrame frame = new JFrame("Cadastro e Consulta de Produto");
        frame.setContentPane(new ProductRegisterPage().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centraliza a janela
        frame.setVisible(true);
    }
}
