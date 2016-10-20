package com.lamtev.poker.core;

import java.util.List;

public class Dealer {

    private Cards cardDeck;
    private List<Player> players;
    private Cards commonCards;

    public Dealer(List<Player> players) {
        cardDeck = new CardDeck();
        this.players = players;
        commonCards = new Cards();
    }

    public Cards cardDeck() {
        return cardDeck;
    }

    public Cards commonCards() {
        return commonCards;
    }

    public void makePreflop() {
        if (preflopHasAlreadyBeen()) {
            //TODO normal exception
            throw new RuntimeException();
        }
        dealTwoCardsToPlayers();
    }

    public void makeFlop() {
        if (!preflopHasAlreadyBeen() || flopHasAlreadyBeen()) {
            //TODO normal exception
            throw new RuntimeException();
        }
        for (int i = 0; i < 3; ++i) {
            putTopCardToCommonCards();
        }
    }

    public void makeTurn() {
        if (!preflopHasAlreadyBeen() || !flopHasAlreadyBeen() || turnHasAlreadyBeen()) {
            //TODO normal exception
            throw new RuntimeException();
        }
        putTopCardToCommonCards();
    }

    public void makeRiver() {
        if (!preflopHasAlreadyBeen() || !flopHasAlreadyBeen() ||
                !turnHasAlreadyBeen() || riverHasAlreadyBeen()) {
            //TODO normal exception
            throw new RuntimeException();
        }
        putTopCardToCommonCards();
    }

    private boolean preflopHasAlreadyBeen() {
        for (Player player : players) {
            if (player.cards().size() == 2) {
                return true;
            }
        }

        return false;
    }

    private boolean flopHasAlreadyBeen() {
        return commonCards.size() >= 3;
    }

    private boolean turnHasAlreadyBeen() {
        return commonCards.size() >= 4;
    }

    private boolean riverHasAlreadyBeen() {
        return commonCards.size() == 5;
    }

    private void dealTwoCardsToPlayers() {
        for (int i = 0; i < 2; ++i) {
            players.forEach((x) -> x.takeCard(cardDeck.pickUpTop()));
        }
    }

    private void putTopCardToCommonCards() {
        commonCards.add(cardDeck.pickUpTop());
    }

}
