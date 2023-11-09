package ru.cs.vsu.oop.poker.base;

public class Player {
    protected Hand hand;
    protected double budget;

    public Player(double budget) {
        this.budget = budget;
    }

    public double makeBet(double bet) {
        if (bet < budget) {
            budget -= bet;
        } else {
            bet = -1;
        }
        return bet;
    }
}
