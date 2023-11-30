package ru.cs.vsu.oop.poker.graphics;

import ru.cs.vsu.oop.poker.base.Player;
import ru.cs.vsu.oop.poker.base.UniversalHand;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import static ru.cs.vsu.oop.poker.graphics.GUIHelper.getIconForCard;

public class ContinueGameDialog extends JDialog {
    private JPanel panel1;
    private JLabel lblWin;
    private JLabel lblLose;
    private JButton btnContinue;
    private JPanel panelPlayer1Hand;
    private JPanel panelYourHand;
    private JLabel lblP1HandCard1;
    private JLabel lblP1HandCard2;
    private JLabel lblP1HandCard3;
    private JLabel lblP1HandCard4;
    private JLabel lblP1HandCard5;
    private JLabel lblYHandCard1;
    private JLabel lblYHandCard2;
    private JLabel lblYHandCard3;
    private JLabel lblYHandCard4;
    private JLabel lblYHandCard5;
    private JLabel lblPlayer1HandHeader;
    private JLabel lblYourHandHeader;
    private JPanel panel2;
    private JLabel lblPlayer2HandHeader;
    private JLabel lblP2HandCard1;
    private JLabel lblP2HandCard2;
    private JLabel lblP2HandCard3;
    private JLabel lblP2HandCard4;
    private JLabel lblP2HandCard5;
    private JPanel panelPlayer2Hand;
    private JPanel panelPlayer3Hand;
    private JLabel lblP3HandCard1;
    private JLabel lblP3HandCard2;
    private JLabel lblP3HandCard3;
    private JLabel lblP3HandCard4;
    private JLabel lblP3HandCard5;
    private JLabel lblPlayer3HandHeader;
    private JLabel lblPlayer4HandHeader;
    private JPanel panelPlayer4Hand;
    private JLabel lblP4HandCard1;
    private JLabel lblP4HandCard2;
    private JLabel lblP4HandCard3;
    private JLabel lblP4HandCard4;
    private JLabel lblP4HandCard5;
    private JLabel lblPlayer5HandHeader;
    private JPanel panelPlayer5Hand;
    private JLabel lblP5HandCard1;
    private JLabel lblP5HandCard2;
    private JLabel lblP5HandCard3;
    private JLabel lblP5HandCard4;
    private JLabel lblP5HandCard5;
    private JLabel lblPlayer6HandHeader;
    private JPanel panelPlayer6Hand;
    private JLabel lblP6HandCard1;
    private JLabel lblP6HandCard2;
    private JLabel lblP6HandCard3;
    private JLabel lblP6HandCard4;
    private JLabel lblP6HandCard5;
    private JLabel[] player1Cards = {lblP1HandCard1, lblP1HandCard2, lblP1HandCard3, lblP1HandCard4, lblP1HandCard5};
    private JLabel[] player2Cards = {lblP2HandCard1, lblP2HandCard2, lblP2HandCard3, lblP2HandCard4, lblP2HandCard5};
    private JLabel[] player3Cards = {lblP3HandCard1, lblP3HandCard2, lblP3HandCard3, lblP3HandCard4, lblP3HandCard5};
    private JLabel[] player4Cards = {lblP4HandCard1, lblP4HandCard2, lblP4HandCard3, lblP4HandCard4, lblP4HandCard5};
    private JLabel[] player5Cards = {lblP5HandCard1, lblP5HandCard2, lblP5HandCard3, lblP5HandCard4, lblP5HandCard5};
    private JLabel[] player6Cards = {lblP6HandCard1, lblP6HandCard2, lblP6HandCard3, lblP6HandCard4, lblP6HandCard5};
    private JLabel[][] playersCards = {player1Cards, player2Cards, player3Cards, player4Cards, player5Cards, player6Cards};
    private JLabel[] playerNames = {lblPlayer1HandHeader, lblPlayer2HandHeader, lblPlayer3HandHeader, lblPlayer4HandHeader, lblPlayer5HandHeader, lblPlayer6HandHeader};
    private JPanel[] playerPanels = {panelPlayer1Hand, panelPlayer2Hand, panelPlayer3Hand, panelPlayer4Hand, panelPlayer5Hand, panelPlayer6Hand};
    private JLabel[] yourCards = {lblYHandCard1, lblYHandCard2, lblYHandCard3, lblYHandCard4, lblYHandCard5};

    public ContinueGameDialog(ActionListener actionListener, LinkedList<Player> winners, LinkedList<Player> players, Player human, boolean canContinue, JFrame parent)  {

        for (JPanel panel: playerPanels) {
            panel.setVisible(false);
        }

        int i = 0;
        for (Player player: players) {
            if (player.isBot()) {
                if (player.getLastAction() != Player.ACTION_FOLD) {
                    playerPanels[i].setVisible(true);
                    String headerText = player.getName();
                    if (player.getHand() != null) {
                        headerText += ": " + player.getHand().getCombinationName();
                        showHand(playersCards[i], player.getHand());
                    } else {
                        for (JLabel cardLabel: playersCards[i]) {
                            cardLabel.setVisible(false);
                        }
                    }
                    headerText += winners.contains(player) ? ": Winner!" : ": Loser!";
                    playerNames[i].setText(headerText);
                }
            }
            i++;
        }



        if (human.getHand() != null && human.getLastAction() != Player.ACTION_FOLD) {
            panelYourHand.setVisible(true);
            showHand(yourCards, human.getHand());
            lblYourHandHeader.setText("Your hand: " + human.getHand().getCombinationName());
        } else {
            panelYourHand.setVisible(false);
        }
        if (winners.contains(human)) {
            lblWin.setVisible(true);
            lblLose.setVisible(false);
        } else {
            lblWin.setVisible(false);
            lblLose.setVisible(true);
            if (!canContinue) {
                btnContinue.setEnabled(false);
            }
        }

        btnContinue.addActionListener(e -> {
            if (actionListener != null) {
                actionListener.actionPerformed(e);
            }
            this.setVisible(false);
        });

        this.setModal(true);
        this.setTitle("Game results");
        panel1.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel2.setBorder(new EmptyBorder(20, 0, 0, 0));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(panel1);

        this.pack();
        this.setLocationRelativeTo(parent);
    }

    private void showHand(JLabel[] cards, UniversalHand hand) {
        for(int i = 0; i < 5; i++) {
            cards[i].setText("");
            cards[i].setIcon(getIconForCard(hand.getBestHand().get(i), true));
        }
    }
}
