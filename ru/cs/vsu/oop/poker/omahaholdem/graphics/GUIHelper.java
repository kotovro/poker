package ru.cs.vsu.oop.poker.omahaholdem.graphics;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Player;

import javax.swing.*;
import java.awt.*;

public class GUIHelper {
    public static Icon getIconForCard(Card card, boolean showCard) {
        String filename = getIconFileName("empty");
        if (card != null) {
            filename = getIconFileName(!showCard ? "b" : card.getShortName());
        }
        return getIconForName(filename);
    }

    public static Icon getIconForName(String filename) {
        return new ImageIcon(filename);
    }
    public static String getIconFileName(String name) {
        String path = "ru\\cs\\vsu\\oop\\poker\\cards\\";
        String extension = ".gif";
        return path + name + extension;
    }
    public static Color getStatusColor(int status) {
        switch (status) {
            case Player.ACTION_FOLD -> { return Color.WHITE; }
            case Player.ACTION_RAISE -> { return Color.RED; }
            default -> { return Color.YELLOW; }
        }
    }
}
