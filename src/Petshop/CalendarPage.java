package Petshop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CalendarPage extends JFrame {

    private final Calendar calendar;
    private final JPanel daysPanel;
    private JLabel title;
    private static final Map<Integer, String> events = new HashMap<>();

    // Constructor for the Calendar Page
    public CalendarPage() {
        setTitle("Calendário");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        calendar = Calendar.getInstance();

        // Initialize some example events
        events.put(5, "Aniversário do Cliente A");
        events.put(12, "Consulta Pet B");
        events.put(18, "Promoção de banho e tosa");
        events.put(23, "Palestra sobre pets");

        // Main panel that stacks components vertically
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Adds the TopPanel (header)
        mainPanel.add(HomePage.createTopPanel());

        // Adds a margin below the header
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));

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
        bottomPanel.add(PetsPage.createFooterPanel());

        // Add the mainPanel and bottomPanel to the window
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Update the calendar display
        updateCalendar();
    }

    // Creates the navigation panel with buttons to navigate months and the month title
    private JPanel createNavPanel() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navPanel.setPreferredSize(new Dimension(700, 50));

        JButton prevButton = new JButton("<");
        prevButton.addActionListener(e -> navigateMonth(-1)); // Button to go to the previous month

        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> navigateMonth(1)); // Button to go to the next month

        title = new JLabel("", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        navPanel.add(prevButton);
        navPanel.add(title);
        navPanel.add(nextButton);

        return navPanel;
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
        calendar.add(Calendar.MONTH, increment);
        // Update the calendar view after changing the month
        updateCalendar();
    }

    // Updates the calendar display with the correct month and year
    private void updateCalendar() {
        daysPanel.removeAll();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        // Display the current month and year
        title.setText("Mês: " + month + " - Ano: " + year);

        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Fill empty cells until the first day of the month
        for (int i = 1; i < firstDayOfWeek; i++) {
            daysPanel.add(new JLabel(""));
        }

        // Fill the days of the month
        for (int day = 1; day <= daysInMonth; day++) {
            JPanel dayPanel = createDayPanel(day);
            daysPanel.add(dayPanel);
        }

        // Fill empty cells at the end of the month
        int totalDays = firstDayOfWeek + daysInMonth - 1;
        int cellsToFill = 42 - totalDays;
        for (int i = 0; i < cellsToFill; i++) {
            daysPanel.add(new JLabel(""));
        }

        // Refresh the panel to show the updated calendar
        daysPanel.revalidate();
        daysPanel.repaint();
    }

    // Creates the panel for each day, including event information if applicable
    private JPanel createDayPanel(int day) {
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

        // If there are events for this day, display them
        if (events.containsKey(day)) {
            JPanel eventPanel = createEventPanel(day);
            dayPanel.add(eventPanel);
            // Add a click listener for the event details
            addEventClickListener(dayPanel, day);
        }

        return dayPanel;
    }

    // Creates the panel displaying the event details for the day
    private JPanel createEventPanel(int day) {
        JPanel eventPanel = new JPanel();
        // Event background color (blue)
        eventPanel.setBackground(new Color(0, 85, 255, 255));
        // Limits the height of the event panel
        eventPanel.setMaximumSize(new Dimension(140, 20));
        eventPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel eventLabel = new JLabel(events.get(day));
        eventLabel.setForeground(Color.WHITE);
        eventLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        eventPanel.add(eventLabel);

        return eventPanel;
    }

    // Adds a mouse click listener to display event details when the day is clicked
    private void addEventClickListener(JPanel dayPanel, int day) {
        dayPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Show a dialog with event details when a day with an event is clicked
                JOptionPane.showMessageDialog(CalendarPage.this,
                        "Evento: " + events.get(day),
                        // "Event Details" title in the dialog
                        "Detalhes do Evento",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // Starts the CalendarPage and displays it
    public static void start() {
        CalendarPage calendarPanel = new CalendarPage();
        // Make the calendar page visible
        calendarPanel.setVisible(true);
        calendarPanel.setLocationRelativeTo(null);

    }

    // Main method to launch the calendar page
    public static void main(String[] args) {
        start();
    }
}