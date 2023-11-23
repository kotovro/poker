package ru.cs.vsu.oop.poker.base;

import java.util.HashMap;
import java.util.LinkedList;

public class ClassicCombo extends AbstractCombination{
    public ClassicCombo() {
        this.comboNames = new LinkedList<>();
        this.comboWeight = new HashMap<>();
        this.finder = new HashMap<>();
        this.comboNames.add("NONE");
        this.comboNames.add("PAIR");
        this.comboNames.add("TWO_PAIR");
        this.comboNames.add("THREE_OF_A_KIND");
        this.comboNames.add("STRAIGHT");
        this.comboNames.add("FLUSH");
        this.comboNames.add("FULL_HOUSE");
        this.comboNames.add("FOUR_OF_A_KIND");
        this.comboNames.add("STRAIGHT_FLUSH");

        this.comboWeight.put("NONE", 0);
        this.comboWeight.put("PAIR", 1);
        this.comboWeight.put("TWO_PAIR",2);
        this.comboWeight.put("THREE_OF_A_KIND",3);
        this.comboWeight.put("STRAIGHT", 4);
        this.comboWeight.put("FLUSH", 5);
        this.comboWeight.put("FULL_HOUSE", 6);
        this.comboWeight.put("FOUR_OF_A_KIND", 7);
        this.comboWeight.put("STRAIGHT_FLUSH", 8);

        this.finder.put("NONE", h -> h.getSortedHand());
        this.finder.put("PAIR", h -> getBestPair(h));
        this.finder.put("TWO_PAIR",h -> getBestTwoPair(h));
        this.finder.put("THREE_OF_A_KIND",h -> getBesThreeOfAKind(h));
        this.finder.put("STRAIGHT", h -> getBestStraight(h));
        this.finder.put("FLUSH", h -> getBestFlush(h));
        this.finder.put("FULL_HOUSE", h -> getBestFullHouse(h));
        this.finder.put("FOUR_OF_A_KIND", h -> getBestFourOfAKind(h));
        this.finder.put("STRAIGHT_FLUSH", h -> getStraightFlush(h));
    }
}
