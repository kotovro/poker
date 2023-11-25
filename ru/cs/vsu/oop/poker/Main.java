package ru.cs.vsu.oop.poker;

import ru.cs.vsu.oop.poker.base.AbstractCombination;
import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.ClassicCombo;
import ru.cs.vsu.oop.poker.graphics.OmahaHoldemForm;
import ru.cs.vsu.oop.poker.graphics.TxHoldemForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        boolean isDebug = false;
        if(isDebug) {
            Card[] newArr = new Card[8];
            newArr[0] = new Card(Card.Suits.SPADES, Card.CardNames.THREE);
            newArr[1] = new Card(Card.Suits.HEARTS, Card.CardNames.THREE);
            newArr[2] = new Card(Card.Suits.HEARTS, Card.CardNames.ACE);
            newArr[3] = new Card(Card.Suits.HEARTS, Card.CardNames.FOUR);
            newArr[4] = new Card(Card.Suits.HEARTS, Card.CardNames.FIVE);
            newArr[5] = new Card(Card.Suits.HEARTS, Card.CardNames.TWO);
            newArr[6] = new Card(Card.Suits.SPADES, Card.CardNames.FIVE);
            newArr[7] = new Card(Card.Suits.HEARTS, Card.CardNames.EIGHT);

            ClassicCombo cmb = new ClassicCombo();
            AbstractCombination.SearchResult searchResult = cmb.findCombination(newArr);
            System.out.println(searchResult.getName());
            for (Card c: searchResult.getBestCombo()) {
                System.out.println(c.getName().name() + " " + c.getSuit().name()) ;
            }
            return;
        }
        boolean isGraphics = true;
        if (args.length > 0 && args[0].equals("--mode=text")){
            isGraphics = false;
        }
        if (isGraphics) {
            Runnable swingStarter = new Runnable() {
                @Override
                public void run() {
//                    new TxHoldemForm();
                    new OmahaHoldemForm();
                }
            };
            SwingUtilities.invokeLater(swingStarter);
        }
    }
}
