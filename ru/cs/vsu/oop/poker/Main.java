package ru.cs.vsu.oop.poker;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Game;
import ru.cs.vsu.oop.poker.base.Player;
import ru.cs.vsu.oop.poker.texasholdem.graphics.TxHoldemForm;
import ru.cs.vsu.oop.poker.texasholdem.logic.TxHoldemGame;
import ru.cs.vsu.oop.poker.texasholdem.logic.TxHoldemPlayer;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean isGraphics = true;
        if (isGraphics) {
            Runnable swingStarter = new Runnable() {
                @Override
                public void run() {
                    new TxHoldemForm();
                }
            };
            SwingUtilities.invokeLater(swingStarter);
        } else {


            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите число ботов:");
            int botsCount = scanner.nextInt();
            System.out.println("Введите бюджет:");
            int budget = scanner.nextInt();
            TxHoldemGame game = new TxHoldemGame(botsCount, budget);
            int action = -1;
            while (game.getState() != Game.FINISHED) {
                game.doStep(action);
                showStepResult(game, true);
                TxHoldemPlayer humanPlayer = (TxHoldemPlayer) game.getHumanPlayer();
                if (game.getState() != Game.FINISHED && humanPlayer.getLastAction() != Player.ACTION_FOLD) {
                    action = -1;
                    while (action < 0) {
                        action = getHumanAction(humanPlayer, game);
                    }
                }
            }
        }
    }

    private static void showStepResult(TxHoldemGame game, boolean isDebug) {
        System.out.println("Current bank: " + game.getBank());
        System.out.println("Current round bet: " + game.getCurrentBet());
        System.out.println("Table: ");
        for (int i = 0; i < 5; i++) {
            Card card = game.getTable(i);
            if (card != null) {
                System.out.println(card.getName().name() + " " + card.getSuit().name());
            }
        }

        TxHoldemPlayer[] players = (TxHoldemPlayer[]) game.getPlayers();
        for (int i = 0; i < players.length; i++) {
            System.out.println("Player " + i + " budget: " + players[i].getBudget());
            System.out.println("Player " + i + " current bet: " + players[i].getCurrentBet());
            System.out.println("Player " + i + " action: " + players[i].getLastActionName());
            if (isDebug || i == players.length - 1) {
                System.out.println("Player " + i + " hand: ");
                for (int j = 0; j < 2; j++) {
                    Card card = players[i].getOwnHand(j);
                    if (card != null) {
                        System.out.println(card.getName().name() + " " + card.getSuit().name());
                    }
                }
            }
        }
    }

    public static int getHumanAction(TxHoldemPlayer humanPlayer, TxHoldemGame game) {
        System.out.println("Choose your action: ");
        Scanner scanner = new Scanner(System.in);
        int action = scanner.nextInt();
        if (action == Player.ACTION_STAY && humanPlayer.canStay(game.getCurrentBet())) {
            humanPlayer.setLastAction(Player.ACTION_STAY);
            return 1;
        } else if (action == Player.ACTION_STAY) {
            System.out.println("You can not stay, choose another action");
            return -1;
        }
        if (action == Player.ACTION_CALL && humanPlayer.canRise(game.getCurrentBet(), 0)) {
            double bet = humanPlayer.makeBet(game.getCurrentBet(), 0);
            if (bet == 0) {
                return 0;
            }
            game.addToBank(bet);
            return 1;
        } else if (action == Player.ACTION_CALL) {
            System.out.println("You can not call, choose another action");
            return -1;
        }
        if (action == Player.ACTION_RAISE && humanPlayer.canRise(game.getCurrentBet(), game.getBetStep())) {
            double bet = humanPlayer.makeBet(game.getCurrentBet(), game.getBetStep());
            game.addToBank(bet);
            game.addToCurrentBet(game.getBetStep());
            return 1;
        } else if (action == Player.ACTION_RAISE) {
            System.out.println("You can not raise, choose another action");
            return -1;
        }
        if (action == Player.ACTION_FOLD) {
            humanPlayer.setLastAction(Player.ACTION_FOLD);
            return 1;
        }
        return 0;
    }
}
