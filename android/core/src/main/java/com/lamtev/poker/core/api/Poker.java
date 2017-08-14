package com.lamtev.poker.core.api;

import com.lamtev.poker.core.event_listeners.ListenerManager;
import com.lamtev.poker.core.exceptions.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.MoveAbility;
import com.lamtev.poker.core.states.PokerState;
import com.lamtev.poker.core.states.SettingsState;

import java.util.List;
import java.util.Map;

public class Poker implements RoundOfPlay {

    private PokerState state;
    private final ListenerManager listenerManager = new ListenerManager();

    @Override
    public void call() throws
            ForbiddenMoveException,
            IsNotEnoughMoneyException,
            RoundOfPlayIsOverException,
            UnallowableMoveException {
        state.call();
    }

    @Override
    public void raise(int additionalWager) throws
            ForbiddenMoveException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException,
            RoundOfPlayIsOverException,
            UnallowableMoveException {
        state.raise(additionalWager);
    }

    @Override
    public void allIn() throws
            ForbiddenMoveException,
            RoundOfPlayIsOverException,
            UnallowableMoveException {
        state.allIn();
    }

    @Override
    public void fold() throws
            ForbiddenMoveException,
            RoundOfPlayIsOverException,
            UnallowableMoveException {
        state.fold();
    }

    @Override
    public void check() throws
            ForbiddenMoveException,
            RoundOfPlayIsOverException,
            UnallowableMoveException {
        state.check();
    }

    @Override
    public void showDown() throws
            ForbiddenMoveException,
            RoundOfPlayIsOverException {
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

    public void notifyRivalsBecomeKnownListeners(List<com.lamtev.poker.core.model.Player> players) {
        listenerManager.notifyRivalsBecomeKnownListeners(players);
    }

    Poker(List<Player> playerIdStackList, String dealerId, int smallBlindWager) {
        state = new SettingsState(this, playerIdStackList, dealerId, smallBlindWager);
    }

    void registerPlay(Play play) {
        listenerManager.subscribe(play);
    }

    void registerAI(AI pokerAI) {
        listenerManager.subscribe(pokerAI);
        listenerManager.notifyRoundOfPlayChanged(this);
    }

    void start() {
        state.start();
    }

    private void notifyStateChangedListeners() {
        listenerManager.notifyStateChangedListeners(state.toString());
    }

}