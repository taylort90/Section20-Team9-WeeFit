package app;
import data.UserDaysDao;
//can eventually chagne ui's to just ui.menu (since menu will have the others)
import ui.MenuPanel;
import ui.WeeFitLoginPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Main {
    private final JFrame frame;
    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    private String currentUsername;
    private final Set<String> addedPanels = new HashSet<>();
    private final UserDaysDao userDaysDao;

    public Main() {
        //creating a frame for Weefit
        frame = new JFrame("Weefit: A Well-Being App!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        //holds all user's dayEntry's seperated by user
        userDaysDao = new UserDaysDao();
        userDaysDao.loadFromFile();

        //cardPanel for switching from menu, to calendar display/panel, goals, etc
        cardPanel = new JPanel(new CardLayout());

        //add cardPanel to frame
        frame.add(cardPanel);

        //LOGIN PANEL:
        WeeFitLoginPanel loginPanel = new WeeFitLoginPanel(this);
        cardPanel.add(loginPanel, "LOGIN");


        //MENU PANEL: to show all buttons (so user can choose what to do)
        //MenuPanel menuPanel = new MenuPanel(userDataDao, currentUsername, this); //stacked buttons

        //cardPanel.add(menuPanel, "MENU");

        //needed to use cl.show and show different "pages"
        cardLayout = (CardLayout) cardPanel.getLayout();

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

    public void addPanel(JPanel panel, String name) {
        if (!addedPanels.contains(name)) {
            cardPanel.add(panel, name);
            addedPanels.add(name);
        }
    }

    public void setCurrentUsername(String username) {
        this.currentUsername= username;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public boolean isPanelAdded(String name) {
        return addedPanels.contains(name);
    }


    public void onLogin(String username) {
        this.currentUsername=username;
        MenuPanel menuPanel = new MenuPanel(userDaysDao, currentUsername, this); //stacked buttons
        addPanel(menuPanel, "MENU");

        showPanel("MENU", "Menu");
    }


}
