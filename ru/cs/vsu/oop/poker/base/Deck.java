package ru.cs.vsu.oop.poker.base;

import java.util.LinkedList;
import java.util.Random;

public class Deck {

    private static Random random = new Random();
    private LinkedList<Card> deckOfCards = new LinkedList<>();

    public Deck() {
        for (Card.Suits suit: Card.Suits.values()) {
            for(Card.CardNames cardName: Card.CardNames.values()) {
                deckOfCards.add(new Card(suit, cardName));
            }
        }
    }
    private Deck(LinkedList<Card> cards) {
        this.deckOfCards = cards;
    }

    public Card drawCard() {
        int cardNumber = random.nextInt(this.deckOfCards.size());
        return deckOfCards.remove(cardNumber);
    }
    public Deck shuffle() {
        LinkedList<Card> shuffledDeck = new LinkedList<>();
        while (this.deckOfCards.size() > 1) {
            int cardNumber = random.nextInt(0, deckOfCards.size());
            shuffledDeck.add(this.deckOfCards.remove(cardNumber));
        }
        shuffledDeck.add(this.deckOfCards.remove(0));
        return new Deck(shuffledDeck);
    }
}