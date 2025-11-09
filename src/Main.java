import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        //creating a frame for app (to test calendar for now)
        JFrame frame = new JFrame("App menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());


        //menu to show all buttons (so user can choose WHAT to do)
        JPanel menuPanel = new JPanel(new GridLayout(3, 1, 10, 10)); //stacked buttons
        frame.add(menuPanel, BorderLayout.CENTER);

        //BUTTONSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSs
        //calendar button
        JButton CalendarBtn = new JButton("Calendar");
        //goals button
        JButton GoalsBtn = new JButton("Goals");


        //adding buttons to menuPanel
        menuPanel.add(CalendarBtn);
        menuPanel.add(GoalsBtn);

        //cardPanel allows us to switch from menu, to calendar display/panel, goals, etc
        JPanel cardPanel = new JPanel(new CardLayout());
        cardPanel.add(menuPanel, "MENU");


        //add cardPanel to frame
        frame.add(cardPanel);

        //needed to use cl.show and show different "pages"
        CardLayout cl = (CardLayout) cardPanel.getLayout();

        //change this to open the current month of user's computer, for now use 11/2025 for testing purposes
        CalendarPanel calendar = new CalendarPanel(2025, 11);
        JButton backToMenuBtn = new JButton("<- Back");
        JPanel calendarScreen = new JPanel(new BorderLayout());
        calendarScreen.add(backToMenuBtn, BorderLayout.NORTH);
        calendarScreen.add(calendar, BorderLayout.CENTER);
        cardPanel.add(calendarScreen, "Calendar");

        //show calendar when CalendarBtn clicked
        CalendarBtn.addActionListener(e -> {cl.show(cardPanel, "Calendar");});
        //backToMenu button goes back to menu (
        backToMenuBtn.addActionListener(e -> cl.show(cardPanel, "MENU"));

        cl.show(cardPanel, "MENU");
        frame.setVisible(true);

    }


}
