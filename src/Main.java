import java.util.Collections;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<Card> currentDeck = Newdeck();

    }

    public static LinkedList<Card> Newdeck() {
        LinkedList<Card> deck = new LinkedList<>();
        LinkedList<Integer> temp = new LinkedList<>();
        for (int i = 0; i < 56; i++) {
            temp.add(i);
        }
        Collections.shuffle(temp);
        for (int i = 0; i < 56; i++) {
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
                deck.add(new Card("Diffuse"));
            }else if(x < 34) {
                deck.add(new Card("Exploding Kitten"));
            }else if(x < 38) {
                deck.add(new Card("Favor"));
            }else if(x < 43) {
                deck.add(new Card("Nope"));
            }else if(x < 48) {
                deck.add(new Card("See The Future"));
            }else if(x < 52) {
                deck.add(new Card("Shuffle"));
            }else if(x < 56) {
                deck.add(new Card("Skip"));
            }
        }
        return deck;
    }
}