package ui;

import app.Main;
import data.DayEntry;
import data.UserDataDao;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;


//created by: Anthony
//a menu that shows several buttons
//for now, just the calendar button works
//opened after user logs in

public class MenuPanel extends JPanel {
    private final UserDataDao userDataDao;
    private final String username;
    private final Main mainApp;


    public MenuPanel(UserDataDao dao, String username, Main mainApp) {
        this.userDataDao = dao;
        this.username = username;
        this.mainApp = mainApp;
        setLayout(new GridLayout(3, 1, 10, 10)); //stacked buttons)

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel greetingLabel= new JLabel("Hello, " + username+ "!");
        topPanel.add(greetingLabel);
        //adding to top of this (MenuPanel)
        add(topPanel);

        //MIDDLE PANEL
        //MAKE SURE THIS IS CORRECT: Make the middle panel the tasks (made TaskPanel.java to make this easier (we already implemented this in dayEntryPanel, so now we can reuse (yayyy!))
        DayEntry todayEntry = userDataDao.getDay(this.username, LocalDate.now());
        TaskPanel todayTaskPanel= new TaskPanel(todayEntry, userDataDao, this.username );
        JScrollPane taskScrollPane = new JScrollPane(todayTaskPanel);
        add(taskScrollPane);
        //END MIDDLE PANEL

        //BOTTOM PANEL
        //STILL NEED: Make a bottom Panel, add buttons to it and put at bottom of this (MenuPanel)
        JPanel bottomPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));

        JButton CalendarBtn = new JButton("Calendar");
        CalendarBtn.addActionListener(e -> openCalendar());
        bottomPanel.add(CalendarBtn);

        //goals button
        JButton GoalsBtn = new JButton("Goals");
        //do addActionListener once we implement the goals part
        bottomPanel.add(GoalsBtn);
        add(bottomPanel);
        //END BOTTOM PANEL
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
