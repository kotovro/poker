package ru.cs.vsu.oop.poker.games.logic.omahaholdem;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Game;
import ru.cs.vsu.oop.poker.base.Player;


import java.util.LinkedList;

public class OmahaHoldemGame extends Game {
    private LinkedList<Card> table = new LinkedList<>();



    private void clearTable() {
        table.clear();
    }
    private static final int PREFLOP = 0;
    private static final int FLOP = 1;
    private static final int TERN = 2;
    private static final int REAVER = 3;
    public OmahaHoldemGame(int numberOfBots, double budget) {
        players = new LinkedList<>();
        for (int i = 0; i < numberOfBots; i++) {
            players.add(new DumbBotOmahaHoldem(budget, i));
        }
        players.add(new OmahaHoldemPlayer(budget, false, "Petya"));

        this.state = PREFLOP;
        this.inStreet = false;
        this.betStep = budget / 100;
    }
    public LinkedList<Game> doStep(int humanAction) {
        LinkedList<Game> gameStates = new LinkedList<>();
        if (inStreet) {
            if (humanAction == STOP_BETS) {
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
                for (Player player: this.players) {
                    for (int i = 0; i < 4; i++) {
                        ((OmahaHoldemPlayer) player).setOwnCard(deck.drawCard());
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
                for (Player winner: winners) {
                    winner.addBudget(bank/ winners.size());
                }
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
        for (Player player: players) {
            ((OmahaHoldemPlayer)player).findBestHand(table) ;
        }
        return super.getGameWinners(false);
    }
    public Card getTable(int i) {
        return table.get(i);
    }

    @Override
    public void continueGame() {
        super.continueGame();
        for (Player player: players) {
            ((OmahaHoldemPlayer)player).clearOwnHand();
        }
        clearTable();
        this.state = PREFLOP;
        this.inStreet = false;
    }

    public LinkedList<Card> getTable() {
        return table;
    }
}
