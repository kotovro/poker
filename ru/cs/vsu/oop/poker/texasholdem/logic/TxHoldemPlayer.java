package ru.cs.vsu.oop.poker.texasholdem.logic;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Hand;
import ru.cs.vsu.oop.poker.base.HandHelper;
import ru.cs.vsu.oop.poker.base.Player;

import java.util.Arrays;

public class TxHoldemPlayer extends Player {
    protected Card[] ownHand = new Card[2];

    public TxHoldemPlayer() {
    }

    public TxHoldemPlayer(double budget) {
        super(budget, false);
    }

    public TxHoldemPlayer(double budget, boolean isBot) { super(budget, isBot); }


    public void findBestHand(Card[] table) {
        Hand bestHand = new Hand(table, new HandHelper());
        Card[] cur = new Card[5];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                fillFromTable(table, cur);
                cur[j] = ownHand[i];
                Hand temp = new Hand(cur, new HandHelper());
                if (temp.compareTo(bestHand) > 0) {
                    bestHand = temp;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 5; j++) {
                fillFromTable(table, cur);
                cur[i] = ownHand[0];
                cur[j] = ownHand[1];
                Hand temp = new Hand(cur, new HandHelper());
                if (temp.compareTo(bestHand) > 0) {
                    bestHand = temp;
                }
            }
        }
        this.hand = bestHand;
    }
    protected static void fillFromTable(Card[] table, Card[] arr) {
        for (int i = 0; i < 5; i++) {
            arr[i] = table[i];
        }
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
