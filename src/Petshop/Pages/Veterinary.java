package Petshop.Pages;

import Petshop.Components.Header;
import Petshop.Components.Footer;
import Petshop.Utils.RoundedButton;
import Petshop.Utils.Validators;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.io.File;

public class Veterinary extends JFrame {

    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 700;

    private JTextField addressField, phoneField, vetNameField, cpfField, birthDateField, emailField, searchField, crmvField;
    private JButton saveButton, searchButton;
    private JTable vetsListTable;
    private DefaultTableModel tableModel;
    private ArrayList<String[]> veterinarians;

    public Veterinary() {
        veterinarians = new ArrayList<>();
        tableModel = new DefaultTableModel(new Object[]{"Nome", "..."}, 0);

        setTitle("Cadastro de veterinários");
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.add(new Header(true));

        Footer footer = new Footer();
        JPanel footerPanel = footer.createFooterPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(PANEL_WIDTH / 2);

        JPanel formPanel = createFormPanel();
        JPanel listPanel = createListPanel();

        splitPane.setLeftComponent(formPanel);
        splitPane.setRightComponent(listPanel);

        add(headerPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.PAGE_END);
    }

    private JComboBox<String> specialtyComboBox;

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Formulário de Cadastro"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        addressField = createTextField();
        phoneField = createTextField();
        emailField = createTextField();
        cpfField = createTextField();
        birthDateField = createTextField();
        vetNameField = createTextField();
        crmvField = createTextField();

        specialtyComboBox = new JComboBox<>(new String[]{
                "Pequenos Animais",
                "Grandes Animais",
                "Anestesiologia",
                "Dermatologia",
                "Oftalmologia",
                "Cardiologia",
                "Oncologia",
                "Medicina Comportamental",
                "Nutrição Animal",
                "Animais Selvagens e Exóticos",
                "Patologia Veterinária",
                "Animais Aquáticos",
                "Epidemiologia"
        });

        specialtyComboBox.setPreferredSize(new Dimension(200, 20));

        addFieldToPanel(panel, gbc, "Nome do veterinário", vetNameField);
        addFieldToPanel(panel, gbc, "Endereço", addressField);
        addFieldToPanel(panel, gbc, "Telefone", phoneField);
        addFieldToPanel(panel, gbc, "Email", emailField);
        addFieldToPanel(panel, gbc, "CPF", cpfField);
        addFieldToPanel(panel, gbc, "CRMV", crmvField);
        addFieldToPanel(panel, gbc, "Data de Nascimento", birthDateField);
        addFieldToPanel(panel, gbc, "Especialidade", specialtyComboBox);

