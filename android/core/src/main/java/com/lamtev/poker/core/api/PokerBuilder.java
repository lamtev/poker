package com.lamtev.poker.core.api;

import java.util.ArrayList;
import java.util.List;

public final class PokerBuilder {

    private List<Player> players = new ArrayList<>();
    private String dealerId;
    private int smallBlindWager;
    private Play play;

    public PokerBuilder registerPlay(Play play) {
        this.play = play;
        return this;
    }

    public PokerBuilder registerPlayers(List<Player> players) {
        this.players.addAll(players);
        return this;
    }

    public PokerBuilder setDealerId(String dealerId) {
        this.dealerId = dealerId;
        return this;
    }

    public PokerBuilder setSmallBlindWager(int smallBlindWager) {
        makeSureThatSmallBlindWagerIsPositive(smallBlindWager);
        this.smallBlindWager = smallBlindWager;
        return this;
    }

    public Poker create() {
        makeSureThatPokerConfigured();
        makeSureThatDealerExists();
        Poker poker = new Poker(players, dealerId, smallBlindWager);
        for (Player it : players) {
            if (it instanceof AI) {
                AI ai = (AI) it;
                poker.registerAI(ai);
            }
        }
        poker.registerPlay(play);
        poker.start();
        return poker;
    }

    private void makeSureThatSmallBlindWagerIsPositive(int smallBlindWager) {
        if (smallBlindWager <= 0) {
            throw new RuntimeException("Small blind wager should be greater than 0");
        }
    }

    private void makeSureThatPokerConfigured() {
        if (players.isEmpty() || dealerId == null || smallBlindWager == 0 || play == null) {
            throw new RuntimeException("Poker is not configured");
        }
    }

    private void makeSureThatDealerExists() {
        for (Player player : players) {
            if (player.id().equals(dealerId)) {
                return;
            }
        }
        throw new RuntimeException("Players does not contain dealer with this id: " + dealerId);
    }

}
