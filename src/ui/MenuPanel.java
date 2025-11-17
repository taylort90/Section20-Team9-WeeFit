package ui;

import app.Main;
import data.DayEntry;
import data.UserDaysDao;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;


//created by: Anthony
//a menu that shows several buttons
//for now, just the calendar button works
//opened after user logs in

public class MenuPanel extends JPanel {
    private final UserDaysDao userDaysDao;
    private final String username;
    private final Main mainApp;


    public MenuPanel(UserDaysDao dao, String username, Main mainApp) {
        this.userDaysDao = dao;
        this.username = username;
        this.mainApp = mainApp;
        setLayout(new BorderLayout(10,10)); //stacked buttons)

        //TOP PANEL
        //STILL NEED: make text bigger
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel greetingLabel= new JLabel("Hello, " + username+ "!");
        topPanel.add(greetingLabel);
        //adding to top of this (MenuPanel)
        add(topPanel,BorderLayout.NORTH);

        //MIDDLE PANEL
        //NOTE: Change up TaskPanel to make it look prettier
        DayEntry todayEntry = userDaysDao.getDay(this.username, LocalDate.now());
        TaskPanel todayTaskPanel= new TaskPanel(todayEntry, userDaysDao, this.username );
        JScrollPane taskScrollPane = new JScrollPane(todayTaskPanel);
        add(taskScrollPane, BorderLayout.CENTER);
        //END MIDDLE PANEL

        //BOTTOM PANEL
        //STILL NEED: Make buttons lower
        JPanel bottomPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));

        JButton CalendarBtn = new JButton("Calendar");
        CalendarBtn.addActionListener(e -> openCalendar());
        bottomPanel.add(CalendarBtn);

        //goals button
        JButton GoalsBtn = new JButton("Goals");
        GoalsBtn.addActionListener(e->openGoals());
        //do addActionListener once we implement the goals part
        bottomPanel.add(GoalsBtn);
        add(bottomPanel, BorderLayout.SOUTH);
        //END BOTTOM PANEL
    }


    private void openCalendar() {
        LocalDate today = LocalDate.now();
        int calendarMonth = today.getMonthValue();
        int calendarYear = today.getYear();
        //could change constructor to have username (for less lines)
        CalendarPanel calendar = new CalendarPanel(calendarYear, calendarMonth, userDaysDao, username);
        //calendar.setUsername(mainApp.getCurrentUsername());

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

    private void openGoals() {
        GoalsPanel goals= new GoalsPanel();
        JPanel goalScreen = new JPanel(new BorderLayout());

        JButton backToMenuBtn = new JButton("<- Back");
        backToMenuBtn.addActionListener(e -> mainApp.showPanel("MENU", "MENU"));

        goalScreen.add(backToMenuBtn, BorderLayout.NORTH);
        goalScreen.add(goals, BorderLayout.CENTER);
        if (!mainApp.isPanelAdded("Goals")) {
            mainApp.addPanel(goalScreen, "Goals");
        }
        mainApp.showPanel("Goals", "Goals");

    }



}
