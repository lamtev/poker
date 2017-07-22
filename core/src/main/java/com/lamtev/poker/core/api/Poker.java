package com.lamtev.poker.core.api;

import com.lamtev.poker.core.event_listeners.ListenerManager;
import com.lamtev.poker.core.event_listeners.MoveAbility;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.states.PokerState;
import com.lamtev.poker.core.states.SettingsState;
import com.lamtev.poker.core.states.exceptions.*;

import java.util.List;
import java.util.Map;

public class Poker implements RoundOfPlay {

    private PokerState state;
    private final ListenerManager listenerManager = new ListenerManager();

    Poker(List<PlayerIdStack> playerIdStackList, String dealerId, int smallBlindWager) {
        state = new SettingsState(this, playerIdStackList, dealerId, smallBlindWager);
    }

    @Override
    public void start() {
        state.start();
    }

    @Override
    public void subscribe(PokerPlay pokerPlay) {
        listenerManager.subscribe(pokerPlay);
    }

    @Override
    public void subscribe(PokerAI pokerAI) {
        listenerManager.subscribe(pokerAI);
        listenerManager.notifyRoundOfPlayChanged(this);
    }

    @Override
    public void call() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            IsNotEnoughMoneyException,
            RoundOfPlayIsOverException,
            UnallowableMoveException {
        makeSureThatGameIsSetUp();
        state.call();
    }

    @Override
    public void raise(int additionalWager) throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException,
            RoundOfPlayIsOverException,
            UnallowableMoveException {
        makeSureThatGameIsSetUp();
        state.raise(additionalWager);
    }

    @Override
    public void allIn() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException,
            RoundOfPlayIsOverException,
            UnallowableMoveException {
        makeSureThatGameIsSetUp();
        state.allIn();
    }

    @Override
    public void fold() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            RoundOfPlayIsOverException,
            UnallowableMoveException {
        makeSureThatGameIsSetUp();
        state.fold();
    }

    @Override
    public void check() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            RoundOfPlayIsOverException,
            UnallowableMoveException {
        makeSureThatGameIsSetUp();
        state.check();
    }

    @Override
    public void showDown() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            RoundOfPlayIsOverException {
        makeSureThatGameIsSetUp();
        state.showDown();
    }

    public void setState(PokerState newState) {
        state = newState;
        notifyStateChangedListeners();
        state.start();
    }

    public void notifyBankMoneyUpdatedListeners(int money, int wager) {
        listenerManager.notifyBankMoneyUpdatedListeners(money, wager);
    }

    public void notifyBlindWagersPlacedListeners() {
        listenerManager.notifyBlindWagersPlacedListeners();
    }

    public void notifyCommunityCardsDealtListeners(List<Card> addedCards) {
        listenerManager.notifyCommunityCardsDealtListeners(addedCards);
    }

    public void notifyCurrentPlayerChangedListeners(String playerId) {
        listenerManager.notifyCurrentPlayerChangedListeners(playerId);
    }

    public void notifyHoleCardsDealtListeners(Map<String, List<Card>> playerIdToCards) {
        listenerManager.notifyHoleCardsDealtListeners(playerIdToCards);
    }

    public void notifyMoveAbilityListeners(String playerId, MoveAbility moveAbility) {
        listenerManager.notifyMoveAbilityListeners(playerId, moveAbility);
    }

    public void notifyPlayerAllinnedListeners(String playerId) {
        listenerManager.notifyPlayerAllinnedListeners(playerId);
    }

    public void notifyPlayerCalledListeners(String playerId) {
        listenerManager.notifyPlayerCalledListeners(playerId);
    }

    public void notifyPlayerCheckedListeners(String playerId) {
        listenerManager.notifyPlayerCheckedListeners(playerId);
    }

    public void notifyPlayerFoldListeners(String playerId) {
        listenerManager.notifyPlayerFoldListeners(playerId);
    }

    public void notifyPlayerMoneyUpdatedListeners(String playerId, int playerStack, int playerWager) {
        listenerManager.notifyPlayerMoneyUpdatedListeners(playerId, playerStack, playerWager);
    }

    public void notifyPlayerRaisedListeners(String playerId) {
        listenerManager.notifyPlayerRaisedListeners(playerId);
    }

    public void notifyPlayerShowedDownListeners(String playerId, List<Card> holeCards, PokerHand hand) {
        listenerManager.notifyPlayerShowedDownListeners(playerId, holeCards, hand);
    }

    public void notifyRoundOfPlayIsOverListeners(List<PlayerIdStack> playersInfo) {
        listenerManager.notifyRoundOfPlayIsOverListeners(playersInfo);
    }

    private void notifyStateChangedListeners() {
        listenerManager.notifyStateChangedListeners(state.toString());
    }

    private void makeSureThatGameIsSetUp() {
        if ("SettingsState".equals(state.toString())) {
            throw new RuntimeException("Game is not set up");
        }
    }

}