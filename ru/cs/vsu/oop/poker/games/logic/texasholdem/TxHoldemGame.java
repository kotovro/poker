package ru.cs.vsu.oop.poker.games.logic.texasholdem;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.DebugLogger;
import ru.cs.vsu.oop.poker.base.Game;
import ru.cs.vsu.oop.poker.base.Player;

import javax.swing.*;
import java.util.LinkedList;

public class TxHoldemGame extends Game implements Cloneable {
    private LinkedList<Card> table = new LinkedList<>();

    public static final int PREFLOP = 0;
    public static final int FLOP = 1;
    public static final int TERN = 2;
    public static final int REAVER = 3;

    public TxHoldemGame(int numberOfBots, double budget) {
        players = new LinkedList<>();

        for (int i = 0; i < numberOfBots; i++) {
            players.add(new DumbBotTxHoldem(budget, i));
        }

        players.add(new TxHoldemPlayer(budget, false, "Vasya"));
        this.state = PREFLOP;
        this.inStreet = false;
        this.betStep = budget / 100;
    }
    private void clearTable() {
        table.clear();
    }
    public LinkedList<Game> doStep(int humanAction) {
        DebugLogger.log(DebugLogger.LogLevel.DEBUG, getDebugMessage());
        LinkedList<Game> gameStates = new LinkedList<>();
        if (inStreet) {
            if (humanAction == STOP_BETS) {
                gameStates.add(this.clone());
                stopStreet();
            } else {
                int result = doBetRound(gameStates);
                if (result == CONTINUE_BETS) {
                    return gameStates;
                } else if (result == FINISH_GAME) {
                    state = FINISHED;
                }
            }
        }
        switch (state) {
            case PREFLOP -> {
                for (Player player : this.players) {
                    for (int i = 0; i < 2; i++) {
                        ((TxHoldemPlayer) player).setOwnCard(deck.drawCard());
                    }
                }
                state = FLOP;
                startStreet();
                doBetRound(gameStates);
            }
            case FLOP -> {
                for (int i = 0; i < 3; i++) {
                    table.add(deck.drawCard());
                }
                state = TERN;
                startStreet();
                doBetRound(gameStates);
            }
            case TERN -> {
                table.add(deck.drawCard());
                state = REAVER;
                startStreet();
                doBetRound(gameStates);
            }
            case REAVER -> {
                table.add(deck.drawCard());
                state = FINISHED;
                startStreet();
                doBetRound(gameStates);
            }
            case FINISHED -> {
                winners = getGameWinners(true);
                for (Player winner : winners) {
                    winner.addBudget(bank / winners.size());
                }
                DebugLogger.log(DebugLogger.LogLevel.DEBUG, getGameEndMessage());
            }
        }
        return gameStates;
    }

    @Override
    protected LinkedList<Player> getGameWinners(boolean tryGetSingleWinner) {
        LinkedList<Player> res = getExclusiveWinner();
        if (res != null) {
            return res;
        }
        for (Player player : players) {
            ((TxHoldemPlayer) player).findBestHand(table);
        }
        return super.getGameWinners(false);
    }
    public LinkedList<Card> getTable() {
        return table;
    }

    @Override
    public void continueGame() {
        super.continueGame();
        for (Player player : players) {
            ((TxHoldemPlayer) player).clearOwnHand();
        }
        clearTable();
        this.state = PREFLOP;
        this.inStreet = false;
    }

    private String getDebugMessage() {
        StringBuilder sb = new StringBuilder();

        sb.append("Current bank: " + this.getBank() + "\n");
        sb.append("Current round bet: " + this.getCurrentBet() + "\n");
        sb.append("Table:\n");
        for (Card card : this.getTable()) {
            if (card != null) {
                sb.append(card.getName().name() + " " + card.getSuit().name() + "\n");
            }
        }

        LinkedList<Player> players = this.getPlayers();
        for (Player player : players) {
            sb.append("--------------------\n");
            sb.append(player.getName() + " budget: " + player.getBudget() + "\n");
            sb.append(player.getName() + " current bet: " + player.getCurrentBet() + "\n");
            sb.append(player.getName() + " action: " + player.getLastActionName() + "\n");

            sb.append(player.getName() + " hand:\n ");
            for (Card c : ((TxHoldemPlayer) player).getOwnHand()) {
                if (c != null) {
                    sb.append(c.getName().name() + " " + c.getSuit().name() + "\n");
                }
            }
        }
        sb.append("=====================\n");
        return sb.toString();
    }
    private String getGameEndMessage() {
        StringBuilder sb = new StringBuilder();
        LinkedList<Player> players = this.getPlayers();
        sb.append("Combinations:\n");
        for (Player player : players) {
            StringBuilder sb2 = new StringBuilder();
            sb.append("--------------------\n");
            sb.append(player.getName() + " budget: " + player.getBudget() + "\n");
            sb.append(player.getName() + " action: " + player.getLastActionName() + "\n");
            if (player.getLastAction() != Player.ACTION_FOLD) {
                sb.append(player.getName());
                if (player.getHand() != null) {
                    sb.append(": " + player.getHand().getCombinationName());
                    for (Card c : player.getHand().getBestHand()) {
                        if (c != null) {
                            sb2.append(c.getShortName() + " ");
                        }
                    }
                }
                sb.append(winners.contains(player) ? ": Winner!\n" : ": Loser!\n");
                sb.append(sb2.toString() + "\n");
            }

        }
        return sb.toString();
    }

    @Override
    public TxHoldemGame clone() {
        TxHoldemGame clone = (TxHoldemGame) super.clone();
        clone.table = (LinkedList<Card>) this.table.clone();
        return clone;
    }
}
