package com.lamtev.poker.core;

import com.lamtev.poker.core.poker_states.BlindsPokerState;
import com.lamtev.poker.core.poker_states.PokerState;
import com.lamtev.poker.core.poker_states.PreflopWageringPokerState;

import java.util.ArrayList;

public class Poker implements PokerAPI {

    private Players players;
    private Bank bank;
    private Cards commonCards;
    private Dealer dealer;
    private PokerState state;

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
        state = new BlindsPokerState(players, bank, smallBlindSize);
        setBlinds();
    }

    private void setBlinds() {
        state.setBlinds();
        //TODO think about: make instance of PokerState listener
        state.nextState(() -> state = new PreflopWageringPokerState(players, bank, dealer));
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

}
