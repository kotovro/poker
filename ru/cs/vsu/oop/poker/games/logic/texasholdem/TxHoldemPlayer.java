package ru.cs.vsu.oop.poker.games.logic.texasholdem;

import ru.cs.vsu.oop.poker.base.*;
import ru.cs.vsu.oop.poker.base.combinations.ClassicCombinationsSet;
import ru.cs.vsu.oop.poker.base.combinations.SearchResultFactory;

import java.util.LinkedList;

public class TxHoldemPlayer extends Player implements Cloneable {
    protected LinkedList<Card> ownHand = new LinkedList<>();
    public TxHoldemPlayer(double budget, boolean isBot, String name) { super(budget, isBot, name); }


    public void findBestHand(LinkedList<Card> table) {
        LinkedList<Card> cardBuffer = new LinkedList<>(table);
        cardBuffer.addAll(ownHand);
        hand = new UniversalHand();
        SearchResult res = SearchResultFactory.getSearchResult(cardBuffer, ClassicCombinationsSet.class);
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
    public LinkedList<Card> getOwnHand() {
        return ownHand;
    }

    public void clearOwnHand() {
        ownHand.clear();
    }

    @Override
    public TxHoldemPlayer clone() {
        TxHoldemPlayer clone = (TxHoldemPlayer) super.clone();
        clone.ownHand = (LinkedList<Card>) this.ownHand.clone();
        return clone;
    }
}
