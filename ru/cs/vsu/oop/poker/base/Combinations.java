package ru.cs.vsu.oop.poker.base;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public enum Combinations {
    NONE(0),
    PAIR(1),
    TWO_PAIR(2),
    THREE_OF_A_KIND(3),
    STRAIGHT(4),
    FLUSH(5),
    FULL_HOUSE(6),
    FOUR_OF_A_KIND(7),
    STRAIGHT_FLUSH(8);

    private final int weight;

    Combinations(int weight) {
        this.weight = weight;
    }

    public int getComboWeight() {
        return weight;
    }

    private static final Map<Integer, Combinations> lookup = new HashMap<>();
    private static final LinkedList<Integer> allWeights = new LinkedList<>();

    static {
        for (Combinations name : Combinations.values()) {
            lookup.put(name.getComboWeight(), name);
            allWeights.add(name.getComboWeight());
        }
    }

    public static LinkedList<Integer> allWeights() {
        return allWeights;
    }

    public static Combinations name(Integer weight) {
        return lookup.get(weight);
    }
}
