package ru.cs.vsu.oop.poker.base;

public class Player {
    protected Hand hand;
    protected double budget;
    protected double currentBet;
    protected boolean isBot = false;
    protected boolean isStaying = false;
    protected boolean isFold = false;

    public Player(double budget) {
        this.budget = budget;
    }
    public Player(){
        this.budget = 0;
    }

    public boolean isStaying() {
        return isStaying;
    }

    public boolean isFold() {
        return isFold;
    }

    public double makeBet(double bet) {
        if (bet < budget) {
            budget -= bet;
            currentBet += bet;
        } else {
            bet = -1;
            isFold = true;
        }
        return bet;
    }
    protected boolean canStay(double bet) {
        return bet <= currentBet;
    }
    public void clearCurrentBet() {
        currentBet = 0;
    }

    public double getCurrentBet() {
        return this.currentBet;
    }

    public double getBudget() {
        return this.budget;
    }
}
