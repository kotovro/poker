package ru.cs.vsu.oop.poker.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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
        int firstIndexOf = -1;
        int i = 0;
        for (Card card : hand) {
            if (cardsCount.get(card.getName()) == 3) {
                firstIndexOf = i;
                break;
            }
            i++;
        }
        if (firstIndexOf < 0) {
            return null;
        }
        LinkedList<Card> clone = new LinkedList<>(hand);
        LinkedList<Card> res = new LinkedList<>();
        for (i = 0; i < 3; i++) {
            res.add(clone.remove(firstIndexOf));
        }
        i = 0;
        firstIndexOf = -1;
        for (Card card : clone) {
            if (cardsCount.get(card.getName()) > 1) {
                firstIndexOf = i;
                break;
            }
            i++;
        }
        if (firstIndexOf < 0) {
            return null;
        }
        for (i = 0; i < 2; i++) {
            res.add(clone.remove(firstIndexOf));
        }
        res.addAll(clone);
        return res;
    }
}
