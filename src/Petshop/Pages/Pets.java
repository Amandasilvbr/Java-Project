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

public class Pets extends JFrame {

    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 700;

    private JTextField nameField, addressField, phoneField, petNameField, cpfField, birthDateField, emailField, searchField;
    private JComboBox<String> petTypeCombo;
    private JButton saveButton, searchButton;
    private JTable petsListTable;
    private DefaultTableModel tableModel;
    private ArrayList<String[]> pets;
    private JLabel imageLabel;
    private String currentPetImagePath = "";

    public Pets() {
        pets = new ArrayList<>();
        tableModel = new DefaultTableModel(new Object[]{"Nome", "..."}, 0);

        setTitle("Cadastro de pets");
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

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Formulário de Cadastro"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        nameField = createTextField();
        addressField = createTextField();
        phoneField = createTextField();
        emailField = createTextField();
        cpfField = createTextField();
        birthDateField = createTextField();
        petNameField = createTextField();

        addFieldToPanel(panel, gbc, "Nome do Pet", petNameField);
        addFieldToPanel(panel, gbc, "Nome do tutor", nameField);
        addFieldToPanel(panel, gbc, "Endereço", addressField);
        addFieldToPanel(panel, gbc, "Telefone", phoneField);
        addFieldToPanel(panel, gbc, "Email", emailField);
        addFieldToPanel(panel, gbc, "CPF", cpfField);
        addFieldToPanel(panel, gbc, "Data de Nascimento", birthDateField);

        // Adiciona o campo de upload de imagem
        addImageUploadField(panel, gbc);

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
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Pets"));

        searchField = new JTextField();
        searchButton = new RoundedButton("Pesquisar");
        searchButton.addActionListener(e -> searchPets());
        searchButton.setPreferredSize(new Dimension(80, 15));

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        petsListTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
        petsListTable.getColumnModel().getColumn(1).setCellRenderer(new LabelRenderer());
        petsListTable.getColumnModel().getColumn(1).setCellEditor(new LabelEditor(new JCheckBox()));
        petsListTable.getColumnModel().getColumn(1).setPreferredWidth(20);
        petsListTable.getColumnModel().getColumn(1).setMaxWidth(20);
        petsListTable.getColumnModel().getColumn(1).setMinWidth(20);
        petsListTable.getColumnModel().getColumn(1).setResizable(false);

        JScrollPane scrollPane = new JScrollPane(petsListTable);
        petsListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                int row = petsListTable.rowAtPoint(evt.getPoint());
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
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String cpf = cpfField.getText().trim();
        String birthDate = birthDateField.getText().trim();
        String petName = petNameField.getText().trim();

        if (name.isEmpty() || !Validators.isString(name)) {
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

        if (petName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome do pet é obrigatório.");
            return;
        }

        String[] data = new String[]{petName, name, cpf, phone, email, address, birthDate, currentPetImagePath};
        pets.add(data);
        tableModel.addRow(data);
        JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!");

        clearForm();
    }

    private void searchPets() {
        String search = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        for (String[] pet : pets) {
            if (pet[0].toLowerCase().contains(search)) {
                tableModel.addRow(pet);
            }
        }
    }

    private void openPetDetailsDialog(int row) {
        String[] petData = pets.get(row);

        // Criar o JDialog para os detalhes do pet
        JDialog petDetailsDialog = new JDialog(this, "Detalhes do Pet", true);
        petDetailsDialog.setSize(800, 500);
        petDetailsDialog.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.add(new Header(false));
        petDetailsDialog.add(headerPanel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Informações do Pet"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel petNameLabel = new JLabel(petData[0]);
        JLabel tutorNameLabel = new JLabel(petData[1]);
        JLabel cpfLabel = new JLabel(petData[5]);
        JLabel phoneLabel = new JLabel(petData[3]);
        JLabel emailLabel = new JLabel(petData[4]);
        JLabel addressLabel = new JLabel(petData[2]);
        JLabel birthDateLabel = new JLabel(petData[6]);

        addFieldToPanel(detailsPanel, gbc, "Nome do Pet", petNameLabel);
        addFieldToPanel(detailsPanel, gbc, "Nome do Tutor", tutorNameLabel);
        addFieldToPanel(detailsPanel, gbc, "CPF", cpfLabel);
        addFieldToPanel(detailsPanel, gbc, "Telefone", phoneLabel);
        addFieldToPanel(detailsPanel, gbc, "Email", emailLabel);
        addFieldToPanel(detailsPanel, gbc, "Endereço", addressLabel);
        addFieldToPanel(detailsPanel, gbc, "Data de Nascimento", birthDateLabel);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());

        String imagePath = petData[7];
        JLabel imageLabel = new JLabel();

        if (!imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists() && imageFile.isFile()) {
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage(); // Obtém a imagem
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

    private void addImageUploadField(JPanel panel, GridBagConstraints gbc) {
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Imagem do Pet"), gbc);

        gbc.gridx = 1;
        JButton uploadButton = new JButton("Upload");
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 100));

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                ImageIcon imageIcon = new ImageIcon(fileChooser.getSelectedFile().getPath());
                Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
                currentPetImagePath = fileChooser.getSelectedFile().getPath();
            }
        });

        panel.add(uploadButton, gbc);

        gbc.gridy++;
        gbc.gridx = 1;
        panel.add(imageLabel, gbc);
    }

    private void clearForm() {
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
        emailField.setText("");
        cpfField.setText("");
        birthDateField.setText("");
        petNameField.setText("");
        clearImage();
    }

    private void clearImage() {
        imageLabel.setIcon(null);
        currentPetImagePath = "";
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
            pets.remove(row);
            tableModel.removeRow(row);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pets frame = new Pets();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });
    }
}
