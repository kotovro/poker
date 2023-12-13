package ru.cs.vsu.oop.poker.base.combinations;

import ru.cs.vsu.oop.poker.base.Card;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class StraightFlush extends AbstractCombination {
    public StraightFlush(int rank) {
        super(rank);
    }

    @Override
    public String getName() {
        return "Straight Flush";
    }

    public LinkedList<Card> find(LinkedList<Card> hand) {
        Map<Card.Suits, Long> suitsCount = createSuitsCountMap(hand);
        LinkedList<Card> res = hand
                .stream()
                .filter(card -> suitsCount.get(card.getSuit()) > 4)
                .collect(Collectors.toCollection(LinkedList::new));
        if (res.size() == 0) {
            return null;
        }

        for (Card card : hand) {
            if (card.getSuit().equals(res.getFirst().getSuit())) {
                if (res.getLast().getCardWeight() == card.getCardWeight() + 1) {
                    res.add(card);
                    if (res.size() == 5) {
                        break;
                    }
                } else if (res.getLast().getCardWeight() != card.getCardWeight()) {
                    res.clear();
                    res.add(card);
                }
            }
        }
        if (res.size() == 4 && res.getFirst().getName() == Card.CardNames.FIVE && hand.getFirst().getName() == Card.CardNames.ACE) {
            Iterator<Card> iterator = hand.iterator();
            while (iterator.hasNext()) {
                Card curCard = iterator.next();
                if (!curCard.getName().equals(Card.CardNames.ACE)) {
                    break;
                }
                if (curCard.getSuit().equals(res.getFirst().getSuit())) {
                    //wheel condition found
                    res.add(curCard);
                    break;
                }
            }
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
