package ru.cs.vsu.oop.poker.base.combinations;

import ru.cs.vsu.oop.poker.base.Card;

import java.util.LinkedList;

public class Pair extends AbstractCombination {
    public Pair(int rank) {
        super(rank);
    }

    @Override
    public String getName() {
        return "Pair";
    }

    public LinkedList<Card> find(LinkedList<Card> hand) {
        return getBestNOfAKind(hand, 2);
    }
}
