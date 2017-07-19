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

    //TODO think about ListenerManager
    private List<BankMoneyUpdatedListener> bankMoneyUpdatedListeners = new ArrayList<>();
    private List<BlindWagersPlacedListener> blindWagersPlacedListeners = new ArrayList<>();
    private List<CommunityCardsDealtListener> communityCardsDealtListeners = new ArrayList<>();
    private List<CurrentPlayerChangedListener> currentPlayerChangedListeners = new ArrayList<>();
    private List<HoleCardsDealtListener> holeCardsDealtListeners = new ArrayList<>();
    private List<MoveAbilityListener> moveAbilityListeners = new ArrayList<>();
    private List<PlayerAllinnedListener> playerAllinnedListeners = new ArrayList<>();
    private List<PlayerCalledListener> playerCalledListeners = new ArrayList<>();
    private List<PlayerCheckedListener> playerCheckedListeners = new ArrayList<>();
    private List<PlayerFoldListener> playerFoldListeners = new ArrayList<>();
    private List<PlayerMoneyUpdatedListener> playerMoneyUpdatedListeners = new ArrayList<>();
    private List<PlayerRaisedListener> playerRaisedListeners = new ArrayList<>();
    private List<PlayerShowedDownListener> playerShowedDownListeners = new ArrayList<>();
    private List<RoundOfPlayIsOverListener> roundOfPlayIsOverListeners = new ArrayList<>();
    private List<StateChangedListener> stateChangedListeners = new ArrayList<>();

    private boolean listenersAdded = false;

    public void subscribe(PokerEventListenerPlayer pokerEventListener) {
        communityCardsDealtListeners.add(pokerEventListener);
        currentPlayerChangedListeners.add(pokerEventListener);
        roundOfPlayIsOverListeners.add(pokerEventListener);
        moveAbilityListeners.add(pokerEventListener);
        playerFoldListeners.add(pokerEventListener);
        playerShowedDownListeners.add(pokerEventListener);
        stateChangedListeners.add(pokerEventListener);
        notifyStateChangedListeners();
        playerMoneyUpdatedListeners.add(pokerEventListener);
        listenersAdded = true;
    }

    @Override
    public void subscribe(PokerAI listener) {
        communityCardsDealtListeners.add(listener);
        currentPlayerChangedListeners.add(listener);
        roundOfPlayIsOverListeners.add(listener);
        moveAbilityListeners.add(listener);
        playerFoldListeners.add(listener);
        stateChangedListeners.add(listener);
        notifyStateChangedListeners();
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

    public void notifyBankMoneyUpdatedListeners(int money, int wager) {
        bankMoneyUpdatedListeners.forEach(it -> it.bankMoneyUpdated(money, wager));
    }

    public void notifyBlindWagersPlacedListeners() {
        blindWagersPlacedListeners.forEach(BlindWagersPlacedListener::blindWagersPlaced);
    }

    public void notifyCommunityCardsDealtListeners(List<Card> addedCards) {
        communityCardsDealtListeners.forEach(it -> it.communityCardsDealt(addedCards));
    }

    public void notifyCurrentPlayerChangedListeners(String playerId) {
        currentPlayerChangedListeners.forEach(it -> it.currentPlayerChanged(playerId));
    }

    public void notifyHoleCardsDealtListeners(Map<String, List<Card>> playerIdToCards) {
        holeCardsDealtListeners.forEach(it -> {
            if (listenerIsAI(it)) {
                it.holeCardsDealt(playerIdToCards.get(it.id()));
            } else {
                it.holeCardsDealt(playerIdToCards);
            }
        });
    }

    public void notifyMoveAbilityListeners() {
        //TODO implement
    }

    public void notifyPlayerAllinnedListeners(String playerId) {
        playerAllinnedListeners.forEach(it -> it.playerAllined(playerId));
    }

    public void notifyPlayerCalledListeners(String playerId) {
        playerCalledListeners.forEach(it -> it.playerCalled(playerId));
    }

    public void notifyPlayerCheckedListeners(String playerId) {
        playerCheckedListeners.forEach(it -> it.playerChecked(playerId));
    }

    public void notifyPlayerFoldListeners(String playerId) {
        playerFoldListeners.forEach(it -> it.playerFold(playerId));
    }

    public void notifyPlayerMoneyUpdatedListeners(String playerId, int playerStack, int playerWager) {
        playerMoneyUpdatedListeners.forEach(it -> it.playerMoneyUpdated(playerId, playerStack, playerWager));
    }

    public void notifyPlayerRaisedListeners(String playerId) {
        playerRaisedListeners.forEach(it -> it.playerRaised(playerId));
    }

    public void notifyPlayerShowedDownListeners(String playerId, List<Card> holeCards, PokerHand hand) {
        playerShowedDownListeners.forEach(it -> {
            if (listenerIsAI(it)) {
                it.playerShowedDown(playerId, holeCards, hand);
            } else {
                it.playerShowedDown(playerId, hand);
            }
        });
    }

    public void notifyRoundOfPlayIsOverListeners(List<PlayerIdStack> playersInfo) {
        roundOfPlayIsOverListeners.forEach(it -> it.roundOfPlayIsOver(playersInfo));
    }

    private void notifyStateChangedListeners() {
        stateChangedListeners.forEach(it -> it.stateChanged(state.toString()));
    }

    private boolean listenerIsAI(Object listener) {
        return listener instanceof PokerAI;
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