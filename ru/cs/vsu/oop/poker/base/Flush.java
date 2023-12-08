package ru.cs.vsu.oop.poker.base;

import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Flush extends AbstractCombination {
    public Flush(int rank) {
        super(rank);
    }

    @Override
    public String getName() {
        return "Flush";
    }

    public LinkedList<Card> find(LinkedList<Card> hand) {
        Map<Card.Suits, Long> suitsCount = createSuitsCountMap(hand);
        LinkedList<Card> clone = new LinkedList<>(hand);
        LinkedList<Card> res = clone.stream()
                .filter(c -> suitsCount.get(c.getSuit()) >= 5)
                .limit(5)
                .collect(Collectors.toCollection(LinkedList::new));

        if (res.size() == 0) {
            return null;
        }

        clone.removeAll(res);
        res.addAll(clone);
        return res;
    }
}