package com.lamtev.poker.core.api;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.states.PokerState;
import com.lamtev.poker.core.states.SettingsPokerState;

import java.util.ArrayList;
import java.util.List;

public class Poker implements PokerAPI {

    private PokerState state = new SettingsPokerState(this);
    //TODO think about what is better: many listeners or one
    private List<StateChangedListener> stateChangedListeners = new ArrayList<>();
    private List<GameIsOverListener> gameIsOverListeners = new ArrayList<>();
    private List<MoveAbilityListener> moveAbilityListeners = new ArrayList<>();
    private boolean gameIsSetUp = false;

    @Override
    public void addStateChangedListener(StateChangedListener stateChangedListener) {
        stateChangedListeners.add(stateChangedListener);
        notifyStateChangedListeners();
    }

    @Override
    public void addGameIsOverListener(GameIsOverListener gameIsOverListener) {
        gameIsOverListeners.add(gameIsOverListener);
    }

    @Override
    public void addMoveAbilityListener(MoveAbilityListener moveAbilityListener) {
        moveAbilityListeners.add(moveAbilityListener);
    }

    @Override
    public void setUp(List<PlayerInfo> playersInfo, int smallBlindSize) {
        if (stateChangedListeners.size() == 0 || gameIsOverListeners.size() == 0) {
            throw new RuntimeException("You must add at least one StageChangedListener and at least one GameIsOverListener");
        }
        state.setUp(playersInfo, smallBlindSize);
        gameIsSetUp = true;
    }

    @Override
    public int getPlayerWager(String playerId) throws Exception {
        validateGameIsSetUp();
        return state.getPlayerWager(playerId);
    }

    @Override
    public Cards getPlayerCards(String playerID) throws Exception {
        validateGameIsSetUp();
        return state.getPlayerCards(playerID);
    }

    @Override
    public int getPlayerStack(String playerId) throws Exception {
        validateGameIsSetUp();
        return state.getPlayerStack(playerId);
    }

    @Override
    public Cards getCommonCards() throws Exception {
        validateGameIsSetUp();
        return state.getCommonCards();
    }

    @Override
    public int getMoneyInBank() throws Exception {
        validateGameIsSetUp();
        return state.getMoneyInBank();
    }

    @Override
    public List<PlayerInfo> getPlayersInfo() throws Exception {
        validateGameIsSetUp();
        return state.getPlayersInfo();
    }

    @Override
    public void call() throws Exception {
        validateGameIsSetUp();
        state.call();
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        validateGameIsSetUp();
        state.raise(additionalWager);
    }

    @Override
    public void allIn() throws Exception {
        validateGameIsSetUp();
        state.allIn();
    }

    @Override
    public void fold() throws Exception {
        validateGameIsSetUp();
        state.fold();
    }

    @Override
    public void check() throws Exception {
        validateGameIsSetUp();
        state.check();
    }

    @Override
    public PokerHand.Name showDown() throws Exception {
        validateGameIsSetUp();
        return state.showDown();
    }

    public void setState(PokerState newState) {
        state = newState;
        notifyStateChangedListeners();
    }

    public void notifyGameIsOverListeners(List<PlayerInfo> playersInfo) {
        gameIsOverListeners.forEach(listener -> listener.updatePlayersInfo(playersInfo));
    }

    public void notifyMoveAbilityListeners() {
        //TODO implement
    }

    private void notifyStateChangedListeners() {
        stateChangedListeners.forEach(listener -> listener.changeState(state.getClass().getSimpleName()));
    }

    private void validateGameIsSetUp() throws Exception {
        if (!gameIsSetUp) {
            throw new Exception("Game is not set up");
        }
    }

}