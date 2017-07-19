package com.lamtev.poker.core.event_listeners;

import com.lamtev.poker.core.api.PlayerIdStack;
import com.lamtev.poker.core.api.PokerAI;
import com.lamtev.poker.core.api.PokerPlay;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.states.SettingsState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListenerManager {

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

    private boolean listenersSubscribed = false;

    public void subscribe(PokerPlay pokerPlay) {
        subscribeForUpdates(pokerPlay);
        listenersSubscribed = true;
    }

    public void subscribe(PokerAI pokerAI) {
        subscribeForUpdates(pokerAI);
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
        playerAllinnedListeners.forEach(it -> it.playerAllinned(playerId));
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

    public void notifyStateChangedListeners(String state) {
        stateChangedListeners.forEach(it -> it.stateChanged(state));
    }

    public boolean listenersSubscribed() {
        return listenersSubscribed;
    }

    private void subscribeForUpdates(Object listener) {
        bankMoneyUpdatedListeners.add((BankMoneyUpdatedListener) listener);
        blindWagersPlacedListeners.add((BlindWagersPlacedListener) listener);
        communityCardsDealtListeners.add((CommunityCardsDealtListener) listener);
        currentPlayerChangedListeners.add((CurrentPlayerChangedListener) listener);
        holeCardsDealtListeners.add((HoleCardsDealtListener) listener);
        moveAbilityListeners.add((MoveAbilityListener) listener);
        playerAllinnedListeners.add((PlayerAllinnedListener) listener);
        playerCalledListeners.add((PlayerCalledListener) listener);
        playerCheckedListeners.add((PlayerCheckedListener) listener);
        playerFoldListeners.add((PlayerFoldListener) listener);
        playerMoneyUpdatedListeners.add((PlayerMoneyUpdatedListener) listener);
        playerRaisedListeners.add((PlayerRaisedListener) listener);
        playerShowedDownListeners.add((PlayerShowedDownListener) listener);
        roundOfPlayIsOverListeners.add((RoundOfPlayIsOverListener) listener);
        stateChangedListeners.add((StateChangedListener) listener);
        notifyStateChangedListeners(SettingsState.class.getSimpleName());
    }

    private boolean listenerIsAI(Object listener) {
        return listener instanceof PokerAI;
    }

}
