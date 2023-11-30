import java.util.LinkedList;

public class Player {
    private LinkedList<Card> deck;
    public Player(LinkedList<Card> deck) {
        this.deck = deck;
    }

    public void addCard(Card card) {
        deck.add(card);
    }
    public static void play() {

    }
    public LinkedList<Card> showDeck() {
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(deck.get(i).getName());
        }
        return deck;
    }
    public String Top() {
        return deck.get(deck.size()-1).getName();
    }

    public boolean containsDiffuse() {
        for (int i = 0; i < deck.size(); i++) {
            if(deck.get(i).getName().equals("Diffuse")) {
                return true;
            }
        }
        return false;
    }

    public void removeCard(String x) {
        int cnt = 0;
        for (int i = 0; i < deck.size(); i++) {
            if(deck.get(i).getName().equals(x)) {
                cnt = i;
            }
        }
        deck.remove(cnt);
    }
}
