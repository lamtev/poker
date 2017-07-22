package com.lamtev.poker.core.api;

import java.util.List;

public class PokerBuilder {

    private List<PlayerIdStack> playerIdStacks;
    private String dealerId;
    private int smallBlindSize;

    public PokerBuilder registerPlayers(List<PlayerIdStack> playerIdStacks) {
        this.playerIdStacks = playerIdStacks;
        return this;
    }

    public PokerBuilder setDealerId(String dealerId) {
        this.dealerId = dealerId;
        return this;
    }

    public PokerBuilder setSmallBlindWager(int smallBlindSize) {
        this.smallBlindSize = smallBlindSize;
        return this;
    }

    public Poker create() {
        makeSureThatPokerConfigured();
        return new Poker(playerIdStacks, dealerId, smallBlindSize);
    }

    private void makeSureThatPokerConfigured() {
        if (playerIdStacks == null || dealerId == null || smallBlindSize == 0) {
            throw new RuntimeException();
        }
    }

}
