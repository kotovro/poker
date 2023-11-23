package ru.cs.vsu.oop.poker;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Player;
import ru.cs.vsu.oop.poker.omahaholdem.graphics.OmahaHoldemForm;
import ru.cs.vsu.oop.poker.texasholdem.logic.TxHoldemGame;
import ru.cs.vsu.oop.poker.texasholdem.logic.TxHoldemPlayer;

import javax.swing.*;
import java.util.Scanner;

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
//                    new TxHoldemForm();
                    new OmahaHoldemForm();
                }
            };
            SwingUtilities.invokeLater(swingStarter);
        }
    }


}
