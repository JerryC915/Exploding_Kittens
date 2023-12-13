import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test extends JFrame {

    public Test() {
        setTitle("Game Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create a panel to hold components
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw the background image
                ImageIcon backgroundImage = new ImageIcon("path/to/your/image.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(null); // Using null layout for absolute positioning

        // Create "Start" button
        JButton startButton = new JButton("Start");
        startButton.setBounds(300, 200, 200, 50); // Set button position and size
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String playersCountStr = JOptionPane.showInputDialog(null, "Enter the number of players:");
                if (playersCountStr != null && !playersCountStr.isEmpty()) {
                    try {
                        int playersCount = Integer.parseInt(playersCountStr);
                        // Use the playersCount variable for further processing
                        JOptionPane.showMessageDialog(null, "Number of players: " + playersCount);
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
        rulesButton.setBounds(300, 270, 200, 50); // Set button position and size
        rulesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Game Rules:\nRule 1\nRule 2\nRule 3");
            }
        });

        // Add buttons to the main panel
        mainPanel.add(startButton);
        mainPanel.add(rulesButton);

        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Test homepage = new Test();
            homepage.setVisible(true);
        });
    }
}