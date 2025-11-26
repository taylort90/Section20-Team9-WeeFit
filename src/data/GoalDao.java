package data;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GoalDao {

    private static final String FILE_PATH = "goalData.json";

    private Map<String, GoalEntry> goalsMap = new HashMap<>();

    private final Gson gson;

    public GoalDao() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        loadFromFile();
    }

    // Load JSON into goalsMap
    public void loadFromFile() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            saveToFile(); // create blank file
            return;
        }

        try (Reader reader = new FileReader(FILE_PATH)) {

            Type mapType = new TypeToken<Map<String, GoalEntry>>(){}.getType();
            Map<String, GoalEntry> loaded = gson.fromJson(reader, mapType);

            if (loaded != null) {
                goalsMap = loaded;
            }

        } catch (Exception e) {
            System.out.println("Error loading goalData.json: " + e.getMessage());
        }
    }

    // Save goalsMap to JSON
    public void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(goalsMap, writer);
        } catch (Exception e) {
            System.out.println("Error saving goalData.json: " + e.getMessage());
        }
    }

    // Add new user entry
    public void addGoalEntry(GoalEntry entry) {
        goalsMap.put(entry.getUsername(), entry);
        saveToFile();
    }

    // Get user entry
    public GoalEntry getGoalEntry(String username) {
        return goalsMap.get(username);
    }

    // Update existing entry
    public void updateGoalEntry(GoalEntry entry) {
        goalsMap.put(entry.getUsername(), entry);
        saveToFile();
    }

    // Check if user exists
    public boolean hasEntry(String username) {
        return goalsMap.containsKey(username);
    }
}
