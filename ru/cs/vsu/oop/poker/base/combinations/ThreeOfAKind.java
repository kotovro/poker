package ru.cs.vsu.oop.poker.base.combinations;

import ru.cs.vsu.oop.poker.base.Card;

import java.util.LinkedList;

public class ThreeOfAKind extends AbstractCombination {
    public ThreeOfAKind(int rank) {
        super(rank);
    }

    @Override
    public String getName() {
        return "Three of a Kind";
    }

    public LinkedList<Card> find(LinkedList<Card> hand) {
        return getBestNOfAKind(hand, 3);
    }
}
