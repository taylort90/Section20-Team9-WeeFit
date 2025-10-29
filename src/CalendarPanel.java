import javax.swing.*;
import java.awt.*;
import java.time.*;

public class CalendarPanel extends JPanel{

    //creating panel that displays a month of a year on a calendar display
    public CalendarPanel(int year, int month) {
        setLayout(new BorderLayout());

        //making the label (title) as the month + year (string + int)
        JLabel title = new JLabel(YearMonth.of(year, month).getMonth() + " " + year, SwingConstants.CENTER);

        //making the calendar grid
        JPanel calendarGrid = new JPanel(new GridLayout(0, 7));
        //putting in center of our panel
        add(calendarGrid, BorderLayout.CENTER);

        //adding monday, tuesday, etc to the top parts of the grid


    }
}