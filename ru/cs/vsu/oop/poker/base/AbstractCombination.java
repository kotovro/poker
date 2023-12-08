package ru.cs.vsu.oop.poker.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractCombination implements Comparable<AbstractCombination>, ICombinationFinder{

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

    protected Map<Card.CardNames, Long> createCardsCountMap(LinkedList<Card> hand) {
        return hand.stream()
                .collect(Collectors.groupingBy(Card::getName,
                        Collectors.counting()));
    }
    protected Map<Card.Suits, Long> createSuitsCountMap(LinkedList<Card> hand) {
        return hand.stream()
                .collect(Collectors.groupingBy(Card::getSuit,
                        Collectors.counting()));
    }
    protected LinkedList<Card> getBestNOfAKind(LinkedList<Card> hand, int countN) {
        Map<Card.CardNames, Long> cardsCount = createCardsCountMap(hand);
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
            LinkedList<Card> clone = new LinkedList<>(hand);
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
