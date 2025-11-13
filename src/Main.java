import javax.swing.*;
import java.awt.*;
import java.time.*;

public class Main {
    private final JFrame frame;
    private final JPanel cardPanel;
    private final CardLayout cardLayout;


    public Main() {
        //creating a frame for Weefit
        frame = new JFrame("Weefit: A Well-Being App!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());


        //cardPanel for switching from menu, to calendar display/panel, goals, etc
        cardPanel = new JPanel(new CardLayout());

        //add cardPanel to frame
        frame.add(cardPanel);
        //we want to first add the login panel, then change to menu panel ONCE USER LOGS IN
        //UPDATE CODE TO MATCH PREV COMMENT
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //also, change the frame to something other than App menu (since it will hold login first, then menu) just to make it more clear

        //LOGIN PANEL:
        WeeFitLoginPanel loginPanel = new WeeFitLoginPanel(this);
        cardPanel.add(loginPanel, "LOGIN");


        //MENU PANEL: to show all buttons (so user can choose what to do)
        JPanel menuPanel = new JPanel(new GridLayout(3, 1, 10, 10)); //stacked buttons
        //BUTTONSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSs
        //calendar button
        JButton CalendarBtn = new JButton("Calendar");
        //goals button
        JButton GoalsBtn = new JButton("Goals");
        menuPanel.add(CalendarBtn);
        menuPanel.add(GoalsBtn);

        cardPanel.add(menuPanel, "MENU");


        //needed to use cl.show and show different "pages"
        cardLayout = (CardLayout) cardPanel.getLayout();

        // ====CALENDAR PANEL====
        LocalDate today = LocalDate.now();
        int calendarMonth = today.getMonthValue();
        int calendarYear = today.getYear();
        CalendarPanel calendar = new CalendarPanel(calendarYear, calendarMonth);
        JButton backToMenuBtn = new JButton("<- Back");
        JPanel calendarScreen = new JPanel(new BorderLayout());
        calendarScreen.add(backToMenuBtn, BorderLayout.NORTH);
        calendarScreen.add(calendar, BorderLayout.CENTER);
        cardPanel.add(calendarScreen, "Calendar");

        //BUTTON FUNCTIONALITY
        //show calendar when CalendarBtn clicked
        CalendarBtn.addActionListener(e -> {showPanel("Calendar", "Weefit: Calendar");});
        //backToMenu button goes back to menu (
        backToMenuBtn.addActionListener(e -> showPanel("MENU", "MENU"));

        //show login first
        showPanel("LOGIN", "WeeFit -- Login");
        //cardLayout.show(cardPanel, "MENU");
        frame.setVisible(true);

    }

    public void showPanel(String name, String title ) {
        cardLayout.show(cardPanel, name);
        frame.setTitle(title);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }


}
