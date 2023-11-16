package ru.cs.vsu.oop.poker.texasholdem.graphics;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Game;
import ru.cs.vsu.oop.poker.base.Player;
import ru.cs.vsu.oop.poker.texasholdem.logic.TxHoldemGame;
import ru.cs.vsu.oop.poker.texasholdem.logic.TxHoldemPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static ru.cs.vsu.oop.poker.texasholdem.graphics.IconHelper.*;

public class TxHoldemForm extends JFrame {

    private JPanel panelTable;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JPanel panelUp;
    private JPanel panelDown;
    private JPanel panelCenter;
    private JPanel panelBot1;
    private JPanel panelBot2;
    private JPanel panelBot3;
    private JPanel panelBot4;
    private JPanel panelBot5;
    private JPanel panelBot6;
    private JLabel lblPlayer1Name;
    private JLabel lblPlayer2Name;
    private JLabel lblPlayer3Name;
    private JLabel lblPlayer4Name;
    private JLabel lblPlayer5Name;
    private JLabel lblPlayer6Name;
    private JLabel lblPlayer1Budget;
    private JLabel lblPlayer1Bet;
    private JLabel lblPlayer2Budget;
    private JLabel lblPlayer2Bet;
    private JLabel lblPlayer3Budget;
    private JLabel lblPlayer3Bet;
    private JLabel lblPlayer4Budget;
    private JLabel lblPlayer4Bet;
    private JLabel lblPlayer5Budget;
    private JLabel lblPlayer5Bet;
    private JLabel lblPlayer6Budget;
    private JLabel lblPlayer6Bet;
    private JLabel lblPlayer1State;
    private JLabel lblPlayer2State;
    private JLabel lblPlayer3State;
    private JLabel lblPlayer4State;
    private JLabel lblPlayer5State;
    private JLabel lblPlayer6State;
    private JLabel lblPlayer1card1;
    private JLabel lblPlayer1card2;
    private JLabel lblPlayer2card1;
    private JLabel lblPlayer2card2;
    private JLabel lblPlayer3card1;
    private JLabel lblPlayer3card2;
    private JLabel lblPlayer4card1;
    private JLabel lblPlayer4card2;
    private JLabel lblPlayer5card1;
    private JLabel lblPlayer5card2;
    private JLabel lblPlayer6card1;
    private JLabel lblPlayer6card2;
    private JLabel lblTableCard1;
    private JLabel lblBank;
    private JPanel panelHumanCards;
    private JPanel panelHuman;
    private JPanel panelActions;
    private JButton btnStay;
    private JButton btnCall;
    private JButton btnRaise;
    private JButton btnFold;
    private JLabel lblActions;
    private JLabel lblHumanHand;
    private JLabel lblHumanCard1;
    private JLabel lblHumanCard2;
    private JLabel lblHumanBudget;
    private JLabel lblHumanBet;
    private JLabel lblHumanState;
    private JLabel lblTableCard2;
    private JLabel lblTableCard3;
    private JLabel lblTableCard4;
    private JLabel lblTableCard5;

    private TxHoldemGame game;
    private JLabel[] names = {lblPlayer1Name, lblPlayer2Name, lblPlayer3Name, lblPlayer4Name, lblPlayer5Name, lblPlayer6Name};
    private JLabel[] budgets = {lblPlayer1Budget, lblPlayer2Budget, lblPlayer3Budget, lblPlayer4Budget, lblPlayer5Budget, lblPlayer6Budget};
    private JLabel[] bets = {lblPlayer1Bet, lblPlayer2Bet, lblPlayer3Bet, lblPlayer4Bet, lblPlayer5Bet, lblPlayer6Bet};
    private JLabel[] states = {lblPlayer1State, lblPlayer2State, lblPlayer3State, lblPlayer4State, lblPlayer5State, lblPlayer6State};
    private JLabel[] cards1 = {lblPlayer1card1, lblPlayer2card1, lblPlayer3card1, lblPlayer4card1, lblPlayer5card1, lblPlayer6card1};
    private JLabel[] cards2 = {lblPlayer1card2, lblPlayer2card2, lblPlayer3card2, lblPlayer4card2, lblPlayer5card2, lblPlayer6card2};
    private JPanel[] botPanels = {panelBot1, panelBot2, panelBot3, panelBot4, panelBot5, panelBot6};
    private JLabel[] tableCards = {lblTableCard1, lblTableCard2, lblTableCard3, lblTableCard4, lblTableCard5};
    private TxHoldemPlayer humanPlayer;
    private GameParams params = new GameParams();
    private ParamsDialog dialogParams = new ParamsDialog(this.panelTable, params, e -> {
        newGame();
    });

