package ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;


//Created by: Anthony
//this is the square of each day on the calendar panel
//want to implement things like highlighted for amount of sleep that day, show preview of tasks (small colored bullets?)
//added something to make the day number appear top left
//and the summarty stuff

public class DayLabel extends JPanel {
    private LocalDate date;
    private JLabel dayNumberLabel;
    private JLabel summaryLabel;

    public DayLabel(LocalDate date){
        this.date = date;

        // Use BorderLayout to position date top-left and summary centered below
        setLayout(new BorderLayout(0, 5)); // 5px gap between components

        // Top-left: day number
        dayNumberLabel = new JLabel(String.valueOf(date.getDayOfMonth()));
        dayNumberLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dayNumberLabel.setVerticalAlignment(SwingConstants.TOP);
        dayNumberLabel.setFont(dayNumberLabel.getFont().deriveFont(Font.BOLD, 12f));
        add(dayNumberLabel, BorderLayout.NORTH);

        // Center: summary info (tasks, sleep)
        summaryLabel = new JLabel("");
        summaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        summaryLabel.setVerticalAlignment(SwingConstants.CENTER);
        summaryLabel.setFont(summaryLabel.getFont().deriveFont(10f));
        add(summaryLabel, BorderLayout.CENTER);

        // black border
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setOpaque(true);
        // white background
        setBackground(Color.WHITE);
    }

    public LocalDate getDate() {
        return date;
    }

    // Set the summary text (tasks and sleep info)
    public void setSummary(String summary) {
        summaryLabel.setText(summary);
    }

    // functions to highlight days (based on sleep / todays date)
    public void highlightToday() {
        setBackground(Color.YELLOW);
    }

    public void highlightBadSleep() {
        setBackground(Color.RED);
    }

    public void highlightGoodSleep() {
        setBackground(Color.green);
    }

}