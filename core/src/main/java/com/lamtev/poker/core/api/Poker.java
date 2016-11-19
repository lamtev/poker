package com.lamtev.poker.core.api;

import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.states.PokerState;
import com.lamtev.poker.core.states.SettingsPokerState;
import com.lamtev.poker.core.util.PlayerInfo;

import java.util.List;

public class Poker implements PokerAPI {

    private PokerState state;

    public Poker() throws Exception {
        state = new SettingsPokerState();
}

    @Override
    public void setUp(List<PlayerInfo> playersInfo, int smallBlindSize) throws Exception {
        state = new SettingsPokerState(this, playersInfo);
        state.setUp(, smallBlindSize);

    }

    @Override
    public int getPlayerWager(String playerId) throws Exception {
        return state.getPlayerWager(playerId);
    }

    @Override
    public int getPlayerStack(String playerId) throws Exception {
        return state.getPlayerStack(playerId);
    }

    @Override
    public Cards getPlayerCards(String playerId) throws Exception {
        return state.getPlayerCards(playerId);
    }

    @Override
    public Cards getCommonCards() throws Exception {
        return state.getCommonCards();
    }

    @Override
    public int getMoneyInBank() throws Exception {
        return state.getMoneyInBank();
    }

    @Override
    public List<PlayerInfo> getPlayersInfo() throws Exception {
        return state.getPlayersInfo();
    }

    @Override
    public void call() throws Exception {
        state.call();
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        state.raise(additionalWager);
    }

    @Override
    public void fold() throws Exception {
        state.fold();
    }

    @Override
    public void check() throws Exception {
        state.check();
    }

    public void setState(PokerState newState) {
        state = newState;
    }

    PokerState getState() {
        return state;
    }

}
