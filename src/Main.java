import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Create a new JFrame (window)
        JFrame frame = new JFrame("brick");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create a new JPanel (container)
        PlayGround panel = new PlayGround();
        frame.add(panel);


        frame.addWindowListener(new java.awt.event.WindowAdapter() {


        });

        // Enable keyboard input for the panel
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.addKeyListener(panel); // Add KeyListener to the panel

        // Start the timer for panel movement
        Timer timer = new Timer(1, e -> panel.move());
        timer.start();

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to display a custom image in a new JFrame

}

