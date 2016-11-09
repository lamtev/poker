package com.lamtev.poker.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Poker implements PokerAPI {

    private List<Player> playersList;
    private Bank bank;
    private Cards commonCards;
    private Dealer dealer;

    @Override
    public void start(LinkedHashMap<String, Integer> playersInfo, int smallBlindSize, String smallBlindID) {
        playersList = new ArrayList<>();
        playersInfo.forEach((key, value) -> {
            Player player = new Player(key, value);
            playersList.add(player);
        });
        bank = new Bank(playersList);
        commonCards = new Cards();
        dealer = new Dealer(playersList, commonCards);
    }

    //TODO move to new class Players
    private Player getById(String id) {
        for (Player player : playersList) {
            if (player.getId().equals(id)) {
                return player;
            }
        }
        throw new NullPointerException();
    }

    @Override
    public int getPlayerWager(String playerID) {
        return getById(playerID).getWager();
    }

    @Override
    public int getPlayerStack(String playerID) {
        return getById(playerID).getStack();
    }

    @Override
    public Cards getPlayerCards(String playerID) {
        return getById(playerID).getCards();
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
