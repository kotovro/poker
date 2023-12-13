package ru.cs.vsu.oop.poker.graphics;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Game;
import ru.cs.vsu.oop.poker.base.Player;
import ru.cs.vsu.oop.poker.games.GameParams;
import ru.cs.vsu.oop.poker.games.logic.texasholdem.TxHoldemGame;
import ru.cs.vsu.oop.poker.games.logic.texasholdem.TxHoldemPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Stack;

import static ru.cs.vsu.oop.poker.graphics.GUIHelper.getIconForCard;
import static ru.cs.vsu.oop.poker.graphics.GUIHelper.getStatusColor;

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
    private JLabel[] cards1 = {lblPlayer1card1, lblPlayer2card1, lblPlayer3card1, lblPlayer4card1, lblPlayer5card1, lblPlayer6card1, lblHumanCard1};
    private JLabel[] cards2 = {lblPlayer1card2, lblPlayer2card2, lblPlayer3card2, lblPlayer4card2, lblPlayer5card2, lblPlayer6card2, lblHumanCard2};
    private JPanel[] botPanels = {panelBot1, panelBot2, panelBot3, panelBot4, panelBot5, panelBot6};
    private JLabel[] tableCards = {lblTableCard1, lblTableCard2, lblTableCard3, lblTableCard4, lblTableCard5};
    private JLabel[] humanCards = {lblHumanCard1, lblHumanCard2};
    private TxHoldemPlayer humanPlayer;

    private GameParams params = new GameParams();
    private ParamsDialog dialogParams = new ParamsDialog(this.panelTable, params, e -> {
        newGame();
    });

    public TxHoldemForm() {
        initControls();
        Stack<Game> gameStack = new Stack<>();

        btnFold.addActionListener(e -> {
            humanPlayer.setLastAction(Player.ACTION_FOLD);
            while (game.getState() != Game.FINISHED) {
                gameStack.addAll(game.doStep(Game.CONTINUE_BETS));
            }
            showGameState();
        });
        btnRaise.addActionListener(e -> {
            gameStack.push(game.clone());
            double bet = humanPlayer.makeBet(game.getCurrentBet(), game.getBetStep());
            game.addToBank(bet);
            game.addToCurrentBet(game.getBetStep());
            gameStack.addAll(game.doStep(Game.CONTINUE_BETS));
            showGameState();
        });
        btnCall.addActionListener(e -> {
            gameStack.push(game.clone());
            double bet = humanPlayer.makeBet(game.getCurrentBet(), 0);
            if (bet == 0) {
                gameStack.addAll(game.doStep(Game.STOP_BETS));
            } else {
                game.addToBank(bet);
                gameStack.addAll(game.doStep(Game.CONTINUE_BETS));
            }
            showGameState();
        });

        btnStay.addActionListener(e -> {
            gameStack.push(game.clone());
            humanPlayer.setLastAction(Player.ACTION_STAY);
            gameStack.addAll(game.doStep(Game.CONTINUE_BETS));
            showGameState();
        });
        this.setTitle("Texas hold'em poker game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(panelTable);
        this.setJMenuBar(createMenuBar());
        this.setVisible(true);

        this.pack();
        this.setLocationRelativeTo(null);
        dialogParams.updateView();
        dialogParams.setVisible(true);
    }

    private void startGame() {
        LinkedList<Player> players = game.getPlayers();
        game.doStep(Game.CONTINUE_BETS);
        int i = 0;
        for (Player player: players) {
            if (player.isBot()) {
                botPanels[i].setVisible(true);
                names[i].setText(player.getName());
                cards1[i].setIcon(getIconForCard(((TxHoldemPlayer)player).getOwnHand(0), params.isXRayEnabled(), getClass()));
                cards2[i].setIcon(getIconForCard(((TxHoldemPlayer)player).getOwnHand(1), params.isXRayEnabled(), getClass()));
            }
            i++;
        }
        hideUnusedBots(players.size());
        lblHumanCard1.setIcon(getIconForCard(humanPlayer.getOwnHand(0), true, getClass()));
        lblHumanCard2.setIcon(getIconForCard(humanPlayer.getOwnHand(1), true, getClass()));
        panelTable.setPreferredSize(new Dimension(900, 900));
        showGameState();
    }

    private void initControls() {

        for (int i = 0; i < 6; i++) {
            names[i].setText(" ");
            names[i].setForeground(Color.white);
            bets[i].setText(" ");
            bets[i].setForeground(Color.white);
            budgets[i].setText(" ");
            budgets[i].setForeground(Color.white);
            states[i].setText(" ");
            states[i].setForeground(Color.white);
        }
        drawEmptyCards(tableCards);
        drawEmptyCards(cards1);
        drawEmptyCards(cards2);
        drawEmptyCards(humanCards);
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
        btnFold.setEnabled(false);
        btnCall.setEnabled(false);
        btnStay.setEnabled(false);
        btnRaise.setEnabled(false);
    }
    private void drawEmptyCards(JLabel[] labels) {
        for (JLabel lbl: labels) {
            lbl.setText("");
            lbl.setIcon(getIconForCard(null, false, getClass()));
        }
    }
    private void hideUnusedBots(int players) {
        for (int i = players - 1; i < 6; i++) {
            botPanels[i].setVisible(false);
        }
    }


    public void showGameState() {
        LinkedList<Player> players = game.getPlayers();
        int i = 0;
        for (Player player: players) {
            if (player.isBot()) {
                showPlayerCards((TxHoldemPlayer) player, i);
                budgets[i].setText("Budget: " + String.format("%.2f", player.getBudget()));
                bets[i].setText("Bet: " + String.format("%.2f", player.getCurrentBet()));
                states[i].setForeground(getStatusColor(player.getLastAction()));
                states[i].setText("State: " + player.getLastActionName());
            }
            i++;
        }
        showPlayerCards(humanPlayer, cards1.length - 1);
        lblHumanBudget.setText("Your budget: " + String.format("%.2f", humanPlayer.getBudget()));
        lblHumanBet.setText("Your bet: " + String.format("%.2f", humanPlayer.getCurrentBet()));
        lblHumanState.setForeground(getStatusColor(humanPlayer.getLastAction()));
        lblHumanState.setText("Your last action: " + humanPlayer.getLastActionName());
        lblBank.setText("Bank: " + String.format("%.2f", game.getBank()));
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

    private void showPlayerCards(TxHoldemPlayer player, int playerNum) {
        if (!params.isXRayEnabled() && player.getLastAction() == Player.ACTION_FOLD) {
            drawEmptyCards(new JLabel[]{cards1[playerNum], cards2[playerNum]});
            return;
        }
        boolean showCard = player == game.getHumanPlayer()
                || params.isXRayEnabled()
                || game.getState() == Game.FINISHED && game.getActivePlayersCount() > 1;
        cards1[playerNum].setIcon(getIconForCard(player.getOwnHand(0), showCard, getClass()));
        cards2[playerNum].setIcon(getIconForCard(player.getOwnHand(1), showCard, getClass()));
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
        }, game.getWinners(), game.getPlayers(), game.getHumanPlayer(), canContinue, this);
        dlg.setVisible(true);
    }

    private void showTable() {
        int i = 0;
        for (Card card: game.getTable()) {
            tableCards[i].setIcon(getIconForCard(card, true, getClass()));
            i++;
        }
        for (int j = i; j < 5; j++) {
            tableCards[j].setIcon(getIconForCard(null, true, getClass()));
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
}