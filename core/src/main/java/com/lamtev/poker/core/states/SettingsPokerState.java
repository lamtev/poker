package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerInfo;
import com.lamtev.poker.core.api.PlayerMoney;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;
import com.lamtev.poker.core.states.exceptions.GameHaveNotBeenStartedException;

import java.util.ArrayList;
import java.util.List;

public class SettingsPokerState implements PokerState {

    private Poker poker;
    private Players players;
    private Bank bank;
    private Dealer dealer;
    private Cards commonCards;

    public SettingsPokerState(Poker poker) {
        this.poker = poker;
    }

    @Override
    public void setUp(List<PlayerInfo> playersInfo, int smallBlindSize) {
        init(playersInfo);
        bank.acceptBlindWagers(smallBlindSize);
        notifyWagerPlacedListeners();
        poker.setState(new PreflopWageringPokerState(poker, players, bank, dealer, commonCards));
    }

    private void notifyWagerPlacedListeners() {
        Player smallBlind = players.get(0);
        String smallBlindId = smallBlind.getId();
        PlayerMoney smallBLindMoney = new PlayerMoney(smallBlind.getStack(), smallBlind.getWager());
        poker.notifyWagerPlacedListeners(smallBlindId, smallBLindMoney, bank.getMoney());

        Player bigBlind = players.get(1);
        String bigBlindId = bigBlind.getId();
        PlayerMoney bigBlindMoney = new PlayerMoney(bigBlind.getStack(), bigBlind.getWager());
        poker.notifyWagerPlacedListeners(bigBlindId, bigBlindMoney, bank.getMoney());
    }

    @Override
    public ArrayList<PlayerInfo> getPlayersInfo() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public int getPlayerWager(String playerID) throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public Cards getPlayerCards(String playerID) throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public int getPlayerStack(String playerID) throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public int getMoneyInBank() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public Cards getCommonCards() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public void call() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public void raise(int additionalWager) throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public void allIn() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public void fold() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public void check() throws Exception {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public void showDown() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    private void init(List<PlayerInfo> playersInfo) {
        players = new Players();
        playersInfo.forEach((playerInfo) -> {
            String id = playerInfo.getId();
            int stack = playerInfo.getStack();
            players.add(new Player(id, stack));
        });
        bank = new Bank(players);
        commonCards = new Cards();
        dealer = new Dealer(players, commonCards);
    }

}
