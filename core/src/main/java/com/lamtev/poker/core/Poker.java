package com.lamtev.poker.core;

import java.util.ArrayList;

public class Poker implements PokerAPI {

    private Players players;
    private Bank bank;
    private Cards commonCards;
    private Dealer dealer;
    //TODO think about moving fields to state
    private PokerState state;
    private int smallBlindSize;

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
        this.smallBlindSize =  smallBlindSize;
        state = new BlindsPokerState(this);
        setBlinds();
    }

    private void setBlinds() {
        state.setBlinds();
        //TODO think about: make instance of PokerState listener
        state.nextState(() -> state = new PreflopWageringPokerState(Poker.this));
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

    Players players() {
        return players;
    }

    Cards commonCards() {
        return commonCards;
    }

    Dealer dealer() {
        return dealer;
    }

    Bank bank() {
        return bank;
    }

    int smallBlindSize() {
        return smallBlindSize;
    }

}
