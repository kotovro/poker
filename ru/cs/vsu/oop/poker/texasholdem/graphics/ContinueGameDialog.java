package ru.cs.vsu.oop.poker.texasholdem.graphics;

import ru.cs.vsu.oop.poker.base.Hand;
import ru.cs.vsu.oop.poker.base.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;

import static ru.cs.vsu.oop.poker.texasholdem.graphics.IconHelper.getIconForCard;

public class ContinueGameDialog extends JDialog {
    private JPanel panel1;
    private JLabel lblWin;
    private JLabel lblLose;
    private JButton btnContinue;
    private JPanel panelWinnerHand;
    private JPanel panelYourHand;
    private JLabel lblWHandCard1;
    private JLabel lblWHandCard2;
    private JLabel lblWHandCard3;
    private JLabel lblWHandCard4;
    private JLabel lblWHandCard5;
    private JLabel lblYHandCard1;
    private JLabel lblYHandCard2;
    private JLabel lblYHandCard3;
    private JLabel lblYHandCard4;
    private JLabel lblYHandCard5;
    private JLabel lblWinnerHandHeader;
    private JLabel lblYourHandHeader;
    private JPanel panel2;
    private JLabel[] winnerCards = {lblWHandCard1, lblWHandCard2, lblWHandCard3, lblWHandCard4, lblWHandCard5};
    private JLabel[] yourCards = {lblYHandCard1, lblYHandCard2, lblYHandCard3, lblYHandCard4, lblYHandCard5};

    public ContinueGameDialog(ActionListener actionListener, Player[] winners, Player human, boolean canContinue, JFrame parent)  {
        Player winner = winners[0];
        for (Player curWinner: winners) {
            if (curWinner == human) {
                winner = human;
                break;
            }
        }
        panelYourHand.setVisible(false);
        panelWinnerHand.setVisible(false);

        if (human.getHand() != null) {
            panelYourHand.setVisible(true);
            showHand(yourCards, human.getHand());
        }
        if (winner == human) {
            lblWin.setVisible(true);
            lblLose.setVisible(false);
            panelWinnerHand.setVisible(false);
        } else {
            lblWin.setVisible(false);
            lblLose.setVisible(true);
            if (winner.getHand() != null) {
                panelWinnerHand.setVisible(true);
                showHand(winnerCards, winner.getHand());
            }
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

    private void showHand(JLabel[] cards, Hand hand) {
        for(int i = 0; i < 5; i++) {
            cards[i].setText("");
            cards[i].setIcon(getIconForCard(hand.getHand()[i], true));
        }
    }
}
