package ru.cs.vsu.oop.poker.texasholdem.logic;

import java.util.Random;

public class DumbBotTxHoldem extends TxHoldemPlayer {
    private Random random = new Random();

    public DumbBotTxHoldem(double budget) {
        super(budget, true);
    }

    private int makeDecision(double currentGameBet, double wantedBet) {
        boolean doNotFold = currentGameBet == currentBet;
        if (canRise(currentGameBet, wantedBet)) {
            return random.nextInt(ACTION_CALL, ACTION_FOLD + (doNotFold ? 0 : 1));
        } else if (canRise(currentGameBet, 0) && (doNotFold || random.nextInt(0, 2) > 0)) {
            return ACTION_CALL;
        }
        return ACTION_FOLD;
    }

    @Override
    public double makeBet(double currentGameBet, double wantedBet) {
        if (canStay(currentGameBet)) {
            if (canRise(currentGameBet, wantedBet) && random.nextInt(0, 2) > 0) {
                return super.makeBet(currentGameBet, wantedBet);
            } else {
                lastAction = ACTION_STAY;
                return 0;
            }
        }
        int acton = makeDecision(currentGameBet, wantedBet);
        if (acton == ACTION_FOLD) {
            lastAction = ACTION_FOLD;
            return -1;
        }
        return super.makeBet(currentGameBet, (acton == ACTION_RAISE) ? wantedBet : 0);
    }

}
