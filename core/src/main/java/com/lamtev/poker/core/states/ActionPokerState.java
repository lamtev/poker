package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerIdStack;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Dealer;
import com.lamtev.poker.core.model.Players;
import com.lamtev.poker.core.states.exceptions.GameOverException;

import java.util.List;

abstract class ActionPokerState extends AbstractPokerState {

    private Poker poker;
    private Players players;
    private Bank bank;
    private Dealer dealer;
    private Cards communityCards;

    ActionPokerState(ActionPokerState state) {
        this(state.poker, state.players, state.bank, state.dealer, state.communityCards);
    }

    ActionPokerState(Poker poker, Players players, Bank bank, Dealer dealer, Cards communityCards) {
        this.poker = poker;
        this.players = players;
        this.bank = bank;
        this.dealer = dealer;
        this.communityCards = communityCards;
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

    //TODO smallBlindSize -> bigBlindSize ?
    @Override
    public void setUp(List<PlayerIdStack> playersInfo, String dealerId, int smallBlindSize)
            throws IllegalStateException, GameOverException {
        throw new IllegalStateException("Can't setUp when" + toString());
    }

    void changePlayerIndex() {
        players.nextActiveNonAllinner();
        poker.notifyCurrentPlayerChangedListeners(players.current().id());
    }

    //TODO time to forced showdown
    boolean timeToForcedShowdown() {
        return players.activePlayersNumber() == players.allinnersNumber() ||
                players.activeNonAllinnersNumber() == 1;
    }

}
