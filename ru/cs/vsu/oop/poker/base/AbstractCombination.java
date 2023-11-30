package ru.cs.vsu.oop.poker.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class AbstractCombination implements Comparable<AbstractCombination>, ICombinationFinder{
    protected HashMap<Card.CardNames, Integer> cardsCount = new HashMap<>();
    protected HashMap<Card.Suits, Integer> suitsCount = new HashMap<>();
    protected int rank;

    public AbstractCombination(int rank) {
        this.rank = rank;
    }
    public abstract String getName();

    @Override
    public int compareTo(AbstractCombination cmb) {
        int res = Integer.compare(this.rank, cmb.getRank());
        return res;
    }

    protected void createMaps(LinkedList<Card> hand) {
        for (Card card: hand) {
            int count = 0;
            if (cardsCount.containsKey(card.getName())) {
                count = cardsCount.get(card.getName());
            }
            cardsCount.put(card.getName(), count + 1);
            count = 0;
            if (suitsCount.containsKey(card.getSuit())) {
                count = suitsCount.get(card.getSuit());
            }
            suitsCount.put(card.getSuit(), count + 1);
        }
    }
    protected LinkedList<Card> getBestNOfAKind(LinkedList<Card> hand, int countN) {
        createMaps(hand);
        int firstIndexOf = -1;
        int i = 0;
        for (Card card: hand) {
            if (cardsCount.get(card.getName()) == countN) {
                firstIndexOf = i;
                break;
            }
            i++;
        }
        if (firstIndexOf < 0) {
            return null;
        } else {
            LinkedList<Card> clone = new LinkedList<>();
            Collections.copy(clone, hand);
            LinkedList<Card> res = new LinkedList<>();
            for (i = 0; i < countN; i++) {
                res.add(clone.remove(firstIndexOf));
            }
            res.addAll(clone);
            return res;
        }
    }

    public int getRank() {
        return rank;
    }
}
