package ru.cs.vsu.oop.poker.base.combinations;

import ru.cs.vsu.oop.poker.base.Card;

import java.util.*;
import java.util.stream.Collectors;

public class TwoPairs extends AbstractCombination {
    public TwoPairs(int rank) {
        super(rank);
    }

    @Override
    public String getName() {
        return "Two Pairs";
    }

    public LinkedList<Card> find(LinkedList<Card> hand) {
        Map<Card.CardNames, Long> cardsCount = createCardsCountMap(hand);
        Optional<Card> card = hand
                .stream()
                .filter(c -> cardsCount.get(c.getName()) == 2)
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
                .filter(c -> cardsCount.get(c.getName()) == 2)
                .findFirst();

        if (secondCard.isEmpty()) {
            return null;
        }

        res.addAll(clone
                .stream()
                .filter(c -> c.getName().equals(secondCard.get().getName()))
                .collect(Collectors.toCollection(LinkedList::new)));

        clone.removeAll(res);
        res.addAll(clone);
        return res;
    }
}
