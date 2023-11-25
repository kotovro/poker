package ru.cs.vsu.oop.poker.games;

public class GameParams {
    boolean isXRayEnabled = false;
    double budget = 100;
    int botCount = 4;

    public boolean isXRayEnabled() {
        return isXRayEnabled;
    }

    public void setXRayEnabled(boolean XRayEnabled) {
        isXRayEnabled = XRayEnabled;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public int getBotCount() {
        return botCount;
    }

    public void setBotCount(int botCount) {
        this.botCount = botCount;
    }
}
