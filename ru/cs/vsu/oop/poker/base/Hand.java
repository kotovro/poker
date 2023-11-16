package ru.cs.vsu.oop.poker.base;

import java.util.Arrays;

public class Hand implements Comparable<Hand> {

    private Card[] hand = new Card[5];
    private Combinations combo;


    public Hand(Card[] hand) {
        for (int i = 0; i < 5; i++) {
            this.hand[i] = hand[i];
        }
        this.combo= GameHelper.findBestCombo(hand);
    }

    public Card[] getHand() {
        return hand;
    }

    public Combinations getCombo() {
        return combo;
    }

    @Override
    public int compareTo(Hand handCmp) {
        int result = Integer.compare(this.getCombo().getComboWeight(), handCmp.getCombo().getComboWeight());
        if (result == 0) {
            for (int i  = 0; i < 5; i++) {
                result = this.hand[i].compareTo(handCmp.getHand()[i]);
                if (result != 0) {
                    break;
                }
            }
        }
        return result;
    }
    public void clear() {
        Arrays.fill(this.hand, null);
    }
}
