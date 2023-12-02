package ru.cs.vsu.oop.poker.base;

import java.util.Comparator;
import java.util.LinkedList;

public enum CombinationsSet {
    BEST_CARD(new BestCard(0)),
    PAIR(new Pair(1)),
    TWO_PAIR(new TwoPairs(2)),
    THREE_OF_A_KIND(new ThreeOfAKind(3)),
    STRAIGHT(new Straight(4)),
    FLUSH(new Flush(5)),
    FULL_HOUSE(new FullHouse(6)),
    FOUR_OF_A_KIND(new FourOfAKind(7)),
    STRAIGHT_FLUSH(new StraightFlush(8));
    private final AbstractCombination combination;
    CombinationsSet(AbstractCombination combination) {
        this.combination = combination;
    }
    public static SearchResult findCombination(LinkedList<Card> hand) {
        LinkedList<Card> clone = new LinkedList<>(hand);
        clone.sort(Comparator.comparing(Card::getCardWeight, Comparator.reverseOrder()));

        LinkedList<Card> bestCombo = clone;
        String bestName = BEST_CARD.combination.getName();
        int bestRank = BEST_CARD.combination.getRank();

        for (CombinationsSet cmb: CombinationsSet.values()) {
            LinkedList<Card> candidate = cmb.combination.find(clone);
            if (candidate != null && cmb.combination.getRank() > bestRank) {
                bestRank = cmb.combination.getRank();
                bestCombo = candidate;
                bestName = cmb.combination.getName();
            }
        }
        return new SearchResult(bestRank, bestCombo, bestName);
    }
}
