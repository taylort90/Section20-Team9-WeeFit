package data;

import java.time.LocalDate;

public class Task {
    private String name;
    private LocalDate date;
    private boolean completed;

    public Task(String name, LocalDate date) {
        this.name =name;
        this.date= date;
        this.completed = false;
    }

}

