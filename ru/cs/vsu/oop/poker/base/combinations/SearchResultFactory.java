package ru.cs.vsu.oop.poker.base.combinations;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.SearchResult;

import java.util.Comparator;
import java.util.LinkedList;


public class SearchResultFactory {
    public static SearchResult getSearchResult(LinkedList<Card> hand, Class<?> combinationSet) {
        if(combinationSet == ClassicCombinationsSet.class) {
            return findBestClassicCombination(hand);
        }
        return null;
    }
    public static SearchResult findBestClassicCombination(LinkedList<Card> hand) {
        LinkedList<Card> clone = new LinkedList<>(hand);
        clone.sort(Comparator.comparing(Card::getCardWeight, Comparator.reverseOrder()));

        LinkedList<Card> bestCombo = clone;
        String bestName = ClassicCombinationsSet.BEST_CARD.combination.getName();
        int bestRank = ClassicCombinationsSet.BEST_CARD.combination.getRank();

        for (ClassicCombinationsSet cmb: ClassicCombinationsSet.values()) {
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
