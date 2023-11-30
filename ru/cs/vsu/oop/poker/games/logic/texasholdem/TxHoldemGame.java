package ru.cs.vsu.oop.poker.games.logic.texasholdem;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Game;
import ru.cs.vsu.oop.poker.base.Player;

import java.util.LinkedList;

public class TxHoldemGame extends Game {
    private LinkedList<Card> table = new LinkedList<>();



    private void clearTable() {
        table.clear();
    }
    private static final int PREFLOP = 0;
    private static final int FLOP = 1;
    private static final int TERN = 2;
    private static final int REAVER = 3;
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
                for (Player player: this.players) {
                    for (int i = 0; i < 2; i++) {
                        ((TxHoldemPlayer)player).setOwnCard(deck.drawCard());
                    }
                }
                state = FLOP;
                startStreet();
                doBetRound();
            }
            case FLOP -> {
                for (int i = 0; i < 3; i++) {
                    table.add(deck.drawCard());
                }
                state = TERN;
                startStreet();
                doBetRound();
            }
            case TERN -> {
                table.add(deck.drawCard());
                state = REAVER;
                startStreet();
                doBetRound();
            }
            case REAVER -> {
                table.add(deck.drawCard());
                state = FINISHED;
                startStreet();
                doBetRound();
            }
            case FINISHED -> {
                winners = getGameWinners(true);
                for (Player winner: winners) {
                    winner.addBudget(bank/ winners.size());
                }
            }
        }
    }
    @Override
    protected LinkedList<Player> getGameWinners(boolean tryGetSingleWinner) {
        LinkedList<Player> res = getExclusiveWinner();
        if (res != null) {
            return res;
        }
        for (Player player: players) {
            ((TxHoldemPlayer)player).findBestHand(table) ;
        }
        return super.getGameWinners(false);
    }
    public Card getTable(int i) {
        return table.get(i);
    }
    public LinkedList<Card> getTable() {
        return table;
    }

    @Override
    public void continueGame() {
        super.continueGame();
        for (Player player: players) {
            ((TxHoldemPlayer)player).clearOwnHand();
        }
        clearTable();
        this.state = PREFLOP;
        this.inStreet = false;
    }
}
