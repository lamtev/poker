package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerInfo;
import com.lamtev.poker.core.api.PlayerMoney;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;
import com.lamtev.poker.core.states.exceptions.GameHaveNotBeenStartedException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SettingsPokerState implements PokerState {

    private Poker poker;
    private Players players;
    private Bank bank;
    private Dealer dealer;
    private Cards commonCards;
    private Player smallBlind;
    private Player bigBlind;

    public SettingsPokerState(Poker poker) {
        this.poker = poker;
    }

    @Override
    public void setUp(List<PlayerInfo> playersInfo, int smallBlindSize) {
        init(playersInfo);
        Bank.BlindsStatus blindsStatus = bank.acceptBlindWagers(smallBlindSize);
        nextState(blindsStatus);

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
        smallBlind = players.get(0);
        bigBlind = players.get(1);
    }

    private void nextState(Bank.BlindsStatus blindsStatus) {
        switch (blindsStatus) {
            case ALL_IN:
                dealer.makePreflop();
                Map<String, Cards> playerIdToCards = new LinkedHashMap<>();
                players.forEach(player -> playerIdToCards.put(player.getId(), player.getCards()));
                poker.notifyPreflopMadeListeners(playerIdToCards);
                dealer.makeFlop();
                dealer.makeTurn();
                dealer.makeRiver();
                poker.notifyCommunityCardsListeners(new ArrayList<Card>() {{
                    commonCards.forEach(this::add);
                }});
                poker.setState(new ShowdownPokerState(poker, players, bank, dealer, commonCards, blindsStatus.getLatestAggressorIndex()));
                break;
            default:
                notifyWagerPlacedListeners();
                poker.setState(new PreflopWageringPokerState(poker, players, bank, dealer, commonCards));
        }
    }

    private void notifyWagerPlacedListeners() {
        String smallBlindId = smallBlind.getId();
        PlayerMoney smallBLindMoney = new PlayerMoney(smallBlind.getStack(), smallBlind.getWager());
        poker.notifyWagerPlacedListeners(smallBlindId, smallBLindMoney, bank.getMoney());

        String bigBlindId = bigBlind.getId();
        PlayerMoney bigBlindMoney = new PlayerMoney(bigBlind.getStack(), bigBlind.getWager());
        poker.notifyWagerPlacedListeners(bigBlindId, bigBlindMoney, bank.getMoney());
    }

}
