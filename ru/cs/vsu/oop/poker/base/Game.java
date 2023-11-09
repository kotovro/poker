package ru.cs.vsu.oop.poker.base;

import java.util.LinkedList;

public class Game {
    public final int FINISHED = -1;
    protected Deck deck;
    protected double bank;
    protected double currentBet;
    protected double allBets;
    protected int state;
    protected boolean inStreet;
    protected Player[] players;

    public Game() {
        this.bank = 0;
        this.deck = new Deck();
    }
    public void playGame() {
    }

    public int getState() {
        return state;
    }

    public double getBank() {
        return this.bank;
    }

    public double getCurrentBet() {
        return currentBet;
    }

    public double getAllBets() {
        return allBets;
    }

    public Player getHumanPlayer() {
        return players[0];
    }
}
