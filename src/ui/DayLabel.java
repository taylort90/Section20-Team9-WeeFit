package ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;


//Created by: Anthony
//this is the square of each day on the calendar panel
//want to implement things like highlighted for amount of sleep that day, show preview of tasks (small colored bullets?)

public class DayLabel extends JLabel {
    private LocalDate date;

    public DayLabel(LocalDate date){
        super(String.valueOf(date.getDayOfMonth()));
        this.date=date;

        //put day # top left
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);
        //black border
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setOpaque(true);
        //white background
        setBackground(Color.WHITE);

    }

    public LocalDate getDate() {
        return date;
    }

    //functions to highlight days (based on sleep/ todays date)
    public void highlightToday() {
        setBackground(Color.YELLOW);
    }

    public void highlightBadSleep() { setBackground(Color.RED);}

    public void highlightGoodSleep() { setBackground(Color.green);}

    //add more for coloring day if bad sleep, or other things


}
