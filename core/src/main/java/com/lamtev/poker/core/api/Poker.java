package com.lamtev.poker.core.api;

import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.states.PokerState;
import com.lamtev.poker.core.states.SettingsPokerState;
import com.lamtev.poker.core.util.PlayerInfo;
import com.lamtev.poker.core.util.StateChangedListener;

import java.util.ArrayList;
import java.util.List;

public class Poker implements PokerAPI {

    private PokerState state = new SettingsPokerState(this);
    private List<StateChangedListener> stateChangedListeners = new ArrayList<>();

    @Override
    public void addStateChangedListener(StateChangedListener stateChangedListener) throws Exception {
        stateChangedListeners.add(stateChangedListener);
    }

    @Override
    public void setUp(List<PlayerInfo> playersInfo, int smallBlindSize) throws Exception {
        if (stateChangedListeners.size() == 0) {
            throw new Exception("You must add at least one StageChangedListener");
        }
        state.setUp(playersInfo, smallBlindSize);
    }

    @Override
    public int getPlayerWager(String playerId) throws Exception {
        return state.getPlayerWager(playerId);
    }

    @Override
    public Cards getPlayerCards(String playerID) throws Exception {
        return state.getPlayerCards(playerID);
    }

    @Override
    public int getPlayerStack(String playerId) throws Exception {
        return state.getPlayerStack(playerId);
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
    public void allIn() throws Exception {
        state.allIn();
    }

    @Override
    public void fold() throws Exception {
        state.fold();
    }

    @Override
    public void check() throws Exception {
        state.check();
    }

    @Override
    public Cards showDown() throws Exception {
        return state.showDown();
    }

    public void setState(PokerState newState) {
        state = newState;
        stateChangedListeners.forEach(listener -> listener.changeState(state.getClass().getSimpleName()));
    }

    PokerState getState() {
        return state;
    }

}
