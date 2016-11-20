package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;
import com.lamtev.poker.core.states.exceptions.GameHaveNotBeenStartedException;
import com.lamtev.poker.core.util.PlayerInfo;

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
    public void setUp(List<PlayerInfo> playersInfo, int smallBlindSize) throws Exception {
        init(playersInfo);
        bank.acceptBlindWagers(smallBlindSize);
        poker.setState(new PreflopWageringPokerState(poker, players, bank, dealer, commonCards));
    }

    @Override
    public ArrayList<PlayerInfo> getPlayersInfo() throws Exception {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public int getPlayerWager(String playerID) throws Exception {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public int getPlayerStack(String playerID) throws Exception {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public int getMoneyInBank() throws Exception {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public Cards getCommonCards() throws Exception {
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
    public void allIn() throws Exception {
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

    @Override
    public Cards showDown() throws Exception {
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
