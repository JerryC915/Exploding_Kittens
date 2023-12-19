import javax.swing.*;
import java.awt.*;

public class Test {
    private JFrame frame;
    private JLabel messageLabel;

    public Test() {
        frame = new JFrame("Cat Blocking Screen");

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Spanning two columns
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        messageLabel = new JLabel("<html><div style='text-align: center;'>You have a choice to block the cats being used on you.<br>Please choose one of the cards below to block the cats or click the Skip button to let them steal you.</div></html>", SwingConstants.CENTER);
        centerPanel.add(messageLabel, gbc);

        gbc.gridwidth = 1; // Resetting gridwidth
        gbc.gridy++; // Moving to the next row

        JButton skipButton = new JButton("Skip");
        skipButton.addActionListener(e -> {
            // Add your action when the skip button is clicked
            JOptionPane.showMessageDialog(frame, "You chose to skip blocking the cats.");
        });

        centerPanel.add(skipButton, gbc);

        gbc.gridx++;

        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(e -> {
            // Add your action when the yes button is clicked
            JOptionPane.showMessageDialog(frame, "You chose to block the cats.");
        });

        centerPanel.add(yesButton, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Test::new);
    }
}
