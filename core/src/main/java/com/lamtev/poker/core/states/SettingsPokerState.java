package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerIdStack;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;
import com.lamtev.poker.core.states.exceptions.GameHaveNotBeenStartedException;

import java.util.ArrayList;
import java.util.List;

public class SettingsPokerState extends AbstractPokerState {

    private final Poker poker;

    public SettingsPokerState(Poker poker) {
        this.poker = poker;
    }

    @Override
    public void setUp(List<PlayerIdStack> playersInfo, String dealerId, int smallBlindSize) {
        List<Player> playerList = new ArrayList<>();
        playersInfo.forEach(playerIdStack -> {
            String id = playerIdStack.getId();
            int stack = playerIdStack.getStack();
            playerList.add(new Player(id, stack));
        });
        final Players players = new Players(playerList, dealerId);
        final Cards communityCards = new Cards();
        poker.setState(new BlindsPokerState(
                poker,
                players,
                new Bank(players),
                new Dealer(players, communityCards),
                communityCards,
                smallBlindSize
        ));
    }

    @Override
    public void placeBlindWagers() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public void call() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public void raise(int additionalWager) throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public void allIn() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public void fold() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public void check() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public void showDown() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

}