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
    private JPanel front;
    private JFrame frame;
    private int numCardAttack;
    private int favorPerson;
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
    public void Play(String x, int favor, int p) {
        if(x.equals("Attack")) {
            this.numCardAttack = this.numCardAttack + 2;
        }else if(x.equals("Shuffle")) {
            Collections.shuffle(this.currentDeck);
            System.out.println("Deck Shuffled");
        }else if(x.equals("Nope")) {
            this.numCardAttack = 0;
        }else if(x.equals("Favor")){
            favorPerson = favor;
        }else if(x.equals("See The Future")) {
            System.out.println();
            System.out.println("The next three cards in the deck are: ");
            System.out.println(currentDeck.get(0).getName());
            System.out.println(currentDeck.get(1).getName());
            System.out.println(currentDeck.get(2).getName());
            System.out.println();
        }else if(x.equals("Catermelon Cat") || x.equals("Bearded Cat") || x.equals("Hairy Potato Cat") || x.equals("Rainbow-Ralphing Cat") || x.equals("Taco Cat")){
            regularCat(x,p);
        }
    }


    public void run() {
        frame = new JFrame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        setTitle("Exploding Kittens!");
        int numPlayer = 0;
        setLocationRelativeTo(null);

        // Create a panel with null layout to position components manually
        front = new JPanel(null);

        // Create a JLabel to hold the background image
        JLabel background = new JLabel();
        background.setSize(800, 600);
        ImageIcon backgroundImage = new ImageIcon("images/Frontpage.png");
        backgroundImage = new ImageIcon(backgroundImage.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH));
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
    public void explode(int i) {
        Scanner scan = new Scanner(System.in);
        if(this.player.get(i).containsCard("Exploding Kittens")) {
            if(this.player.get(i).containsDiffuse()) {
                gameboard.get(i).setPublicText("Oops! Looks like you drawn an Exploding Kitten! Please choose the Diffuse Card in your deck!");
                this.player.get(i).removeCard("Diffuse");
                this.gameboard.get(i).removeCardImage("Diffuse");
                this.player.get(i).removeCard("Exploding Kittens");
                this.gameboard.get(i).removeCardImage("Exploding Kittens");
                System.out.println("Now you have the choice to put the Exploding Kitten anywhere in the deck you want");
                System.out.println();
                System.out.println("There are " + currentDeck.size() + " cards left in the deck");
                System.out.println("Please choose an index for which index you want to put the card at: ");
                String index = scan.nextLine();
                currentDeck.add(Integer.parseInt(index) -1 ,new Card("Exploding Kittens"));
            }else {
                System.out.println("Ouch, looks like you don't have any Diffuse in your deck");
                System.out.println("You are out!");
                player.remove(i);
            }
        }
    }
    public void showHand(int i) {
        System.out.println("Your hand now is: ");
        System.out.println();
        this.player.get(i).showDeck();
        System.out.println();
    }
    public void regularCat(String cat, int x) {
        Scanner scan = new Scanner(System.in);
        String want = "";
        int numCat = 0;
        if(player.get(x).countCard(cat) < 2)  {
            System.out.println("Sorry, you do not have enough " + cat + " to play it");
            player.get(x).addCard(new Card(cat));
        }else if(player.get(x).countCard(cat) < 3) {
            System.out.println("You have the ability to play 2 " + cat + ", do you wish to play it?");
            if(scan.nextLine().equals("yes")) {
                numCat = 2;
            }
        }else if(player.get(x).countCard(cat) > 2) {
            System.out.println("You have the ability to play 2 or 3 " + cat + ", how many cards of " + cat + " do you wish to play?");
            String num = scan.nextLine();
            if(num.equals("3")) {
                numCat = 3;
                System.out.println("Which card do you wish to get?");
                want = scan.nextLine();
            }else {
                numCat = 2;
            }
        }
        System.out.println("Which player do you want to steal from?");
        String tempPlayer = scan.nextLine();
        System.out.println("Is this player " + tempPlayer + "?");
        if(scan.nextLine().equals("yes")) {
            if(!player.get(Integer.parseInt(tempPlayer) - 1).containsCard(want) && numCat == 3) {
                System.out.println("Oops! Looks like the card the other player want does not exist in your deck! Nothing happend!");
                player.get(x).removeCard(cat);
                gameboard.get(x).removeCardImage(cat);
                player.get(x).removeCard(cat);
                gameboard.get(x).removeCardImage(cat);
            }
            if(numCat == 3) {
                if (player.get(Integer.parseInt(tempPlayer) - 1).containsCard("Nope")) {
                    System.out.println("You have the chance to Nope from giving away your " + want + ", do you wish to play the Nope?");
                    if (scan.nextLine().equals("no")) {
                        player.get(x).addCard(new Card(want));
                        gameboard.get(x).addCardImage(want);
                        player.get(Integer.parseInt(tempPlayer) - 1).removeCard(want);
                        gameboard.get(Integer.parseInt(tempPlayer) - 1).removeCardImage(want);
                    }else {
                        player.get(Integer.parseInt(tempPlayer) - 1).removeCard("Nope");
                        gameboard.get(Integer.parseInt(tempPlayer)-1).removeCardImage("Nope");
                    }
                    player.get(x).removeCard(cat);
                    gameboard.get(x).removeCardImage(cat);
                    player.get(x).removeCard(cat);
                    gameboard.get(x).removeCardImage(cat);
                }
            }else if(numCat == 2) {
                if (player.get(Integer.parseInt(tempPlayer) - 1).containsCard("Nope")) {
                    System.out.println("You have the chance to Nope, do you wish to play the Nope?");
                    if (scan.nextLine().equals("no")) {
                        System.out.println("Is this player " + (x + 1) + "?");
                        if(scan.nextLine().equals("yes")){
                            System.out.println("The other player have " + player.get(Integer.parseInt(tempPlayer)-1).totalCardNum() + " cards, ");
                            System.out.println("Which card do you wish to steal");
                            String rs = scan.nextLine();
                            String temp = player.get(Integer.parseInt(tempPlayer) - 1).removeCardIndex(Integer.parseInt(rs));
                            player.get(x).addCard(new Card(temp));
                            gameboard.get(x).addCardImage(temp);
                            player.get(x).removeCard(cat);
                            gameboard.get(x).removeCardImage(cat);
                        }
                    } else {
                        player.get(Integer.parseInt(tempPlayer) - 1).removeCard("Nope");
                    }
                }
            }
        }
    }
    public void run1(int numPlayer) {
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < numPlayer; i++) {
            this.player.add(new Player(new LinkedList<Card>())); //Creating the class of Players
            gameboard.add(new Gameboard());
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
        boolean check = true;
        while(check) { //Game Start
            for (int i = 0; i < this.player.size(); i++) {
                String ans = "";
                int t = i + 1;
                System.out.println("It is now player " + t + "'s turn");
                System.out.println("is this player " + t + "?");
                String cp = scan.nextLine();
                if(numCardAttack > 0) { //Check if any player attacks
                    System.out.println("Oops! Looks like you are under attack!");
                    if(!this.player.get(i).containsCard("Nope") && !this.player.get(i).containsCard("Skip") && !this.player.get(i).containsCard("Attack")) {
                        System.out.println("You don't have any card possible to block from the attack, we will automatically draw you the card");
                        for (int j = 0; j < numCardAttack; j++) {
                            Card getAttackCard = currentDeck.pop();
                            this.player.get(i).addCard(getAttackCard);
                            this.gameboard.get(i).addCardImage(getAttackCard.getName());
                            explode(i);
                        }
                    }else {
                        System.out.println("1. Defend with: ");
                        this.player.get(i).showCounterCards();
                        System.out.println("or");
                        System.out.println("2. You choose to draw the cards");
                        String c = scan.nextLine();
                        if(c.equals("1")){
                            System.out.println("Which card do you choose to defend?");
                            String df = scan.nextLine();
                            Play(df,0, i);
                            player.get(i).removeCard(df);
                            if(df.equals("Attack") || df.equals("Skip")) {
                                break;
                            }
                        }else if(c.equals("2")) {
                            for (int j = 0; j < numCardAttack; j++) {
                                this.player.get(i).addCard(currentDeck.pop());
                                explode(i);
                            }
                        }
                    }
                }
                if(cp.equals("yes")) {
                    frame.remove(front);
                    frame.add(gameboard.get(i));
                    frame.setVisible(true);
                    showHand(i);
                    System.out.println("1. Play a card");
                    System.out.println("2. Draw a card");
                    ans = scan.nextLine();
                    if(ans.equals("1")) {
                        boolean keepPlay = true;
                        while(keepPlay) {
                            System.out.println("What card do you wish to play?");
                            String play = scan.nextLine();
                            if (this.player.get(i).containsCard(play)) {
                                if(play.equals("Favor")){
                                    System.out.println("Which Player do you want to favor?");
                                    String fp = scan.nextLine();
                                    Play(play, Integer.parseInt(fp), i);
                                    System.out.println("Is this player " + Integer.parseInt(fp) + "?");
                                    if(scan.nextLine().equals("yes")) {
                                        if(player.get(Integer.parseInt(fp)-1).containsCard("Nope")) {
                                            System.out.println("You have a chance to Nope this favor, do you wish to Nope this card?");
                                            if(scan.nextLine().equals("yes")) {
                                                player.get(Integer.parseInt(fp)-1).removeCard("Nope");
                                            }
                                        }else {
                                            System.out.println("Which card do you wish to favor?");
                                            String cardToRemove = scan.nextLine();
                                            if (player.get(Integer.parseInt(fp) - 1).containsCard(cardToRemove)) {
                                                player.get(Integer.parseInt(fp) - 1).removeCard(cardToRemove);
                                                player.get(i).addCard(new Card(cardToRemove));
                                            }
                                        }
                                    }
                                    this.player.get(i).removeCard(play);
                                }else {
                                    Play(play, 0, i);
                                    this.player.get(i).removeCard(play);
                                    if (play.equals("Attack") || play.equals("Skip")) {
                                        break;
                                    }
                                }
                            }else {
                                System.out.println("Sorry, this card is not in your deck");
                            }
                            showHand(i);
                            System.out.println("Do you still want to play a card? Yes or No");
                            if(scan.nextLine().toLowerCase().equals("no")) {
                                keepPlay = false;
                                System.out.println("Card automatically drawn for you");
                            }
                        }
                        if(keepPlay) {
                            continue;
                        }
                    }
                    this.player.get(i).addCard(currentDeck.pop());
                    explode(i);
                    showHand(i);
                }
            }
        }
    }
}