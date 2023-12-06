import java.util.LinkedList;

public class Player {
    private LinkedList<Card> deck;
    public Player(LinkedList<Card> deck) {
        this.deck = deck;
    }

    public void addCard(Card card) {
        deck.add(card);
    }
    public LinkedList<Card> showDeck() {
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(deck.get(i).getName());
        }
        return deck;
    }
    public void showCounterCards() {
        for (int i = 0; i < deck.size(); i++) {
            if(deck.get(i).getName().equals("Attack") || deck.get(i).getName().equals("Skip") || deck.get(i).getName().equals("Nope")) {
                System.out.println(deck.get(i).getName());
            }
        }
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
    public boolean containsCard(String x) {
        for (int i = 0; i < deck.size(); i++) {
            if(deck.get(i).getName().equals(x)) {
                return true;
            }
        }
        return false;
    }
    public int countCard(String x){
        int cnt = 0;
        for (int i = 0; i < deck.size(); i++) {
            if(deck.get(i).getName().equals(x)) {
                cnt++;
            }
        }
        return cnt;
    }
    public int totalCardNum() {
        return deck.size();
    }
    public String removeCardIndex(int x) {
        String temp = deck.get(x).getName();
        deck.remove(x);
        return temp;
    }
}
