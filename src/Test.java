import javax.swing.*;
import java.awt.*;

public class Test extends JPanel {

    public Test() {
        setPreferredSize(new Dimension(800, 600)); // Set preferred size of the panel

        // Set up the panel to paint the "GAME OVER" text
        setLayout(new BorderLayout());
        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 72));

        add(gameOverLabel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Over");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Test gameOverPanel = new Test();
            frame.getContentPane().add(gameOverPanel);

            frame.pack(); // Adjust frame size to fit the panel
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            frame.setVisible(true);
        });
    }
}
