package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;

import java.util.ArrayList;
import java.util.List;

public final class SettingsState extends AbstractPokerState {

    private final Poker poker;

    private final List<com.lamtev.poker.core.api.Player> players;
    private final String dealerId;
    private final int smallBlindWager;

    public SettingsState(Poker poker, List<com.lamtev.poker.core.api.Player> players, String dealerId, int smallBlindWager) {
        this.poker = poker;
        this.players = players;
        this.dealerId = dealerId;
        this.smallBlindWager = smallBlindWager;
    }

    @Override
    public void start() {
        List<Player> playerList = new ArrayList<>();
        players.forEach(it -> {
            String id = it.id();
            int stack = it.stack();
            playerList.add(new Player(id, stack));
        });
        final Players players = new Players(playerList, dealerId);
        final Cards communityCards = new Cards();
        poker.notifyRivalsBecomeKnownListeners(playerList);
        poker.setState(new BlindsState(
                poker,
                players,
                new Bank(players),
                new Dealer(players, communityCards),
                communityCards,
                new MoveAbility(),
                smallBlindWager
        ));
    }

    @Override
    public void call() {

    }

    @Override
    public void raise(int additionalWager) {

    }

    @Override
    public void allIn() {

    }

    @Override
    public void fold() {

    }

    @Override
    public void check() {

    }

    @Override
    public void showDown() {

    }

}