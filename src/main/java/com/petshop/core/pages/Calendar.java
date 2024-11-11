package com.petshop.core.pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.petshop.core.utils.*;
import com.petshop.core.components.*;
import com.petshop.db.DeleteDB;
import com.petshop.db.InsertDB;
import com.petshop.db.QueryDB;
import com.petshop.models.Evento;

public class Calendar extends JFrame {

    private final java.util.Calendar calendar;
    private final JPanel daysPanel;
    private JLabel title;
    private static final Map<Integer, String> events = new HashMap<>();

    // Constructor for the Calendar Page
    public Calendar() {
        setTitle("Calendário");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        calendar = java.util.Calendar.getInstance();

        // Main panel that stacks components vertically
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Adds the TopPanel (header)
        mainPanel.add(new Header(true));

        // Adds the month and year title panel below the header
        mainPanel.add(createMonthYearPanel());

        // Adds a margin below the month-year title
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel for the weekdays (days of the week)
        mainPanel.add(createWeekDaysPanel());

        // Panel for the days of the month
        daysPanel = new JPanel(new GridLayout(6, 7));

        // Creation of the calendar content panel
        JPanel calendarContentPanel = new JPanel();
        calendarContentPanel.setLayout(new BoxLayout(calendarContentPanel, BoxLayout.Y_AXIS));
        calendarContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        calendarContentPanel.add(daysPanel);
        mainPanel.add(calendarContentPanel);

        // Panel for navigation (prev, next buttons) and title of the month
        mainPanel.add(createNavPanel());

        // Panel that will contain the navigation and footer panels
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        // Adds the footer panel
        Footer footer = new Footer();
        bottomPanel.add(footer.createFooterPanel());

        // Add the mainPanel and bottomPanel to the window
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Update the calendar display
        updateCalendar();
    }

    // Create the panel for the month and year title
    private JPanel createMonthYearPanel() {
        JPanel monthYearPanel = new JPanel();
        monthYearPanel.setBackground(new Color(115, 0, 255));
        monthYearPanel.setPreferredSize(new Dimension(800, 40));

        // Initializes the title and centers it
        title = new JLabel("", SwingConstants.CENTER);  // Initializes the empty title
        title.setFont(new Font("Arial", Font.PLAIN, 12));
        title.setForeground(Color.WHITE);
        monthYearPanel.add(title);

        return monthYearPanel;
    }

    // Creates the navigation panel with buttons to navigate months and the month title
    private JPanel createNavPanel() {
        // Main panel that contains all the components
        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setPreferredSize(new Dimension(700, 50));

        // Left panel with <> buttons
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton prevButton = new RoundedButton("<");
        prevButton.setPreferredSize(new Dimension(30, 25));
        prevButton.addActionListener(e -> navigateMonth(-1));
        Hover.addHandCursorOnHover(prevButton);
        leftPanel.add(prevButton);

        JButton nextButton = new RoundedButton(">");
        nextButton.setPreferredSize(new Dimension(30, 25));
        nextButton.addActionListener(e -> navigateMonth(1));
        Hover.addHandCursorOnHover(nextButton);
        leftPanel.add(nextButton);

        // Central panel will no longer contain the title
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(new JLabel("")); // Just empty space now

        // "New Event" button with right align
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton newEventButton = new RoundedButton("Novo Evento");
        newEventButton.setPreferredSize(new Dimension(110, 25));
        newEventButton.addActionListener(e -> createNewEvent());
        Hover.addHandCursorOnHover(newEventButton);

        rightPanel.add(newEventButton);

        // Adding all the panels
        navPanel.add(leftPanel, BorderLayout.WEST);
        navPanel.add(centerPanel, BorderLayout.CENTER);
        navPanel.add(rightPanel, BorderLayout.EAST);

        return navPanel;
    }

