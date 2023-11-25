package ru.cs.vsu.oop.poker.base;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public abstract class AbstractCombination {
    public class SearchResult {
        int rank;
        Card[] bestCombo;
        String name;

        public SearchResult(int rank, Card[] bestCombo, String name) {
            this.rank = rank;
            this.bestCombo = bestCombo;
            this.name = name;
        }

        public int getRank() {
            return rank;
        }

        public Card[] getBestCombo() {
            return bestCombo;
        }

        public String getName() {
            return name;
        }
    }
    protected Map<String, Integer> comboWeight;
    protected  Map<String, IComboFinder> finder;
    protected LinkedList<String> comboNames;

    public IComboFinder getFinder(String comboName) {
        if (comboNames.contains(comboName)) {
            return finder.get(comboName);
        } else {
            return null;
        }
    }
    public Integer getRank(String comboName) {
        if (comboNames.contains(comboName)) {
            return comboWeight.get(comboName);
        } else {
            return null;
        }
    }

    public SearchResult findCombination(Card[] hand) {
        Card[] bestCombo = hand;
        String bestName = "NONE";
        int bestRank = 0;
        for (String comboName: this.comboNames) {
            Card[] candidate = getFinder(comboName).find(hand.clone());
            if (candidate != null && getRank(comboName) > bestRank) {
                bestRank = getRank(comboName);
                bestCombo = candidate;
                bestName = comboName;
            }
        }
        return new SearchResult(bestRank, bestCombo, bestName);
    }
}
