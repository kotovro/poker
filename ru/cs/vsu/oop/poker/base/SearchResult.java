package ru.cs.vsu.oop.poker.base;

import java.util.LinkedList;

public class SearchResult {
    int rank;
    LinkedList<Card> bestCombo;
    String name;

    public SearchResult(int rank, LinkedList<Card> bestCombo, String name) {
        this.rank = rank;
        this.bestCombo = bestCombo;
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public LinkedList<Card> getBestCombo() {
        return bestCombo;
    }

    public String getName() {
        return name;
    }
}
