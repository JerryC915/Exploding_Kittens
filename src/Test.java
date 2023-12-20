import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("CardLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.RED);
        panel1.add(new JLabel("Panel 1"));
        cardPanel.add(panel1, "Panel1");

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.BLUE);
        panel2.add(new JLabel("Panel 2"));
        cardPanel.add(panel2, "Panel2");

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.GREEN);
        panel3.add(new JLabel("Panel 3"));
        cardPanel.add(panel3, "Panel3");

        frame.add(cardPanel, BorderLayout.CENTER);

        JButton showPanel1Button = new JButton("Show Panel 1");
        showPanel1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Panel1");
            }
        });

        JButton showPanel2Button = new JButton("Show Panel 2");
        showPanel2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Panel2");
            }
        });

        JButton showPanel3Button = new JButton("Show Panel 3");
        showPanel3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Panel3");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(showPanel1Button);
        buttonPanel.add(showPanel2Button);
        buttonPanel.add(showPanel3Button);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
