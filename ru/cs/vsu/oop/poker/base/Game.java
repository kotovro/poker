package ru.cs.vsu.oop.poker.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Game implements Cloneable {
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
    protected LinkedList<Player> players;
    protected double betStep = 0;
    private int curBotIdx = 0;


    public Game() {
        this.bank = 0;
        this.deck = new Deck();
        this.deck.shuffle();
    }
    public int getState() {
        if (!inStreet) {
            return state;
        } else {
            return IN_STREET;
        }
    }
    public LinkedList<Player> getPlayers() {
        return players;
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
        return (int) players
                .stream()
                .filter(p -> p.getLastAction() != Player.ACTION_FOLD)
                .count();
    }

    protected void startStreet() {
        inStreet = true;
    }
    protected void stopStreet() {
        inStreet = false;
        currentBet = 0;
        players.forEach(p -> {
            p.clearCurrentBet();
            if (p.getLastAction() != Player.ACTION_FOLD) {
                p.setLastAction(Player.ACTION_NONE);
            }
        });
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
        this.deck.shuffle();
        this.players = this.players.stream()
                .filter(p -> p.getBudget() >= this.betStep)
                .collect(Collectors.toCollection(LinkedList::new));
        this.players.forEach(p -> {
            p.setLastAction(Player.ACTION_NONE);
            p.clearCurrentBet();
            p.clearHand();
        });
    }

    public int getCurBotIdx() {
        return curBotIdx;
    }

    private void removePoorPlayer(Player player) {
        players.remove(player);
    }
    protected int doBetRound(LinkedList<Game> gameStates) {

        int i = 0;
        for (Player player: players) {
            if (player.getLastAction() == Player.ACTION_FOLD || curBotIdx > i) {
                if (curBotIdx <= i) {
                    curBotIdx++;
                }
                i++;
                continue;
            }
            if (player.isBot() && curBotIdx <= i) {
                gameStates.add(this.clone());
                curBotIdx++;
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
            i++;
        }
        curBotIdx = 0;
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
            return players
                    .stream()
                    .filter(p -> p.getLastAction() != Player.ACTION_FOLD)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
        return null;
    }

    @Override
    public Game clone() {
        try {
            Game clone = (Game) super.clone();
            clone.deck = this.deck.clone();
            clone.players = this.players.stream().map(p -> p.clone()).collect(Collectors.toCollection(LinkedList::new));
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
