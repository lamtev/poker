package com.lamtev.poker.core;

import java.util.ArrayList;

public class Poker implements PokerAPI {

    private int numberOfPlayers;
    private int smallBlind;
    private int bigBlind;
    private ArrayList<Player> players;
    private Dealer dealer;
    private int bank;

    public void setUpNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setUpBlinds(int smallBlind) {
        this.smallBlind = smallBlind;
        this.bigBlind = 2 * smallBlind;
    }

    public void  call() {

    }

    public void raise(int additionalWager) {

    }

    public void fold() {

    }
    public void check() {

    }

    public void reRaise(int additionalWager) {

    }

}
