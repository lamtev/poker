package com.lamtev.poker.core;

import java.util.ArrayList;

public class Dealer {

    private CardDeck cardDeck;
    private ArrayList<Player> players;
    private Cards commonCards;

    public Dealer(ArrayList<Player> players) {
        cardDeck = new CardDeck();
        this.players = players;
        commonCards = new Cards();
    }

    public CardDeck cardDeck() {
        return cardDeck;
    }

    public Cards commonCards() {
        return commonCards;
    }

    public void makePreflop() {
        for (int i = 0; i < 2; ++i) {
            players.forEach((x) -> x.takeCard(cardDeck.pickUpTop()));
        }
    }

    public void makeFlop() {
        if (!commonCards.isEmpty()) {
            //TODO normal exception
            throw new RuntimeException();
        }
        for (int i = 0; i < 3; ++i) {
            putTopCardToCommonCards();
        }
    }

    public void makeTurn() {
        if (commonCards.size() != 3) {
            //TODO normal exception
            throw new RuntimeException();
        }
        putTopCardToCommonCards();
    }

    public void makeRiver() {
        if (commonCards.size() != 4) {
            //TODO normal exception
            throw new RuntimeException();
        }
        putTopCardToCommonCards();
    }

    private void putTopCardToCommonCards() {
        commonCards.add(cardDeck.pickUpTop());
    }



}
