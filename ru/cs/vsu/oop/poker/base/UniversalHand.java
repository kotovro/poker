package ru.cs.vsu.oop.poker.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class UniversalHand implements Comparable<UniversalHand> {
    private Card[] bestHand;
    private Card[] sortedHand;
    private int rank;

    private HashMap<Card.CardNames, Integer> cardsCount = new HashMap<>();
    private HashMap<Card.Suits, Integer> suitsCount = new HashMap<>();

    public Card[] getBestHand() {
        return bestHand;
    }

    public Card[] getSortedHand() {
        return sortedHand;
    }

    public int getRank() {
        return rank;
    }

    public void setBestHand(Card[] bestHand) {
        this.bestHand = bestHand;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public UniversalHand(Card[] hand) {
        sortedHand = hand.clone();
        Arrays.sort(sortedHand, Collections.reverseOrder());
        rank = 0;
        bestHand = sortedHand;
        buildMaps();
    }

    private void buildMaps() {
        for (Card.Suits suit: Card.Suits.values()) {
            suitsCount.put(suit, 0);
        }
        for (Card.CardNames cardName: Card.CardNames.values()) {
            cardsCount.put(cardName, 0);
        }
        for (Card card: sortedHand) {
            suitsCount.put(card.getSuit(), suitsCount.get(card.getSuit()) + 1);
            cardsCount.put(card.getName(), cardsCount.get(card.getName()) + 1);
        }
    }

    public int getCardsCount(Card.CardNames cardName) {
        return cardsCount.get(cardName);
    }
    public int getSuitsCount(Card.Suits suit) {
        return suitsCount.get(suit);
    }

    @Override
    public int compareTo(UniversalHand hand) {
        int cmpRes = Integer.compare(this.rank, hand.getRank());
        if (cmpRes != 0) {
            return cmpRes;
        }
        for (int i = 0; i < 5; i++) {
            cmpRes = Integer.compare(this.bestHand[i].getName().getCardWeight(), hand.getBestHand()[i].getName().getCardWeight());
            if (cmpRes != 0) {
                return cmpRes;
            }
        }
        return 0;
    }

    public Card[] getActualHand() {
        Card[] res = new Card[5];
        if (bestHand != null) {
            for (int i = 0; i < 5; i++) {
                res[i] = this.bestHand[i];
            }
            return res;
        }
        return null;
    }
}
