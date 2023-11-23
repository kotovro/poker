package ru.cs.vsu.oop.poker.texasholdem.logic;

import ru.cs.vsu.oop.poker.base.*;

import java.util.Arrays;

public class TxHoldemPlayer extends Player {
    protected Card[] ownHand = new Card[2];
    public TxHoldemPlayer(double budget, boolean isBot) { super(budget, isBot); }


    public void findBestHand(Card[] table, AbstractCombination cmb) {
        Card[] cardBuffer = new Card[table.length + ownHand.length];
        for (int i = 0; i < table.length; i++) {
            cardBuffer[i] = table[i];
        }
        for (int i = table.length; i <  table.length + ownHand.length; i++) {
            cardBuffer[i] = ownHand[i - table.length];

        }
        hand = new UniversalHand(cardBuffer);
        AbstractCombination.SearchResult res = cmb.findCombination(hand);
        hand.setBestHand(res.getBestCombo());
        hand.setRank(res.getRank());
    }
    public void setOwnCard(int idx, Card card) {
        ownHand[idx] = card;
    }

    public boolean isBot() {
        return isBot;
    }

    public Card getOwnHand(int i) {
        return ownHand[i];
    }

    public void clearOwnHand() {
        Arrays.fill(ownHand, null);
    }
}
