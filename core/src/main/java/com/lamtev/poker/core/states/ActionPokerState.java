package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerInfo;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;

import java.util.List;

abstract class ActionPokerState implements PokerState {

    protected int playerIndex;
    protected Poker poker;
    protected Players players;
    protected Bank bank;
    protected Dealer dealer;
    protected Cards commonCards;

    ActionPokerState(Poker poker, Players players, Bank bank, Dealer dealer, Cards commonCards) {
        this.poker = poker;
        this.players = players;
        this.bank = bank;
        this.dealer = dealer;
        this.commonCards = commonCards;
    }

    ActionPokerState(ActionPokerState state) {
        this(state.poker, state.players, state.bank, state.dealer, state.commonCards);
    }

    @Override
    public void setUp(List<PlayerInfo> playersInfo, int smallBlindSize) {
        throw new RuntimeException("Can't setUp when action poker state");
    }

    void changePlayerIndex() {
        ++playerIndex;
        playerIndex %= players.size();
        while (!players.get(playerIndex).isActive()) {
            ++playerIndex;
            playerIndex %= players.size();
        }
        poker.notifyCurrentPlayerListeners(players.get(playerIndex).getId());
    }

    Player currentPlayer() {
        return players.get(playerIndex);
    }

}
