package ui;

import data.DayEntry;
import data.UserDaysDao;
import data.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.*;
import java.util.List;


//class ui.CalendarPanel to create a calendar
//created by: Anthony Hernandez (i made sum changes)

public class CalendarPanel extends JPanel{

    private int calendarMonth;
    private int calendarYear;
    private JLabel monthYearLabel;
    private JPanel calendarGrid;
    private UserDaysDao userDaysDao;
    private String username;
    YearMonth yearMonth;
    //buttons for changing months
    JButton prevMonthBtn = new JButton("<-");
    JButton nextMonthBtn = new JButton("->");

    //creating panel that displays a month of a year on a calendar display
    public CalendarPanel(int year, int month, UserDaysDao dao, String username) {
        setLayout(new BorderLayout());
        this.calendarMonth = month;
        this.calendarYear = year;
        this.userDaysDao =dao;
        this.username=username;
        yearMonth = YearMonth.of(calendarYear, calendarMonth);
        calendarGrid = new JPanel(new GridLayout(0, 7));
        add(calendarGrid, BorderLayout.CENTER);
        monthYearLabel= new JLabel("", SwingConstants.CENTER);
        //to draw calendar
        renderCalendar();

        //panel to add on top of the calendar grid (navigation + label of month and year)
        JPanel calendarNavPanel = new JPanel();
        calendarNavPanel.add(prevMonthBtn);
        calendarNavPanel.add(monthYearLabel);
        calendarNavPanel.add(nextMonthBtn);
        add(calendarNavPanel, BorderLayout.NORTH);

        //update calendar as fitness enthusiast changes months
        prevMonthBtn.addActionListener(e -> {
            calendarMonth--;
            //if going to prev month results in prev year, update variables as needed
            if (calendarMonth<1) {
                calendarMonth = 12;
                calendarYear --;
            }
            yearMonth = YearMonth.of(calendarYear, calendarMonth);
            renderCalendar();
        });
        nextMonthBtn.addActionListener(e -> {
            calendarMonth++;
            //if going to next month results in next year, update variables as needed
            if (calendarMonth>12) {
                calendarMonth=1;
                calendarYear ++;
            }
            yearMonth = YearMonth.of(calendarYear, calendarMonth);
            renderCalendar();
        });
    }

    //render Calendar will use local (updated) variables calendarYear and calendarMonth to show the correct calendar view
    private void renderCalendar() {
        calendarGrid.removeAll();

        //making title = month + year (string + int) Aligned CENTER
        this.monthYearLabel.setText(yearMonth.getMonth() + " " + calendarYear);

        //making the calendar grid, will add rows as we go. 7 cols for 7 days in week

        //putting in center of our panel

        //adding Sunday, monday,... to top parts of the grid
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        for (String d : days) { //goes through array of days for top labels
            JLabel dayLabel = new JLabel(d, SwingConstants.CENTER);
            calendarGrid.add(dayLabel);
        }

        //used to know where (what box) to start drawing calendar
        LocalDate firstOfMonth = yearMonth.atDay(1);
        //day of week with sun=0, mon=1, ...
        int startDayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
        //add blanks for sun, mon, ... of last month
        for (int i =0; i <startDayOfWeek; i++) {
            calendarGrid.add(new JLabel("")); //empty String
        } //then add numbered days
        for (int day =1; day<=yearMonth.lengthOfMonth(); day++) {
            //
            LocalDate date= LocalDate.of(calendarYear, calendarMonth, day);
            //using DayLabel so we can edit how this Label works in a separate class
            DayLabel dayLabel = new DayLabel(date);

            dayLabel.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    //clickedDay= day user clicked
                    DayLabel clickedDay = (DayLabel) e.getSource();
                    LocalDate clickedDate = clickedDay.getDate();

                    //getting DayEntry using DAO
                    DayEntry dayEntry = userDaysDao.getDay(username, clickedDate);

                    DayEntryPanel entryPanel = new DayEntryPanel(dayEntry, userDaysDao, username, () -> {
                        refresh();
                    });
                    Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(CalendarPanel.this);
                    JDialog dialog = new JDialog(parentFrame, "Edit " + clickedDate , true);
                    dialog.setContentPane(entryPanel);
                    dialog.pack();
                    dialog.setLocationRelativeTo(parentFrame);
                    dialog.setVisible(true);
                }
            });

            if (LocalDate.now().equals(date)) { //highlight today's date
                dayLabel.highlightToday();
            }
            calendarGrid.add(dayLabel);

            // Get the DayEntry for this date
            DayEntry userDay = userDaysDao.getDay(username, date);

            // ===== NEW: compute tasks + sleep summary =====
            int totalTasks = 0;
            int completedTasks = 0;
            double hoursSlept = 0.0;

            if (userDay != null) {
                // Sleep
                hoursSlept = userDay.getHoursSlept();

                // Tasks
                List<Task> tasks = userDay.getTasks();
                if (tasks != null) {
                    totalTasks = tasks.size();
                    for (Task t : tasks) {
                        if (t.isCompleted()) {
                            completedTasks++;
                        }
                    }
                }
            }

            boolean hasTasks = totalTasks > 0;
            boolean hasSleep = hoursSlept > 0.0;

            if (hasTasks || hasSleep) {
                double roundedSleep = Math.round(hoursSlept * 10.0) / 10.0;

                StringBuilder sb = new StringBuilder();

                // Summary (centered content)
                if (hasTasks) {
                    sb.append("<html>");
                    sb.append(totalTasks)
                            .append(totalTasks == 1 ? " task" : " tasks")
                            .append("<br>")
                            .append(completedTasks)
                            .append("/")
                            .append(totalTasks)
                            .append(" completed");
                    sb.append("</html>");
                }

                if (hasSleep) {
                    if (hasTasks) {
                        sb.append("\n\n");
                    }
                    sb.append(roundedSleep).append(" hours of sleep");
                }

                dayLabel.setSummary(sb.toString());
            }

            // ===== highlight logic for sleep =====
            if (userDay != null && userDay.getHoursSlept() != 0) {
                System.out.println(date + " slept: " + userDay.getHoursSlept());
                if (userDay.getHoursSlept() < 7.5) {
                    dayLabel.highlightBadSleep();
                } else if (userDay.getHoursSlept() >= 7.5) {
                    dayLabel.highlightGoodSleep();
                }
            }
        }
        calendarGrid.revalidate();
        calendarGrid.repaint();

    }

    public void refresh() {
        renderCalendar();
        revalidate();
        repaint();
    }

}