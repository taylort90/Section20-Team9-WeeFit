import java.util.HashMap;
import java.util.Scanner;

public class login {
    private static final HashMap<String, String> users = new HashMap<>();

    public static void main(String[] args) {
        // Pre-register some users
        try (Scanner input = new Scanner(System.in)) {
            // Pre-register some users
            users.put("ava", "password123");
            users.put("quinn", "securepass");
            users.put("admin", "admin123");
            
            System.out.println("Welcome to WeeFit - your personalized goal tracker. Please Login here:");
            System.out.print("Enter username: ");
            String username = input.nextLine();
            
            System.out.print("Enter password: ");
            String password = input.nextLine();
            
            if (authenticate(username, password)) {
                System.out.println("Login successful! Welcome, " + username + "!");
            } else {
                System.out.println("It seems those credentials are not in our system. Please try again or check your login input. If you do not have an account, create one here:");
            }
        }
    }

    // Check if username and password match
    private static boolean authenticate(String username, String password) {
        if (users.containsKey(username)) {
            return users.get(username).equals(password);
        }
        return false;
    }
}