import java.util.LinkedList;
import java.util.Random;

public class Deck {

    private static Random random = new Random();
    private LinkedList<Card> deckOfCards = new LinkedList<>();

    public Deck() {
        for (Card.Suits suit: Card.Suits.values()) {
            for(Card.CardNames cardName: Card.CardNames.values()) {
                deckOfCards.add(new Card(suit, cardName));
            }
        }
    }

    public Card drawCard() {
        int cardNumber = random.nextInt(this.deckOfCards.size());
        return deckOfCards.remove(cardNumber);
    }

}