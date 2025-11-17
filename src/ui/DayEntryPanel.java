package ui;
import data.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;


//Created by: Anthony
//This is the panel that opens once the user clicks on a day (on the monthly calendar view)

public class DayEntryPanel extends JPanel {
    private DayEntry dayEntry;
    private UserDaysDao dao;
    private String username;

    public DayEntryPanel(DayEntry dayEntry, UserDaysDao dao, String username) {
        this.dayEntry=dayEntry;
        this.dao=dao;
        this.username=username;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //Sleep times
        JPanel sleepPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sleepPanel.add(new JLabel("Time went to sleep: "));
        JTextField sleepField = new JTextField(dayEntry.getTimeWentToSleep() != null ? dayEntry.getTimeWentToSleep().toString() : "", 5);
        sleepPanel.add(sleepField);
        sleepPanel.add(new JLabel("Time woke up :"));
        JTextField wakeUpField = new JTextField(dayEntry.getTimeWokeUp() != null ? dayEntry.getTimeWokeUp().toString() : "", 5);
        sleepPanel.add(wakeUpField);

        add(sleepPanel);

        //tasks
        TaskPanel taskPanel = new TaskPanel(dayEntry, dao, username);
        JScrollPane taskScrollPane = new JScrollPane(taskPanel);
        add(taskScrollPane);

        //Save Button
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e-> {
            try {
                //save sleep times
                if (!sleepField.getText().isEmpty()) {
                    dayEntry.setTimeWentToSleep(LocalTime.parse(sleepField.getText()));
                }
                if (!wakeUpField.getText().isEmpty()) {
                    dayEntry.setTimeWokeUp(LocalTime.parse(wakeUpField.getText()));
                }
                //save to dao
                dao.saveDay(username, dayEntry);
                JOptionPane.showMessageDialog(this, "Saved day");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        add(saveBtn);

    }

}
