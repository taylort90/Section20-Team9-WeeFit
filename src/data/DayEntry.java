package data;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;

public class DayEntry {
    private LocalDate date; //to store the date for all this data
    //going to store the tasks user wants per day,
    private List<Task> tasks= new ArrayList<>();
    //this will differ, and actually be workouts that user has done
    private List<String> workouts = new ArrayList<>();
    //not sure if i will implement this right away, but have it for now
    //might change this out of DayEntry
    private List<String> goals = new ArrayList<>();
    //going to use sleep times to calculate how much sleep user got
    //idea: from calendar view, show red for days (or maybe weeks) where user didn't get enough sleep
    private LocalTime timeSlept;
    private LocalTime timeWokeUp;
    //going to have to put these as strings to store, so maybe change to string instead of LocalTime

    public DayEntry(LocalDate date) {
        this.date = date;
    }
    public LocalDate getDate () {
        return date;
    }

    //Tasks
    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addGoal(String goal) {
        goals.add(goal);
    }


}
