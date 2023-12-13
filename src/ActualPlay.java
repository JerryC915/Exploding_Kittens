import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActualPlay extends JFrame{
    private JPanel front;
    private int numPlayer;
    public ActualPlay() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create a panel with null layout to position components manually
        front = new JPanel(null);

        // Create a JLabel to hold the background image
        JLabel background = new JLabel();
        background.setSize(800, 600);
        ImageIcon backgroundImage = new ImageIcon("images/Frontpage.png");
        backgroundImage = new ImageIcon(backgroundImage.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH));
        background.setIcon(backgroundImage);

        // Create "Start" button
        JButton startButton = new JButton("Start");
        startButton.setBounds(320, 250, 150, 50); // Set button position and size
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String playersCountStr = JOptionPane.showInputDialog(null, "Enter the number of players:");
                if (playersCountStr != null && !playersCountStr.isEmpty()) {
                    try {
                        int numPlayer = Integer.parseInt(playersCountStr);
                        // Use the playersCount variable for further processing
                        JOptionPane.showMessageDialog(null, "Number of players: " + numPlayer);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter the number of players.");
                }
            }
        });
        // Create "Rules" button
        JButton rulesButton = new JButton("Rules");
        rulesButton.setBounds(320, 320, 150, 50); // Set button position and size
        rulesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Game Rules:\nRule 1\nRule 2\nRule 3");
            }
        });
        front.add(background);
        front.add(startButton);
        front.add(rulesButton);
        add(front);
    }
    public int getNumPlayer() {
        return this.numPlayer;
    }
}
