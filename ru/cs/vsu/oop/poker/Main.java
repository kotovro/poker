package ru.cs.vsu.oop.poker;

import ru.cs.vsu.oop.poker.base.combinations.AbstractCombination;
import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.SearchResult;
import ru.cs.vsu.oop.poker.graphics.TxHoldemForm;

import javax.swing.*;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

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
