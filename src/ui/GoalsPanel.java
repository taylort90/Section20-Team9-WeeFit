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

    //sum gender radio buttons
    private final JRadioButton femaleBtn = new JRadioButton("Female");
    private final JRadioButton maleBtn = new JRadioButton("Male");

    //radio buttons for goal
    private final JRadioButton skinnyBtn = new JRadioButton("Weight Loss");
    private final JRadioButton tonedBtn = new JRadioButton("Weight Maintenance");
    private final JRadioButton bulkBtn = new JRadioButton("Weight Gain");

    //text fields
    private final JTextField ageField = new JTextField(10);
    private final JTextField heightField = new JTextField(10);
    private final JTextField weightField = new JTextField(10);

    private final JLabel caloriesTarget = new JLabel("Calories: -");
    private final JLabel minutesTarget = new JLabel("Workout Minutes: -");
    private int calorieLow, calorieHigh;
    private int minutesLow, minutesHigh;

    private final JTextField caloriesToday = new JTextField(10);
    private final JTextField minutesToday = new JTextField(10);

    private final JLabel evaluationLabel = new JLabel("Evaluation: -");
    //private final JLabel streakLabel = new JLabel("Current Streak: 0 days");

// set streak to 0 for incrementing
    //private int streak = 0;

    public String username;
    public GoalsPanel(String username) {
        this.username = username;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        //INPUTS FOR PERSONAL INFO
        JPanel personalPanel = createSectionPanel("Personal Information");

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        genderPanel.setOpaque(false);
        genderPanel.add(new JLabel("Gender:"));
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(femaleBtn);
        genderGroup.add(maleBtn);
        maleBtn.setSelected(true);
        genderPanel.add(femaleBtn);
        genderPanel.add(maleBtn);
        personalPanel.add(genderPanel);

        //height
        JPanel heightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        heightPanel.setOpaque(false);
        heightPanel.add(new JLabel("Height (inches):"));
        heightPanel.add(heightField);
        personalPanel.add(heightPanel);

        //age
        JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        agePanel.setOpaque(false);
        agePanel.add(new JLabel("Age:"));
        agePanel.add(ageField);
        personalPanel.add(agePanel);

        //weight
        JPanel weightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        weightPanel.setOpaque(false);
        weightPanel.add(new JLabel("Weight (lbs):"));
        weightPanel.add(weightField);
        personalPanel.add(weightPanel);


        add(personalPanel);
        add(Box.createVerticalStrut(15));

        // INPUTS FOR SELECTING GOAL

        JPanel goalPanel = createSectionPanel("Select Your Goal");

        JPanel goalButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        goalButtonsPanel.setOpaque(false);
        ButtonGroup goalGroup = new ButtonGroup();
        goalGroup.add(skinnyBtn);
        goalGroup.add(tonedBtn);
        goalGroup.add(bulkBtn);
        goalButtonsPanel.add(skinnyBtn);
        goalButtonsPanel.add(tonedBtn);
        goalButtonsPanel.add(bulkBtn);
        goalPanel.add(goalButtonsPanel);

        // track goal selection
        JButton setGoalBtn = new JButton("Set Goal");
        setGoalBtn.setPreferredSize(new Dimension(120, 35));
        setGoalBtn.addActionListener(e -> {
                    setTargets();
                    saveGoalToJson();
                });

        JPanel setGoalPanelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setGoalPanelWrapper.setOpaque(false);
        setGoalPanelWrapper.add(setGoalBtn);
        goalPanel.add(setGoalPanelWrapper);

        add(goalPanel);
        add(Box.createVerticalStrut(15));

        //THE TARGET DISPLAY
        JPanel targetsPanel = createSectionPanel("Your Targets");

        caloriesTarget.setFont(caloriesTarget.getFont().deriveFont(Font.BOLD, 13f));
        minutesTarget.setFont(minutesTarget.getFont().deriveFont(Font.BOLD, 13f));

        JPanel calDisplay = new JPanel(new FlowLayout(FlowLayout.CENTER));
        calDisplay.setOpaque(false);
        calDisplay.add(caloriesTarget);
        targetsPanel.add(calDisplay);

        JPanel minDisplay = new JPanel(new FlowLayout(FlowLayout.CENTER));
        minDisplay.setOpaque(false);
        minDisplay.add(minutesTarget);
        targetsPanel.add(minDisplay);

        add(targetsPanel);
        add(Box.createVerticalStrut(15));

        //DAILY CHECKIN
        JPanel checkInPanel = createSectionPanel("Daily Check-In");

        JPanel calorieCheckPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        calorieCheckPanel.setOpaque(false);
        calorieCheckPanel.add(new JLabel("Calories eaten today:"));
        calorieCheckPanel.add(caloriesToday);
        checkInPanel.add(calorieCheckPanel);

        JPanel minuteCheckPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        minuteCheckPanel.setOpaque(false);
        minuteCheckPanel.add(new JLabel("Workout minutes today:"));
        minuteCheckPanel.add(minutesToday);
        checkInPanel.add(minuteCheckPanel);

        JButton evalBtn = new JButton("Evaluate");
        evalBtn.setPreferredSize(new Dimension(120, 35));
        evalBtn.addActionListener(e -> evaluate());
        JPanel evalPanelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        evalPanelWrapper.setOpaque(false);
        evalPanelWrapper.add(evalBtn);
        checkInPanel.add(evalPanelWrapper);

        add(checkInPanel);
        add(Box.createVerticalStrut(15));

        //RESULTS AND WHATNOT

        JPanel evalResultPanel = createSectionPanel("Evaluation Result");
        evaluationLabel.setFont(evaluationLabel.getFont().deriveFont(Font.BOLD, 12f));
        evaluationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel evalDisplayPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        evalDisplayPanel.setOpaque(false);
        evalDisplayPanel.add(evaluationLabel);
        evalResultPanel.add(evalDisplayPanel);
        add(evalResultPanel);

        add(Box.createVerticalGlue());
    }

    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setOpaque(false);
        return panel;
    }

    private void setTargets() {
        try {
            int age = Integer.parseInt(ageField.getText());
            int weightLbs = Integer.parseInt(weightField.getText());
            int heightInches = Integer.parseInt(heightField.getText());
            String gender = maleBtn.isSelected() ? "Male" : "Female";

            double weightKg = weightLbs * 0.453592;
            double heightCm = heightInches * 2.54;

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

            calorieLow = (int) low;
            calorieHigh = (int) high;
            minutesLow = minMin;
            minutesHigh = maxMin;

            caloriesTarget.setText(String.format("Calories: %d - %d", calorieLow, calorieHigh));
            minutesTarget.setText(String.format("Workout Minutes: %d - %d", minutesLow, minutesHigh));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage());
        }
    }

    private void evaluate() {
        if (!skinnyBtn.isSelected() && !tonedBtn.isSelected() && !bulkBtn.isSelected()) {
            JOptionPane.showMessageDialog(this, "Select a goal before evaluating.");
            return;
        }

        String calText = caloriesToday.getText().trim();
        String minText = minutesToday.getText().trim();

        if (calText.isEmpty() && minText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter today's calories or minutes to evaluate.");
            return;
        }

        int cals = calText.isEmpty() ? 0 : Integer.parseInt(calText);
        int mins = minText.isEmpty() ? 0 : Integer.parseInt(minText);

        boolean hitCalories = (cals >= calorieLow && cals <= calorieHigh);
        boolean overCalories = cals > calorieHigh;

        boolean hitMinutes = (mins >= minutesLow && mins <= minutesHigh);
        boolean overMinutes = mins > minutesHigh;

        String message;

        if ((hitCalories || overCalories) && (hitMinutes || overMinutes)) {
            message = "Amazing job! You hit or exceeded all your targets today!";

        } else if (hitCalories || hitMinutes) {
            message = "Nice work! You met part of your goal — keep pushing!";

        } else {
            message = "Small steps count. You didn't reach your targets today, but tomorrow is a new chance.";
        }

        evaluationLabel.setText(message);
    }

    private void saveGoalToJson() {
        try {
            GoalEntry entry = new GoalEntry();
            entry.setUsername(username);
            entry.setGender(maleBtn.isSelected() ? "Male" : "Female");
            entry.setAge(Integer.parseInt(ageField.getText()));
            entry.setWeightLbs(Integer.parseInt(weightField.getText()));
            entry.setHeightInches(Integer.parseInt(heightField.getText()));

            entry.setCalorieTargetLow(calorieLow);
            entry.setCalorieTargetHigh(calorieHigh);

            entry.setMinutesTargetLow(minutesLow);
            entry.setMinutesTargetHigh(minutesHigh);

            File file = new File("goalData.json");
            Gson gson = new Gson();
            List<GoalEntry> entries;

            if (file.exists()) {
                Reader reader = new FileReader(file);
                Type listType = new TypeToken<ArrayList<GoalEntry>>() {}.getType();
                entries = gson.fromJson(reader, listType);
                reader.close();
            } else {
                entries = new ArrayList<>();
            }

            entries.add(entry);

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