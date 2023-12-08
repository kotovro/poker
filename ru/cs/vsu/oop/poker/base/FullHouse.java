package ru.cs.vsu.oop.poker.base;

import java.util.*;
import java.util.stream.Collectors;

public class FullHouse extends AbstractCombination {
    public FullHouse(int rank) {
        super(rank);
    }

    @Override
    public String getName() {
        return "Full House";
    }

    public LinkedList<Card> find(LinkedList<Card> hand) {
        Map<Card.CardNames, Long> cardsCount = createCardsCountMap(hand);
        Optional<Card> card = hand
                .stream()
                .filter(c -> cardsCount.get(c.getName()) == 3)
                .findFirst();
        if (card.isEmpty()) {
            return null;
        }
        LinkedList<Card> clone = new LinkedList<>(hand);
        LinkedList<Card> res = clone
                .stream()
                .filter(c -> c.getName().equals(card.get().getName()))
                .collect(Collectors.toCollection(LinkedList::new));
        clone.removeAll(res);

        Optional<Card> secondCard = clone
                .stream()
                .filter(c -> cardsCount.get(c.getName()) >= 2)
                .findFirst();
        if (secondCard.isEmpty()) {
            return null;
        }
        res.addAll(clone
                .stream()
                .filter(c -> c.getName().equals(secondCard.get().getName()))
                .limit(2)
                .collect(Collectors.toCollection(LinkedList::new)));
        clone.removeAll(res);
        res.addAll(clone);
        return res;
    }
}
