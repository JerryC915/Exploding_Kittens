import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Gameboard extends JFrame {

    private LinkedList<ImageIcon> cardImages = new LinkedList<>();
    private LinkedList<String> cardNames = new LinkedList<>();
    private JTextField mainScreen;
    private JButton Play, Draw;
    private JPanel mainPanel, centerPanel, playerPanel;

    public Gameboard() {
        setTitle("Exploding Kittens");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());

        playerPanel = new JPanel(new FlowLayout());

        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE); // Represents the center of the game
        mainScreen = new JTextField(100);
        centerPanel.add(mainScreen);

        mainPanel.add(playerPanel, BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public void addCardImage(String name) {
        ImageIcon temp = new ImageIcon("images/"+name+".png");
        temp = new ImageIcon(temp.getImage().getScaledInstance(115, 160, Image.SCALE_SMOOTH));
        cardImages.add(temp);
        JButton cardButton = new JButton();
        cardButton.setIcon(temp);
        cardButton.setPreferredSize(new Dimension(115, 160));
        playerPanel.add(cardButton);
        cardNames.add(name);
    }
    public void removeCardImage(String name) {
        for (int i = 0; i < cardImages.size(); i++) {
            if(cardNames.get(i).equals(name)) {
                cardImages.remove(i);
            }
        }
    }
    public void setPublicText(String x) {
        this.mainScreen.setText(x);
    }
    public String getPublicText() {
        return this.mainScreen.getText();
    }
}