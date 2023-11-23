package ru.cs.vsu.oop.poker.base;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public abstract class AbstractCombination {
    public class SearchResult {
        int rank;
        Card[] bestCombo;
        String name;

        public SearchResult(int rank, Card[] bestCombo, String name) {
            this.rank = rank;
            this.bestCombo = bestCombo;
            this.name = name;
        }

        public int getRank() {
            return rank;
        }

        public Card[] getBestCombo() {
            return bestCombo;
        }

        public String getName() {
            return name;
        }
    }
    protected Map<String, Integer> comboWeight;
    protected  Map<String, IComboChecker> finder;
    protected LinkedList<String> comboNames;

    public IComboChecker getFinder(String comboName) {
        if (comboNames.contains(comboName)) {
            return finder.get(comboName);
        } else {
            return null;
        }
    }
    public Integer getRank(String comboName) {
        if (comboNames.contains(comboName)) {
            return comboWeight.get(comboName);
        } else {
            return null;
        }
    }

    public SearchResult findCombination(UniversalHand hand) {
        Card[] bestCombo = hand.getSortedHand();
        String bestName = "";
        int bestRank = 0;
        for (String comboName: this.comboNames) {
            Card[] candidate = getFinder(comboName).find(hand);
            if (candidate != null && getRank(comboName) > bestRank) {
                bestRank = getRank(comboName);
                bestCombo = candidate;
                bestName = comboName;
            }
        }
        return new SearchResult(bestRank, bestCombo, bestName);
    }
    public static Card[] getBestStraight(UniversalHand hand) {
        return getBestStraight(hand, null);
    }

    public static Card[] getStraightFlush(UniversalHand hand) {
        Card.Suits suit = null;
        for (Card.Suits s: Card.Suits.values()) {
            if (hand.getSuitsCount(s) > 4) {
                suit = s;
                break;
            }
        }
        if (suit != null) {
            return getBestStraight(hand, suit);
        }
        return null;
    }
    private static Card[] getBestStraight(UniversalHand hand, Card.Suits suit) {
        Card[] buffer = hand.getSortedHand().clone();
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
                firstIndexOf = i;
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

    public static Card[] getBestFlush(UniversalHand hand) {
        Card[] buffer = hand.getSortedHand().clone();
        Card.Suits suit = null;
        for (int i = 0; i < buffer.length - 4; i++) {
            if (hand.getSuitsCount(buffer[i].getSuit()) > 4) {
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
    public static Card[] getBestPair(UniversalHand hand) {
        Card[] buffer = hand.getSortedHand().clone();
        return getBestNOfAKind(hand, buffer, 2, 0);
    }
    public static Card[] getBesThreeOfAKind(UniversalHand hand) {
        Card[] buffer = hand.getSortedHand().clone();
        return getBestNOfAKind(hand, buffer,3, 0);
    }
    public static Card[] getBestFourOfAKind(UniversalHand hand) {
        Card[] buffer = hand.getSortedHand().clone();
        return getBestNOfAKind(hand, buffer, 4, 0);
    }
    public static Card[] getBestTwoPair(UniversalHand hand) {
        Card[] buffer = hand.getSortedHand().clone();
        Card[] res = getBestNOfAKind(hand, buffer, 2, 0);
        if (res != null) {
            return getBestNOfAKind(hand, buffer,2, 2);
        }
        return null;
    }
    public static Card[] getBestFullHouse(UniversalHand hand) {
        Card[] buffer = hand.getSortedHand().clone();
        Card[] res = getBestNOfAKind(hand, buffer, 3, 0);
        if (res != null) {
            return getBestNOfAKind(hand, buffer,2, 3);
        }
        return null;
    }

    private static Card[] getBestNOfAKind(UniversalHand hand, Card[] buffer, int count, int startIndex) {
        int firstIndexOf = -1;
        for (int i = startIndex; i < buffer.length; i++) {
            Card card = buffer[i];
            if (hand.getCardsCount(card.getName()) >= count) {
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
