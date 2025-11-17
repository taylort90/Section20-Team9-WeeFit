import javax.swing.*;
import java.awt.*;

public class GoalsPanel extends JPanel {

// buttons for panel
    private final JTextField nameField = new JTextField(15);
    private final JTextField heightField = new JTextField(5);

    private final JRadioButton skinnyBtn = new JRadioButton("Skinny");
    private final JRadioButton tonedBtn = new JRadioButton("Toned");
    private final JRadioButton bulkBtn = new JRadioButton("Bulk");

    private final JLabel caloriesTarget = new JLabel("Calories: -");
    private final JLabel minutesTarget = new JLabel("Workout Minutes: -");

    private final JTextField caloriesToday = new JTextField(6);
    private final JTextField minutesToday = new JTextField(6);

    private final JLabel evaluationLabel = new JLabel("Evaluation: -");
    private final JLabel streakLabel = new JLabel("Current Streak: 0 days");

// set streak to 0 for incrementing
    private int streak = 0;

    public GoalsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(new JLabel("Name:"));
        add(nameField);
        add(Box.createVerticalStrut(8));

        JPanel heightRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        heightRow.add(new JLabel("Height (inches):"));
        heightRow.add(heightField);
        add(heightRow);

    
    // selecting goal
        add(Box.createVerticalStrut(10));
        add(new JLabel("Select Goal:"));

        ButtonGroup group = new ButtonGroup();
        group.add(skinnyBtn); group.add(tonedBtn); group.add(bulkBtn);

        JPanel goalsRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        goalsRow.add(skinnyBtn);
        goalsRow.add(tonedBtn);
        goalsRow.add(bulkBtn);
        add(goalsRow);

    // track goal selection
        JButton setGoalBtn = new JButton("Set Goal");
        setGoalBtn.addActionListener(e -> setTargets());
        add(setGoalBtn);


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
        add(streakLabel);
    }

    private void setTargets() {
        /*String hText = heightField.getText().trim();
        int h;
        try {
            h = Integer.parseInt(hText);
            if (h <= 0) throw new NumberFormatException();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid height in inches.");
            return;
        }*/

    // cals based on goal 
    // try to evaluate based on different peoples heights
        int h = 60; // default height for now
        if (skinnyBtn.isSelected()) {
            caloriesTarget.setText("Calories: " + (h*11) + " - " + (h*13));
            minutesTarget.setText("Workout Minutes: 20 - 40");
        } else if (tonedBtn.isSelected()) {
            caloriesTarget.setText("Calories: " + (h*12) + " - " + (h*14));
            minutesTarget.setText("Workout Minutes: 40 - 75");
        } else if (bulkBtn.isSelected()) {
            caloriesTarget.setText("Calories: " + (h*14) + " - " + (h*16));
            minutesTarget.setText("Workout Minutes: 20 - 60");
        } else {
            JOptionPane.showMessageDialog(this, "Select a goal.");
        }
    }

    private void evaluate() {
        String user = nameField.getText().trim();
        if (user.isEmpty()) user = "User";

        boolean goalSelected = skinnyBtn.isSelected() || tonedBtn.isSelected() || bulkBtn.isSelected();
        if (!goalSelected) {
            JOptionPane.showMessageDialog(this, "Select a goal before evaluating.");
            return;
        }

    // very simple check: if user entered either calories or minutes, consider it a positive day
        String calText = caloriesToday.getText().trim();
        String minText = minutesToday.getText().trim();

    // must enter to evaluate, error handling message
        if (calText.isEmpty() && minText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter today's calories or minutes to evaluate.");
            return;
        }

    // Update user with a  small evaluation message
    // Maybe we can randomize a handful of messages?
        String goalName = skinnyBtn.isSelected() ? "skinny" : tonedBtn.isSelected() ? "toned" : "bulk";
        evaluationLabel.setText("Great job, " + user + "! Moving toward your " + goalName + " goal.");

    // where streaks are incremented
        streak++;
        streakLabel.setText("Current Streak: " + streak + " days");
    }
}
