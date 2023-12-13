package ru.cs.vsu.oop.poker.base.combinations;

import ru.cs.vsu.oop.poker.base.Card;


import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractCombination implements Comparable<AbstractCombination>, ICombinationFinder {

    protected int rank;
    public int getRank() {
        return rank;
    }

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
        Optional<Card> card = hand.stream()
                .filter(c -> cardsCount.get(c.getName()) == countN)
                .findFirst();

        if (card.isEmpty()) {
            return null;
        }

        LinkedList<Card> clone = new LinkedList<>(hand);
        LinkedList<Card> res = clone.stream()
                .filter(c -> c.getName().equals(card.get().getName()))
                .collect(Collectors.toCollection(LinkedList::new));
        clone.removeAll(res);
        res.addAll(clone);
        return res;
    }
}
