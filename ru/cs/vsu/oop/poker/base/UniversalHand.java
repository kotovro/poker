package ru.cs.vsu.oop.poker.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class UniversalHand implements Comparable<UniversalHand> {
    private Card[] bestHand;
    private int rank;
    public Card[] getBestHand() {
        return bestHand;
    }
    public int getRank() {
        return rank;
    }

    public void setBestHand(Card[] bestHand) {
        this.bestHand = bestHand;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }


    @Override
    public int compareTo(UniversalHand hand) {
        int cmpRes = Integer.compare(this.rank, hand.getRank());
        if (cmpRes != 0) {
            return cmpRes;
        }
        for (int i = 0; i < 5; i++) {
            cmpRes = Integer.compare(this.bestHand[i].getName().getCardWeight(), hand.getBestHand()[i].getName().getCardWeight());
            if (cmpRes != 0) {
                return cmpRes;
            }
        }
        return 0;
    }

    public Card[] getActualHand() {
        Card[] res = new Card[5];
        if (bestHand != null) {
            for (int i = 0; i < 5; i++) {
                res[i] = this.bestHand[i];
            }
            return res;
        }
        return null;
    }
}
