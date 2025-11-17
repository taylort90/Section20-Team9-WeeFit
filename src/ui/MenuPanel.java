package ui;

import app.Main;
import data.UserDataDao;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class MenuPanel extends JPanel {
    private final UserDataDao userDataDao;
    private final String username;
    private final Main mainApp;


    public MenuPanel(UserDataDao dao, String username, Main mainApp) {
        this.userDataDao = dao;
        this.username = username;
        this.mainApp = mainApp;
        setLayout(new GridLayout(3, 1, 10, 10)); //stacked buttons)
        JButton CalendarBtn = new JButton("Calendar");
        CalendarBtn.addActionListener(e -> openCalendar());

        //goals button
        JButton GoalsBtn = new JButton("Goals");
        //do addActionListener once we implement the goals part
        add(CalendarBtn);
        add(GoalsBtn);

    }


    private void openCalendar() {
        LocalDate today = LocalDate.now();
        int calendarMonth = today.getMonthValue();
        int calendarYear = today.getYear();
        //could change constructor to have username (for less lines)
        CalendarPanel calendar = new CalendarPanel(calendarYear, calendarMonth, userDataDao);
        calendar.setUsername(mainApp.getCurrentUsername());

        JButton backToMenuBtn = new JButton("<- Back");
        backToMenuBtn.addActionListener(e -> mainApp.showPanel("MENU", "MENU"));

        JPanel calendarScreen = new JPanel(new BorderLayout());
        calendarScreen.add(backToMenuBtn, BorderLayout.NORTH);
        calendarScreen.add(calendar, BorderLayout.CENTER);
        if (!mainApp.isPanelAdded("Calendar")) {
            mainApp.addPanel(calendarScreen, "Calendar");
        }

        mainApp.showPanel("Calendar", "Calendar");

    }



}
