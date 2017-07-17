package com.lamtev.poker.webserver;

import com.lamtev.poker.core.api.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.states.exceptions.*;

import java.util.*;

import static com.lamtev.poker.webserver.Util.names;

public class Game implements PokerEventListener, GameAPI {

    private PokerAPI poker = new Poker();
    private List<Card> communityCards = new ArrayList<>();
    private Map<String, PlayerExpandedInfo> playersInfo = new LinkedHashMap<>();
    private Map<String, Cards> playersCards = new LinkedHashMap<>();
    private String currentPlayerId;
    private String currentStateName;
    private int bank;
    private int smallBlindSize;
    private int wager;

    @Override
    public void start(String humanId, int playersNumber, int stack) throws GameOverException, ForbiddenMoveException, NotPositiveWagerException, IsNotEnoughMoneyException, UnallowableMoveException, GameHaveNotBeenStartedException {
        poker.subscribe(this);
        final int numberOfBots = playersNumber - 1;
        List<String> players = names(numberOfBots);
        //TODO check name existence
        players.add(humanId);
        Collections.shuffle(players);
        players.forEach(player -> playersInfo.put(player, new PlayerExpandedInfo(stack, 0, true)));
        List<PlayerIdStack> playersStacks = new ArrayList<>();
        playersInfo.forEach((id, info) -> playersStacks.add(new PlayerIdStack(id, info.getStack())));
        smallBlindSize = stack / 1000;
        poker.setUp(playersStacks, playersStacks.get(0).getId(), smallBlindSize);
        poker.placeBlindWagers();
    }

    @Override
    public void call() throws Exception {
        poker.call();
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        poker.raise(additionalWager);
    }

    @Override
    public void allIn() throws Exception {
        poker.allIn();
    }

    @Override
    public void fold() throws Exception {
        poker.fold();
    }

    @Override
    public void check() throws Exception {
        poker.check();
    }

    @Override
    public void showDown() throws Exception {
        poker.showDown();
    }

    @Override
    public List<Map.Entry<String, PlayerExpandedInfo>> getPlayersInfo() {
        return new ArrayList<>(playersInfo.entrySet());
    }

    @Override
    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    @Override
    public String getCurrentStateName() {
        return currentStateName;
    }

    @Override
    public int getBank() {
        return bank;
    }

    @Override
    public int getWager() {
        return wager;
    }

    @Override
    public List<Card> getCommunityCards() {
        return communityCards;
    }

    @Override
    public List<Card> getPlayerCards(String playerId) {
        List<Card> cards = new ArrayList<>();
        playersCards.get(playerId).forEach(cards::add);
        return cards;
    }

    @Override
    public void playerFold(String foldPlayerId) {
        //TODO
        playersInfo.get(foldPlayerId).setActive(false);
    }

    @Override
    public void wagerPlaced(String playerId, PlayerMoney playerMoney, int bank) {
        PlayerExpandedInfo playerInfo = this.playersInfo.get(playerId);
        playerInfo.setStack(playerMoney.getStack());
        playerInfo.setWager(playerMoney.getWager());
        this.bank = bank;
        this.wager = playerMoney.getWager();
    }

    @Override
    public void stateChanged(String stateName) {
        //FIXME bug with states gameIsOver after preflop
        currentStateName = stateName;
        System.out.println(currentStateName);
    }

    @Override
    public void currentPlayerChanged(String playerId) {
        currentPlayerId = playerId;
    }

    @Override
    public void gameOver(List<PlayerIdStack> playersInfo) {
        long cnt = playersInfo.stream().filter(x -> x.getStack() > 0).count();
        System.out.println(cnt);
        if (cnt == 1) {
            currentStateName = "Finished";
            return;
        }
        this.playersInfo = new LinkedHashMap<>();
        playersInfo.stream()
                .filter(playerIdStack -> playerIdStack.getStack() > 0)
                .forEach(playerIdStack -> {
                    String id = playerIdStack.getId();
                    int stack = playerIdStack.getStack();
                    PlayerExpandedInfo info = new PlayerExpandedInfo(stack, 0, true);
                    this.playersInfo.put(id, info);
                });

        poker = new Poker();
        poker.subscribe(this);
        communityCards = new ArrayList<>();
        playersCards = new LinkedHashMap<>();

        smallBlindSize *= 1.25;

        List<PlayerIdStack> playersStacks = new ArrayList<>();
        this.playersInfo.forEach((id, info) -> playersStacks.add(new PlayerIdStack(id, info.getStack())));

        try {
            poker.setUp(playersStacks, playersStacks.get(0).getId(), smallBlindSize);
        } catch (GameOverException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {

    }

    @Override
    public void preflopMade(Map<String, Cards> playerIdToCards) {
        playerIdToCards.forEach(playersCards::put);
    }

    @Override
    public void communityCardsAdded(List<Card> addedCommunityCards) {
        communityCards.addAll(addedCommunityCards);
    }

    @Override
    public void callAbilityChanged(boolean flag) {

    }

    @Override
    public void raiseAbilityChanged(boolean flag) {

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
