package ui;

import javax.swing.*;
import java.awt.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import data.GoalEntry;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GoalsPanel extends JPanel {

    //text fields
    private final JTextField nameField = new JTextField(10);
    private final JTextField ageField = new JTextField(5);
    private final JTextField heightField = new JTextField(5);
    private final JTextField weightField = new JTextField(5);

    // gender radio buttons
    private final JRadioButton femaleBtn = new JRadioButton("Female");
    private final JRadioButton maleBtn = new JRadioButton("Male");

    //radio buttons for goal
    private final JRadioButton skinnyBtn = new JRadioButton("Weight Loss");
    private final JRadioButton tonedBtn = new JRadioButton("Weight Maintenance");
    private final JRadioButton bulkBtn = new JRadioButton("Weight Gain");

    private final JLabel caloriesTarget = new JLabel("Calories: -");
    private final JLabel minutesTarget = new JLabel("Workout Minutes: -");
    private int calorieLow, calorieHigh;
    private int minutesLow, minutesHigh;

    private final JTextField caloriesToday = new JTextField(6);
    private final JTextField minutesToday = new JTextField(6);

    private final JLabel evaluationLabel = new JLabel("Evaluation: -");
    //private final JLabel streakLabel = new JLabel("Current Streak: 0 days");

// set streak to 0 for incrementing
    //private int streak = 0;

    public GoalsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));


        // inputs for target calorie range
        add(new JLabel("Name:"));
        add(nameField);
        add(Box.createVerticalStrut(8));

        JPanel heightRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        heightRow.add(new JLabel("Enter your height (in inches):"));
        heightRow.add(heightField);
        add(heightRow);

        JPanel ageRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ageRow.add(new JLabel("Enter your age:"));
        ageRow.add(ageField);
        add(ageRow);

        JPanel weightRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        weightRow.add(new JLabel("Enter your weight (in lbs):"));
        weightRow.add(weightField);
        add(weightRow);


        // selecting goal
        add(Box.createVerticalStrut(10));
        add(new JLabel("Select Goal:"));

        ButtonGroup group = new ButtonGroup();
        group.add(skinnyBtn);
        group.add(tonedBtn);
        group.add(bulkBtn);

        JPanel goalsRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        goalsRow.add(skinnyBtn);
        goalsRow.add(tonedBtn);
        goalsRow.add(bulkBtn);
        add(goalsRow);

        // track goal selection
        JButton setGoalBtn = new JButton("Set Goal");
        setGoalBtn.addActionListener(e -> setTargets());
        add(setGoalBtn);
        setGoalBtn.addActionListener(e -> saveGoalToJson());


        // entering calories target and minutes target
        add(Box.createVerticalStrut(10));
        add(caloriesTarget);
        add(minutesTarget);

        // enter daily checkin
        add(Box.createVerticalStrut(15));
        add(new JLabel("Daily Check-In:"));

        // first check in: total cals for the day
        JPanel eatenRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        eatenRow.add(new JLabel("Calories eaten today:"));
        eatenRow.add(caloriesToday);
        add(eatenRow);

        // second check in: total minutes worked out for the day
        JPanel minutesRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        minutesRow.add(new JLabel("Workout minutes today:"));
        minutesRow.add(minutesToday);
        add(minutesRow);

        // 'evaluate' for recommendation and encouragement
        JButton evalBtn = new JButton("Evaluate");
        evalBtn.addActionListener(e -> evaluate());
        add(evalBtn);

        add(Box.createVerticalStrut(10));
        add(evaluationLabel);


        add(Box.createVerticalStrut(8));
       // add(streakLabel);
    }

    private void setTargets() {
        try {
            int age = Integer.parseInt(ageField.getText());
            int weightLbs = Integer.parseInt(weightField.getText());
            int heightInches = Integer.parseInt(heightField.getText());
            String gender = maleBtn.isSelected() ? "Male" : "Female";

            // ---- Convert lbs to kg and inches to cm ----
            double weightKg = weightLbs * 0.453592;
            double heightCm = heightInches * 2.54;

            // ---- BMR Calculation (Mifflin–St Jeor) ----
            double BMR;
            if (gender.equals("Male")) {
                BMR = 10 * weightKg + 6.25 * heightCm - 5 * age + 5;
            } else {
                BMR = 10 * weightKg + 6.25 * heightCm - 5 * age - 161;
            }

            double low, high;
            int minMin, maxMin;

            if (skinnyBtn.isSelected()) {
                low = BMR * 1.15;
                high = BMR * 1.25;
                minMin = 20;
                maxMin = 40;

            } else if (tonedBtn.isSelected()) {
                low = BMR * 1.25;
                high = BMR * 1.35;
                minMin = 40;
                maxMin = 75;

            } else if (bulkBtn.isSelected()) {
                low = BMR * 1.50;
                high = BMR * 1.70;
                minMin = 20;
                maxMin = 60;

            } else {
                JOptionPane.showMessageDialog(this, "Select a goal type.");
                return;
            }

            caloriesTarget.setText(String.format("Calories: %d - %d", (int)low, (int)high));
            minutesTarget.setText(String.format("Workout Minutes: %d - %d", minMin, maxMin));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage());
        }
    }


    private void evaluate() {

        String user = nameField.getText().trim();
        if (user.isEmpty()) user = "User";

        if (!skinnyBtn.isSelected() && !tonedBtn.isSelected() && !bulkBtn.isSelected()) {
            JOptionPane.showMessageDialog(this, "Select a goal before evaluating.");
            return;
        }

        // Make sure user entered something
        String calText = caloriesToday.getText().trim();
        String minText = minutesToday.getText().trim();

        if (calText.isEmpty() && minText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter today's calories or minutes to evaluate.");
            return;
        }

        int cals = calText.isEmpty() ? 0 : Integer.parseInt(calText);
        int mins = minText.isEmpty() ? 0 : Integer.parseInt(minText);

        // -------- Parse calorie range from label text --------
        // Example: "Calories: 1800 - 2100"
        String[] calParts = caloriesTarget.getText().replace("Calories:", "").trim().split("-");
        int calLow = Integer.parseInt(calParts[0].trim());
        int calHigh = Integer.parseInt(calParts[1].trim());

        // -------- Parse minutes range from label text --------
        String[] minParts = minutesTarget.getText().replace("Workout Minutes:", "").trim().split("-");
        int minLow = Integer.parseInt(minParts[0].trim());
        int minHigh = Integer.parseInt(minParts[1].trim());

        // ----- Evaluate performance -----
        boolean hitCalories = (cals >= calLow && cals <= calHigh);
        boolean overCalories = cals > calHigh;
        boolean underCalories = cals < calLow;

        boolean hitMinutes = (mins >= minLow && mins <= minHigh);
        boolean overMinutes = mins > minHigh;
        boolean underMinutes = mins < minLow;

        String message;

        if ((hitCalories || overCalories) && (hitMinutes || overMinutes)) {
            message = "Amazing job, " + user + "! You hit or exceeded all your targets today!";

        } else if (hitCalories || hitMinutes) {
            message = "Nice work, " + user + "! You met part of your goal — keep pushing!";

        } else if (underCalories && underMinutes) {
            message = "Small steps, " + user + ". You didn’t reach your targets today, but tomorrow is a new chance.";

        } else {
            message = "You're trying, " + user + "! Stay consistent and you’ll get there.";
        }

        evaluationLabel.setText(message);

        // ----- Update streak -----
      /*  if (hitCalories || hitMinutes) {
            streak++;
        } else {
            streak = 0; // reset streak on a missed day
        }

        streakLabel.setText("Current Streak: " + streak + " days");*/
    }


    private void saveGoalToJson() {
        try {
            // Create GoalEntry from UI
            GoalEntry entry = new GoalEntry();
            entry.setUsername(nameField.getText());
            entry.setGender(maleBtn.isSelected() ? "Male" : "Female");
            entry.setAge(Integer.parseInt(ageField.getText()));
            entry.setWeightLbs(Integer.parseInt(weightField.getText()));
            entry.setHeightInches(Integer.parseInt(heightField.getText()));

            entry.setCalorieTargetLow((int)calorieLow);
            entry.setCalorieTargetHigh((int)calorieHigh);

            entry.setMinutesTargetLow(minutesLow);
            entry.setMinutesTargetHigh(minutesHigh);


            File file = new File("goalData.json");
            Gson gson = new Gson();
            List<GoalEntry> entries;

            // Load existing data or create new list
            if (file.exists()) {
                Reader reader = new FileReader(file);
                Type listType = new TypeToken<ArrayList<GoalEntry>>() {}.getType();
                entries = gson.fromJson(reader, listType);
                reader.close();
            } else {
                entries = new ArrayList<>();
            }

            // Add new entry
            entries.add(entry);

            // Write back to JSON
            Writer writer = new FileWriter(file);
            gson.toJson(entries, writer);
            writer.close();

            JOptionPane.showMessageDialog(this, "Goal saved successfully!");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save goal: " + ex.getMessage());
        }
    }

    public JTextField getAgeField() {
        return ageField;
    }

    public JTextField getWeightField() {
        return weightField;
    }

    public JRadioButton getFemaleBtn() {
        return femaleBtn;
    }

    public JRadioButton getMaleBtn() {
        return maleBtn;
    }
}