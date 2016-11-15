package com.lamtev.poker.core.model;

public final class Dealer {

    private CardDeck cardDeck;
    private Players players;
    private Cards commonCards;

    public Dealer(Players players, Cards commonCards) {
        this.cardDeck = new CardDeck();
        this.players = players;
        this.commonCards = commonCards;
        cardDeck.shuffle();
    }

    public void makePreflop() {
        if (preflopHasAlreadyBeen()) {
            throw new RuntimeException("preflop has already been");
        }
        dealTwoCardsToPlayers();
    }

    public void makeFlop() {
        if (isAbleToMakeFlop()) {
            for (int i = 0; i < 3; ++i) {
                commonCards.add(cardDeck.pickUpTop());
            }
        } else {
            throw new RuntimeException("can't make flop");
        }

    }

    public void makeTurn() {
        if (isAbleToMakeTurn()) {
            commonCards.add(cardDeck.pickUpTop());
        } else {
            throw new RuntimeException("can't make turn");
        }

    }

    public void makeRiver() {
        if (isAbleToMakeRiver()) {
            commonCards.add(cardDeck.pickUpTop());
        } else {
            throw new RuntimeException("can't make river");
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
            players.forEach((player) -> player.addCard(cardDeck.pickUpTop()));
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
