package ru.cs.vsu.oop.poker.base;

import java.util.LinkedList;

public class UniversalHand implements Comparable<UniversalHand>, Cloneable {
    private LinkedList<Card> bestHand;
    private int rank;
    private String combinationName;

    public void setCombinationName(String combinationName) {
        this.combinationName = combinationName;
    }
    public String getCombinationName() {
        return this.combinationName;
    }

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

    @Override
    public UniversalHand clone() {
        try {
            UniversalHand clone = (UniversalHand) super.clone();
            clone.bestHand = (LinkedList<Card>) this.bestHand.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
