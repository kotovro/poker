package ru.cs.vsu.oop.poker.base;

@FunctionalInterface
public interface IComboFinder {
    Combinations findBestCombo(Card[] hand);
}
