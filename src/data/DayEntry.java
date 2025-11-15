package data;
import java.time.Duration;
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
    //timeWentToSleep is the time from the night before, but stored in current day for ease of calculating sleep
    private LocalTime timeWentToSleep;
    private LocalTime timeWokeUp;
    //going to have to put these as strings to store, so maybe change to string instead of LocalTime

    private float weight;

    public DayEntry(LocalDate date) {
        this.date = date;
    }
    public LocalDate getDate () {
        return date;
    }

    public float getWeight() {
        return weight;
    }
    //Tasks
    public List<Task> getTasks() {
        return tasks;
    }

    public LocalTime getTimeWentToSleep() {
        return timeWentToSleep;
    }

    public LocalTime getTimeWokeUp() {
        return timeWokeUp;
    }

    public List<String> getGoals() {
        return goals;
    }

    public List<String> getWorkouts() {
        return workouts;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void setWeight(float weight) {
        this.weight=weight;
    }
    public void addGoal(String goal) {
        goals.add(goal);
    }

    //sleep+wake up sets
    public void setTimeWentToSleep(LocalTime time) {
        timeWentToSleep = time;
    }

    public void setTimeWokeUp(LocalTime time) {
        timeWokeUp = time;
    }

    public double getHoursSlept() {
        if (timeWentToSleep == null || timeWokeUp== null) return 0; //if user didn't record either one, return 0 (can't calc)
        LocalTime wentToSleep = timeWentToSleep;
        LocalTime wokeUp = timeWokeUp;
        if (wokeUp.isBefore(wentToSleep)) { //if recorded time of going to sleep was before midnight, need to make wake up time greater (interpreted as next day) to calc sleep time
            wokeUp=wokeUp.plusHours(24);//move to "next day" for calculating sleep
        }
        //can't use toHours because it would just give whole number. we want precision: need to use toMinutes()/60
        return Duration.between(wentToSleep, wokeUp).toMinutes()/60.0;
    }



}
