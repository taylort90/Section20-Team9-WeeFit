package ui;

import app.Main;
import data.DayEntry;
import data.UserDaysDao;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class MenuPanel extends JPanel {
    private final UserDaysDao userDaysDao;
    private final String username;
    private final Main mainApp;

    public MenuPanel(UserDaysDao dao, String username, Main mainApp) {
        this.userDaysDao = dao;
        this.username = username;
        this.mainApp = mainApp;

        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);

        // TOP PANEL (Title + Description)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel(username + "'s Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel description = new JLabel("Check today's tasks, set your goals, or view your calendar.");
        description.setFont(new Font("SansSerif", Font.PLAIN, 14));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(Box.createVerticalStrut(15));
        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(description);
        topPanel.add(Box.createVerticalStrut(15));

        add(topPanel, BorderLayout.NORTH);

        //Todays tasks
        DayEntry todayEntry = userDaysDao.getDay(username, LocalDate.now());
        TaskPanel todayTaskPanel = new TaskPanel(todayEntry, userDaysDao, username);

        JScrollPane taskScrollPane = new JScrollPane(todayTaskPanel);
        taskScrollPane.setBorder(BorderFactory.createTitledBorder("Today's Tasks"));

        add(taskScrollPane, BorderLayout.CENTER);

      //buttons panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
        bottomPanel.setBackground(Color.WHITE);

        JButton calendarBtn = createStyledButton("Calendar");
        calendarBtn.addActionListener(e -> openCalendar());

        JButton goalsBtn = createStyledButton("Goals");
        goalsBtn.addActionListener(e -> openGoals());

        bottomPanel.add(calendarBtn);
        bottomPanel.add(goalsBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Helper: Styled Button
    private JButton createStyledButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 16));
        b.setPreferredSize(new Dimension(180, 40));
        b.setFocusPainted(false);
        b.setBackground(new Color(70, 130, 180));
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        b.setOpaque(true);
        return b;
    }

    private void openCalendar() {
        LocalDate today = LocalDate.now();
        CalendarPanel calendar = new CalendarPanel(
                today.getYear(),
                today.getMonthValue(),
                userDaysDao,
                username
        );

        JButton backBtn = new JButton("← Back");
        backBtn.addActionListener(e -> mainApp.showPanel("MENU", "MENU"));

        JPanel screen = new JPanel(new BorderLayout());
        screen.add(backBtn, BorderLayout.NORTH);
        screen.add(calendar, BorderLayout.CENTER);

        if (!mainApp.isPanelAdded("Calendar")) {
            mainApp.addPanel(screen, "Calendar");
        }
        mainApp.showPanel("Calendar", "Calendar");
    }

    private void openGoals() {
        GoalsPanel goals = new GoalsPanel();

        JButton backBtn = new JButton("← Back");
        backBtn.addActionListener(e -> mainApp.showPanel("MENU", "MENU"));

        JPanel screen = new JPanel(new BorderLayout());
        screen.add(backBtn, BorderLayout.NORTH);
        screen.add(goals, BorderLayout.CENTER);

        if (!mainApp.isPanelAdded("Goals")) {
            mainApp.addPanel(screen, "Goals");
        }
        mainApp.showPanel("Goals", "Goals");
    }
}
