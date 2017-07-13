package com.lamtev.poker.core.model;

public final class Dealer {

    private CardDeck cardDeck;
    private Players players;
    private Cards communityCards;

    public Dealer(Players players, Cards communityCards) {
        this.cardDeck = new CardDeck();
        this.players = players;
        this.communityCards = communityCards;
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
                communityCards.add(cardDeck.pickUpTop());
            }
        } else {
            throw new RuntimeException("can't make flop");
        }

    }

    public void makeTurn() {
        if (isAbleToMakeTurn()) {
            communityCards.add(cardDeck.pickUpTop());
        } else {
            throw new RuntimeException("can't make turn");
        }

    }

    public void makeRiver() {
        if (isAbleToMakeRiver()) {
            communityCards.add(cardDeck.pickUpTop());
        } else {
            throw new RuntimeException("can't make river");
        }
    }

    private boolean preflopHasAlreadyBeen() {
        for (Player player : players) {
            //TODO may be fix bug adding isActive checking
            if (player.isActive() && player.cards().size() != 2) {
                return false;
            }
        }
        return true;
    }

    private void dealTwoCardsToPlayers() {
        for (int i = 0; i < 2; ++i) {
            players.forEach(player -> player.addCard(cardDeck.pickUpTop()));
        }
    }

    private boolean isAbleToMakeFlop() {
        return preflopHasAlreadyBeen() && communityCards.size() == 0;
    }

    private boolean isAbleToMakeTurn() {
        return preflopHasAlreadyBeen() && communityCards.size() == 3;
    }

    private boolean isAbleToMakeRiver() {
        return preflopHasAlreadyBeen() && communityCards.size() == 4;
    }

}
