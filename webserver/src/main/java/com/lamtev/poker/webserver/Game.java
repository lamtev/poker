package com.lamtev.poker.webserver;

import com.lamtev.poker.core.api.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;

import java.util.*;

public class Game implements PokerEventListener {

    private PokerAPI poker = new Poker();
    private List<Card> communityCards = new ArrayList<>();
    private Map<String, PlayerExpandedInfo> playersInfo = new LinkedHashMap<>();
    private Map<String, Cards> playersCards = new LinkedHashMap<>();
    private static final List<String> names = Arrays.asList(
            "Anna", "Arina", "Katya", "Vova", "Zhenya", "Zlatan", "Keanu",
            "Wayne", "Tereza", "George", "Max", "Vika", "Mel", "Donald");

    public void start(String humanId, int playersNumber, int stack) {
        poker.subscribe(this);
        Collections.shuffle(names);
        final int numberOfBots = playersNumber - 1;
        List<String> bots = names.subList(0, numberOfBots);
        bots.forEach(bot -> playersInfo.put(bot, new PlayerExpandedInfo(stack, 0, true)));
        playersInfo.put(humanId, new PlayerExpandedInfo(stack, 0, true));
        List<PlayerIdStack> playersStacks = new ArrayList<>();
        playersInfo.forEach((id, info) -> playersStacks.add(new PlayerIdStack(id, info.getStack())));
        poker.setUp(playersStacks, playersStacks.get(0).getId(), playersStacks.get(1).getId(), stack / 1000);
    }

    @Override
    public void playerFold(String foldPlayerId) {

    }

    @Override
    public void wagerPlaced(String playerId, PlayerMoney playerMoney, int bank) {

    }

    @Override
    public void stateChanged(String stateName) {

    }

    @Override
    public void currentPlayerChanged(String playerId) {

    }

    @Override
    public void callAbilityChanged(boolean flag) {

    }

    @Override
    public void gameOver(List<PlayerIdStack> playersInfo) {

    }

    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {

    }

    @Override
    public void raiseAbilityChanged(boolean flag) {

    }

    @Override
    public void preflopMade(Map<String, Cards> playerIdToCards) {

    }

    @Override
    public void communityCardsAdded(List<Card> addedCommunityCards) {

    }

    @Override
    public void allInAbilityChanged(boolean flag) {

    }

    @Override
    public void checkAbilityChanged(boolean flag) {

    }

    @Override
    public void foldAbilityChanged(boolean flag) {

    }

    @Override
    public void showDownAbilityChanged(boolean flag) {

    }

}
