package com.lamtev.poker.core.api;

import java.util.ArrayList;
import java.util.List;

public final class PokerBuilder {

    private List<Player> players = new ArrayList<>();
    private String dealerId;
    private int smallBlindSize;
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
        this.smallBlindSize = smallBlindWager;
        return this;
    }

    public Poker create() {
        makeSureThatPokerConfigured();
        Poker poker = new Poker(players, dealerId, smallBlindSize);
        players.stream()
                .filter(it -> it instanceof AI)
                .map(it -> (AI) it)
                .forEach(poker::registerAI);
        poker.registerPlay(play);
        poker.start();
        return poker;
    }

    private void makeSureThatPokerConfigured() {
        if (players.isEmpty() || dealerId == null || smallBlindSize == 0 || play == null) {
            throw new RuntimeException();
        }
    }

}
