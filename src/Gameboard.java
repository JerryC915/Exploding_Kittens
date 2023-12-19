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
    private JPanel mainPanel, centerPanel, playerPanel, cardPanel,gameOverScreen,cardPanel2;
    private JFrame frame;
    private CardLayout cardLayout,cardLayout2;
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
                Card x = main.popDeck();
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
                                main.addCardToDeck(new Card("Exploding Kittens"),index - 1);
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
            if(selectedCards.size() > 1) {
                String splayerToSteal = JOptionPane.showInputDialog(null, "Which player do you wish to steal from?");
                int playerToSteal = Integer.parseInt(splayerToSteal);
                if(main.getSpecificPlayer(playerToSteal).CounterCards()) {
                    cardLayout.show(cardPanel2,"RegularCatAttack");
                    if (selectedCards.size() == 2) {
                        createRegularCatAttackPanel(2,playerToSteal);
                        for (int i = 0; i < 2; i++) {
                            playerPanel.remove(selectedCardButton);
                            cardImages.removeIf(icon -> icon.equals(selectedCardButton.getIcon()));
                            cardNames.removeIf(name -> ("images/" + name + ".png").equals(selectedCardButton.getIcon().toString()));
                        }
                        break;
                    } else if (selectedCards.size() == 3) {
                        createRegularCatAttackPanel(3,playerToSteal);
                        for (int i = 0; i < 3; i++) {
                            playerPanel.remove(selectedCardButton);
                            cardImages.removeIf(icon -> icon.equals(selectedCardButton.getIcon()));
                            cardNames.removeIf(name -> ("images/" + name + ".png").equals(selectedCardButton.getIcon().toString()));
                        }
                    }
                }else {
                    if(selectedCards.size() == 2) {
                        String scardWant = JOptionPane.showInputDialog(null, "Which index of card do you want?  From 1-" + main.getSpecificPlayer(playerToSteal).totalCardNum());
                        int cardWant = Integer.parseInt(scardWant);
                        String x = main.getSpecificPlayer(playerToSteal).showCard(cardWant).getName();
                        main.getSpecificPlayer(playerToSteal).removeCard(x);
                        main.returnPlayer().addCard(new Card(x));
                        for (int i = 0; i < 2; i++) {
                            playerPanel.remove(selectedCardButton);
                            cardImages.removeIf(icon -> icon.equals(selectedCardButton.getIcon()));
                            cardNames.removeIf(name -> ("images/" + name + ".png").equals(selectedCardButton.getIcon().toString()));
                        }
                    }else if(selectedCards.size() == 3) {
                        String cardWant = JOptionPane.showInputDialog(null, "What card do you want from the player?");
                        if(!main.getSpecificPlayer(playerToSteal).containsCard(cardWant)) {
                            JOptionPane.showMessageDialog(null, "The player you selected do no have the card that you wanted, so you just wasted three cats");
                        }else {
                            main.getSpecificPlayer(playerToSteal).removeCard(cardWant);
                            main.returnPlayer().addCard(new Card(cardWant));
                        }
                        for (int i = 0; i < 3; i++) {
                            playerPanel.remove(selectedCardButton);
                            cardImages.removeIf(icon -> icon.equals(selectedCardButton.getIcon()));
                            cardNames.removeIf(name -> ("images/" + name + ".png").equals(selectedCardButton.getIcon().toString()));
                        }
                    }
                }
            }else {
                play(selectedCardName);
                playerPanel.remove(selectedCardButton);
                cardImages.removeIf(icon -> icon.equals(selectedCardButton.getIcon())); //Quick note, I looked this part up online
                cardNames.removeIf(name -> ("images/" + name + ".png").equals(selectedCardButton.getIcon().toString())); //This one too, I was in confusion for how to remove these things away
            }
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
    public void play(String name) {
        if(name.equals("Attack")) {
            main.setNumCardAttack(2);
        }else if(name.equals("Shuffle")) {
            main.shuffleDeck();
        }else if(name.equals("See The Future")) {
            JOptionPane.showMessageDialog(null, "The next three cards in the deck: \n" +main.returnDeck().get(0).getName()+"\n"+main.returnDeck().get(1).getName()+"\n"+main.returnDeck().get(2).getName());
        }
    }
    public void createRegularCatAttackPanel(int x, int playerToSteal) {
        String sIndexCardWant = "";
        String CardWant = "";
        int indexCardWant = 0;
        if(x == 2) {
            sIndexCardWant = JOptionPane.showInputDialog(null, "Which index of card do you wish to steal? From 1-" + main.getSpecificPlayer(playerToSteal).totalCardNum());
            indexCardWant = Integer.parseInt(sIndexCardWant);
        }else if(x == 3) {
            CardWant = JOptionPane.showInputDialog(null,"What card do you wish to get?");
        }

        cardPanel2 = new JPanel();
        cardLayout2 = new CardLayout();
        cardPanel2.setLayout(cardLayout2);
        JPanel tempConfirmation = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("Is this player " + playerToSteal +"?");
        label2.setFont(new Font("Arial", Font.PLAIN, 36));
        label2.setHorizontalAlignment(JLabel.CENTER);
        JButton yesButton2 = new JButton("Yes");
        yesButton2.setFont(new Font("Arial", Font.PLAIN, 24));

        yesButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout2.show(tempConfirmation, "Confirmation2");
            }
        });
        tempConfirmation.add(label2, BorderLayout.CENTER);
        tempConfirmation.add(yesButton2, BorderLayout.SOUTH);

        JPanel tempText = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>You have a choice to block the cats being used on you.<br>Please choose one of the cards below to block the cats or click the Skip button to let them steal you.</div></html>", SwingConstants.CENTER);
        tempText.add(messageLabel, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;

        JButton skipButton = new JButton("Skip");
        int finalIndexCardWant = indexCardWant;
        String finalCardWant = CardWant;
        skipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(x == 2) {
                    String tempCardSteal = main.getSpecificPlayer(playerToSteal).showCard(finalIndexCardWant).getName();
                    main.getSpecificPlayer(playerToSteal).removeCard(tempCardSteal);
                    main.returnPlayer().addCard(new Card(tempCardSteal));
                }else if(x == 3) {
                    if(!main.getSpecificPlayer(playerToSteal).containsCard(finalCardWant)) {
                        cardLayout.show(mainPanel,"Actual Screen");
                        JOptionPane.showMessageDialog(null, "The player you selected do no have the card that you wanted, so you just wasted three cats");
                    }else {
                        main.getSpecificPlayer(playerToSteal).removeCard(finalCardWant);
                        main.returnPlayer().addCard(new Card(finalCardWant));
                    }
                }
            }
        });
        tempText.add(skipButton, gbc);
        gbc.gridx++;

        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.getSpecificPlayer(playerToSteal).removeCard("Nope");
            }
        });
        tempText.add(yesButton,gbc);

        cardPanel2.add(tempConfirmation, "Confirmation2");
        cardPanel.add(cardPanel2,"RegularCatAttack");
    }
}
