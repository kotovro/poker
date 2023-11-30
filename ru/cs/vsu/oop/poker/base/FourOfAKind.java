package ru.cs.vsu.oop.poker.base;

import java.util.LinkedList;

public class FourOfAKind extends AbstractCombination {
    public FourOfAKind(int rank) {
        super(rank);
    }

    @Override
    public String getName() {
        return "Four of a Kind";
    }

    public LinkedList<Card> find(LinkedList<Card> hand) {
        return getBestNOfAKind(hand, 4);
    }
}
