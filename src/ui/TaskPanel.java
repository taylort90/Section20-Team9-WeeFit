package ui;

import data.DayEntry;
import data.Task;
import data.UserDataDao;

import javax.swing.*;
import java.awt.*;



//created by: Anthony
//Purpose: a Panel that holds tasks for the day (user can check them off) and an option to add new tasks
//Used in: menuPanel, dayEntryPanel

//NOTE: NEED TO ADD DELETE TASK
public class TaskPanel extends JPanel {

    public TaskPanel(DayEntry dayEntry, UserDataDao dao, String username ) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Tasks"));

        for (Task task : dayEntry.getTasks()) {
            JCheckBox checkBox = new JCheckBox(task.getTaskName(), task.isCompleted());
            checkBox.addActionListener(e-> task.setCompleted(checkBox.isSelected()));
            add(checkBox);
        }

        //add task
        JTextField newTaskField = new JTextField(15);
        JButton addTaskBtn = new JButton("Add task");
        addTaskBtn.addActionListener((e) -> {
            String taskName = newTaskField.getText().trim();
            if(!taskName.isEmpty()) {
                Task newTask= new Task(taskName, dayEntry.getDate());
                dayEntry.addTask(newTask);

                JCheckBox checkBox = new JCheckBox(newTask.getTaskName());
                checkBox.addActionListener(ev -> newTask.setCompleted(checkBox.isSelected()));
                add(checkBox);

                newTaskField.setText("");

                //write to file
                dao.saveDay(username, dayEntry);
                revalidate();
                repaint();
            }
        });
        JPanel addTaskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addTaskPanel.add(newTaskField);
        addTaskPanel.add(addTaskBtn);
        add(addTaskPanel);

    }


}
