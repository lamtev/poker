package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerIdStack;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.event_listeners.MoveAbility;
import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Dealer;
import com.lamtev.poker.core.model.Players;
import com.lamtev.poker.core.states.exceptions.RoundOfPlayIsOverException;

import java.util.List;

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

    public Poker poker() {
        return poker;
    }

    public Players players() {
        return players;
    }

    public Bank bank() {
        return bank;
    }

    public Dealer dealer() {
        return dealer;
    }

    public Cards communityCards() {
        return communityCards;
    }

    public MoveAbility moveAbility() {
        return moveAbility;
    }

    @Override
    public void setUp(List<PlayerIdStack> playersInfo, String dealerId, int smallBlindSize)
            throws IllegalStateException, RoundOfPlayIsOverException {
        throw new IllegalStateException("Can't setUp when" + toString());
    }

    void changePlayerIndex() {
        players.nextActiveNonAllinner();
        poker.notifyCurrentPlayerChangedListeners(players.current().id());
    }

    boolean timeToForcedShowdown() {
        return players.activeNonAllinnersNumber() <= 1;
    }

}
