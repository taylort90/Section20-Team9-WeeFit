import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class loginUI {
    private static final HashMap<String, String> users = new HashMap<>();

    public static void main(String[] args) {
        // Pre-register some users
        users.put("ava", "password123");
        users.put("quinn", "securepass");
        users.put("admin", "admin123");

        // Create the frame (window)
        JFrame frame = new JFrame("WeeFit Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null); // center the window

        // Create a panel to hold components
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(230, 245, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components
        JLabel titleLabel = new JLabel("Welcome to WeeFit!", JLabel.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 18));
        titleLabel.setForeground(new Color(30, 60, 90));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Create Account");

        JLabel messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);

        // Layout setup
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(userLabel, gbc);
        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(passLabel, gbc);
        gbc.gridx = 1;
        panel.add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(loginButton, gbc);
        gbc.gridx = 1;
        panel.add(signupButton, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        // Button actions
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (authenticate(username, password)) {
                messageLabel.setForeground(new Color(0, 128, 0));
                messageLabel.setText("Login successful! Welcome, " + username + "!");
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Invalid credentials. Please try again.");
            }
        });

        signupButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (users.containsKey(username)) {
                messageLabel.setText("Username already exists.");
            } else if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Enter a valid username and password.");
            } else {
                users.put(username, password);
                messageLabel.setForeground(new Color(0, 128, 0));
                messageLabel.setText("Account created! You can log in now.");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}