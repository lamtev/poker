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
        for (BankMoneyUpdatedListener it : bankMoneyUpdatedListeners) {
            it.bankMoneyUpdated(money, wager);
        }

    }

    public void notifyBlindWagersPlacedListeners() {
        for (BlindWagersPlacedListener blindWagersPlacedListener : blindWagersPlacedListeners) {
            blindWagersPlacedListener.blindWagersPlaced();
        }
    }

    public void notifyCommunityCardsDealtListeners(List<Card> addedCards) {
        for (CommunityCardsDealtListener it : communityCardsDealtListeners) {
            it.communityCardsDealt(addedCards);
        }
    }

    public void notifyCurrentPlayerChangedListeners(String playerId) {
        for (CurrentPlayerChangedListener it : currentPlayerChangedListeners) {
            it.currentPlayerChanged(playerId);
        }
    }

    public void notifyHoleCardsDealtListeners(Map<String, List<Card>> playerIdToCards) {
        for (HoleCardsDealtListener it : holeCardsDealtListeners) {
            if (listenerIsAI(it)) {
                it.holeCardsDealt(playerIdToCards.get(it.id()));
            } else {
                it.holeCardsDealt(playerIdToCards);
            }
        }
    }

    public void notifyMoveAbilityListeners(String playerId, MoveAbility moveAbility) {
        for (MoveAbilityListener it : moveAbilityListeners) {
            if (!listenerIsAI(it) || playerId.equals(it.id())) {
                it.allInAbilityChanged(moveAbility.allInIsAble());
                it.callAbilityChanged(moveAbility.callIsAble());
                it.checkAbilityChanged(moveAbility.checkIsAble());
                it.foldAbilityChanged(moveAbility.foldIsAble());
                it.raiseAbilityChanged(moveAbility.raiseIsAble());
                it.showDownAbilityChanged(moveAbility.showdownIsAble());
            }
        }
    }

    public void notifyPlayerAllinnedListeners(String playerId) {
        for (PlayerAllinnedListener it : playerAllinnedListeners) {
            it.playerAllinned(playerId);
        }
    }

    public void notifyPlayerCalledListeners(String playerId) {
        for (PlayerCalledListener it : playerCalledListeners) {
            it.playerCalled(playerId);
        }
    }

    public void notifyPlayerCheckedListeners(String playerId) {
        for (PlayerCheckedListener it : playerCheckedListeners) {
            it.playerChecked(playerId);
        }
    }

    public void notifyPlayerFoldListeners(String playerId) {
        for (PlayerFoldListener it : playerFoldListeners) {
            it.playerFold(playerId);
        }
    }

    public void notifyPlayerMoneyUpdatedListeners(String playerId, int playerStack, int playerWager) {
        for (PlayerMoneyUpdatedListener it : playerMoneyUpdatedListeners) {
            it.playerMoneyUpdated(playerId, playerStack, playerWager);
        }
    }

    public void notifyPlayerRaisedListeners(String playerId) {
        for (PlayerRaisedListener it : playerRaisedListeners) {
            it.playerRaised(playerId);
        }
    }

    public void notifyPlayerShowedDownListeners(String playerId, List<Card> holeCards, PokerHand hand) {
        for (PlayerShowedDownListener it : playerShowedDownListeners) {
            if (listenerIsAI(it)) {
                it.playerShowedDown(playerId, holeCards, hand);
            } else {
                it.playerShowedDown(playerId, hand);
            }
        }
    }

    public void notifyStateChangedListeners(String state) {
        for (StateChangedListener it : stateChangedListeners) {
            it.stateChanged(state);
        }
    }

    public void notifyRoundOfPlayChanged(RoundOfPlay roundOfPlay) {
        for (RoundOfPlayChangedListener it : roundOfPlayChangedListeners) {
            it.roundOfPlayChanged(roundOfPlay);
        }
    }

    public void notifyRivalsBecomeKnownListeners(List<Player> players) {
        List<Rival> rivals = new ArrayList<>();
        for (Player it : players) {
            Rival rival = new Rival(it.id(), it.stack());
            rivals.add(rival);
        }
        for (RivalsBecomeKnownListener listener : rivalsBecomeKnownListeners) {
            List<Rival> becomeKnownRivals = new ArrayList<>();
            for (Rival rival : rivals) {
                if (!rival.id().equals(listener.id())) {
                    becomeKnownRivals.add(rival);
                }
            }
            listener.rivalsBecomeKnown(becomeKnownRivals);
        }
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
