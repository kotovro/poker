package ru.cs.vsu.oop.poker.base;

import ru.cs.vsu.oop.poker.texasholdem.logic.TxHoldemPlayer;

import java.util.ArrayList;

public class Game {
    public static final int CONTINUE_BETS = 1;
    public static final int STOP_BETS = 0;
    public static final int FINISH_GAME = -1;
    public static final int FINISHED = -1;
    public static final int IN_STREET = -10;
    protected Deck deck;
    protected double bank;
    protected double currentBet;
    protected int state;
    protected boolean inStreet;
    protected Player[] winners;
    protected AbstractCombination comboDefinition;


    public Player[] getPlayers() {
        return players;
    }

    protected Player[] players;
    protected double betStep = 0;

    public Game() {
        this.bank = 0;
        this.deck = new Deck();
    }
    public int getState() {
        if (!inStreet) {
            return state;
        } else {
            return IN_STREET;
        }
    }

    public double getBank() {
        return this.bank;
    }
    public Player[] getWinners() {
        return winners;
    }

    public double getCurrentBet() {
        return currentBet;
    }

    public Player getHumanPlayer() {
        return players[players.length - 1];
    }
    public int getActivePlayersCount() {
        int activePlayers = 0;
        for (Player p : players) {
            if (p.getLastAction() != Player.ACTION_FOLD) {
                activePlayers++;
            }
        }
        return activePlayers;
    }

    protected void startStreet() {
        inStreet = true;
    }
    protected void stopStreet() {
        inStreet = false;
        currentBet = 0;
        for (Player p: players) {
            p.clearCurrentBet();
            if (p.getLastAction() != Player.ACTION_FOLD) {
                p.setLastAction(Player.ACTION_NONE);
            }
        }
    }

    public double getBetStep() {
        return this.betStep;
    }

    public void addToCurrentBet(double bet) {
        this.currentBet += bet;
    }

    public void addToBank(double bet) {
        this.bank += bet;
    }
    public void continueGame() {
        this.bank = 0;
        this.deck = new Deck();
        Player[] currentPlayers = players;
        for (Player player: currentPlayers) {
            if (player.getBudget() == 0) {
                removePoorPlayer(player);
            } else {
                player.setLastAction(Player.ACTION_NONE);
                player.clearCurrentBet();
                player.clearHand();
            }
        }
    }

    private void removePoorPlayer(Player player) {
        Player[] res = new Player[players.length - 1];
        int idx = 0;
        for (Player curPlayer: players) {
            if (curPlayer != player) {
                res[idx] = curPlayer;
                idx++;
            }
        }
        this.players = res;
    }
    protected int doBetRound() {

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
    protected Player[] getGameWinners(boolean tryGetSingleWinner) {
        Player[] player = getExclusiveWinner();
        if (player != null) return player;
        ArrayList<Player> winners = new ArrayList<>();
        Player winner = null;
        for (Player p: players) {
            if (p.getLastAction() != Player.ACTION_FOLD) {
                if (winner == null) {
                    winner = p;
                    winners.add(winner);
                } else {
                    if (p.getHand().compareTo(winner.getHand()) == 0) {
                        winners.add(p);
                    } else if (p.getHand().compareTo(winner.getHand()) > 0) {
                        winner = p;
                        winners = new ArrayList<>();
                        winners.add(winner);
                    }
                }
            }
        }
        return winners.toArray(new Player[1]);
    }

    protected Player[] getExclusiveWinner() {
        if (getActivePlayersCount() == 1) {
            for (Player player: players) {
                if (player.getLastAction() != Player.ACTION_FOLD) {
                    return new Player[]{player};
                }
            }
        }
        return null;
    }

}
