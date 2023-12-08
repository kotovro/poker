package ru.cs.vsu.oop.poker;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.ClassicCombinationsSet;
import ru.cs.vsu.oop.poker.base.SearchResult;
import ru.cs.vsu.oop.poker.graphics.TxHoldemForm;

import javax.swing.*;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        boolean isDebug = true;
        if(isDebug) {
            LinkedList<Card> buffer = new LinkedList<>();
            buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.SIX));
            buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.THREE));
            buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.NINE));
            buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.FOUR));
            buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.TWO));
            buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.JACK));
            buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.FIVE));


            SearchResult searchResult = ClassicCombinationsSet.findCombination(buffer);
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
                    new TxHoldemForm();
//                    new OmahaHoldemForm();
                }
            };
            SwingUtilities.invokeLater(swingStarter);
        }
    }
}
