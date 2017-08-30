package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;

import java.util.Collection;

abstract class ActionState extends AbstractPokerState {
    final Poker poker;
    final Players players;
    final Bank bank;
    final Dealer dealer;
    final Cards communityCards;
    final MoveAbility moveAbility;

    ActionState(ActionState state) {
        this(state.poker, state.players, state.bank, state.dealer, state.communityCards, state.moveAbility);
    }

    ActionState(Poker poker, Players players, Bank bank, Dealer dealer, Cards communityCards, MoveAbility moveAbility) {
        this.poker = poker;
        this.players = players;
        this.bank = bank;
        this.dealer = dealer;
        this.communityCards = communityCards;
        this.moveAbility = moveAbility;
    }

    void changePlayerIndex() {
        players.nextActiveNonAllinner();
        updateMoveAbility();
        poker.notifyCurrentPlayerChangedListeners(players.current().id());
    }

    abstract void updateMoveAbility();

    boolean timeToForcedShowdown() {
        return players.activeNonAllinnersNumber() == 1
                && players.activeNonAllinnersWithSameWagerNumber(bank.wager()) == 1
                || players.activeNonAllinnersNumber() == 0;
    }

}