    // Create new event
    private void createNewEvent() {
        JTextField dayField = new JTextField(2);
        JTextField timeField = new JTextField(5);
        JTextField eventField = new JTextField(20);
        JTextField responsibleField = new JTextField(11);

        JPanel panel = new JPanel(new GridLayout(4, 4));
        panel.add(new JLabel("Dia:"));
        panel.add(dayField);
        panel.add(new JLabel("Horário do Evento:"));
        panel.add(timeField);
        panel.add(new JLabel("Descrição do Evento:"));
        panel.add(eventField);
        panel.add(new JLabel("CPF do responsável"));
        panel.add(responsibleField);


        int result = JOptionPane.showConfirmDialog(this, panel, "Criar Novo Evento", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int day = Integer.parseInt(dayField.getText());
                String eventTime = timeField.getText().trim();
                String eventDescription = eventField.getText();
                String responsible = responsibleField.getText().trim();

                if (!QueryDB.getAllVet().stream().anyMatch(vet -> vet.getCpf().equals(responsible))) {
                    JOptionPane.showMessageDialog(this, "O CPF do responsável é inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!(day > 0 && day <= calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH) && !eventDescription.isEmpty())) {
                    JOptionPane.showMessageDialog(this, "Entrada inválida. Verifique o dia e a descrição.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int month = calendar.get(java.util.Calendar.MONTH) + 1;
                int year = calendar.get(java.util.Calendar.YEAR);


                String monthString = String.valueOf(month);
                String yearString = String.valueOf(year);
                String dayString;

                if (day > 10) {
                    dayString = String.valueOf(day);
                } else {
                    dayString = "0" + String.valueOf(day);
                }

                String dateString = yearString + "-" + monthString + "-" + dayString;

                InsertDB.insertEvento(dateString, eventTime, eventDescription, responsible);

//                if (day > 0 && day <= calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH) && !eventDescription.isEmpty()) {
//                    events.put(day, eventDescription);
//                    updateCalendar(); // Reload the calendar with new events
//                    JOptionPane.showMessageDialog(this, "Evento criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
//                } else {
//                    JOptionPane.showMessageDialog(this, "Entrada inválida. Verifique o dia e a descrição.", "Erro", JOptionPane.ERROR_MESSAGE);
//                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Dia inválido. Por favor, insira um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Creates the weekdays panel, where each day of the week is displayed
    private JPanel createWeekDaysPanel() {
        JPanel weekDaysPanel = new JPanel(new GridLayout(1, 7));
        String[] weekDays = {"Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"};
        for (String day : weekDays) {
            JLabel label = new JLabel(day, SwingConstants.LEFT);
            label.setFont(new Font("Arial", Font.PLAIN, 12));
            label.setPreferredSize(new Dimension(100, 20));
            label.setBorder(BorderFactory.createEmptyBorder(0,30,0,0));
            weekDaysPanel.add(label);
        }
        weekDaysPanel.setPreferredSize(new Dimension(700, 50));
        weekDaysPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return weekDaysPanel;
    }

    // Navigates to the previous or next month
    private void navigateMonth(int increment) {
        // Change the month
        calendar.add(java.util.Calendar.MONTH, increment);
        // Update the calendar view after changing the month
        updateCalendar();
    }

    // Updates the calendar display with the correct month and year
    private void updateCalendar() {
        daysPanel.removeAll();
        calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);

        int month = calendar.get(java.util.Calendar.MONTH) + 1;
        int year = calendar.get(java.util.Calendar.YEAR);

        title.setText(month +"/"+ year);

        int firstDayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

        for (int i = 1; i < firstDayOfWeek; i++) {
            daysPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= daysInMonth; day++) {
            JPanel dayPanel = createDayPanel(day, month, year);
            daysPanel.add(dayPanel);
        }

        int totalDays = firstDayOfWeek + daysInMonth - 1;
        int cellsToFill = 42 - totalDays;
        for (int i = 0; i < cellsToFill; i++) {
            daysPanel.add(new JLabel(""));
        }

        daysPanel.revalidate();
        daysPanel.repaint();
    }

    // Creates the panel for each day, including event information if applicable
    private JPanel createDayPanel(int day, int month, int year) {
        JPanel dayPanel = new JPanel();
        dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));
        dayPanel.setBorder(BorderFactory.createLineBorder(new Color(222, 219, 219), 1));
        dayPanel.setPreferredSize(new Dimension(100, 80));

        // Create the panel for the day number and align to the left
        JPanel dayLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dayLabelPanel.setOpaque(true);
        // Limits the height of the day panel
        dayLabelPanel.setMaximumSize(new Dimension(100, 30));

        JLabel dayLabel = new JLabel(String.valueOf(day), SwingConstants.LEFT);
        dayLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dayLabelPanel.add(dayLabel);

        dayPanel.add(dayLabelPanel);

        ArrayList<Evento> eventoArrayList = QueryDB.getAllEvento();

        for (Evento evento : eventoArrayList) {
            int dayEvento = LocalDate.parse(evento.getDate()).getDayOfMonth();
            int monthEvento = LocalDate.parse(evento.getDate()).getMonthValue();
            int yearEvento = LocalDate.parse(evento.getDate()).getYear();
            if ( dayEvento == day && monthEvento == month && yearEvento == year) {
                JPanel eventPanel = createEventPanel(evento);
                dayPanel.add(eventPanel);
                addEventClickListener(dayPanel, evento);
            }
        }

//        // If there are events for this day, display them
//        if (events.containsKey(day)) {
//            JPanel eventPanel = createEventPanel(day);
//            dayPanel.add(eventPanel);
//            // Add a click listener for the event details
//            addEventClickListener(dayPanel, day);
//        }

        return dayPanel;
    }

    // Creates the panel displaying the event details for the day
    private JPanel createEventPanel(Evento evento) {
        JPanel eventPanel = new JPanel();
        // Event background color (blue)
        eventPanel.setBackground(new Color(0, 85, 255, 255));
        // Limits the height of the event panel
        eventPanel.setMaximumSize(new Dimension(140, 20));
        eventPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel eventLabel = new JLabel(evento.getDescricao());
        eventLabel.setForeground(Color.WHITE);
        eventLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        eventPanel.add(eventLabel);

        return eventPanel;
    }

    // Adds a mouse click listener to display event details when the day is clicked
    private void addEventClickListener(JPanel dayPanel, Evento evento) {
        dayPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Creates custom buttons for the dialog
                Object[] options = {"Fechar", "Excluir Evento"};
                int result = JOptionPane.showOptionDialog(Calendar.this,
                        "Evento: " + evento.getDescricao() + "\nResponsável: " + evento.getResponsavel().getName() + "\nData/Hora: " + evento.getDate() + "-" + evento.getDatahora(),
                        "Detalhes do Evento",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null, options, options[0]);

                // Checks the selected option
                if (result == 1) { // If the "Excluir Evento" option is selected
                    DeleteDB.delete("evento", String.valueOf(evento.getId()));
                    updateCalendar(); // Updates the calendar to reflect the event removal
                }
            }
        });
    }

    // Starts the Calendar and displays it
    public static void start() {
        Calendar calendarPanel = new Calendar();
        // Make the calendar page visible
        calendarPanel.setVisible(true);
        calendarPanel.setLocationRelativeTo(null);
        calendarPanel.setResizable(false);
    }

    // Main method to launch the calendar page
    public static void main(String[] args) {
        start();
    }
}
