package ru.cs.vsu.oop.poker.texasholdem.logic;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Game;
import ru.cs.vsu.oop.poker.base.Player;

import java.util.Arrays;

public class TxHoldemGame extends Game {
    private Card[] table = new Card[5];



    private void clearTable() {
        Arrays.fill(table, null);
    }
    private static final int PREFLOP = 0;
    private static final int FLOP = 1;
    private static final int TERN = 2;
    private static final int REAVER = 3;
    public TxHoldemGame(int numberOfBots, double budget) {
        players = new TxHoldemPlayer[numberOfBots + 1];
        players[numberOfBots] = new TxHoldemPlayer(budget, false);
        for (int i = 0; i < numberOfBots; i++) {
            players[i] = new DumbBotTxHoldem(budget);
        }
        this.state = PREFLOP;
        this.inStreet = false;
        this.betStep = budget / 100;
    }
    public void doStep(int humanAction) {
        if (inStreet) {
            if (humanAction == STOP_BETS) {
                stopStreet();
            } else {
                int result = doBetRound();
                if (result == CONTINUE_BETS) {
                    return;
                } else if (result == FINISH_GAME) {
                    state = FINISHED;
                }
            }
        }
        switch (state) {
            case PREFLOP -> {
                for (TxHoldemPlayer player: (TxHoldemPlayer[]) this.players) {
                    for (int i = 0; i < 2; i++) {
                        player.setOwnCard(i, deck.drawCard());
                    }
                }
                state = FLOP;
                startStreet();
                doBetRound();
            }
            case FLOP -> {
                for (int i = 0; i < 3; i++) {
                    table[i] = deck.drawCard();
                }
                state = TERN;
                startStreet();
                doBetRound();
            }
            case TERN -> {
                table[3] = deck.drawCard();
                state = REAVER;
                startStreet();
                doBetRound();
            }
            case REAVER -> {
                table[4] = deck.drawCard();
                state = FINISHED;
                startStreet();
                doBetRound();
            }
            case FINISHED -> {
                winners = getGameWinners(true);
                for (Player winner: winners) {
                    winner.addBudget(bank/ winners.length);
                }
            }
        }
    }
    @Override
    protected Player[] getGameWinners(boolean tryGetSingleWinner) {
        Player[] res = getExclusiveWinner();
        if (res != null) {
            return res;
        }
        for (TxHoldemPlayer player: (TxHoldemPlayer[]) players) {
            player.findBestHand(table);
        }
        return super.getGameWinners(false);
    }
    public Card getTable(int i) {
        return table[i];
    }

    @Override
    public void continueGame() {
        super.continueGame();
        for (TxHoldemPlayer player: (TxHoldemPlayer[]) players) {
            player.clearOwnHand();
        }
        clearTable();
        this.state = PREFLOP;
        this.inStreet = false;
    }
}
