package com.lamtev.poker.core;

import java.util.List;

public final class Dealer {

    private Cards cardDeck;
    private List<Player> players;
    private Cards commonCards;

    public Dealer(List<Player> players) {
        this.cardDeck = new CardDeck();
        this.players = players;
        this.commonCards = new Cards();
    }

    public Cards getCardDeck() {
        return cardDeck;
    }

    public Cards getCommonCards() {
        return commonCards;
    }

    public void makePreflop() throws Exception {
        if (preflopHasAlreadyBeen()) {
            //TODO normal exception
            throw new RuntimeException();
        }
        dealTwoCardsToPlayers();
    }

    public void makeFlop() throws Exception {
        if (isAbleToMakeFlop()) {
            for (int i = 0; i < 3; ++i) {
                commonCards.add(cardDeck.pickUpTop());
            }
        } else {
            //TODO normal exception
            throw new RuntimeException();
        }

    }

    public void makeTurn() throws Exception {
        if (isAbleToMakeTurn()) {
            commonCards.add(cardDeck.pickUpTop());
        } else {
            //TODO normal exception
            throw new RuntimeException();
        }

    }

    public void makeRiver() throws Exception {
        if (isAbleToMakeRiver()) {
            commonCards.add(cardDeck.pickUpTop());
        } else {
            //TODO normal exception
            throw new RuntimeException();
        }
    }

    private boolean preflopHasAlreadyBeen() {
        for (Player player : players) {
            if (player.getCards().size() != 2) {
                return false;
            }
        }
        return true;
    }

    private void dealTwoCardsToPlayers() {
        for (int i = 0; i < 2; ++i) {
            players.forEach((x) -> x.addCard(cardDeck.pickUpTop()));
        }
    }

    private boolean isAbleToMakeFlop() {
        return preflopHasAlreadyBeen() && commonCards.size() == 0;
    }

    private boolean isAbleToMakeTurn() {
        return preflopHasAlreadyBeen() && commonCards.size() == 3;
    }

    private boolean isAbleToMakeRiver() {
        return preflopHasAlreadyBeen() && commonCards.size() == 4;
    }

}
