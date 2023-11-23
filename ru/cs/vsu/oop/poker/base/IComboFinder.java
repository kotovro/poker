package ru.cs.vsu.oop.poker.base;

@FunctionalInterface
public interface IComboFinder {
    Card[] find(UniversalHand hand);
}
