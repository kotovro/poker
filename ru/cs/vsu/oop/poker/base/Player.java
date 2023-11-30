package ru.cs.vsu.oop.poker.base;


public class Player {
    public final static int ACTION_NONE = 0;
    public final static int ACTION_STAY = 1;
    public final static int ACTION_CALL = 2;
    public final static int ACTION_RAISE = 3;
    public final static int ACTION_FOLD = 4;
    protected final boolean isBot;

    public UniversalHand getHand() {
        return hand;
    }

    protected UniversalHand hand;
    protected double budget;
    protected double currentBet;
    protected String name;

    public String getName() {
        return name;
    }

    protected int lastAction = ACTION_NONE;
    public Player(double budget, boolean isBot, String name) {
        this.budget = budget;
        this.isBot = isBot;
        this.name = name;
    }

    public int getLastAction() {
        return lastAction;
    }
    public void setLastAction(int action) {
        this.lastAction = action;
    }

    public double makeBet(double currentGameBet, double wantedBet) {
        double callSum = currentGameBet - currentBet;
        if (callSum + wantedBet <= budget) {
            budget -= callSum + wantedBet;
            currentBet += callSum + wantedBet;
            lastAction = (wantedBet > 0) ? ACTION_RAISE : ACTION_CALL;
            return callSum + wantedBet;
        } else {
            lastAction = ACTION_FOLD;
            return -1;
        }
    }
    public boolean canStay(double bet) {
        return lastAction == ACTION_NONE && bet == 0;
    }
    public boolean canRise(double currentBet, double wantedBet) {
        return budget - currentBet - wantedBet >= 0;
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
    public boolean isBot() {
        return this.isBot;
    }

    public void addBudget(double amount) {
        this.budget += amount;
    }

    public String getLastActionName() {
        switch (lastAction) {
            case ACTION_CALL -> {
                return "CALL";
            }
            case ACTION_FOLD -> {
                return "FOLD";
            }
            case ACTION_RAISE -> {
                return "RAISE";
            }
            case ACTION_STAY -> {
                return "STAY";
            }
            default -> {
                return "NONE";
            }
        }
    }
    public void clearHand(){
        this.hand = null;
    }

}
