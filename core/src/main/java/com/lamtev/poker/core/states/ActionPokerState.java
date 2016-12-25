package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerInfo;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;

import java.util.ArrayList;
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
        throw new RuntimeException();
    }

    @Override
    public ArrayList<PlayerInfo> getPlayersInfo() throws Exception {
        return new ArrayList<PlayerInfo>() {{
            players.forEach((player) -> {
                String id = player.getId();
                int stack = player.getStack();
                add(new PlayerInfo(id, stack));
            });
        }};
    }

    @Override
    public int getPlayerWager(String playerID) throws Exception {
        return players.get(playerID).getWager();
    }

    @Override
    public Cards getPlayerCards(String playerID) throws Exception {
        return players.get(playerID).getCards();
    }

    @Override
    public int getPlayerStack(String playerID) throws Exception {
        return players.get(playerID).getStack();
    }

    @Override
    public int getMoneyInBank() throws Exception {
        return bank.getMoney();
    }

    @Override
    public Cards getCommonCards() throws Exception {
        return commonCards;
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
