package ru.cs.vsu.oop.poker.base;

import java.util.LinkedList;

public class Flush extends AbstractCombination {
    public Flush(int rank) {
        super(rank);
    }

    @Override
    public String getName() {
        return "Flush";
    }

    public LinkedList<Card> find(LinkedList<Card> hand) {
        Card.Suits suit = null;
        for (Card card: hand) {
            if(suitsCount.get(card.getSuit()) >= 5) {
                suit = card.getSuit();
                break;
            }
        }
        if (suit != null) {
            LinkedList<Card> res = new LinkedList<>();
            for (Card card: hand) {
                if (card.getSuit().equals(suit)) {
                    res.add(card);
                    if (res.size() == 5) {
                        break;
                    }
                }
            }
            for (Card card: hand) {
                if (!res.contains(card)) {
                    res.add(card);
                }
            }
            return res;
        }
        return null;
    }
}