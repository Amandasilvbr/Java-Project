package com.petshop.core.pages;

import com.petshop.core.components.*;
import com.petshop.core.utils.*;
import com.petshop.db.DeleteDB;
import com.petshop.db.InsertDB;
import com.petshop.db.QueryDB;
import com.petshop.models.Pet;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.io.File;

public class Pets extends JFrame {

    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 700;

    private JTextField tutorField, petNameField, racaField, birthDateField, searchField, especieField;
    private JButton saveButton, searchButton;
    private JTable petsListTable;
    private DefaultTableModel tableModel;
    private JLabel imageLabel;
    private String currentPetImagePath = "";

    public Pets() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Tutor", "..."}, 0);

        setTitle("Cadastro de pets");
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
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
        searchPets();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Formulário de Cadastro"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        tutorField = createTextField();
        birthDateField = createTextField();
        petNameField = createTextField();
        racaField = createTextField();
        especieField = createTextField();

        addFieldToPanel(panel, gbc, "Nome do Pet", petNameField);
        addFieldToPanel(panel, gbc, "CPF do tutor", tutorField);
        addFieldToPanel(panel, gbc, "Espécie", especieField);
        addFieldToPanel(panel, gbc, "Raça", racaField);
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
                return column == 3;
            }
        };
        petsListTable.getColumnModel().getColumn(3).setCellRenderer(new LabelRenderer());
        petsListTable.getColumnModel().getColumn(3).setCellEditor(new LabelEditor(new JCheckBox()));
        petsListTable.getColumnModel().getColumn(3).setPreferredWidth(20);
        petsListTable.getColumnModel().getColumn(3).setMaxWidth(20);
        petsListTable.getColumnModel().getColumn(3).setMinWidth(20);
        petsListTable.getColumnModel().getColumn(3).setResizable(false);

        JScrollPane scrollPane = new JScrollPane(petsListTable);
        petsListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                int row = petsListTable.rowAtPoint(evt.getPoint());
                int col = petsListTable.columnAtPoint(evt.getPoint());
                if (col == 3 && row >=0){
                    String petID = String.valueOf(petsListTable.getValueAt(row, 0));
                    DeleteDB.delete("pet", petID);
                }
                else if (row >= 0) {
                    String petID = String.valueOf(petsListTable.getValueAt(row, 0));
                    openPetDetailsDialog(petID);
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = petsListTable.rowAtPoint(e.getPoint());
                int col = petsListTable.columnAtPoint(e.getPoint());

                if (col == 2 && row >= 0) {
                    String cpfValue = petsListTable.getValueAt(row, 1).toString();
                    DeleteDB.delete("tutor", cpfValue);
                    searchPets();
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
        String tutor = tutorField.getText().trim();
        String especie = especieField.getText();
        String raca = racaField.getText();
        String birthDate = birthDateField.getText().trim();
        String petName = petNameField.getText().trim();

        if (tutor.isEmpty() || !Validators.validarCPF(tutor)) {
            JOptionPane.showMessageDialog(this, "O CPF do tutor é obrigatório e deve conter 11 digitos.");
            return;
        }

        if (especie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Espécie é obrigatória.");
            return;
        }

        if (raca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Raça é obrigatória.");
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

        try {
            int petId = InsertDB.insertPet(
                    petName,
                    birthDate,
                    raca,
                    especie,
                    currentPetImagePath,
                    tutor
            );
            JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!");
            searchPets();
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tutor não encontrado.");
        }

    }

    private void searchPets() {
        String search = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        for (Pet pet : QueryDB.getAllPet()) {
            if (pet.getName().toLowerCase().contains(search)) {
                String[] petData = {String.valueOf(pet.getId()), pet.getName(), pet.getTutor().getName(), "X"};
                tableModel.addRow(petData);
            }
        }
    }

    private void openPetDetailsDialog(String petId) {
        for (Pet pet : QueryDB.getAllPet()) {
            if (petId.equals(String.valueOf(pet.getId()))) {
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

                JLabel petNameLabel = new JLabel(pet.getName());
                JLabel tutorCpfLabel = new JLabel(pet.getTutor().getCpf());
                JLabel especieLabel = new JLabel(pet.getEspecie());
                JLabel racaLabel = new JLabel(pet.getRaca());
                JLabel birthDateLabel = new JLabel(pet.getBorn());

                addFieldToPanel(detailsPanel, gbc, "Nome do Pet", petNameLabel);
                addFieldToPanel(detailsPanel, gbc, "CPF do Tutor", tutorCpfLabel);
                addFieldToPanel(detailsPanel, gbc, "Espécie", especieLabel);
                addFieldToPanel(detailsPanel, gbc, "Raça", racaLabel);
                addFieldToPanel(detailsPanel, gbc, "Data de Nascimento", birthDateLabel);

                JPanel imagePanel = new JPanel();
                imagePanel.setLayout(new BorderLayout());

                String imagePath = pet.getFilepath();
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
        }



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
                File selectedFile = fileChooser.getSelectedFile();
                ImageIcon imageIcon = new ImageIcon(fileChooser.getSelectedFile().getPath());
                Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
                currentPetImagePath = selectedFile.getPath();

                Path sourcePath = selectedFile.toPath();
                Path targetPath = Paths.get("pets", selectedFile.getName());

                try {
                    Files.createDirectories(targetPath.getParent());
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    currentPetImagePath = targetPath.toString();
                    System.out.println("Image copied to " + targetPath);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Failed to copy image to target directory.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(uploadButton, gbc);

        gbc.gridy++;
        gbc.gridx = 1;
        panel.add(imageLabel, gbc);
    }

    private void clearForm() {
        tutorField.setText("");
        especieField.setText("");
        racaField.setText("");
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
            return new JLabel("X");
        }

    }


    public static void start() {
        SwingUtilities.invokeLater(() -> {
            Pets frame = new Pets();
            DefaultPage.getDefaultConfig(frame);
        });
    }
}
