package com.lamtev.poker.core.api;

import com.lamtev.poker.core.event_listeners.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.states.PokerState;
import com.lamtev.poker.core.states.SettingsPokerState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Poker implements PokerAPI {

    private PokerState state = new SettingsPokerState(this);

    private List<StateChangedListener> stateChangedListeners = new ArrayList<>();
    private List<GameIsOverListener> gameIsOverListeners = new ArrayList<>();
    private List<MoveAbilityListener> moveAbilityListeners = new ArrayList<>();
    private List<CurrentPlayerListener> currentPlayerListeners = new ArrayList<>();
    private List<CommunityCardsListener> communityCardsListeners = new ArrayList<>();
    private List<WagerPlacedListener> wagerPlacedListeners = new ArrayList<>();
    private List<PlayerFoldListener> playerFoldListeners = new ArrayList<>();
    private List<PreflopMadeListener> preflopMadeListeners = new ArrayList<>();
    private List<PlayerShowedDownListener> playerShowedDownListeners = new ArrayList<>();

    private boolean gameIsSetUp = false;

    @Override
    public void setUp(List<PlayerIdStack> playersStacks, String smallBlindId, String bigBlindId, int smallBlindSize) {
        if (!allListenersAdded()) {
            throw new RuntimeException("You must add at least one of each listeners");
        }
        state.setUp(playersStacks, smallBlindId, bigBlindId, smallBlindSize);
        gameIsSetUp = true;
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
    public void showDown() throws Exception {
        validateGameIsSetUp();
        state.showDown();
    }

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
    }

    private boolean allListenersAdded() {
        return stateChangedListeners.size() != 0 && gameIsOverListeners.size() != 0 &&
                communityCardsListeners.size() != 0 && currentPlayerListeners.size() != 0 &&
                moveAbilityListeners.size() != 0 && playerFoldListeners.size() != 0 &&
                preflopMadeListeners.size() != 0 && wagerPlacedListeners.size() != 0 &&
                playerShowedDownListeners.size() != 0;
    }

    public void setState(PokerState newState) {
        state = newState;
        notifyStateChangedListeners();
    }

    public void notifyGameIsOverListeners(List<PlayerIdStack> playersInfo) {
        gameIsOverListeners.forEach(listener -> listener.gameIsOver(playersInfo));
    }

    public void notifyPlayerShowedDownListeners(String playerId, PokerHand hand) {
        playerShowedDownListeners.forEach(listener -> listener.playerShowedDown(playerId, hand));
    }

    public void notifyMoveAbilityListeners() {
        //TODO implement
    }

    public void notifyCommunityCardsChangedListeners(List<Card> addedCards) {
        communityCardsListeners.forEach(listener -> listener.communityCardsAdded(addedCards));
    }

    public void notifyCurrentPlayerListeners(String id) {
        currentPlayerListeners.forEach(listener -> listener.currentPlayerChanged(id));
    }

    private void notifyStateChangedListeners() {
        stateChangedListeners.forEach(listener -> listener.stateChanged(state.getClass().getSimpleName()));
    }

    public void notifyWagerPlacedListeners(String playerId, PlayerMoney playerMoney, int bank) {
        wagerPlacedListeners.forEach(listener -> listener.wagerPlaced(playerId, playerMoney, bank));
    }

    public void notifyPreflopMadeListeners(Map<String, Cards> playerIdToCards) {
        preflopMadeListeners.forEach(listener -> listener.preflopMade(playerIdToCards));
    }

    public void notifyPlayerFoldListeners(String id) {
        playerFoldListeners.forEach(listener -> listener.playerFold(id));
    }

    private void validateGameIsSetUp() throws Exception {
        if (!gameIsSetUp) {
            throw new Exception("Game is not set up");
        }
    }

    private void addStateChangedListener(StateChangedListener stateChangedListener) {
        stateChangedListeners.add(stateChangedListener);
        notifyStateChangedListeners();
    }

    private void addGameIsOverListener(GameIsOverListener gameIsOverListener) {
        gameIsOverListeners.add(gameIsOverListener);
    }

    private void addCurrentPlayerChangedListener(CurrentPlayerListener currentPlayerListener) {
        currentPlayerListeners.add(currentPlayerListener);
    }


    private void addCommunityCardsChangedListener(CommunityCardsListener communityCardsListener) {
        communityCardsListeners.add(communityCardsListener);
    }


    private void addPlayerShowedDownListener(PlayerShowedDownListener playerShowedDownListener) {
        playerShowedDownListeners.add(playerShowedDownListener);
    }


    private void addWagerPlacedListener(WagerPlacedListener wagerPlacedListener) {
        wagerPlacedListeners.add(wagerPlacedListener);
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