import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Gameboard extends JPanel {

    private LinkedList<ImageIcon> cardImages = new LinkedList<>();
    private LinkedList<String> cardNames = new LinkedList<>();
    private Map<JButton, String> selectedCards = new HashMap<>();
    private JButton Play, Draw;
    private JPanel mainPanel, centerPanel, playerPanel, cardPanel,gameOverScreen;
    private JFrame frame;
    private CardLayout cardLayout;
    private Main main;

    public Gameboard(int player, Main main) {
        this.main = main;
        frame = new JFrame("Player Confirmation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Is this player " + player +"?");
        label.setFont(new Font("Arial", Font.PLAIN, 36));
        label.setHorizontalAlignment(JLabel.CENTER);

        JButton yesButton = new JButton("Yes");
        yesButton.setFont(new Font("Arial", Font.PLAIN, 24));
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel,"Actual Screen");
            }
        });

        panel.add(label, BorderLayout.CENTER);
        panel.add(yesButton, BorderLayout.SOUTH);
        cardPanel.add(panel,"Confirmation");
        cardPanel.add(mainPanel,"Actual Screen");
        cardPanel.add(gameOverScreen, "Game Over Screen");

        frame.add(cardPanel);
        frame.setVisible(true);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setSize(800,600);
        playerPanel = new JPanel(new FlowLayout());

        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE); // Represents the center of the game

        Draw = new JButton("Draw");
        Draw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card x = main.returnDeck().pop();
                addCardImage(x.getName());
                main.returnPlayer().addCard(x);
                if(x.getName().equals("Exploding Kittens")) {
                    if(cardNames.contains("Diffuse")) {
                        main.returnPlayer().removeCard(x.getName());
                        main.returnPlayer().removeCard("Diffuse");
                        int tempIndex = cardNames.indexOf("Diffuse");
                        playerPanel.remove(tempIndex);
                        cardImages.remove(tempIndex);
                        cardNames.remove(tempIndex);
                        tempIndex = cardNames.indexOf("Exploding Kittens");
                        playerPanel.remove(tempIndex);
                        cardImages.remove(tempIndex);
                        cardNames.remove(tempIndex);
                        String explodingKittenIndex = JOptionPane.showInputDialog(null, "Please choose an index from 1-" + main.returnDeck().size()+" that you want to put the exploding kitten at: ");
                        if (explodingKittenIndex != null && !explodingKittenIndex.isEmpty()) {
                            try {
                                int index = Integer.parseInt(explodingKittenIndex);
                                main.returnDeck().add(index-1,new Card("Exploding Kittens"));
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Please choose an index from 1-" + main.returnDeck().size()+" that you want to put the exploding kitten at: ");
                        }
                    }else {
                        cardLayout.show(gameOverScreen,"Game Over Screen");
                    }
                }
            }
        });
        centerPanel.add(Draw, BorderLayout.NORTH); // Add "Draw" button to the top of centerPanel

        // Create "Play" button
        Play = new JButton("Play");
        Play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaySelectedCards();
            }
        });
        centerPanel.add(Play, BorderLayout.SOUTH); // Add "Play" button to the bottom of centerPanel

        mainPanel.add(playerPanel, BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);


        JPanel gameOverScreen = new JPanel();
        gameOverScreen.setLayout(new BorderLayout());
        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 72));
        gameOverScreen.add(gameOverLabel,BorderLayout.CENTER);

    }

    public void addCardImage(String name) {
        ImageIcon temp = new ImageIcon("images/"+name+".png");
        temp = new ImageIcon(temp.getImage().getScaledInstance(115, 160, Image.SCALE_SMOOTH));
        cardImages.add(temp);
        JButton cardButton = new JButton();
        cardButton.setIcon(temp);
        cardButton.setPreferredSize(new Dimension(115, 160));
        cardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCardSelection(cardButton, name);
            }
        });
        playerPanel.add(cardButton);
        cardNames.add(name);
    }
    private void handleCardSelection(JButton cardButton, String cardName) {
        if (selectedCards.containsKey(cardButton)) {
            cardButton.setBorder(null); // Unselect the card
            selectedCards.remove(cardButton);
        } else {
            cardButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3)); // Highlight selected card
            selectedCards.put(cardButton, cardName);
        }
    }
    private void PlaySelectedCards() {
        for (Map.Entry<JButton, String> entry : selectedCards.entrySet()) {
            JButton selectedCardButton = entry.getKey();
            String selectedCardName = entry.getValue();
            playerPanel.remove(selectedCardButton);
            cardImages.removeIf(icon -> icon.equals(selectedCardButton.getIcon())); //Quick note, I looked this part up online
            cardNames.removeIf(name -> ("images/" + name + ".png").equals(selectedCardButton.getIcon().toString())); //This one too, I was in confusion for how to remove these things away
        }
        playerPanel.revalidate(); // Refresh the panel after removing components
        playerPanel.repaint(); // Repaint the panel
        selectedCards.clear(); // Clear the list of selected cards
    }
    public void removeCardImage(String name) {
        for (int i = 0; i < cardImages.size(); i++) {
            if(cardNames.get(i).equals(name)) {
                cardImages.remove(i);
            }
        }
    }
}
