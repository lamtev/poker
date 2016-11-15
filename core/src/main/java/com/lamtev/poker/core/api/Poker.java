package com.lamtev.poker.core.api;

import com.lamtev.poker.core.model.*;
import com.lamtev.poker.core.states.BlindsPokerState;
import com.lamtev.poker.core.states.OnNextStateListener;
import com.lamtev.poker.core.states.PokerState;
import com.lamtev.poker.core.states.exceptions.GameHaveNotBeenStartedException;
import com.lamtev.poker.core.util.PlayerInfo;

import java.util.ArrayList;

public class Poker implements PokerAPI {

    private Players players;
    private Bank bank;
    private Cards commonCards;
    private Dealer dealer;
    private PokerState state;

    public Poker() {
        state = new PokerState() {
            @Override
            public void setBlinds() throws Exception {
                throw new GameHaveNotBeenStartedException();
            }

            @Override
            public void call() throws Exception {
                throw new GameHaveNotBeenStartedException();
            }

            @Override
            public void raise(int additionalWager) throws Exception {
                throw new GameHaveNotBeenStartedException();
            }

            @Override
            public void fold() throws Exception {
                throw new GameHaveNotBeenStartedException();
            }

            @Override
            public void check() throws Exception {
                throw new GameHaveNotBeenStartedException();
            }
        };
    }

    @Override
    public void start(ArrayList<PlayerInfo> playersInfo, int smallBlindSize) throws Exception {
        players = new Players();
        playersInfo.forEach((playerInfo) -> {
            String id = playerInfo.getId();
            int stack = playerInfo.getStack();
            players.add(new Player(id, stack));
        });
        bank = new Bank(players);
        commonCards = new Cards();
        dealer = new Dealer(players, commonCards);
        state = new BlindsPokerState(this, smallBlindSize);
        state.setBlinds();
    }

    @Override
    public int getPlayerWager(String playerId) {
        return players.get(playerId).getWager();
    }

    @Override
    public int getPlayerStack(String playerId) {
        return players.get(playerId).getStack();
    }

    @Override
    public Cards getPlayerCards(String playerId) {
        return players.get(playerId).getCards();
    }

    @Override
    public Cards getCommonCards() {
        return commonCards;
    }

    @Override
    public int getMoneyInBank() {
        return bank.getMoney();
    }

    @Override
    public ArrayList<PlayerInfo> getPlayersInfo() {
        return new ArrayList<PlayerInfo>() {{
            players.forEach((player) -> {
                String id = player.getId();
                int stack = player.getStack();
                add(new PlayerInfo(id, stack));
            });
        }};
    }

    @Override
    public void call() throws Exception {
        state.call();
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        state.raise(additionalWager);
    }

    @Override
    public void fold() throws Exception {
        state.fold();
    }

    @Override
    public void check() throws Exception {
        state.check();
    }

    public void setState(PokerState newState) {
        state = newState;
    }

    public void update(OnNextStateListener onNextStateListener) {
        onNextStateListener.onNextState();
    }

    public Players getPlayers() {
        return players;
    }

    public Bank getBank() {
        return bank;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public PokerState getState() {
        return state;
    }

}
