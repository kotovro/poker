package ru.cs.vsu.oop.poker.games.logic.omahaholdem;

import ru.cs.vsu.oop.poker.base.AbstractCombination;
import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Player;
import ru.cs.vsu.oop.poker.base.UniversalHand;

import java.util.Arrays;

public class OmahaHoldemPlayer extends Player {
    protected Card[] ownHand = new Card[4];
    public OmahaHoldemPlayer(double budget, boolean isBot) { super(budget, isBot); }


    public void findBestHand(Card[] table, AbstractCombination cmb) {
        UniversalHand bestHand = null;
        Card[] cardBuffer = new Card[table.length];
        for (int i = 0; i < ownHand.length - 1; i++) {
            for (int j = i + 1; j < ownHand.length; j++) {
                for (int k = 0; k < table.length - 2; k++) {
                    for (int l = k + 1; l < table.length - 1; l++) {
                        for (int m = l + 1; m < table.length; m++) {
                            cardBuffer[0] = ownHand[i];
                            cardBuffer[1] = ownHand[j];
                            cardBuffer[2] = table[k];
                            cardBuffer[3] = table[l];
                            cardBuffer[4] = table[m];
                            AbstractCombination.SearchResult res = cmb.findCombination(cardBuffer);

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
