package ru.cs.vsu.oop.poker.base;

import java.util.*;

public class ClassicCombo extends AbstractCombination{

    private HashMap<Card.CardNames, Integer> cardsCount = new HashMap<>();
    private HashMap<Card.Suits, Integer> suitsCount = new HashMap<>();
    public ClassicCombo() {
        this.comboNames = new LinkedList<>();
        this.comboWeight = new HashMap<>();
        this.finder = new HashMap<>();
        this.comboNames.add("NONE");
        this.comboNames.add("PAIR");
        this.comboNames.add("TWO_PAIR");
        this.comboNames.add("THREE_OF_A_KIND");
        this.comboNames.add("STRAIGHT");
        this.comboNames.add("FLUSH");
        this.comboNames.add("FULL_HOUSE");
        this.comboNames.add("FOUR_OF_A_KIND");
        this.comboNames.add("STRAIGHT_FLUSH");

        this.comboWeight.put("NONE", 0);
        this.comboWeight.put("PAIR", 1);
        this.comboWeight.put("TWO_PAIR",2);
        this.comboWeight.put("THREE_OF_A_KIND",3);
        this.comboWeight.put("STRAIGHT", 4);
        this.comboWeight.put("FLUSH", 5);
        this.comboWeight.put("FULL_HOUSE", 6);
        this.comboWeight.put("FOUR_OF_A_KIND", 7);
        this.comboWeight.put("STRAIGHT_FLUSH", 8);

        this.finder.put("NONE", h -> h);
        this.finder.put("PAIR", h -> getBestPair(h));
        this.finder.put("TWO_PAIR",h -> getBestTwoPair(h));
        this.finder.put("THREE_OF_A_KIND",h -> getBesThreeOfAKind(h));
        this.finder.put("STRAIGHT", h -> getBestStraight(h));
        this.finder.put("FLUSH", h -> getBestFlush(h));
        this.finder.put("FULL_HOUSE", h -> getBestFullHouse(h));
        this.finder.put("FOUR_OF_A_KIND", h -> getBestFourOfAKind(h));
        this.finder.put("STRAIGHT_FLUSH", h -> getStraightFlush(h));
    }

    @Override
    public SearchResult findCombination(Card[] hand) {
        Arrays.sort(hand, Collections.reverseOrder());
        buildMaps(hand);
        return super.findCombination(hand);
        
    }
    public int getCardsCount(Card.CardNames cardName) {
        return cardsCount.get(cardName);
    }
    public int getSuitsCount(Card.Suits suit) {
        return suitsCount.get(suit);
    }

    private void buildMaps(Card[] hand) {
        for (Card.Suits suit: Card.Suits.values()) {
            suitsCount.put(suit, 0);
        }
        for (Card.CardNames cardName: Card.CardNames.values()) {
            cardsCount.put(cardName, 0);
        }
        for (Card card: hand) {
            suitsCount.put(card.getSuit(), suitsCount.get(card.getSuit()) + 1);
            cardsCount.put(card.getName(), cardsCount.get(card.getName()) + 1);
        }
    }
    public static Card[] getBestStraight(Card[] hand) {
        return getBestStraight(hand, null);
    }

    public Card[] getStraightFlush(Card[] hand) {
        Card.Suits suit = null;
        for (Card.Suits s: Card.Suits.values()) {
            if (getSuitsCount(s) > 4) {
                suit = s;
                break;
            }
        }
        if (suit != null) {
            return getBestStraight(hand, suit);
        }
        return null;
    }
    private static Card[] getBestStraight(Card[] hand, Card.Suits suit) {
        Card[] buffer = hand.clone();
        int inStraight = 0;
        int firstIndexOf = -1;
        boolean aceFound = buffer[0].getName().equals(Card.CardNames.ACE)
                && suit == null || isAceSuitFound(buffer, suit);
        for (int i = 0; i < buffer.length - 1; i++) {
            if (inStraight == 4
                    && buffer[i].getName().equals(Card.CardNames.TWO)
                    && aceFound) {
                break; // wheel condition: when ace's weight is equal ONE
            }
            if (buffer[i].getName().getCardWeight() == buffer[i+1].getName().getCardWeight() + 1 //regular straight
                    && (suit == null || buffer[i].getSuit().equals(suit) && buffer[i + 1].getSuit().equals(suit))) {
                inStraight++;
                if (firstIndexOf < 0) {
                    firstIndexOf = i;
                }
            } else {
                inStraight = 0;
                firstIndexOf = -1;
            }
        }
        if (inStraight == 4
                && buffer[firstIndexOf + 3].getName().equals(Card.CardNames.TWO)
                && aceFound) {
            // wheel condition: when ace's weight is equal ONE
            if (suit != null) {
                adjustAcePosition(buffer, suit);
            }
            return moveTo(buffer, 0, 4, firstIndexOf);
        } else if (inStraight > 4) {
            return moveTo(buffer, 0, 5, firstIndexOf);
        } else {
            return null;
        }
    }

