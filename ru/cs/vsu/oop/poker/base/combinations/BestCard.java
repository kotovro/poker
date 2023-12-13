package ru.cs.vsu.oop.poker.base.combinations;

import ru.cs.vsu.oop.poker.base.Card;

import java.util.LinkedList;

public class BestCard extends AbstractCombination {
    public BestCard(int rank) {
        super(rank);
    }

    @Override
    public String getName() {
        return "Best Card";
    }

    public LinkedList<Card> find(LinkedList<Card> hand) {
        return hand;
    }
}
