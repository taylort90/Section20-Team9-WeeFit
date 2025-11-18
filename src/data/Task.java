package data;

import java.time.LocalDate;


//Created by: Tony
//tasks are used in the DayEntry.java
public class Task {
    private String taskName;
    private String date;
    private boolean completed;

    public Task(String name, String date) {
        this.taskName =name;
        this.date= date;
        this.completed = false;
    }

    public String getTaskName() {
        return taskName;
    }
    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTaskName(String name) {
        taskName=name;
    }

    public void setDate(String date) {
        this.date=date;
    }

    public void setCompleted(boolean completed) {
        this.completed=completed;
    }

    public void toggleCompleted() {
        completed = !completed;
    }

}

