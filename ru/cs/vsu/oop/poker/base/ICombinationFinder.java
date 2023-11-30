package ru.cs.vsu.oop.poker.base;

import java.util.LinkedList;

@FunctionalInterface
public interface ICombinationFinder {
    LinkedList<Card> find(LinkedList<Card> hand);
}
