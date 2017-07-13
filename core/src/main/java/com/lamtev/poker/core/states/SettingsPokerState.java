package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerIdStack;
import com.lamtev.poker.core.api.PlayerMoney;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;
import com.lamtev.poker.core.states.exceptions.GameHaveNotBeenStartedException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SettingsPokerState extends AbstractPokerState {

    private Poker poker;
    private Players players;
    private Bank bank;
    private Dealer dealer;
    private Cards communityCards;
    private Player smallBlind;
    private Player bigBlind;

    public SettingsPokerState(Poker poker) {
        this.poker = poker;
    }

    @Override
    public void setUp(List<PlayerIdStack> playersInfo, String dealerId, int smallBlindSize) {
        init(playersInfo, dealerId);
        Bank.BlindsStatus blindsStatus = bank.acceptBlindWagers(smallBlindSize);
        notifyWagerPlacedListeners();
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
    public void check() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    @Override
    public void showDown() throws GameHaveNotBeenStartedException {
        throw new GameHaveNotBeenStartedException();
    }

    private void init(List<PlayerIdStack> playersIdsStacks, String dealerId) {
        List<Player> playerList = new ArrayList<>();
        playersIdsStacks.forEach(playerIdStack -> {
            String id = playerIdStack.getId();
            int stack = playerIdStack.getStack();
            playerList.add(new Player(id, stack));
        });
        players = new Players(playerList, dealerId);
        bank = new Bank(players);
        communityCards = new Cards();
        dealer = new Dealer(players, communityCards);
        smallBlind = players.smallBlind();
        bigBlind = players.bigBlind();
    }

    private void notifyWagerPlacedListeners() {
        String smallBlindId = smallBlind.getId();
        PlayerMoney smallBLindMoney = new PlayerMoney(smallBlind.getStack(), smallBlind.getWager());
        poker.notifyWagerPlacedListeners(smallBlindId, smallBLindMoney, bank.getMoney());
        PlayerMoney bigBlindMoney = new PlayerMoney(bigBlind.getStack(), bigBlind.getWager());
        poker.notifyWagerPlacedListeners(bigBlind.getId(), bigBlindMoney, bank.getMoney());
    }

    private void nextState(Bank.BlindsStatus blindsStatus) {
        switch (blindsStatus) {
            case ALL_IN:
                dealer.makePreflop();
                Map<String, Cards> playersIdsCards = new LinkedHashMap<>();
                players.forEach(player -> playersIdsCards.put(player.getId(), player.getCards()));
                poker.notifyPreflopMadeListeners(playersIdsCards);
                dealer.makeFlop();
                dealer.makeTurn();
                dealer.makeRiver();
                poker.notifyCommunityCardsChangedListeners(new ArrayList<Card>() {{
                    communityCards.forEach(this::add);
                }});
                poker.setState(new ShowdownPokerState(poker, players, bank, dealer, communityCards,
                        blindsStatus.getLatestAggressor()));
                break;
            default:
                poker.setState(new PreflopWageringPokerState(poker, players, bank, dealer, communityCards));
        }
    }

}
