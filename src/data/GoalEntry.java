package data;

public class GoalEntry {

    private String username;
    private String gender;
    private int age;
    private int weight;
    private String goalType;
    private int weightLbs;
    private int heightInches;

    private int calorieTargetLow;
    private int calorieTargetHigh;
    private int minutesTargetLow;
    private int minutesTargetHigh;

    private int caloriesToday;
    private int minutesToday;

   // private int streak;

    private String evaluation;

    public GoalEntry() {
     //   this.streak = 0;
        this.evaluation = "";
    }

    // ---------- FIXED USERNAME GETTER + SETTER ----------
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // height getter setter
    public int getHeightInches() { return heightInches; }
    public void setHeightInches(int heightInches) { this.heightInches = heightInches; }

    // gender getter setter
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    // age getter setter
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    // weight getter setter
    public int getWeight() { return weight; }
    public void setWeightLbs(int weightLbs) { this.weightLbs = weight; }

    // ---------- GOAL TYPE ----------
    public String getGoalType() {
        return goalType;
    }
    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    // ---------- CALORIE TARGETS ----------
    public int getCalorieTargetLow() { return calorieTargetLow; }
    public void setCalorieTargetLow(int calorieTargetLow) { this.calorieTargetLow = calorieTargetLow; }

    public int getCalorieTargetHigh() { return calorieTargetHigh; }
    public void setCalorieTargetHigh(int calorieTargetHigh) { this.calorieTargetHigh = calorieTargetHigh; }

    // ---------- MINUTES TARGETS ----------
    public int getMinutesTargetLow() { return minutesTargetLow; }
    public void setMinutesTargetLow(int minutesTargetLow) { this.minutesTargetLow = minutesTargetLow; }

    public int getMinutesTargetHigh() { return minutesTargetHigh; }
    public void setMinutesTargetHigh(int minutesTargetHigh) { this.minutesTargetHigh = minutesTargetHigh; }

    // ---------- DAILY VALUES ----------
    public int getCaloriesToday() { return caloriesToday; }
    public void setCaloriesToday(int caloriesToday) { this.caloriesToday = caloriesToday; }

    public int getMinutesToday() { return minutesToday; }
    public void setMinutesToday(int minutesToday) { this.minutesToday = minutesToday; }

    // ---------- STREAK ----------
   // public int getStreak() { return streak; }
    //public void setStreak(int streak) { this.streak = streak; }

    // ---------- EVALUATION ----------
    public String getEvaluation() { return evaluation; }
    public void setEvaluation(String evaluation) { this.evaluation = evaluation; }
}
