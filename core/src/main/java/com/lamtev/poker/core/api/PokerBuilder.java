package com.lamtev.poker.core.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PokerBuilder {

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
        List<PlayerIdStack> playerIdStackList = players.stream()
                .map(it -> new PlayerIdStack(it.id(), it.stack()))
                .collect(Collectors.toList());
        Poker poker = new Poker(playerIdStackList, dealerId, smallBlindSize);
        poker.registerPlay(play);
        players.stream()
                .filter(it -> it instanceof AI)
                .map(it -> (AI) it)
                .forEach(poker::registerAI);
        poker.start();
        return poker;
    }

    private void makeSureThatPokerConfigured() {
        if (players.isEmpty() || dealerId == null || smallBlindSize == 0 || play == null) {
            throw new RuntimeException();
        }
    }

}
