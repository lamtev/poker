package com.lamtev.poker.core;

import java.util.ArrayList;

public class Dealer {

    private CardDeck cardDeck;
    private ArrayList<Player> players;
    private ArrayList<Card> commonCards;

    public Dealer(ArrayList<Player> players) {
        cardDeck = new CardDeck();
        this.players = players;
        commonCards = new ArrayList<>();
    }

    public CardDeck cardDeck() {
        return cardDeck;
    }

    public ArrayList<Player> players() {
        return players;
    }

    public ArrayList<Card> commonCards() {
        return commonCards;
    }

    public void shuffle() {
        //TODO shuffling cardDeck
    }

    public void preflop() {
        for (int i = 0; i < 2; ++i) {
            for (Player player : players) {
                player.takeCard(cardDeck.giveTop());
            }
        }
    }

    public void flop() {
        if (!commonCards.isEmpty()) {
            //TODO normal exception
            throw new RuntimeException();
        }
        for (int i = 0; i < 3; ++i) {
            putTopCardToCommonCards();
        }
    }

    public void turn() {
        if (commonCards.size() != 3) {
            //TODO normal exception
            throw new RuntimeException();
        }
        putTopCardToCommonCards();
    }

    public void river() {
        if (commonCards.size() != 4) {
            //TODO normal exception
            throw new RuntimeException();
        }
        putTopCardToCommonCards();
    }

    private void putTopCardToCommonCards() {
        commonCards.add(cardDeck.giveTop());
    }

}
