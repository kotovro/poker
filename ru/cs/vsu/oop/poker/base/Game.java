package ru.cs.vsu.oop.poker.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

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
    protected LinkedList<Player> winners;


    public LinkedList<Player> getPlayers() {
        return players;
    }

    protected LinkedList<Player> players;
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
    public LinkedList<Player> getWinners() {
        return winners;
    }

    public double getCurrentBet() {
        return currentBet;
    }

    public Player getHumanPlayer() {
        return players.getLast();
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
        this.players = this.players.stream()
                .filter(p -> p.getBudget() >= this.betStep)
                .collect(Collectors.toCollection(LinkedList::new));
        this.players.forEach(p -> {
            p.setLastAction(Player.ACTION_NONE);
            p.clearCurrentBet();
            p.clearHand();
        });
    }

    private void removePoorPlayer(Player player) {
        players.remove(player);
    }
    protected int doBetRound() {

        for (Player player: players) {
            if (player.getLastAction() == Player.ACTION_FOLD) {
                continue;
            }
            if (player.isBot()) {
                double bet = player.makeBet(currentBet, betStep);
                if (bet > 0) {
                    bank += bet;
                    if (player.getLastAction() == Player.ACTION_RAISE) {
                        currentBet += betStep;
                    }
                } else if (bet == 0 && player.getLastAction() != Player.ACTION_STAY) {
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
    protected LinkedList<Player> getGameWinners(boolean tryGetSingleWinner) {
        LinkedList<Player> player = getExclusiveWinner();
        if (player != null) return player;
        LinkedList<Player> winners = new LinkedList<>();
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
                        winners = new LinkedList<>();
                        winners.add(winner);
                    }
                }
            }
        }
        return winners;
    }

    protected LinkedList<Player> getExclusiveWinner() {
        if (getActivePlayersCount() == 1) {
            for (Player player: players) {
                if (player.getLastAction() != Player.ACTION_FOLD) {
                    LinkedList<Player> res = new LinkedList<>();
                    res.add(player);
                    return res;
                }
            }
        }
        return null;
    }

}