    private static void adjustAcePosition(Card[] buffer, Card.Suits suit) {
        int i = 0;
        while(buffer[i].getName().equals(Card.CardNames.ACE)) {
            if (buffer[i].getSuit().equals(suit)) {
                if (i != 0) {
                    Card temp = buffer[0];
                    buffer[0] = buffer[i];
                    buffer[i] = temp;
                }
                return;
            }
            i++;
        }
    }
    private static boolean isAceSuitFound(Card[] buffer, Card.Suits suit) {
        int i = 0;
        while(buffer[i].getName().equals(Card.CardNames.ACE)) {
            if (buffer[i].getSuit().equals(suit)) {
                return true;
            }
            i++;
        }
        return false;
    }

    public Card[] getBestFlush(Card[] hand) {
        Card[] buffer = hand.clone();
        Card.Suits suit = null;
        for (int i = 0; i < buffer.length - 4; i++) {
            if (getSuitsCount(buffer[i].getSuit()) > 4) {
                suit = buffer[i].getSuit();
                break;
            }
        }
        if (suit == null) {
            return null;
        }
        int inFlush = 0;
        int i = 0;
        Queue<Card> queue = new LinkedList<>();
        while (inFlush < 5) {
            if (buffer[i].getSuit().equals(suit)) {
                inFlush++;
                if (queue.size() != 0) {
                    buffer[i - queue.size()] = buffer[i];
                }
            } else {
                queue.add(buffer[i]);
            }
            i++;
        }
        for (i = 5; i < 5 + queue.size(); i++) {
            buffer[i] = queue.poll();
        }
        return buffer;
    }
    public Card[] getBestPair(Card[] hand) {
        Card[] buffer = hand.clone();
        return getBestNOfAKind(hand, buffer, 2, 0);
    }
    public Card[] getBesThreeOfAKind(Card[] hand) {
        Card[] buffer = hand.clone();
        return getBestNOfAKind(hand, buffer,3, 0);
    }
    public Card[] getBestFourOfAKind(Card[] hand) {
        Card[] buffer = hand.clone();
        return getBestNOfAKind(hand, buffer, 4, 0);
    }
    public Card[] getBestTwoPair(Card[] hand) {
        Card[] buffer = hand.clone();
        Card[] res = getBestNOfAKind(hand, buffer, 2, 0);
        if (res != null) {
            return getBestNOfAKind(hand, buffer,2, 2);
        }
        return null;
    }
    public Card[] getBestFullHouse(Card[] hand) {
        Card[] buffer = hand.clone();
        Card[] res = getBestNOfAKind(hand, buffer, 3, 0);
        if (res != null) {
            Card[] tmp = getBestNOfAKind(hand, buffer,2, 3);
            if (tmp == null && buffer.length > 5) {
                return getBestNOfAKind(hand, buffer, 3, 3);
            } else {
                return tmp;
            }
        }
        return null;
    }

    private Card[] getBestNOfAKind(Card[] hand, Card[] buffer, int count, int startIndex) {
        int firstIndexOf = -1;
        for (int i = startIndex; i < buffer.length; i++) {
            Card card = buffer[i];
            if (getCardsCount(card.getName()) == count) {
                firstIndexOf = i;
                break;
            }
        }
        if (firstIndexOf < 0) {
            return null;
        } else {
            return moveTo(buffer, startIndex, count, firstIndexOf);
        }
    }

    private static Card[] moveTo(Card[] buffer, int startIndex, int amount, int firstIndex) {

        Card[] tmp = new Card[firstIndex - startIndex];
        for (int i = startIndex; i < firstIndex; i++) {
            tmp[i - startIndex] = buffer[i];
        }
        for (int i = startIndex; i < startIndex + amount; i++) {
            buffer[i] = buffer[firstIndex + i - startIndex];
        }
        for (int i = startIndex + amount; i < firstIndex + amount; i++) {
            buffer[i] = tmp[i - startIndex - amount];
        }
        return buffer;
    }
}
