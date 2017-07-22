package com.lamtev.poker.core.api;

import java.util.ArrayList;
import java.util.List;

public class PokerBuilder {

    private List<PlayerIdStack> playerIdStacks;
    private String dealerId;
    private int smallBlindSize;
    private Play play;
    private List<AI> ais = new ArrayList<>();

    public PokerBuilder registerPlayers(List<PlayerIdStack> playerIdStacks) {
        this.playerIdStacks = playerIdStacks;
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

    public PokerBuilder registerPlay(Play play) {
        this.play = play;
        return this;
    }

    public PokerBuilder registerAI(AI ai) {
        ais.add(ai);
        return this;
    }

    public Poker create() {
        makeSureThatPokerConfigured();
        Poker poker = new Poker(playerIdStacks, dealerId, smallBlindSize);
        poker.registerPlay(play);
        ais.forEach(poker::registerAI);
        poker.start();
        return poker;
    }

    private void makeSureThatPokerConfigured() {
        if (playerIdStacks == null || dealerId == null || smallBlindSize == 0 || play == null) {
            throw new RuntimeException();
        }
    }

}
