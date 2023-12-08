package ru.cs.vsu.oop.poker.games.logic.omahaholdem;

import ru.cs.vsu.oop.poker.base.*;

import java.util.LinkedList;

public class OmahaHoldemPlayer extends Player {
    protected LinkedList<Card> ownHand = new LinkedList<>();
    public OmahaHoldemPlayer(double budget, boolean isBot, String name) { super(budget, isBot, name); }


    public void findBestHand(LinkedList<Card> table) {
        UniversalHand bestHand = null;
        LinkedList<Card> cardBuffer = new LinkedList<Card>();
        Card[] handBuffer = ownHand.toArray(new Card[0]);
        Card[] tableBuffer = table.toArray(new Card[0]);
        for (int i = 0; i < handBuffer.length - 1; i++) {
            for (int j = i + 1; j < handBuffer.length; j++) {
                for (int k = 0; k < tableBuffer.length - 2; k++) {
                    for (int l = k + 1; l < tableBuffer.length - 1; l++) {
                        for (int m = l + 1; m < tableBuffer.length; m++) {
                            cardBuffer.add(handBuffer[i]);
                            cardBuffer.add(handBuffer[j]);
                            cardBuffer.add(tableBuffer[k]);
                            cardBuffer.add(tableBuffer[l]);
                            cardBuffer.add(tableBuffer[m]);
                            SearchResult res = ClassicCombinationsSet.findCombination(cardBuffer);

                            UniversalHand  candidate = new UniversalHand();
                            candidate.setBestHand(res.getBestCombo());
                            candidate.setRank(res.getRank());
                            if (bestHand == null || bestHand.compareTo(candidate) < 0) {
                                bestHand = candidate;
                            }
                        }
                    }
                }
            }
        }
        hand = bestHand;
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
