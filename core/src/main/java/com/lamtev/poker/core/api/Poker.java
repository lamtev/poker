package com.lamtev.poker.core.api;

import com.lamtev.poker.core.event_listeners.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.states.PokerState;
import com.lamtev.poker.core.states.SettingsPokerState;
import com.lamtev.poker.core.states.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Poker implements PokerAPI {

    private PokerState state = new SettingsPokerState(this);

    private List<StateChangedListener> stateChangedListeners = new ArrayList<>();
    private List<GameOverListener> gameOverListeners = new ArrayList<>();
    private List<MoveAbilityListener> moveAbilityListeners = new ArrayList<>();
    private List<CurrentPlayerChangedListener> currentPlayerChangedListeners = new ArrayList<>();
    private List<CommunityCardsAddedListener> communityCardsAddedListeners = new ArrayList<>();
    private List<WagerPlacedListener> wagerPlacedListeners = new ArrayList<>();
    private List<PlayerFoldListener> playerFoldListeners = new ArrayList<>();
    private List<PreflopMadeListener> preflopMadeListeners = new ArrayList<>();
    private List<PlayerShowedDownListener> playerShowedDownListeners = new ArrayList<>();

    private boolean gameIsSetUp = false;
    private boolean listenersAdded = false;

    @Override
    public void setUp(List<PlayerIdStack> playersStacks, String smallBlindId, String bigBlindId, int smallBlindSize)
            throws GameOverException {
        if (playersStacks.size() < 2) {
            //TODO
            throw new RuntimeException("There must be at least 2 players");
        }
        if (!listenersAdded) {
            throw new RuntimeException("You must subscribe");
        }
        state.setUp(playersStacks, smallBlindId, bigBlindId, smallBlindSize);
        gameIsSetUp = true;
    }

    @Override
    public void call() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            IsNotEnoughMoneyException,
            GameOverException,
            UnavailableMoveException {
        validateGameIsSetUp();
        state.call();
    }

    @Override
    public void raise(int additionalWager) throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException,
            GameOverException,
            UnavailableMoveException {
        validateGameIsSetUp();
        state.raise(additionalWager);
    }

    @Override
    public void allIn() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            GameOverException,
            UnavailableMoveException {
        validateGameIsSetUp();
        state.allIn();
    }

    @Override
    public void fold() throws Exception {
        validateGameIsSetUp();
        state.fold();
    }

    @Override
    public void check() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            GameOverException,
            UnavailableMoveException {
        validateGameIsSetUp();
        state.check();
    }

    @Override
    public void showDown() throws
            ForbiddenMoveException,
            GameHaveNotBeenStartedException,
            GameOverException {
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
        listenersAdded = true;
    }

    public void setState(PokerState newState) {
        state = newState;
        notifyStateChangedListeners();
    }

    public void notifyGameOverListeners(List<PlayerIdStack> playersInfo) {
        gameOverListeners.forEach(listener -> listener.gameOver(playersInfo));
    }

    public void notifyPlayerShowedDownListeners(String playerId, PokerHand hand) {
        playerShowedDownListeners.forEach(listener -> listener.playerShowedDown(playerId, hand));
    }

    public void notifyMoveAbilityListeners() {
        //TODO implement
    }

    public void notifyCommunityCardsChangedListeners(List<Card> addedCards) {
        communityCardsAddedListeners.forEach(listener -> listener.communityCardsAdded(addedCards));
    }

    public void notifyCurrentPlayerChangedListeners(String id) {
        currentPlayerChangedListeners.forEach(listener -> listener.currentPlayerChanged(id));
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

    private void validateGameIsSetUp() {
        if (!gameIsSetUp) {
            throw new RuntimeException("Game is not set up");
        }
    }

    private void addStateChangedListener(StateChangedListener stateChangedListener) {
        stateChangedListeners.add(stateChangedListener);
        notifyStateChangedListeners();
    }

    private void addGameIsOverListener(GameOverListener gameOverListener) {
        gameOverListeners.add(gameOverListener);
    }

    private void addCurrentPlayerChangedListener(CurrentPlayerChangedListener currentPlayerChangedListener) {
        currentPlayerChangedListeners.add(currentPlayerChangedListener);
    }


    private void addCommunityCardsChangedListener(CommunityCardsAddedListener communityCardsAddedListener) {
        communityCardsAddedListeners.add(communityCardsAddedListener);
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