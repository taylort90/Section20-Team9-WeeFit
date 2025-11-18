package data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//Created by: Tony
//Purpose: Reads/writes all of the data from userDays.json. Can give DayEntrys, and add days to the users list of days.

public class UserDaysDao {

    //using a hashmap to store all the days for a user
    private Map<String,Map<String, DayEntry>> users = new HashMap<>();
    //this will be the file we save all user data to. NOTE: one file will do for now, but in long run we would want one file per day (if we have time we can implement that)
    private static final Path FILE = Paths.get("userDays.json");
    //use gson to convert objects to strings to store in the userDays.json
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    //


    //loading all the user's data (to fill calendar)
    public void loadFromFile() {
        try {
            if (Files.exists(FILE)) {
                //read the json file
                String json = Files.readString(FILE);
                //loaded is UserDataDao that holds all days gson got from the userDays.json
                UserDaysDao loaded = gson.fromJson(json, UserDaysDao.class);
                //storing all the days by using gson
                this.users = loaded.users;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //saving data to file
    public void saveToFile() {
        try {
            String json = gson.toJson(this);
            Files.writeString(FILE, json);
        } catch(Exception e) { //error handling
            e.printStackTrace();
        }
    }


    //will return the dayEntry or create one if none exists
    public DayEntry getDay(String username, LocalDate date) {
        Map<String, DayEntry> days;
        if (users.get(username)== null) { //if days doesn't exist, create it and add new DayEntry
            days = new HashMap<>();
            DayEntry newDay= new DayEntry(date);
            days.put(date.toString(), newDay);
            users.put(username, days);
        } else { //if days does, we want to return the DayEntry inside user's days
            days = users.get(username);
            if (days.get(date.toString())==null) {
                days.put(date.toString(),new DayEntry(date));
            }
        }
        return days.get(date.toString());
    }

    public void saveDay(String username, DayEntry day) {
        Map<String, DayEntry> days; //going to put days into user's days, need to see if user already has collection of days
        if (users.get(username)==null) {
            days= new HashMap<>();
        } else {//get user's days
            days = users.get(username);
        }
        //put day in user's days
        days.put(day.getDate(), day);
        users.put(username, days);
        //write everything back to file
        saveToFile();
    }

}
