package com.petshop.core.pages;

import com.petshop.core.components.*;
import com.petshop.core.utils.*;
import com.petshop.core.utils.CustomDatePicker;
import com.petshop.db.InsertDB;
import com.petshop.db.QueryDB;
import com.petshop.models.Consulta;
import com.petshop.models.Pet;
import com.petshop.models.Vet;
import org.jdatepicker.impl.JDatePickerImpl;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JSpinner;
import java.text.ParseException;

public class Consultation extends JFrame {
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 700;
    private JTextField descriptionField;
    private JTextField typeField;
    private JButton saveButton;
    private DefaultTableModel tableModel;
    private ArrayList<String[]> consultations;
    private JTextField petField;
    private JDatePickerImpl datePicker;
    private int currentMonthOffset = 0;
    private JSpinner timeSpinner;
    private JTextField vetField;
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    public Consultation() {
        consultations = new ArrayList<>();
        tableModel = new DefaultTableModel(new Object[]{"Descrição", "Data", "Hora", "Veterinário", "Pet"}, 0);

        setTitle("Cadastro de Consultas");
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.add(new Header(true));

        Footer footer = new Footer();
        JPanel footerPanel = footer.createFooterPanel();

        splitPane.setDividerLocation(PANEL_WIDTH / 2);

        JPanel formPanel = createFormPanel();
        JPanel listPanel = createListPanel();

        splitPane.setLeftComponent(formPanel);
        splitPane.setRightComponent(listPanel);

        add(headerPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.PAGE_END);
    }

    private JTextField createTextField() {
        return new JTextField();
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Formulário de Cadastro de Consulta"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        petField = createTextField();
        petField.setPreferredSize(new Dimension(200, 25));

        descriptionField = createTextField();
        descriptionField.setPreferredSize(new Dimension(200, 25));

        typeField = createTextField();
        typeField.setPreferredSize(new Dimension(200, 25));

        vetField = createTextField();
        vetField.setPreferredSize(new Dimension(200, 25));

        datePicker = new CustomDatePicker().getDatePicker();

        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeModel.setCalendarField(Calendar.MINUTE);
        timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setPreferredSize(new Dimension(100, 25));

        addFieldToPanel(panel, gbc, "ID do pet", petField);
        addFieldToPanel(panel, gbc, "Tipo", typeField);
        addFieldToPanel(panel, gbc, "Descrição", descriptionField);
        addFieldToPanel(panel, gbc, "Data", datePicker);
        addFieldToPanel(panel, gbc, "ID do veterinário", vetField);
        addFieldToPanel(panel, gbc, "Hora", timeSpinner);

        gbc.gridy++;
        gbc.insets = new Insets(15, 5, 5, 5);
        saveButton = new RoundedButton("Salvar consulta");
        saveButton.addActionListener(e -> saveData());

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        panel.add(saveButton, gbc);

        return panel;
    }


    private void saveData() {
        int petId = Integer.parseInt(petField.getText().trim());
        String type = typeField.getText().trim();
        String description = descriptionField.getText();
        String vet = vetField.getText().trim();

        Date selectedDate = (Date) datePicker.getModel().getValue();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma data válida!");
            return;
        }

