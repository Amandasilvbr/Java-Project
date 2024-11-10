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

public class Tutor extends JFrame {

    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 700;

    private JTextField nameField, addressField, phoneField, petNameField, cpfField, birthDateField, emailField, searchField;
    private JComboBox<String> petTypeCombo;
    private JButton saveButton, searchButton;
    private JTable tutorListTable;
    private DefaultTableModel tableModel;
    private ArrayList<String[]> tutors;

    // Constructor initializes the UI components
    public Tutor() {
        tutors = new ArrayList<>();
        tableModel = new DefaultTableModel(new Object[]{"Nome", "Ação"}, 0); // Table header

        // Set up the JFrame properties
        setTitle("Cadastro de Tutor de Pet");
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create and add the header panel
        JPanel headerPanel = new JPanel();
        headerPanel.add(new Header(true));

        // Create and add the footer panel
        Footer footer = new Footer();
        JPanel footerPanel = footer.createFooterPanel();

        // Create a split pane for the left and right panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(PANEL_WIDTH / 2);

        // Create the form panel and the list panel
        JPanel formPanel = createFormPanel();
        JPanel listPanel = createListPanel();

        // Set the left and right components of the split pane
        splitPane.setLeftComponent(formPanel);
        splitPane.setRightComponent(listPanel);

        // Add components to the JFrame
        add(headerPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.PAGE_END);
    }

    // Method to create the form panel
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Formulário de Cadastro"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // Padding for fields
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Initialize form fields
        nameField = createTextField();
        addressField = createTextField();
        phoneField = createTextField();
        emailField = createTextField();
        cpfField = createTextField();
        birthDateField = createTextField();
        petNameField = createTextField();
        petTypeCombo = new JComboBox<>(new String[]{"Cão", "Gato", "Pássaro", "Outro"});

        // Add form fields to the panel
        addFieldToPanel(panel, gbc, "Nome do Tutor", nameField);
        addFieldToPanel(panel, gbc, "Endereço", addressField);
        addFieldToPanel(panel, gbc, "Telefone", phoneField);
        addFieldToPanel(panel, gbc, "Email", emailField);
        addFieldToPanel(panel, gbc, "CPF", cpfField);
        addFieldToPanel(panel, gbc, "Data de Nascimento", birthDateField);
        addFieldToPanel(panel, gbc, "Nome do Pet", petNameField);
        addFieldToPanel(panel, gbc, "Tipo de Pet", petTypeCombo);

        // Create and add the save button with an action listener
        saveButton = new RoundedButton("Salvar cadastro");
        saveButton.addActionListener(e -> saveData());
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveButton, gbc);

        return panel;
    }

    // Method to create the list panel that displays tutor data
    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Tutores"));

        searchField = new JTextField();
        searchButton = new RoundedButton("Pesquisar");
        searchButton.addActionListener(e -> searchTutor());
        searchButton.setPreferredSize(new Dimension(80,15));

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        tutorListTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };
        tutorListTable.getColumnModel().getColumn(1).setCellRenderer(new LabelRenderer());
        tutorListTable.getColumnModel().getColumn(1).setCellEditor(new LabelEditor(new JCheckBox()));
        tutorListTable.getColumnModel().getColumn(1).setPreferredWidth(20);
        tutorListTable.getColumnModel().getColumn(1).setMaxWidth(20);
        tutorListTable.getColumnModel().getColumn(1).setMinWidth(20);
        tutorListTable.getColumnModel().getColumn(1).setResizable(false);

        JScrollPane scrollPane = new JScrollPane(tutorListTable);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Method to add form fields to the panel
    private void addFieldToPanel(JPanel panel, GridBagConstraints gbc, String labelText, Component field) {
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    // Method to create a JTextField
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 20));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        return field;
    }

    // Method to save the tutor's data
    private void saveData() {
        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String cpf = cpfField.getText().trim();
        String birthDate = birthDateField.getText().trim();
        String petName = petNameField.getText().trim();

        // Validate form fields
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

        // Add data to the list and update the table
        String[] data = new String[]{name, "X"};
        tutors.add(data);
        tableModel.addRow(data);
        JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!");
    }

    // Method to search for a tutor by name
    private void searchTutor() {
        String search = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);  // Clear the table
        for (String[] tutor : tutors) {
            if (tutor[0].toLowerCase().contains(search)) {
                tableModel.addRow(tutor);
            }
        }
    }

    // Custom table cell renderer to display a label with "X"
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

    // Custom table cell editor to display a label with "X"
    class LabelEditor extends DefaultCellEditor {
        public LabelEditor(JCheckBox checkBox) {
            super(checkBox);
            checkBox.setForeground(Color.RED);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JLabel label = new JLabel("X");
            label.setForeground(Color.RED);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            return label;
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tutor tutorWindow = new Tutor();
            tutorWindow.setVisible(true);
            tutorWindow.setLocationRelativeTo(null); // This centers the window
        });
    }

}