        saveButton = new RoundedButton("Salvar cadastro");
        saveButton.addActionListener(e -> saveData());
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);

        return panel;
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Veterinários"));

        searchField = new JTextField();
        searchButton = new RoundedButton("Pesquisar");
        searchButton.addActionListener(e -> searchVeterinarians());
        searchButton.setPreferredSize(new Dimension(80, 15));

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        vetsListTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
        vetsListTable.getColumnModel().getColumn(1).setCellRenderer(new LabelRenderer());
        vetsListTable.getColumnModel().getColumn(1).setCellEditor(new LabelEditor(new JCheckBox()));
        vetsListTable.getColumnModel().getColumn(1).setPreferredWidth(20);
        vetsListTable.getColumnModel().getColumn(1).setMaxWidth(20);
        vetsListTable.getColumnModel().getColumn(1).setMinWidth(20);
        vetsListTable.getColumnModel().getColumn(1).setResizable(false);

        JScrollPane scrollPane = new JScrollPane(vetsListTable);
        vetsListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                int row = vetsListTable.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    openPetDetailsDialog(row);
                }
            }
        });

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 20));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        return field;
    }

    private void saveData() {
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String cpf = cpfField.getText().trim();
        String birthDate = birthDateField.getText().trim();
        String vetName = vetNameField.getText().trim();
        String crmv = crmvField.getText().trim();
        String specialty = (String) specialtyComboBox.getSelectedItem();

        if (vetName.isEmpty() || !Validators.isString(vetName)) {
            JOptionPane.showMessageDialog(this, "O nome do tutor é obrigatório e deve conter apenas letras.");
            return;
        }

        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Endereço é obrigatório.");
            return;
        }

        if (phone.isEmpty() || !Validators.isNumero(phone)) {
            JOptionPane.showMessageDialog(this, "Telefone é obrigatório e deve ser um número.");
            return;
        }

        if (email.isEmpty() || !Validators.validarEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email inválido.");
            return;
        }

        if (cpf.isEmpty() || !Validators.validarCPF(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido.");
            return;
        }

        if (birthDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data de nascimento é obrigatória.");
            return;
        }

        if (crmv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "CRMV é obrigatório.");
            return;
        }

        if (specialty == null || specialty.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Especialidade é obrigatória.");
            return;
        }

        String[] data = new String[]{vetName, specialty, cpf, phone, email, address, birthDate};
        veterinarians.add(data);
        tableModel.addRow(data);
        JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!");

        clearForm();
    }

    private void searchVeterinarians() {
        String search = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        for (String[] pet : veterinarians) {
            if (pet[0].toLowerCase().contains(search)) {
                tableModel.addRow(pet);
            }
        }
    }

    private void openPetDetailsDialog(int row) {
        String[] veterinaryData = veterinarians.get(row);

        JDialog petDetailsDialog = new JDialog(this, "Detalhes do veterinário", true);
        petDetailsDialog.setSize(800, 500);
        petDetailsDialog.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.add(new Header(false));
        petDetailsDialog.add(headerPanel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Informações do veterinário"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel vetNameLabel = new JLabel(veterinaryData[0]);
        JLabel cpfLabel = new JLabel(veterinaryData[5]);
        JLabel phoneLabel = new JLabel(veterinaryData[3]);
        JLabel emailLabel = new JLabel(veterinaryData[4]);
        JLabel addressLabel = new JLabel(veterinaryData[2]);
        JLabel crmvLabel = new JLabel(veterinaryData[6]);

        addFieldToPanel(detailsPanel, gbc, "Nome do veterinário", vetNameLabel);
        addFieldToPanel(detailsPanel, gbc, "CPF", cpfLabel);
        addFieldToPanel(detailsPanel, gbc, "Telefone", phoneLabel);
        addFieldToPanel(detailsPanel, gbc, "Email", emailLabel);
        addFieldToPanel(detailsPanel, gbc, "Endereço", addressLabel);
        addFieldToPanel(detailsPanel, gbc, "CRMV", crmvLabel);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());

        String imagePath = veterinaryData[7];
        JLabel imageLabel = new JLabel();

        if (!imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists() && imageFile.isFile()) {
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage();
                Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                imageLabel.setText("Imagem não encontrada");
            }
        } else {
            imageLabel.setText("Imagem não disponível");
        }

        imagePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, detailsPanel);
        splitPane.setDividerLocation(300);
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);
        splitPane.setResizeWeight(0.5);

        petDetailsDialog.add(splitPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> petDetailsDialog.dispose());
        petDetailsDialog.add(closeButton, BorderLayout.SOUTH);

        petDetailsDialog.setLocationRelativeTo(null);

        petDetailsDialog.setVisible(true);
    }

    private void addFieldToPanel(JPanel panel, GridBagConstraints gbc, String labelText, Component field) {
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void clearForm() {
        vetNameField.setText("");
        addressField.setText("");
        phoneField.setText("");
        emailField.setText("");
        cpfField.setText("");
        birthDateField.setText("");
        vetNameField.setText("");
    }

    class LabelRenderer extends JLabel implements TableCellRenderer {
        public LabelRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
            setForeground(Color.RED);
            setFont(new Font("Arial", Font.BOLD, 12));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("X");
            return this;
        }
    }

    class LabelEditor extends DefaultCellEditor {
        public LabelEditor(JCheckBox checkBox) {
            super(checkBox);
            checkBox.setForeground(Color.RED);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            removePet(row);
            return new JLabel("X");
        }

        private void removePet(int row) {
            veterinarians.remove(row);
            tableModel.removeRow(row);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Veterinary frame = new Veterinary();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });
    }
}