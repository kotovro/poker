package ru.cs.vsu.oop.poker.base;

public class Game {
    public static final int FINISHED = -1;
    public static final int IN_STREET = -10;
    protected Deck deck;
    protected double bank;
    protected double currentBet;
    protected int state;
    protected boolean inStreet;

    public Player[] getPlayers() {
        return players;
    }

    protected Player[] players;
    protected double betStep = 0;

    public Game() {
        this.bank = 0;
        this.deck = new Deck();
    }
    public void playGame() {
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
}
