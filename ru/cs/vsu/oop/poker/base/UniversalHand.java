package ru.cs.vsu.oop.poker.base;

import java.util.LinkedList;

public class UniversalHand implements Comparable<UniversalHand> {
    private LinkedList<Card> bestHand;
    private String CombinationName;
    private int rank;
    public LinkedList<Card> getBestHand() {
        return bestHand;
    }
    public int getRank() {
        return rank;
    }

    public void setBestHand(LinkedList<Card> bestHand) {
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
            cmpRes = Integer.compare(this.bestHand.get(i).getCardWeight(), hand.getBestHand().get(i).getCardWeight());
            if (cmpRes != 0) {
                return cmpRes;
            }
        }
        return 0;
    }
    public String getCombinationName() {
        return CombinationName;
    }
    //    public Card[] getActualHand() {
//        Card[] res = new Card[5];
//        if (bestHand != null) {
//            for (int i = 0; i < 5; i++) {
//                res[i] = this.bestHand.[i];
//            }
//            return res;
//        }
//        return null;
//    }
}
