package ru.cs.vsu.oop.poker.base.combinations;

import ru.cs.vsu.oop.poker.base.Card;

import java.util.LinkedList;

public class Straight extends AbstractCombination {
    public Straight(int rank) {
        super(rank);
    }

    @Override
    public String getName() {
        return "Straight";
    }

    public LinkedList<Card> find(LinkedList<Card> hand) {
        LinkedList<Card> res = new LinkedList<>();
        res.add(hand.getFirst());
        for (Card card: hand) {
            if(res.getLast().getCardWeight() == card.getCardWeight() + 1) {
                res.add(card);
                if (res.size() == 5) {
                    break;
                }
            } else if (res.getLast().getCardWeight() != card.getCardWeight()) {
                res.clear();
                res.add(card);
            }
        }
        if(res.size() == 4 && res.getFirst().getName() == Card.CardNames.FIVE && hand.getFirst().getName() == Card.CardNames.ACE) {
            //wheel condition found
            res.add(hand.get(0));
        }
        if (res.size() < 5) {
            return null;
        }
        hand.forEach(c -> {
            if (!res.contains(c)) {
                res.add(c);
            }
        });
        return res;
    }
}
