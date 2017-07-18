package com.lamtev.poker.core.api;

import com.lamtev.poker.core.event_listeners.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.states.PokerState;
import com.lamtev.poker.core.states.SettingsState;
import com.lamtev.poker.core.states.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Poker implements RoundOfPlay {

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

    public void subscribe(PokerEventListener pokerEventListener) {
        communityCardsDealtListeners.add(pokerEventListener);
        currentPlayerChangedListeners.add(pokerEventListener);
        roundOfPlayIsOverListeners.add(pokerEventListener);
        moveAbilityListeners.add(pokerEventListener);
        playerFoldListeners.add(pokerEventListener);
        playerShowedDownListeners.add(pokerEventListener);
        preflopMadeListeners.add(pokerEventListener);
        stateChangedListeners.add(pokerEventListener);
        notifyStateChangedListeners();
        moneyChangedListeners.add(pokerEventListener);
        listenersAdded = true;
    }

    @Override
    public void subscribe(PokerAI listener) {
        communityCardsDealtListeners.add(listener);
        currentPlayerChangedListeners.add(listener);
//        roundOfPlayIsOverListeners.add(listener);
        moveAbilityListeners.add(listener);
        playerFoldListeners.add(listener);
        stateChangedListeners.add(listener);
        notifyStateChangedListeners();
        holeCardsDealtListeners.add(listener);
        moneyChangedListeners.add(listener);
    }

    @Override
    public void subscribe(PokerPlayer listener) {
        moneyChangedListeners.add(listener);
        holeCardsDealtListeners.add(listener);
    }

    @Override
    public void subscribe(PokerPlay listener) {
        communityCardsDealtListeners.add(listener);
        currentPlayerChangedListeners.add(listener);
        roundOfPlayIsOverListeners.add(listener);
        moveAbilityListeners.add(listener);
        playerFoldListeners.add(listener);
        stateChangedListeners.add(listener);
        notifyStateChangedListeners();
        moneyChangedListeners.add(listener);
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
        roundOfPlayIsOverListeners.stream()
                .filter(this::listenerIsPlayOrAI)
                .forEach(listener -> listener.onRoundOfPlayIsOver(playersInfo));
    }

    public void notifyPlayerShowedDownListeners(String playerId, List<Card> holeCards, PokerHand hand) {
        playerShowedDownListeners.stream()
                .filter(this::listenerIsPlayOrAI)
                .forEach(listener -> listener.playerShowedDown(playerId, holeCards, hand));
    }

    public void notifyMoveAbilityListeners() {
        //TODO implement
    }

    public void notifyCommunityCardsChangedListeners(List<Card> addedCards) {
        communityCardsDealtListeners.stream()
                .filter(this::listenerIsPlayOrAI)
                .forEach(listener -> listener.onCommunityCardsDealt(addedCards));
    }

    private boolean listenerIsPlayOrAI(Object listener) {
        return listener instanceof PokerPlay || listener instanceof PokerAI;
    }

    public void notifyCurrentPlayerChangedListeners(String id) {
        currentPlayerChangedListeners.stream()
                .filter(this::listenerIsPlayOrAI)
                .forEach(listener -> listener.onCurrentPlayerChanged(id));
    }

    private void notifyStateChangedListeners() {
        stateChangedListeners.stream()
                .filter(this::listenerIsPlayOrAI)
                .forEach(listener -> listener.stateChanged(state.toString()));
    }

    public void notifyMoneyChangedListeners(String playerId, int playerStack, int playerWager, int bank) {
        moneyChangedListeners.forEach(listener -> listener.onMoneyChanged(playerId, playerStack, playerWager, bank));
    }

    public void notifyPreflopMadeListeners(Map<String, List<Card>> playerIdToCards) {
        preflopMadeListeners.forEach(listener -> listener.preflopMade(playerIdToCards));
    }

    public void notifyHoleCardsDealtListeners(Map<String, List<Card>> playerIdToCards) {
        playerIdToCards.forEach((id, cards) ->
                holeCardsDealtListeners.stream()
                        .filter(listener -> listener.id().equals(id))
                        .findFirst()
                        .orElseThrow(RuntimeException::new)
                        .onHoleCardsDealt(cards)
        );
    }

    public void notifyPlayerFoldListeners(String id) {
        playerFoldListeners.stream()
                .filter(this::listenerIsPlayOrAI)
                .forEach(listener -> listener.playerFold(id));
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

}