        Date selectedTime = (Date) timeSpinner.getValue();
        String formattedTime = new SimpleDateFormat("HH:mm").format(selectedTime);
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
        try {
            InsertDB.insertConsulta(type, description, formattedDate, formattedTime, vet, petId);
            InsertDB.insertEvento(formattedDate, formattedTime, description, vet);
            for (Vet v : QueryDB.getAllVet()){
                if (v.getCpf().contains(vet)){
                    vet = v.getName();
                }
            }
            String petName = "";
            for (Pet p : QueryDB.getAllPet()){
                if (p.getId() == petId){
                    petName = p.getName();
                }
            }


            consultations.add(new String[]{type, description, formattedDate, formattedTime, vet, petName});



            tableModel.addRow(new String[]{description, formattedDate, formattedTime, vet, petName});
            tableModel.fireTableDataChanged();

            petField.setText("");
            descriptionField.setText("");
            vetField.setText("");

            updateConsultationPanel(description, petName, vet, formattedDate, formattedTime);
            splitPane.setRightComponent(createListPanel());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }


    }

    private void updateConsultationPanel(String description, String petId, String vet, String formattedDate, String formattedTime) {

        JPanel consultationsPanel = new JPanel(new GridBagLayout());
        consultationsPanel.setBorder(BorderFactory.createTitledBorder("Consultas da Semana"));


        JPanel newCard = createCards(description, "Pet: " + petId, "Veterinário: " + vet, "Data: " + formattedDate, "Hora: " + formattedTime, 10, 10, 10, 10);
        consultationsPanel.add(newCard);

        System.out.println("Card Adicionado");

        consultationsPanel.revalidate();
        consultationsPanel.repaint();
    }

    private void addFieldToPanel(JPanel panel, GridBagConstraints gbc, String label, JComponent field) {
        JLabel jLabel = new JLabel(label);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }


    private JPanel createListPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel consultationsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        consultationsPanel.setBorder(BorderFactory.createTitledBorder("Consultas da Semana"));

