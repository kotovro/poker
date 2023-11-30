package ru.cs.vsu.oop.poker.games.logic.texasholdem;

import ru.cs.vsu.oop.poker.base.*;

import java.util.Arrays;
import java.util.LinkedList;

public class TxHoldemPlayer extends Player {
    protected LinkedList<Card> ownHand = new LinkedList<>();
    public TxHoldemPlayer(double budget, boolean isBot, String name) { super(budget, isBot, name); }


    public void findBestHand(LinkedList<Card> table) {
        LinkedList<Card> cardBuffer = new LinkedList<>(table);
        cardBuffer.addAll(ownHand);
        hand = new UniversalHand();
        SearchResult res = ClassicCombinationsSet.findCombination(cardBuffer);
        hand.setBestHand(res.getBestCombo());
        hand.setRank(res.getRank());
        hand.setCombinationName(res.getName());
    }
    public void setOwnCard(Card card) {
        ownHand.add(card);
    }

    public boolean isBot() {
        return isBot;
    }

    public Card getOwnHand(int i) {
        return ownHand.get(i);
    }

    public void clearOwnHand() {
        ownHand.clear();
    }
}
