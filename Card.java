import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Card implements Comparable<Card> {
    @Override
    public int compareTo(Card c) {
        return Integer.compare(this.name.getCardWeight(), c.name.getCardWeight());
    }

    public enum Suits {
        SPADES,
        CROSSES,
        DIAMONDS,
        HEARTS
    }

    public enum CardNames {
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(11),
        QUEEN(12),
        KING(13),
        ACE(14);

        private final int weight;

        CardNames(int weight) {
            this.weight = weight;
        }

        public int getCardWeight() {
            return weight;
        }

        private static final Map<Integer, CardNames> lookup = new HashMap<>();
        private static final LinkedList<Integer> allWeights = new LinkedList<>();

        static {
            for (CardNames name : CardNames.values()) {
                lookup.put(name.getCardWeight(), name);
                allWeights.add(name.getCardWeight());
            }
        }

        public static LinkedList<Integer> allWeights() {
            return allWeights;
        }

        public static CardNames name(Integer weight) {
            return lookup.get(weight);
        }
    }
    private Suits suit;
    private CardNames name;

    public Suits getSuit() {
        return suit;
    }

    public CardNames getName() {
        return name;
    }

    public Card(Suits suit, CardNames name) {
        this.suit = suit;
        this.name = name;
    }
}
