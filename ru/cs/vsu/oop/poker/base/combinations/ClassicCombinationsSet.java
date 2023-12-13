package ru.cs.vsu.oop.poker.base.combinations;
public enum ClassicCombinationsSet {
    BEST_CARD(new BestCard(0)),
    PAIR(new Pair(1)),
    TWO_PAIR(new TwoPairs(2)),
    THREE_OF_A_KIND(new ThreeOfAKind(3)),
    STRAIGHT(new Straight(4)),
    FLUSH(new Flush(5)),
    FULL_HOUSE(new FullHouse(6)),
    FOUR_OF_A_KIND(new FourOfAKind(7)),
    STRAIGHT_FLUSH(new StraightFlush(8));
    final AbstractCombination combination;
    ClassicCombinationsSet(AbstractCombination combination) {
        this.combination = combination;
    }
}
