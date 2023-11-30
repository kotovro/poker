package ru.cs.vsu.oop.poker.base;

public class DebugLogger {
    public enum LogLevel {
        DEBUG;
    }

    public static void log(LogLevel loglvl, String message) {
        System.out.println(message);
    }
}
