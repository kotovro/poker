package ru.cs.vsu.oop.poker.texasholdem.graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParamsDialog extends JDialog {
    private JSlider sliderBotsCount;
    private JLabel lblBotsCount;
    private JCheckBox cbXRay;
    private JLabel lblBudget;
    private JTextField txtBudget;
    private JPanel panelMain;
    private JButton btnApply;
    private JButton btnCancel;

    private TxHoldemForm.GameParams params;

    public ParamsDialog(JComponent parent, TxHoldemForm.GameParams params, ActionListener newGameAction) {
        this.setTitle("Settings");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(parent);

        this.params = params;
        btnCancel.addActionListener(e -> {
            this.setVisible(false);
        });
        btnApply.addActionListener(e -> {
            params.setBotCount((int) sliderBotsCount.getValue());
            params.setBudget(Double.parseDouble(txtBudget.getText()));
            params.setXRayEnabled(cbXRay.isSelected());
            this.setVisible(false);
            if (newGameAction != null) {
                newGameAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "newGame"));
            }
        });
    }
    public void updateView() {
        sliderBotsCount.setValue(params.getBotCount());
        cbXRay.setSelected(params.isXRayEnabled());
        txtBudget.setText(Double.toString(params.getBudget()));
    }
}
