package data;

import java.time.LocalDate;

public class Task {
    private String taskName;
    private LocalDate date;
    private boolean completed;

    public Task(String name, LocalDate date) {
        this.taskName =name;
        this.date= date;
        this.completed = false;
    }

    public String getTaskName() {
        return taskName;
    }
    public LocalDate getDate() {
        return date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTaskName(String name) {
        taskName=name;
    }

    public void setDate(LocalDate date) {
        this.date=date;
    }

    public void setCompleted(boolean completed) {
        this.completed=completed;
    }

    public void toggleCompleted() {
        completed = !completed;
    }

}

