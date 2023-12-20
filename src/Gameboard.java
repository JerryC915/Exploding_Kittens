import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Gameboard extends JPanel {

    private LinkedList<ImageIcon> cardImages = new LinkedList<>();
    private JFrame frame;
    private LinkedList<String> cardNames = new LinkedList<>();
    private Map<JButton, String> selectedCards = new HashMap<>();
    private JButton Play, Draw;
    private JPanel mainPanel, centerPanel, playerPanel, cardPanel,gameOverScreen,cardPanel2,cardPanel3;
    private CardLayout cardLayout,cardLayout2,cardlayout3;
    private Main main;

    public Gameboard(int player, Main main) {
        this.main = main;
        frame = new JFrame();
        frame.setSize(1500,600);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);



        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setSize(1500,600);
        playerPanel = new JPanel(new FlowLayout());

        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE); // Represents the center of the game

        Draw = new JButton("Draw");
        Draw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawCard(1);
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

        cardPanel.add(mainPanel,"Actual Screen");
        cardPanel.add(gameOverScreen, "Game Over Screen");
        frame.add(cardPanel);
        frame.setVisible(true);
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
                int playerToSteal = Integer.parseInt(splayerToSteal) - 1;
                if(main.getSpecificPlayer(playerToSteal).CounterCards()) {
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
                cardLayout.show(cardPanel,"Actual Screen");
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
            main.setNumCardAttack(main.getNumAttack() + 2);
            main.showNextGameboard();
        }else if(name.equals("Shuffle")) {
            main.shuffleDeck();
        }else if(name.equals("See The Future")) {
            JOptionPane.showMessageDialog(null, "The next three cards in the deck: \n" +main.returnDeck().get(0).getName()+"\n"+main.returnDeck().get(1).getName()+"\n"+main.returnDeck().get(2).getName());
        }else if(name.equals("Favor")) {
            String splayerToFavor = JOptionPane.showInputDialog(null, "Which player do you wish to favor?");
            int playToFavor = Integer.parseInt(splayerToFavor);
            createFavorPanel(playToFavor);
            cardLayout.show(cardPanel,"Actual Screen");
        }else if(name.equals("Skip")) {
            main.showNextGameboard();
        }
    }
    public void createRegularCatAttackPanel(int x, int playerToSteal) {
        cardPanel2 = new JPanel();
        cardLayout2 = new CardLayout();
        cardPanel2.setLayout(cardLayout2);
        String sIndexCardWant = "";
        String CardWant = "";
        int indexCardWant = 0;

        JPanel tempText = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>You have a choice to Nope the cats being used on you.<br>Please click the 'yes' button to Nope the cats or click the Skip button to let them steal you.</div></html>", SwingConstants.CENTER);
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
                    String tempCardSteal = main.getSpecificPlayer(playerToSteal-1).showCard(finalIndexCardWant).getName();
                    main.getSpecificPlayer(playerToSteal-1).removeCard(tempCardSteal);
                    main.returnPlayer().addCard(new Card(tempCardSteal));
                }else if(x == 3) {
                    if(!main.getSpecificPlayer(playerToSteal-1).containsCard(finalCardWant)) {
                        cardLayout.show(cardPanel,"Actual Screen");
                        JOptionPane.showMessageDialog(null, "The player you selected do no have the card that you wanted, so you just wasted three cats");
                    }else {
                        main.getSpecificPlayer(playerToSteal-1).removeCard(finalCardWant);
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
                main.getSpecificPlayer(playerToSteal-1).removeCard("Nope");
            }
        });
        tempText.add(yesButton,gbc);
        if(x == 2) {
            sIndexCardWant = JOptionPane.showInputDialog(null, "Which index of card do you wish to steal? From 1-" + main.getSpecificPlayer(playerToSteal).totalCardNum());
            indexCardWant = Integer.parseInt(sIndexCardWant);
        }else if(x == 3) {
            CardWant = JOptionPane.showInputDialog(null,"What card do you wish to get?");
        }

        JPanel tempConfirmation = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("Is this player " + playerToSteal +"?");
        label2.setFont(new Font("Arial", Font.PLAIN, 36));
        label2.setHorizontalAlignment(JLabel.CENTER);
        JButton yesButton2 = new JButton("Yes");
        yesButton2.setFont(new Font("Arial", Font.PLAIN, 24));

        yesButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout2.show(cardPanel2, "TempText");
            }
        });
        tempConfirmation.add(label2, BorderLayout.CENTER);
        tempConfirmation.add(yesButton2, BorderLayout.SOUTH);
        cardLayout2.show(cardPanel2,"Confirmation2");



        cardPanel2.add(tempConfirmation, "Confirmation2");
        cardPanel2.add(tempText,"TempText");
        cardPanel.add(cardPanel2,"RegularCatAttack");
        cardLayout.show(cardPanel2,"RegularCatAttack");
    }
    public void createFavorPanel(int playerToFavor) {
        cardPanel3 = new JPanel();
        cardlayout3 = new CardLayout();
        cardPanel3.setLayout(cardlayout3);
        JPanel temp = new JPanel();
        if(main.returnPlayer().containsCard("Nope")) {

            JLabel noNopeCard = new JLabel("You have the chance to Nope the favor used on you \n Do you wish to use it?");
            JButton yesButton = new JButton("Yes");
            yesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    main.getSpecificPlayer(playerToFavor-1).removeCard("Nope");
                }
            });
            JButton noButton = new JButton("No");
            noButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    favorCatExtended(playerToFavor-1);
                }
            });
            temp.add(noNopeCard);
            temp.add(yesButton);
            temp.add(noButton);
        }
        JLabel tempLabel = new JLabel("You do not have any card to defend the favor");
        favorCatExtended(playerToFavor);


        JPanel tempConfirmation2 = new JPanel(new BorderLayout());
        JLabel label3 = new JLabel("Is this player " + playerToFavor +"?");
        label3.setFont(new Font("Arial", Font.PLAIN, 36));
        label3.setHorizontalAlignment(JLabel.CENTER);
        JButton yesButton3 = new JButton("Yes");
        yesButton3.setFont(new Font("Arial", Font.PLAIN, 24));
        yesButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardlayout3.show(cardPanel3,"temp");
            }
        });
        tempConfirmation2.add(label3, BorderLayout.CENTER);
        tempConfirmation2.add(yesButton3, BorderLayout.SOUTH);

        cardPanel3.add(temp,"temp");
        cardPanel3.add(tempConfirmation2,"tempConfirmation2");
        cardPanel.add(cardPanel3,"Favor");
        cardLayout.show(cardPanel3,"Favor");
        cardlayout3.show(tempConfirmation2,"Confirmation3");
    }
    public void favorCatExtended(int playerToFlavor) {
        main.showSpecificBoard(playerToFlavor);
        String giveAwayCard = JOptionPane.showInputDialog(null, "Which card do you want to give away?");
        main.showCurrentBoard();
        if(main.getSpecificPlayer(playerToFlavor-1).containsCard(giveAwayCard)){
            main.returnPlayer().addCard(new Card(giveAwayCard));
            main.getSpecificPlayer(playerToFlavor-1).removeCard(giveAwayCard);
        }
    }
    public void drawCard(int y) {
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
                tempIndex = cardNames.indexOf("Exploding Kittens") - 1;
                playerPanel.remove(tempIndex);
                cardImages.remove(tempIndex);
                cardNames.remove(tempIndex);
                frame.repaint();
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
                cardLayout.show(cardPanel,"Game Over Screen");
            }
        }
        if(y == 1) {
            main.showNextGameboard();
        }
    }
    public void display(){
        this.frame.setVisible(true);
        this.frame.repaint();
    }
}
