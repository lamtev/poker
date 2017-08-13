package com.lamtev.poker.core.event_listeners;

import com.lamtev.poker.core.ai.AbstractAI.Rival;
import com.lamtev.poker.core.api.AI;
import com.lamtev.poker.core.api.Play;
import com.lamtev.poker.core.api.RoundOfPlay;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.MoveAbility;
import com.lamtev.poker.core.model.Player;
import com.lamtev.poker.core.states.SettingsState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ListenerManager {

    private final List<BankMoneyUpdatedListener> bankMoneyUpdatedListeners = new ArrayList<>();
    private final List<BlindWagersPlacedListener> blindWagersPlacedListeners = new ArrayList<>();
    private final List<CommunityCardsDealtListener> communityCardsDealtListeners = new ArrayList<>();
    private final List<CurrentPlayerChangedListener> currentPlayerChangedListeners = new ArrayList<>();
    private final List<HoleCardsDealtListener> holeCardsDealtListeners = new ArrayList<>();
    private final List<MoveAbilityListener> moveAbilityListeners = new ArrayList<>();
    private final List<PlayerAllinnedListener> playerAllinnedListeners = new ArrayList<>();
    private final List<PlayerCalledListener> playerCalledListeners = new ArrayList<>();
    private final List<PlayerCheckedListener> playerCheckedListeners = new ArrayList<>();
    private final List<PlayerFoldListener> playerFoldListeners = new ArrayList<>();
    private final List<PlayerMoneyUpdatedListener> playerMoneyUpdatedListeners = new ArrayList<>();
    private final List<PlayerRaisedListener> playerRaisedListeners = new ArrayList<>();
    private final List<PlayerShowedDownListener> playerShowedDownListeners = new ArrayList<>();
    private final List<StateChangedListener> stateChangedListeners = new ArrayList<>();
    private final List<RoundOfPlayChangedListener> roundOfPlayChangedListeners = new ArrayList<>();
    private final List<RivalsBecomeKnownListener> rivalsBecomeKnownListeners = new ArrayList<>();

    public void subscribe(Play play) {
        subscribeForUpdates(play);
    }

    public void subscribe(AI ai) {
        subscribeForUpdates(ai);
        roundOfPlayChangedListeners.add(ai);
        rivalsBecomeKnownListeners.add(ai);
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

    public void notifyMoveAbilityListeners(String playerId, MoveAbility moveAbility) {
        moveAbilityListeners.forEach(it -> {
            if (!listenerIsAI(it) || playerId.equals(it.id())) {
                it.allInAbilityChanged(moveAbility.allInIsAble());
                it.callAbilityChanged(moveAbility.callIsAble());
                it.checkAbilityChanged(moveAbility.checkIsAble());
                it.foldAbilityChanged(moveAbility.foldIsAble());
                it.raiseAbilityChanged(moveAbility.raiseIsAble());
                it.showDownAbilityChanged(moveAbility.showdownIsAble());
            }
        });
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

    public void notifyStateChangedListeners(String state) {
        stateChangedListeners.forEach(it -> it.stateChanged(state));
    }

    public void notifyRoundOfPlayChanged(RoundOfPlay roundOfPlay) {
        roundOfPlayChangedListeners.forEach(it -> it.roundOfPlayChanged(roundOfPlay));
    }

    public void notifyRivalsBecomeKnownListeners(List<Player> players) {
        List<Rival> rivals = players.stream()
                .map(it -> new Rival(it.id(), it.stack()))
                .collect(Collectors.toList());
        rivalsBecomeKnownListeners.forEach(
                listener -> listener.rivalsBecomeKnown(
                        rivals.stream()
                                .filter(rival -> !rival.id().equals(listener.id()))
                                .collect(Collectors.toList())
                )
        );
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
        stateChangedListeners.add((StateChangedListener) listener);
        notifyStateChangedListeners(SettingsState.class.getSimpleName());
    }

    private boolean listenerIsAI(Object listener) {
        return listener instanceof AI;
    }

}
