package ru.cs.vsu.oop.poker.base;

@FunctionalInterface
public interface IComboChecker {
    Card[] find(UniversalHand hand);
}
