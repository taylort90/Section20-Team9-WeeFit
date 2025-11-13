import javax.swing.*;
import java.awt.*;
import java.time.*;


//class CalendarPanel to create a calendar
//created by: Anthony Hernandez

public class CalendarPanel extends JPanel{

    private int calendarMonth;
    private int calendarYear;
    private JLabel monthYearLabel;
    private JPanel calendarGrid;
    YearMonth yearMonth;
    //buttons for changing months
    JButton prevMonthBtn = new JButton("<-");
    JButton nextMonthBtn = new JButton("->");

    //creating panel that displays a month of a year on a calendar display
    public CalendarPanel(int year, int month) {
        setLayout(new BorderLayout());
        this.calendarMonth = month;
        this.calendarYear = year;
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
            LocalDate today = LocalDate.now();
            JLabel dayLabel = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            dayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if (today.getDayOfMonth() == day && today.getMonthValue() == calendarMonth && today.getYear() == calendarYear) { //wanna highlight today's date for user
                dayLabel.setBackground(Color.YELLOW);
                dayLabel.setOpaque(true);
            }
            calendarGrid.add(dayLabel);
        }
        calendarGrid.revalidate();
        calendarGrid.repaint();

    }

}