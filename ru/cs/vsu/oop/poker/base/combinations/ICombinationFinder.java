package ru.cs.vsu.oop.poker.base.combinations;

import ru.cs.vsu.oop.poker.base.Card;

import java.util.LinkedList;

@FunctionalInterface
public interface ICombinationFinder {
    LinkedList<Card> find(LinkedList<Card> hand);
}
