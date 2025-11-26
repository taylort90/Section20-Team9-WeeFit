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
    private Runnable onSaveCallback;

    public DayEntryPanel(DayEntry dayEntry, UserDaysDao dao, String username, Runnable onSaveCallback) {
        this.dayEntry=dayEntry;
        this.dao=dao;
        this.username=username;
        this.onSaveCallback = onSaveCallback;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //Sleep times
        JPanel sleepPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sleepPanel.add(new JLabel("Slept at (hh:mm): "));
        JTextField sleepField = new JTextField(dayEntry.getTimeWentToSleep() != null ? dayEntry.getTimeWentToSleep().toString() : "", 5);
        sleepPanel.add(sleepField);
        sleepPanel.add(new JLabel("Woke up at (hh:mm): "));
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
            if(onSaveCallback!=null) {
                onSaveCallback.run();
            }
        });
        add(saveBtn);

    }

}
