import javax.swing.*;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JPanel front,cardPanel;
    private CardLayout cardLayout;
    private JFrame frame;
    private int numCardAttack,currentGameboardIndex = 0,cnt = 0;
    private int favorPerson, playerAt;
    private LinkedList<Card> currentDeck;
    private LinkedList<Player> player;
    private LinkedList<Gameboard> gameboard = new LinkedList<Gameboard>();

    public Main(int numCardAttack, LinkedList<Card> currentDeck, LinkedList<Player> player, int favorPerson) {
        this.numCardAttack = this.numCardAttack + numCardAttack;
        this.currentDeck = currentDeck;
        this.player = player;
        this.favorPerson = favorPerson;
    }
    public static void main(String[] args) throws IOException {
        Main m = new Main(0, newdeck(),new LinkedList<Player>(),-1);
        m.run();
    }

    public static LinkedList<Card> newdeck() {
        LinkedList<Card> deck = new LinkedList<>();
        LinkedList<Integer> temp = new LinkedList<>();
        for (int i = 0; i < 49; i++) {
            temp.add(i);
        }
        Collections.shuffle(temp);
        for (int i = 0; i < 49; i++) {
            int x = temp.get(i);
            if(x < 4) {
                deck.add(new Card("Attack"));
            }else if(x < 8) {
                deck.add(new Card("Bearded Cat"));
            }else if(x < 12) {
                deck.add(new Card("Catermelon Cat"));
            }else if(x < 16) {
                deck.add(new Card("Hairy Potato Cat"));
            }else if(x < 20) {
                deck.add(new Card("Rainbow-Ralphing Cat"));
            }else if(x < 24) {
                deck.add(new Card("Taco Cat"));
            }else if(x < 30) {
                deck.add(new Card("Shuffle"));
            }else if(x < 34) {
                deck.add(new Card("Skip"));
            }else if(x < 38) {
                deck.add(new Card("Favor"));
            }else if(x < 43) {
                deck.add(new Card("Nope"));
            }else if(x < 48) {
                deck.add(new Card("See The Future"));
            }else if(x < 49) {
                deck.add(new Card("Shuffle"));
            }
        }
        return deck;
    }
    public void run() {
        frame = new JFrame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        setTitle("Exploding Kittens!");
        int numPlayer = 0;
        setLocationRelativeTo(null);

        // Create a panel with null layout to position components manually
        front = new JPanel(null);

        // Create a JLabel to hold the background image
        JLabel background = new JLabel();
        background.setSize(1000, 600);
        ImageIcon backgroundImage = new ImageIcon("images/Frontpage.png");
        backgroundImage = new ImageIcon(backgroundImage.getImage().getScaledInstance(1000, 600, Image.SCALE_SMOOTH));
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
                        run1(numPlayer);
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
        frame.add(front);
        frame.setVisible(true);
    }
    public void run1(int numPlayer) {
        for (int i = 0; i < numPlayer; i++) {
            this.player.add(new Player(new LinkedList<Card>())); //Creating the class of Players
            gameboard.add(new Gameboard(i + 1,this));
        }
        for (int i = 0; i < this.player.size(); i++) { //Add Diffuse Card to each Player's Deck
            this.player.get(i).addCard(new Card("Diffuse"));
            this.gameboard.get(i).addCardImage("Diffuse");
            for (int j = 0; j < 6; j++) {
                Card popCard = currentDeck.pop();
                this.player.get(i).addCard(popCard);
                this.gameboard.get(i).addCardImage(popCard.getName());
            }
        }
        for (int i = 0; i < 4; i++) { //Add Exploding Kittens into the public deck
            int x = (int)(Math.random() * currentDeck.size());
            currentDeck.add(x, new Card("Exploding Kittens"));
        }
        cardPanel = new JPanel();
        cardLayout= new CardLayout();
        cardPanel.setLayout(cardLayout);
    }
    public LinkedList<Card> returnDeck(){
        return this.currentDeck;
    }
    public void addCardToDeck(Card x, int i) {
        this.currentDeck.add(i, x);
    }
    public Card popDeck() {
        return this.currentDeck.pop();
    }
    public void shuffleDeck() {
        Collections.shuffle(currentDeck);
    }
    public Player returnPlayer(){
        return player.get(playerAt);
    }
    public int getNumCardAttack() {
        return this.numCardAttack;
    }
    public void setNumCardAttack(int i) {
        this.numCardAttack = i;
    }
    public Player getSpecificPlayer(int i) {
        return this.player.get(i);
    }
    public int getNumAttack() {
        return this.numCardAttack;
    }
    public int getNumPlayer() {
        return this.player.size();
    }
    public void showNextGameboard() {
        JFrame temp = new JFrame();
        temp.setSize(1500,600);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Is this player " + ((int)(currentGameboardIndex)+1) +"?");
        label.setFont(new Font("Arial", Font.PLAIN, 36));
        label.setHorizontalAlignment(JLabel.CENTER);

        JButton yesButton = new JButton("Yes");
        yesButton.setFont(new Font("Arial", Font.PLAIN, 24));
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temp.remove(panel);
                temp.setVisible(false);
                currentGameboardIndex = (currentGameboardIndex + 1) % gameboard.size();
                cardLayout.show(cardPanel, Integer.toString(currentGameboardIndex));
                gameboard.get(currentGameboardIndex).display();
                if(numCardAttack> 0) {
                    if(returnPlayer().CounterCards()) {
                        String cardDefend = JOptionPane.showInputDialog(null, "You are under attack! Which card do you choose to defend with? You can also say none");
                        if(cardDefend.equals("Attack") && returnPlayer().containsCard("Attack")) {
                            setNumCardAttack(getNumAttack() + 2);

                        }else if(cardDefend.equals("Nope") && returnPlayer().containsCard("Nope")) {
                            setNumCardAttack(0);
                        }else if(cardDefend.equals("none")) {
                            for (int i = 0; i < getNumAttack(); i++) {
                                gameboard.get(currentGameboardIndex).drawCard(0);
                            }
                            setNumCardAttack(0);
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "You do not have any card to defend with the attack, we will automatically draw " + getNumAttack() + " cards for you ");
                        for (int i = 0; i < getNumAttack(); i++) {
                            gameboard.get(currentGameboardIndex).drawCard(0);
                        }
                    }
                }
            }
        });
        panel.add(label, BorderLayout.CENTER);
        panel.add(yesButton, BorderLayout.SOUTH);
        temp.add(panel);
        temp.setVisible(true);

    }
    public void showSpecificBoard(int player){
        cardLayout.show(cardPanel, Integer.toString(player-1));
        gameboard.get(currentGameboardIndex).display();
    }
    public void showCurrentBoard(){
        cardLayout.show(cardPanel, Integer.toString(currentGameboardIndex));
        gameboard.get(currentGameboardIndex).display();
    }
}