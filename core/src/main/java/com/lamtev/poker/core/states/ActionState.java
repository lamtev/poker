package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.MoveAbility;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Dealer;
import com.lamtev.poker.core.model.Players;

abstract class ActionState extends AbstractPokerState {

    private final Poker poker;
    private final Players players;
    private final Bank bank;
    private final Dealer dealer;
    private final Cards communityCards;
    private final MoveAbility moveAbility;

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

    Poker poker() {
        return poker;
    }

    Players players() {
        return players;
    }

    Bank bank() {
        return bank;
    }

    Dealer dealer() {
        return dealer;
    }

    Cards communityCards() {
        return communityCards;
    }

    MoveAbility moveAbility() {
        return moveAbility;
    }

    void changePlayerIndex() {
        players.nextActiveNonAllinner();
        updateMoveAbility();
        poker.notifyCurrentPlayerChangedListeners(players.current().id());
    }

    abstract void updateMoveAbility();

    boolean timeToForcedShowdown() {
        return players.activeNonAllinnersNumber() <= 1;
    }

}
