package com.lamtev.poker.core;

import java.util.LinkedHashMap;

public class Poker implements PokerAPI {

    private Players players;
    private Bank bank;
    private Cards commonCards;
    private Dealer dealer;

    @Override
    public void start(LinkedHashMap<String, Integer> playersInfo, int smallBlindSize, String smallBlindID) {
        players = new Players();
        playersInfo.forEach((key, value) -> {
            Player player = new Player(key, value);
            players.add(player);
        });
        bank = new Bank(players);
        commonCards = new Cards();
        dealer = new Dealer(players, commonCards);
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
    public LinkedHashMap<String, Integer> getPlayersInfo() {
        return new LinkedHashMap<String, Integer>() {{
           players.forEach((player) -> {
               String id = player.getId();
               Integer stack = player.getStack();
               put(id, stack);
           });
        }};
    }

    @Override
    public void call() throws Exception {
        //TODO implement it
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        //TODO implement it
    }

    @Override
    public void fold() throws Exception {
        //TODO implement it
    }

    @Override
    public void check() throws Exception {
        //TODO implement it
    }

}
