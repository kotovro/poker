package ru.cs.vsu.oop.poker.texasholdem.logic;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Game;
import ru.cs.vsu.oop.poker.base.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class TxHoldemGame extends Game {
    private Card[] table = new Card[5];

    private void clearTable() {
        Arrays.fill(table, null);
    }
    public static final int CONTINUE_BETS = 1;
    public static final int STOP_BETS = 0;
    public static final int FINISH_GAME = -1;
    private static final int PREFLOP = 0;
    private static final int FLOP = 1;
    private static final int TERN = 2;
    private static final int REAVER = 3;
    public TxHoldemGame(int numberOfBots, double budget) {
        players = new TxHoldemPlayer[numberOfBots + 1];
        players[numberOfBots] = new TxHoldemPlayer(budget);
        for (int i = 0; i < numberOfBots; i++) {
            players[i] = new DumbBotTxHoldem(budget);
        }
        this.state = PREFLOP;
        this.inStreet = false;
        this.betStep = budget / 100;
    }

    @Override
    public void playGame() {

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
                TxHoldemPlayer[] winners = getGameWinners();
                for (Player winner: winners) {
                    winner.addBudget(bank/winners.length);
                }
            }
        }
    }

    private TxHoldemPlayer[] getGameWinners() {
        if (getActivePlayersCount() == 1) {
            for (TxHoldemPlayer player: (TxHoldemPlayer[]) players) {
                if (player.getLastAction() != Player.ACTION_FOLD) {
                    return new TxHoldemPlayer[] {player};
                }
            }
        }
        for (TxHoldemPlayer player: (TxHoldemPlayer[]) players) {
            player.findBestHand(table);
        }
        ArrayList<TxHoldemPlayer> winners = new ArrayList<>();
        TxHoldemPlayer winner = null;
        for (TxHoldemPlayer player: (TxHoldemPlayer[]) players) {
            if (player.getLastAction() != Player.ACTION_FOLD) {
                if (winner == null) {
                    winner = player;
                    winners.add(winner);
                } else {
                    if (player.getHand().compareTo(winner.getHand()) == 0) {
                        winners.add(player);
                    } else if (player.getHand().compareTo(winner.getHand()) > 0) {
                        winner = player;
                        winners = new ArrayList<>();
                        winners.add(winner);
                    }
                }
            }
        }
        return winners.toArray(new TxHoldemPlayer[1]);
    }

    private int doBetRound() {

        for (int i = 0; i < players.length - 1; i++) {
            if (players[i].getLastAction() == Player.ACTION_FOLD) {
                continue;
            }
            if (players[i].isBot()) {
                double bet = players[i].makeBet(currentBet, betStep);
                if (bet > 0) {
                    bank += bet;
                    if (players[i].getLastAction() == Player.ACTION_RAISE) {
                        currentBet += betStep;
                    }
                } else if (bet == 0 && players[i].getLastAction() != Player.ACTION_STAY) {
                    stopStreet();
                    return STOP_BETS;
                } else if (bet < 0 && this.getActivePlayersCount() < 2) {
                    stopStreet();
                    return FINISH_GAME;
                }
            }
        }
        return CONTINUE_BETS;
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
