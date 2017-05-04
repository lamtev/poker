package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerIdStack;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;
import com.lamtev.poker.core.states.exceptions.GameOverException;

import java.util.List;

abstract class ActionPokerState extends AbstractPokerState {

    protected int bigBlindIndex;
    protected int playerIndex;
    protected Poker poker;
    protected Players players;
    protected Bank bank;
    protected Dealer dealer;
    protected Cards commonCards;

    ActionPokerState(Poker poker, Players players, Bank bank, Dealer dealer, Cards commonCards, int bigBlindIndex) {
        this(poker, players, bank, dealer, commonCards);
        this.bigBlindIndex = bigBlindIndex;
    }

    ActionPokerState(ActionPokerState state) {
        this(state.poker, state.players, state.bank, state.dealer, state.commonCards, state.bigBlindIndex);
    }

    ActionPokerState(Poker poker, Players players, Bank bank, Dealer dealer, Cards commonCards) {
        this.poker = poker;
        this.players = players;
        this.bank = bank;
        this.dealer = dealer;
        this.commonCards = commonCards;
    }

    @Override
    public void setUp(List<PlayerIdStack> playersInfo, String smallBlindId, String bigBlindId, int smallBlindSize)
            throws IllegalStateException, GameOverException {
        throw new IllegalStateException("Can't setUp when" + toString());
    }

    void changePlayerIndex() {
        ++playerIndex;
        playerIndex %= players.size();
        while (!players.get(playerIndex).isActive()) {
            ++playerIndex;
            playerIndex %= players.size();
        }
        poker.notifyCurrentPlayerChangedListeners(players.get(playerIndex).getId());
    }

    Player currentPlayer() {
        return players.get(playerIndex);
    }

}
