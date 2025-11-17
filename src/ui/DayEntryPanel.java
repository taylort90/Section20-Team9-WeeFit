package ui;
import data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;


public class DayEntryPanel extends JPanel {
    private DayEntry dayEntry;
    private UserDataDao dao;
    private String username;

    public DayEntryPanel(DayEntry dayEntry, UserDataDao dao, String username) {
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
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        taskPanel.setBorder(BorderFactory.createTitledBorder("Tasks"));

        //show tasks with check box //!!!!!!!!!!!!!!!!!make it so box changes color when completed
        for (Task task : dayEntry.getTasks()) {
            JCheckBox checkBox = new JCheckBox(task.getTaskName(), task.isCompleted());
            checkBox.addActionListener(e -> task.setCompleted(checkBox.isSelected()));
            taskPanel.add(checkBox);
        }


        // add task

        JTextField newTaskField = new JTextField(15);
        JButton addTaskBtn = new JButton("Add task");
        addTaskBtn.addActionListener((ActionEvent e) -> {
            String taskName = newTaskField.getText().trim();
            if(!taskName.isEmpty()) {
                Task newTask = new Task(taskName, dayEntry.getDate());
                dayEntry.addTask(newTask);

                JCheckBox checkBox = new JCheckBox(newTask.getTaskName());
                checkBox.addActionListener(ev -> newTask.setCompleted(checkBox.isSelected()));
                taskPanel.add(checkBox);
                taskPanel.revalidate();
                newTaskField.setText("");

                //write to file
                dao.saveDay(username, dayEntry);
            }
        });

        JPanel addTaskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addTaskPanel.add(newTaskField);
        addTaskPanel.add(addTaskBtn);
        taskPanel.add(addTaskPanel);

        add(taskPanel);

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
