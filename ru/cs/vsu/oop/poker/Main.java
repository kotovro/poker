package ru.cs.vsu.oop.poker;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Player;
import ru.cs.vsu.oop.poker.texasholdem.TxHoldemGame;
import ru.cs.vsu.oop.poker.texasholdem.TxHoldemPlayer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите число ботов:");
        int botsCount = scanner.nextInt();
        System.out.println("Введите бюджет:");
        int budget = scanner.nextInt();
        TxHoldemGame game = new TxHoldemGame(botsCount, budget);
        //while (game.getState() != game.FINISHED) {
            game.doStep();
            showStepResult(game, true);
        //}

    }

    private static void showStepResult(TxHoldemGame game, boolean isDebug) {
        TxHoldemPlayer player = (TxHoldemPlayer) game.getHumanPlayer();
        System.out.println("Current bank: " + (game.getBank() + game.getAllBets()));
        System.out.println("Current round bet: " + game.getCurrentBet());
        System.out.println("Your budget: " + player.getBudget());
        System.out.println("Your current bet: " + player.getCurrentBet());
        System.out.println("Table: ");
        for (int i = 0; i < 5; i++) {
            Card card = game.getTable(i);
            if (card != null) {
                System.out.println(card.getName().name() + " " + card.getSuit().name());
            }
        }
        System.out.println("Your hand: ");
        for (int i = 0; i < 2; i++) {
            Card card = player.getOwnHand(i);
            if (card != null) {
                System.out.println(card.getName().name() + " " + card.getSuit().name());
            }

        }
        if (isDebug) {

        }
    }
}
