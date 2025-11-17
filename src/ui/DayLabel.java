package ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

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

    public void highlightToday() {
        setBackground(Color.YELLOW);
    }

    //add more for coloring day if bad sleep, or other things


}