//        consultationsPanel.add(createCards("Consulta Veterinária", "Pet: Bella", "Veterinário: Dr. Silva", "Data: 15/11/2024", "Hora: 10:00", 10, 10, 10, 10));
//        consultationsPanel.add(createCards("Vacinação", "Pet: Max", "Veterinário: Dr. Lima", "Data: 16/11/2024", "Hora: 11:30", 10, 10, 10, 10));
//        consultationsPanel.add(createCards("Consulta de Retorno", "Pet: Luna", "Veterinário: Dra. Souza", "Data: 17/11/2024", "Hora: 14:00", 10, 10, 10, 10));
//        consultationsPanel.add(createCards("Banho e Tosa", "Pet: Bob", "Veterinário: Dr. Costa", "Data: 18/11/2024", "Hora: 16:00", 10, 10, 10, 10));
//        consultationsPanel.add(createCards("Exame", "Pet: Nino", "Veterinário: Dr. Silva", "Data: 19/11/2024", "Hora: 09:00", 10, 10, 10, 10));
//        consultationsPanel.add(createCards("Check-up", "Pet: Luna", "Veterinário: Dra. Souza", "Data: 20/11/2024", "Hora: 13:00", 10, 10, 10, 10));
//        consultationsPanel.add(createCards("Consulta Emergencial", "Pet: Bella", "Veterinário: Dr. Costa", "Data: 21/11/2024", "Hora: 15:00", 10, 10, 10, 10));
//        consultationsPanel.add(createCards("Banho", "Pet: Max", "Veterinário: Dr. Lima", "Data: 22/11/2024", "Hora: 16:00", 10, 10, 10, 10));

        for (Consulta c : QueryDB.getAllConsulta()){
            LocalDate parseDate = LocalDate.parse(c.getDate());
            LocalDate presentDate = LocalDate.now();

            WeekFields weekFields = WeekFields.of(Locale.getDefault());

            if (
                    parseDate.get(weekFields.weekOfWeekBasedYear()) == presentDate.get(weekFields.weekOfWeekBasedYear())
                    && parseDate.get(weekFields.weekBasedYear()) == presentDate.get(weekFields.weekBasedYear())
            ) {
                consultationsPanel.add(
                        createCards(
                                c.getTipo(),
                                "Pet: " + c.getPet().getName(),
                                "Veterinario: " + c.getVeterinario().getName(),
                                "Data: " + c.getDate(),
                                "Hora: " + c.getDatahora(),
                                10, 10, 10 ,10
                        )
                );
            }
        }

        JScrollPane scrollPane = new JScrollPane(consultationsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel calendarPanel = createCalendarPanel();
        panel.add(calendarPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCards(String title, String pet, String vet, String date, String time, int topMargin, int leftMargin, int bottomMargin, int rightMargin) {
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };

        cardPanel.setLayout(new GridLayout(0, 1));
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(5, 1));
        textPanel.setOpaque(false);

        JLabel titleLabel = createLabel(title, new Font("Arial", Font.BOLD, 10));
        titleLabel.setPreferredSize(new Dimension(160, 15));

        JLabel petLabel = createLabel(pet, new Font("Arial", Font.PLAIN, 9));
        petLabel.setPreferredSize(new Dimension(160, 15));
        textPanel.add(petLabel);

        JLabel vetLabel = createLabel(vet, new Font("Arial", Font.PLAIN, 9));
        vetLabel.setPreferredSize(new Dimension(160, 15));
        textPanel.add(vetLabel);

        JLabel dateLabel = createLabel(date, new Font("Arial", Font.PLAIN, 9));
        dateLabel.setPreferredSize(new Dimension(160, 15));
        textPanel.add(dateLabel);

        JLabel timeLabel = createLabel(time, new Font("Arial", Font.PLAIN, 9));
        timeLabel.setPreferredSize(new Dimension(160, 15));
        textPanel.add(timeLabel);

        cardPanel.add(textPanel);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        cardPanel.setBorder(new EmptyBorder(10, 10, 10, -10));

        return cardPanel;
    }

    private JPanel createCalendarPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel daysPanel = new JPanel(new GridLayout(7, 7));
        panel.add(monthLabel, BorderLayout.NORTH);
        panel.add(daysPanel, BorderLayout.CENTER);

        JPanel navPanel = new JPanel(new FlowLayout());
        JButton prevButton = new JButton("<");
        prevButton.addActionListener(e -> {
            currentMonthOffset--;
            updateCalendar(monthLabel, daysPanel);
        });

        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> {
            currentMonthOffset++;
            updateCalendar(monthLabel, daysPanel);
        });

        navPanel.add(prevButton);
        navPanel.add(nextButton);
        panel.add(navPanel, BorderLayout.SOUTH);

        panel.setPreferredSize(new Dimension(panel.getWidth(), 200));

        updateCalendar(monthLabel, daysPanel);

        return panel;
    }

    private void updateCalendar(JLabel monthLabel, JPanel daysPanel) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, currentMonthOffset);

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
        monthLabel.setText(monthFormat.format(calendar.getTime()));

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int emptyDays = (firstDayOfWeek == Calendar.SUNDAY) ? 0 : firstDayOfWeek - 1;
        int totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        daysPanel.removeAll();

        for (int i = 0; i < emptyDays; i++) {
            daysPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= totalDays; day++) {
            JLabel dayLabel = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            if (temConsultaNoDia(day, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR))) {
                dayLabel.setBackground(Color.LIGHT_GRAY);
                dayLabel.setOpaque(true);
                dayLabel.setToolTipText("Consulta marcada");
            }
            daysPanel.add(dayLabel);
        }

        for (int i = totalDays + emptyDays; i < 42; i++) {
            daysPanel.add(new JLabel(""));
        }

        daysPanel.revalidate();
        daysPanel.repaint();
    }

    private boolean temConsultaNoDia(int day, int month, int year) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Consulta c : QueryDB.getAllConsulta()) {
            try {
                Date consultaDate = sdf.parse(c.getDate());
                Calendar consultaCalendar = Calendar.getInstance();
                consultaCalendar.setTime(consultaDate);

                if (consultaCalendar.get(Calendar.DAY_OF_MONTH) == day &&
                        consultaCalendar.get(Calendar.MONTH) == month &&
                        consultaCalendar.get(Calendar.YEAR) == year) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void start() {
        SwingUtilities.invokeLater(() -> {
            Consultation consultation = new Consultation();
            DefaultPage.getDefaultConfig(consultation);
        });
    }
}
