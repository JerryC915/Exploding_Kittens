import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LinkedList<Player> player = new LinkedList<>();
        LinkedList<Card> currentDeck = Newdeck();
        Scanner scan = new Scanner(System.in);
        String answer = "";
        System.out.println("Do you wish to start the game? (Yes or No)");
        answer = scan.nextLine().toLowerCase();
        while(answer.equals("yes")) {
            String current = "";
            System.out.println("Welcome to the game of Exploding Kittens!");
            System.out.println("1. Start game?");
            System.out.println("2. Check the Rule");
            System.out.println("1 or 2");
            current = scan.nextLine();
            String temp = "";
            if(current.equals("1")) {
                System.out.println("How many players are playing?");
                temp = scan.nextLine();
                for (int i = 0; i < Integer.parseInt(temp); i++) {
                    player.add(new Player(new LinkedList<Card>()));
                }
                for (int i = 0; i < player.size(); i++) {
                    player.get(i).addCard(new Card("Diffuse"));
                    for (int j = 0; j < 6; j++) {
                        player.get(i).addCard(currentDeck.pop());
                    }
                }
                for (int i = 0; i < 4; i++) {
                    int x = (int)(Math.random() * currentDeck.size());
                    currentDeck.add(x, new Card("Exploding Kittens"));
                }
                boolean check = true;
                while(check) {
                    for (int i = 0; i < player.size(); i++) {
                        String ans = "";
                        int t = i + 1;
                        System.out.println("It is now player " + t + "'s turn");
                        System.out.println("is this player " + t + "?");
                        String cp = scan.nextLine();
                        if(cp.equals("yes")) {
                            System.out.println("This is your hand below: ");
                            System.out.println();
                            player.get(i).showDeck();
                            System.out.println();
                            System.out.println("1. Play a card");
                            System.out.println("2. Draw a card");
                            ans = scan.nextLine();
                            if(ans.equals("2")) {
                                player.get(i).addCard(currentDeck.pop());
                                if(player.get(i).Top().equals("Exploding Kittens")){
                                    System.out.println("Oops! Looks like you drawed an Exploding Kitten!");
                                    if(player.get(i).containsDiffuse()) {
                                        System.out.println("The system will automatically play your Diffuse card");
                                        player.get(i).removeCard("Diffuse");
                                        player.get(i).removeCard("Exploding Kittens");
                                        System.out.println("Now you have the choice to put the Exploding Kitten anywhere in the deck you want");
                                        System.out.println("Please choose an index for where you want to put the card at: ");
                                        String index = scan.nextLine();
                                        currentDeck.add(Integer.parseInt(index),new Card("Exploding Kitten"));
                                    }else {
                                        System.out.println("Ouch, looks like you don't have any Diffuse in your deck");
                                        System.out.println("You are out!");
                                    }
                                }
                                System.out.println("Your hand now is: ");
                                System.out.println();
                                player.get(i).showDeck();
                                System.out.println();
                            }else if(ans.equals("1")) {
                                System.out.println("What card do you wish to play?");
                                String play = scan.nextLine();
                                function(play);
                            }
                        }
                    }
                }
            }
        }
    }

    public static LinkedList<Card> Newdeck() {
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
                deck.add(new Card("Breaded Cat"));
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
    public static void function(String x) {
        if(x.equals("Attack")) {

        }
    }
}