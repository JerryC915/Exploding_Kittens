import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Gameboard extends JFrame {

    private List<ImageIcon> cardImages;

    public Gameboard(LinkedList<Card> deck) {
        cardImages = new ArrayList<>();
        for (int i = 0; i < deck.size(); i++) {
            String name = deck.get(i).getName();
            if(name.equals("Diffuse")) {
                cardImages.add(new ImageIcon("images/Diffuse.png"));
            }
        }
        cardImages.add(new ImageIcon("card2.png"));

        setTitle("Exploding Kittens");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel playerPanel = new JPanel(new FlowLayout());
        for (ImageIcon image : cardImages) {
            JButton cardButton = new JButton();
            cardButton.setIcon(image);
            cardButton.setPreferredSize(new Dimension(100, 150));
            playerPanel.add(cardButton);
        }

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE); // Represents the center of the game

        mainPanel.add(playerPanel, BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}