package Petshop.Pages;

import Petshop.Utils.Hover;
import Petshop.Components.Header;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pets {
    private static final int PANEL_WIDTH = 700;
    private static final int PANEL_HEIGHT = 630;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 12);

    private final JPanel mainPanel;
    private final DefaultListModel<String> petsListModel;

    // Construtor da página PetsPage
    public Pets() {
        petsListModel = new DefaultListModel<>();
        mainPanel = createMainPanel();
    }

    // Método para criar o painel principal
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        panel.setBorder(BorderFactory.createEmptyBorder());

        // Painel para o título e botões
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JLabel("Gerenciamento de Pets", JLabel.CENTER));

        // Painel para cadastro de novo pet
        JPanel addPetPanel = createAddPetPanel();

        // Footer com ícone de casa
        JPanel footerPanel = createFooterPanel();

        // Adiciona os painéis ao painel principal
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(addPetPanel, BorderLayout.WEST);
        panel.add(footerPanel, BorderLayout.SOUTH);  // Adiciona o footer na parte inferior

        return panel;
    }

    // Painel de cadastro de novo pet
    private JPanel createAddPetPanel() {
        JPanel addPetPanel = new JPanel();
        addPetPanel.setLayout(new GridBagLayout()); // Usando GridBagLayout para alinhar os componentes
        GridBagConstraints gbc = new GridBagConstraints();
        addPetPanel.setPreferredSize(new Dimension(350, PANEL_HEIGHT)); // Ajuste do tamanho

        // Define a largura e altura dos campos de texto
        JTextField petIdField = new JTextField(15);
        JTextField petNameField = new JTextField(15);
        JPanel petBirthPanel = createDatePanel(); // Painel para o campo de data com dropdown
        JTextField petBreedField = new JTextField(15);
        JTextField petSpeciesField = new JTextField(15);
        JTextField petTutorField = new JTextField(15); // Campo para CPF do tutor

        // Adicionando bordas arredondadas aos campos de texto
        petIdField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        petNameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        petBreedField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        petSpeciesField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        petTutorField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));

        // Títulos dos campos
        JLabel idLabel = new JLabel("ID:");
        JLabel nameLabel = new JLabel("Nome:");
        JLabel birthLabel = new JLabel("Nascimento:");
        JLabel breedLabel = new JLabel("Raça:");
        JLabel speciesLabel = new JLabel("Espécie:");
        JLabel tutorLabel = new JLabel("Tutor (CPF):");

        // Alinhando os campos de texto com GridBagConstraints
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre os componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        addPetPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        addPetPanel.add(petIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addPetPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        addPetPanel.add(petNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        addPetPanel.add(birthLabel, gbc);
        gbc.gridx = 1;
        addPetPanel.add(petBirthPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        addPetPanel.add(breedLabel, gbc);
        gbc.gridx = 1;
        addPetPanel.add(petBreedField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        addPetPanel.add(speciesLabel, gbc);
        gbc.gridx = 1;
        addPetPanel.add(petSpeciesField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        addPetPanel.add(tutorLabel, gbc);
        gbc.gridx = 1;
        addPetPanel.add(petTutorField, gbc);

        // Botão para cadastrar pet
        JButton addButton = new JButton("Cadastrar Pet");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação de cadastro do pet
                String petId = petIdField.getText();
                String petName = petNameField.getText();
                String petBirth = getSelectedDate(petBirthPanel);
                String petBreed = petBreedField.getText();
                String petSpecies = petSpeciesField.getText();
                String petTutor = petTutorField.getText();

                // Validando campos
                if (petId.isEmpty() || petName.isEmpty() || petBirth.isEmpty() || petBreed.isEmpty() || petSpecies.isEmpty() || petTutor.isEmpty()) {
                    JOptionPane.showMessageDialog(addPetPanel, "Por favor, preencha todos os campos.");
                } else if (!validateCPF(petTutor)) {
                    JOptionPane.showMessageDialog(addPetPanel, "CPF inválido. Por favor, insira um CPF válido.");
                } else {
                    petsListModel.addElement(petName); // Adiciona o nome do pet na lista
                    petIdField.setText("");
                    petNameField.setText("");
                    petBreedField.setText("");
                    petSpeciesField.setText("");
                    petTutorField.setText("");
                    JOptionPane.showMessageDialog(addPetPanel, "Pet cadastrado com sucesso!");
                }
            }
        });

        // Adicionando o botão no painel
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // O botão ocupa as duas colunas
        addPetPanel.add(addButton, gbc);

        return addPetPanel;
    }

    // Método para criar um painel com dropdowns de data
    private JPanel createDatePanel() {
        JPanel datePanel = new JPanel();
        JComboBox<String> dayComboBox = new JComboBox<>();
        JComboBox<String> monthComboBox = new JComboBox<>();
        JComboBox<String> yearComboBox = new JComboBox<>();

        // Preenchendo o JComboBox de dias
        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(String.format("%02d", i));
        }

        // Preenchendo o JComboBox de meses
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        for (String month : months) {
            monthComboBox.addItem(month);
        }

        // Preenchendo o JComboBox de anos
        for (int i = 1900; i <= 2024; i++) {
            yearComboBox.addItem(String.valueOf(i));
        }

        datePanel.add(dayComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(yearComboBox);

        return datePanel;
    }

    // Método para pegar a data selecionada do painel
    private String getSelectedDate(JPanel datePanel) {
        JComboBox<String> dayComboBox = (JComboBox<String>) datePanel.getComponent(0);
        JComboBox<String> monthComboBox = (JComboBox<String>) datePanel.getComponent(1);
        JComboBox<String> yearComboBox = (JComboBox<String>) datePanel.getComponent(2);

        return dayComboBox.getSelectedItem() + "/" + monthComboBox.getSelectedItem() + "/" + yearComboBox.getSelectedItem();
    }

    // Validação do CPF (simples)
    private boolean validateCPF(String cpf) {
        String cpfRegex = "^[0-9]{11}$";
        Pattern pattern = Pattern.compile(cpfRegex);
        Matcher matcher = pattern.matcher(cpf);
        return matcher.matches();
    }

    // Criar o painel de footer
    static JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 50));
        footerPanel.setBackground(new Color(30, 10, 60)); // Cor de fundo similar ao header

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
                Home.start();  // Chama a HomePage, ou seja, página inicial
            }
        });

        // Adiciona o ícone de casa ao footer
        footerPanel.add(homeButton, BorderLayout.WEST);

        return footerPanel;
    }

    // Criar o ícone de casa
    private static ImageIcon createFooterIcon() {
        // Tentando carregar o ícone de casa usando getClass().getClassLoader()
        URL homeIconUrl = Pets.class.getClassLoader().getResource("resources/home.png");

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

    // Método para iniciar a página de Pets
    public static void start() {
        JFrame frame = new JFrame("Pets Page");
        Pets pets = new Pets(); // Cria uma instância da página de Pets

        JPanel petsPanel = new JPanel(new BorderLayout());
        petsPanel.add(new Header(true), BorderLayout.NORTH); // Adiciona o painel superior
        petsPanel.add(pets.mainPanel, BorderLayout.CENTER); // Adiciona o painel principal da página de pets

        frame.setContentPane(petsPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Método main para iniciar a aplicação
    public static void main(String[] args) {
        // Chama o método start() para exibir a página de pets
        start();
    }
}
