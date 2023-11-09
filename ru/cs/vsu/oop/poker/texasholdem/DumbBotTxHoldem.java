package ru.cs.vsu.oop.poker.texasholdem;

import java.util.Random;

public class DumbBotTxHoldem extends TxHoldemPlayer {
    private Random random = new Random();
    private final int FOLD = 0;
    private final int CALL = 1;
    private final int RAISE = 2;
    private final int STAY = 3;

    public DumbBotTxHoldem(double budget) {
        super(budget);
        this.isBot = true;
    }

    private int makeDecision() {

        return random.nextInt(0, 3);
    }

    @Override
    public double makeBet(double bet) {
        if (canStay(bet)) {
            if (random.nextInt(0, 2) > 0) {
                isStaying = true;
                return 0;
            }
        }
        if (makeDecision() == FOLD) {
            isFold = true;
            return -1;
        }
        if (makeDecision() == CALL) {
            return super.makeBet(bet);
        }
        if (makeDecision() == RAISE && this.budget >= bet) {
            return super.makeBet(random.nextDouble(bet, budget));
        }
        return -1;
    }
}
