package com.lamtev.poker.core.api;

import com.lamtev.poker.core.event_listeners.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.states.PokerState;
import com.lamtev.poker.core.states.SettingsState;
import com.lamtev.poker.core.states.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Poker implements PokerAPI {

    private PokerState state = new SettingsState(this);

    private List<StateChangedListener> stateChangedListeners = new ArrayList<>();
    private List<RoundOfPlayIsOverListener> roundOfPlayIsOverListeners = new ArrayList<>();
    private List<MoveAbilityListener> moveAbilityListeners = new ArrayList<>();
    private List<CurrentPlayerChangedListener> currentPlayerChangedListeners = new ArrayList<>();
    private List<CommunityCardsDealtListener> communityCardsDealtListeners = new ArrayList<>();
    private List<MoneyChangedListener> moneyChangedListeners = new ArrayList<>();
    private List<PlayerFoldListener> playerFoldListeners = new ArrayList<>();
    private List<PreflopMadeListener> preflopMadeListeners = new ArrayList<>();
    private List<HoleCardsDealtListener> holeCardsDealtListeners = new ArrayList<>();
    private List<PlayerShowedDownListener> playerShowedDownListeners = new ArrayList<>();
    private boolean listenersAdded = false;

    @Override
    public void subscribe(PokerEventListener pokerEventListener) {
        addCommunityCardsChangedListener(pokerEventListener);
        addCurrentPlayerChangedListener(pokerEventListener);
        addGameIsOverListener(pokerEventListener);
        addMoveAbilityListener(pokerEventListener);
        addPlayerFoldListener(pokerEventListener);
        addPlayerShowedDownListener(pokerEventListener);
        addPreflopMadeListener(pokerEventListener);
        addStateChangedListener(pokerEventListener);
        addWagerPlacedListener(pokerEventListener);
        listenersAdded = true;
    }

    @Override
    public void setUp(List<PlayerIdStack> playersStacks,
                      String dealerId,
                      int smallBlindSize)
            throws RoundOfPlayIsOverException {
        if (playersStacks.size() < 2) {
            //TODO
            throw new RuntimeException("There must be at least 2 players");
        }
        if (!listenersAdded) {
            throw new RuntimeException("You must subscribe");
        }
        state.setUp(playersStacks, dealerId, smallBlindSize);
    }

    @Override
    public void placeBlindWagers() throws
            NotPositiveWagerException,
            ForbiddenMoveException,
            IsNotEnoughMoneyException,
            GameHaveNotBeenStartedException,
            RoundOfPlayIsOverException,
            UnallowableMoveException {
        makeSureThatGameIsSetUp();
        state.placeBlindWagers();

    }

    @Override
    public void call() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            IsNotEnoughMoneyException,
            RoundOfPlayIsOverException,
            UnallowableMoveException {
        makeSureThatGameIsSetUp();
        makeSureThatBlindWagersPlaced();
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
        makeSureThatBlindWagersPlaced();
        state.raise(additionalWager);
    }

    @Override
    public void allIn() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            RoundOfPlayIsOverException,
            UnallowableMoveException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException {
        makeSureThatGameIsSetUp();
        makeSureThatBlindWagersPlaced();
        state.allIn();
    }

    @Override
    public void fold() throws
            UnallowableMoveException,
            RoundOfPlayIsOverException,
            GameHaveNotBeenStartedException {
        makeSureThatGameIsSetUp();
        makeSureThatBlindWagersPlaced();
        state.fold();
    }

    @Override
    public void check() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            RoundOfPlayIsOverException,
            UnallowableMoveException {
        makeSureThatGameIsSetUp();
        makeSureThatBlindWagersPlaced();
        state.check();
    }

    @Override
    public void showDown() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            RoundOfPlayIsOverException {
        makeSureThatGameIsSetUp();
        makeSureThatBlindWagersPlaced();
        state.showDown();
    }

    public void setState(PokerState newState) {
        state = newState;
        notifyStateChangedListeners();
    }

    public void notifyGameOverListeners(List<PlayerIdStack> playersInfo) {
        roundOfPlayIsOverListeners.forEach(listener -> listener.onRoundOfPlayIsOver(playersInfo));
    }

    public void notifyPlayerShowedDownListeners(String playerId, List<Card> holeCards, PokerHand hand) {
        playerShowedDownListeners.forEach(listener -> listener.playerShowedDown(playerId, holeCards, hand));
    }

    public void notifyMoveAbilityListeners() {
        //TODO implement
    }

    public void notifyCommunityCardsChangedListeners(List<Card> addedCards) {
        communityCardsDealtListeners.forEach(listener -> listener.onCommunityCardsDealt(addedCards));
    }

    public void notifyCurrentPlayerChangedListeners(String id) {
        currentPlayerChangedListeners.forEach(listener -> listener.onCurrentPlayerChanged(id));
    }

    private void notifyStateChangedListeners() {
        stateChangedListeners.forEach(listener -> listener.stateChanged(state.getClass().getSimpleName()));
    }

    public void notifyMoneyChangedListeners(String playerId, int playerStack, int playerWager, int bank) {
        moneyChangedListeners.forEach(listener -> listener.onMoneyChanged(playerId, playerStack, playerWager, bank));
    }

    public void notifyPreflopMadeListeners(Map<String, Cards> playerIdToCards) {
        preflopMadeListeners.forEach(listener -> listener.preflopMade(playerIdToCards));
    }

    public void notifyHoleCardsDealtListeners(String playerId, List<Card> cards) {

    }

    public void notifyPlayerFoldListeners(String id) {
        playerFoldListeners.forEach(listener -> listener.playerFold(id));
    }

    private void makeSureThatGameIsSetUp() {
        if ("SettingsState".equals(state.toString())) {
            throw new RuntimeException("Game is not set up");
        }
    }

    private void makeSureThatBlindWagersPlaced() {
        if ("BlindsState".equals(state.toString())) {
            throw new RuntimeException("Blind wagers have not been placed");
        }
    }

    private void addStateChangedListener(StateChangedListener stateChangedListener) {
        stateChangedListeners.add(stateChangedListener);
        notifyStateChangedListeners();
    }

    private void addGameIsOverListener(RoundOfPlayIsOverListener roundOfPlayIsOverListener) {
        roundOfPlayIsOverListeners.add(roundOfPlayIsOverListener);
    }

    private void addCurrentPlayerChangedListener(CurrentPlayerChangedListener currentPlayerChangedListener) {
        currentPlayerChangedListeners.add(currentPlayerChangedListener);
    }

    private void addCommunityCardsChangedListener(CommunityCardsDealtListener communityCardsDealtListener) {
        communityCardsDealtListeners.add(communityCardsDealtListener);
    }

    private void addPlayerShowedDownListener(PlayerShowedDownListener playerShowedDownListener) {
        playerShowedDownListeners.add(playerShowedDownListener);
    }

    private void addWagerPlacedListener(MoneyChangedListener moneyChangedListener) {
        moneyChangedListeners.add(moneyChangedListener);
    }

    private void addPlayerFoldListener(PlayerFoldListener playerFoldListener) {
        playerFoldListeners.add(playerFoldListener);
    }

    private void addMoveAbilityListener(MoveAbilityListener moveAbilityListener) {
        moveAbilityListeners.add(moveAbilityListener);
    }

    private void addPreflopMadeListener(PreflopMadeListener preflopMadeListener) {
        preflopMadeListeners.add(preflopMadeListener);
    }

}