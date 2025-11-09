import javax.swing.*;
import java.awt.*;
import java.time.*;

public class CalendarPanel extends JPanel{

    //creating panel that displays a month of a year on a calendar display
    public CalendarPanel(int year, int month) {
        setLayout(new BorderLayout());

        //making title=month + year (string + int) Alligned CENTER. Put on top of
        JLabel title = new JLabel(YearMonth.of(year, month).getMonth() + " " + year, SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);
        //making the calendar grid, will add rows as we go. 7 cols for 7 days in week
        JPanel calendarGrid = new JPanel(new GridLayout(0, 7));
        //putting in center of our panel
        add(calendarGrid, BorderLayout.CENTER);
        //adding Sunday, monday,... to top parts of the grid
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        for (String d : days) { //goes through array of days for top labels
            JLabel dayLabel = new JLabel(d, SwingConstants.CENTER);
            calendarGrid.add(dayLabel);
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstOfMonth = yearMonth.atDay(1);

        //day of week with sun=0, mon=1, ...
        int startDayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;
        //add blanks for sun, mon, ... of last month
        for (int i =0; i <startDayOfWeek; i++) {
            calendarGrid.add(new JLabel("")); //empty String
        }

        for (int day =1; day<=yearMonth.lengthOfMonth(); day++) {
            LocalDate today = LocalDate.now();

            JLabel dayLabel = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            dayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if (today.getDayOfMonth() == day && today.getMonthValue()== month && today.getYear() == year) { //wanna highlight today's date for user
                dayLabel.setBackground(Color.YELLOW);
                dayLabel.setOpaque(true);
            }

            calendarGrid.add(dayLabel);
        }


    }
}