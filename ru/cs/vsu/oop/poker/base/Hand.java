//package ru.cs.vsu.oop.poker.base;
//
//import java.util.Arrays;
//
//public class Hand implements Comparable<Hand> {
//
//    private Card[] hand;
//    private Combinations combo;
//
//    public Hand(Card[] hand, IComboFinder comboFinder) {
//        this.hand = new Card[hand.length];
//        for (int i = 0; i < hand.length; i++) {
//            this.hand[i] = hand[i];
//        }
//        this.combo = comboFinder.findBestCombo(this.hand);
//    }
//
//    public Card[] getHand() {
//        return hand;
//    }
//
//    public Combinations getCombo() {
//        return combo;
//    }
//
//    @Override
//    public int compareTo(Hand handCmp) {
//        int result = Integer.compare(this.getCombo().getComboWeight(), handCmp.getCombo().getComboWeight());
//        if (result == 0) {
//            for (int i  = 0; i < this.hand.length; i++) {
//                result = this.hand[i].compareTo(handCmp.getHand()[i]);
//                if (result != 0) {
//                    break;
//                }
//            }
//        }
//        return result;
//    }
//    public void clear() {
//        Arrays.fill(this.hand, null);
//    }
//}
