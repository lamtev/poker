package com.lamtev.poker.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Poker implements PokerAPI {

    private Map<String, Player> players;
    private List<Player> playersList;
    private Bank bank;
    private Cards commonCards;
    private Dealer dealer;

    @Override
    public void start(Map<String, Integer> playersInfo, int smallBlindSize, String smallBlindID) {
        players = new LinkedHashMap<>();
        playersInfo.forEach((key, value) -> {
            Player player = new Player(value);
            players.put(key, player);
        });
        playersList = new ArrayList<>(players.values());
        bank = new Bank(playersList);
        commonCards = new Cards();
        dealer = new Dealer(playersList, commonCards);
    }

    @Override
    public int getPlayerWager(String playerID) {
        return players.get(playerID).getWager();
    }

    @Override
    public int getPlayerStack(String playerID) {
        return players.get(playerID).getStack();
    }

    @Override
    public Cards getPlayerCards(String playerID) {
        return players.get(playerID).getCards();
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
