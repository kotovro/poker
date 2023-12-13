package ru.cs.vsu.oop.poker.graphics;

import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.Player;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GUIHelper {
    public static Icon getIconForCard(Card card, boolean showCard, Class<?> cls) {
        URL filename = getIconFileName("empty", cls);
        if (card != null) {
            filename = getIconFileName(!showCard ? "b" : card.getShortName(), cls);
        }
        return getIconForName(filename);
    }

    public static Icon getIconForName(URL filename) {
        return new ImageIcon(filename);
    }
    public static URL getIconFileName(String name, Class<?> cls) {
        String path = "ru\\cs\\vsu\\oop\\poker\\cards\\";
        String extension = ".gif";
        return cls.getClassLoader().getResource("ru/cs/vsu/oop/poker/graphics/images/" + name + extension);
    }
    public static Color getStatusColor(int status) {
        switch (status) {
            case Player.ACTION_FOLD -> { return Color.WHITE; }
            case Player.ACTION_RAISE -> { return Color.RED; }
            default -> { return Color.YELLOW; }
        }
    }
}
