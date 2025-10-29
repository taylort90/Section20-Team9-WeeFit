import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        //creating a frame for app (to test calendar for now)
        JFrame frame = new JFrame("App menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        //creating button to show calendar
        JButton CalendarBtn = new JButton("Calendar");
        //putting button on frame
        frame.add(CalendarBtn, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new CardLayout());
        frame.add(mainPanel, BorderLayout.CENTER);

        CalendarPanel calendar = new CalendarPanel(2025, 10);
        mainPanel.add(calendar, "Calendar");


        //to show calendar when clicked
        CalendarBtn.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "Calendar");
        });

        frame.setVisible(true);

    }


}
