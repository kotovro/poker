package ru.cs.vsu.oop.poker.texasholdem.graphics;

import ru.cs.vsu.oop.poker.base.Card;

import javax.swing.*;

public class IconHelper {
    public static Icon getIconForCard(Card card, boolean showCard) {
        String filename = getIconFileName(!showCard ? "b" : card.getShortName());
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
}