    public TxHoldemForm() {

        initLabels();
        newGame();



        btnFold.addActionListener(e -> {
            humanPlayer.setLastAction(Player.ACTION_FOLD);
            while (game.getState() != Game.FINISHED) {
                game.doStep(Game.CONTINUE_BETS);
            }
            showGameState();
        });
        btnRaise.addActionListener(e -> {
            double bet = humanPlayer.makeBet(game.getCurrentBet(), game.getBetStep());
            game.addToBank(bet);
            game.addToCurrentBet(game.getBetStep());
            game.doStep(Game.CONTINUE_BETS);
            showGameState();
        });
        btnCall.addActionListener(e -> {
            double bet = humanPlayer.makeBet(game.getCurrentBet(), 0);
            if (bet == 0) {
                game.doStep(Game.STOP_BETS);
            } else {
                game.addToBank(bet);
                game.doStep(Game.CONTINUE_BETS);
            }
            showGameState();
        });
        btnStay.addActionListener(e -> {
            humanPlayer.setLastAction(Player.ACTION_STAY);
            game.doStep(Game.CONTINUE_BETS);
            showGameState();
        });
        this.setTitle("Texas holdem poker game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(panelTable);
        this.setJMenuBar(createMenuBar());
        this.setVisible(true);

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void startGame() {
        TxHoldemPlayer[] players = (TxHoldemPlayer[]) game.getPlayers();
        game.doStep(Game.CONTINUE_BETS);
        for (int i = 0; i < players.length - 1; i++) {
            botPanels[i].setVisible(true);
            names[i].setText("Bot player " + (i + 1));
            cards1[i].setIcon(getIconForCard(players[i].getOwnHand(0), params.isXRayEnabled()));
            cards1[i].setText("");
            cards2[i].setIcon(getIconForCard(players[i].getOwnHand(1), params.isXRayEnabled()));
            cards2[i].setText("");
        }
        hideUnusedBots(players.length);
        lblHumanCard1.setIcon(getIconForCard(humanPlayer.getOwnHand(0), true));
        lblHumanCard2.setIcon(getIconForCard(humanPlayer.getOwnHand(1), true));
        panelTable.setPreferredSize(new Dimension(800, 800));
        showGameState();
    }

    private void initLabels() {
        for (int i = 0; i < 6; i++) {
            names[i].setForeground(Color.white);
            bets[i].setForeground(Color.white);
            budgets[i].setForeground(Color.white);
            states[i].setForeground(Color.white);
        }
        for (JLabel label : tableCards) {
            label.setText("");
        }
        Font bankFont = new Font(lblBank.getFont().getName(), Font.PLAIN, 20);
        lblBank.setFont(bankFont);
        lblBank.setForeground(Color.white);
        Font humanFont = new Font(lblBank.getFont().getName(), Font.BOLD, 16);
        lblActions.setFont(humanFont);
        lblActions.setForeground(Color.white);
        lblHumanHand.setFont(humanFont);
        lblHumanHand.setForeground(Color.white);
        lblHumanBudget.setFont(humanFont);
        lblHumanBudget.setForeground(Color.white);
        lblHumanBet.setFont(humanFont);
        lblHumanBet.setForeground(Color.white);
        lblHumanState.setFont(humanFont);
        lblHumanState.setForeground(Color.white);
    }

    private void hideUnusedBots(int players) {
        for (int i = players - 1; i < 6; i++) {
            botPanels[i].setVisible(false);
        }
    }


    public void showGameState() {
        TxHoldemPlayer[] players = (TxHoldemPlayer[]) game.getPlayers();
        for (int i = 0; i < players.length - 1; i++) {
            budgets[i].setText("Budget: " + players[i].getBudget());
            bets[i].setText("Bet: " + players[i].getCurrentBet());
            states[i].setText("State: " + players[i].getLastActionName());
        }
        lblHumanBudget.setText("Your budget: " + humanPlayer.getBudget());
        lblHumanBet.setText("Your bet: " + humanPlayer.getCurrentBet());
        lblHumanState.setText("Your last action: " + humanPlayer.getLastActionName());
        lblBank.setText("Bank: " + game.getBank());
        showTable();
        if (game.getState() == Game.FINISHED) {
            btnFold.setEnabled(false);
            btnStay.setEnabled(false);
            btnCall.setEnabled(false);
            btnRaise.setEnabled(false);
            showContinueGameDialogue();
        } else {
            boolean canStay = humanPlayer.canStay(game.getCurrentBet());
            boolean canCall = !canStay && humanPlayer.canRise(game.getCurrentBet(), 0);
            boolean canRise = humanPlayer.canRise(game.getCurrentBet(), game.getBetStep());
            btnFold.setEnabled(!canStay);
            btnStay.setEnabled(canStay);
            btnCall.setEnabled(canCall);
            btnRaise.setEnabled(canRise);
        }
    }

    private void showContinueGameDialogue() {
        boolean canContinue = game.getHumanPlayer().getBudget() > 0;
        int playersLeft = 0;
        for (Player p : game.getPlayers()) {
            if (p.getBudget() > 0) {
                playersLeft++;
                if (playersLeft == 2) {
                    break;
                }
            }
        }
        canContinue = canContinue && playersLeft > 1;
        JDialog dlg = new ContinueGameDialog(a -> {
            game.continueGame();
            startGame();
        }, game.getWinners(), game.getHumanPlayer(), canContinue, this);
        dlg.setVisible(true);
    }

    private void showTable() {
        for (int i = 0; i < 5; i++) {
            Card card = game.getTable(i);
            if (card != null) {
                tableCards[i].setIcon(getIconForCard(card, true));
            } else {
                tableCards[i].setIcon(getIconForName(getIconFileName("empty")));
            }
        }
    }

    private JMenuItem createMenuItem(String text, String shortcut, Character mnemonic, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(listener);
        if (shortcut != null) {
            menuItem.setAccelerator(KeyStroke.getKeyStroke(shortcut.replace('+', ' ')));
        }
        if (mnemonic != null) {
            menuItem.setMnemonic(mnemonic);
        }
        return menuItem;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBarMain = new JMenuBar();

        JMenu menuGame = new JMenu("Игра");
        menuBarMain.add(menuGame);
        menuGame.add(createMenuItem("Новая", "ctrl+N", null, e -> {
            newGame();
        }));
        menuGame.add(createMenuItem("Параметры", "ctrl+P", null, e -> {
            dialogParams.updateView();
            dialogParams.setVisible(true);
        }));
        menuGame.addSeparator();
        menuGame.add(createMenuItem("Выход", "ctrl+X", null, e -> {
            System.exit(0);
        }));
        return menuBarMain;

    }

    private void newGame() {
        game = new TxHoldemGame(params.getBotCount(), params.getBudget());
        humanPlayer = (TxHoldemPlayer) game.getHumanPlayer();
        startGame();
    }
    public class GameParams {
        boolean isXRayEnabled = true;
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
}