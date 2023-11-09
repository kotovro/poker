package ru.cs.vsu.oop.poker.texasholdem;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Game;
import ru.cs.vsu.oop.poker.base.Player;

import java.util.Arrays;
import java.util.LinkedList;

public class TxHoldemGame extends Game {
    private Card[] table = new Card[5];

    private void clearTable() {
        Arrays.fill(table, null);
    }
    private final int PREFLOP = 0;
    private final int FLOP = 1;
    private final int TERN = 2;
    private final int REAVER = 3;

    public TxHoldemGame(int numberOfBots, double budget) {
        players = new TxHoldemPlayer[numberOfBots + 1];
        players[0] = new TxHoldemPlayer(budget);
        for (int i = 0; i < numberOfBots; i++) {
            players[i+1] = new DumbBotTxHoldem(budget);
        }
        this.state = PREFLOP;
        this.inStreet = false;
    }

    @Override
    public void playGame() {

    }

    public void doStep() {
        if (inStreet) {
            doBetRound();
            return;
        }
        this.bank += allBets;
        allBets = 0;
        currentBet = 0;
        for (Player player: players) {
            player.clearCurrentBet();
        }
        switch (state) {
            case PREFLOP -> {
                for (TxHoldemPlayer player: (TxHoldemPlayer[]) this.players) {
                    for (int i = 0; i < 2; i++) {
                        player.setOwnCard(i, deck.drawCard());
                    }
                }
                inStreet = true;
                state = FLOP;
                return;
            }
        }
    }

    private void doBetRound() {
        int inGame = players.length;
        for (TxHoldemPlayer player: (TxHoldemPlayer[]) players) {
            if (player.isFold()) {
                inGame--;
            } else if (player.isBot()) {
                double bet = player.makeBet(currentBet);
                if (bet <= 0) {
                    inGame--;
                } else {
                    allBets += bet;
                    currentBet = player.getCurrentBet();
                }
            }
        }
        if (inGame == 0) {
            inStreet = false;
        }
    }

    public Card getTable(int i) {
        return table[i];
    }
}
