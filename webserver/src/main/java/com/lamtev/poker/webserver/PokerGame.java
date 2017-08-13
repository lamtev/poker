package com.lamtev.poker.webserver;

import com.lamtev.poker.core.api.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.states.exceptions.*;

import java.util.*;

import static com.lamtev.poker.core.util.PlayersNames.nNamesExceptThis;
import static java.util.stream.Collectors.toList;

public class PokerGame implements Play, GameAPI {

    private List<User> users = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private RoundOfPlay poker;
    private List<Card> communityCards = new ArrayList<>();
    private List<String> nicks;
    private Map<String, PokerHand> showedDownPlayers = new HashMap<>();
    private String currentPlayerId;
    private String currentStateName;
    private int bank;
    private int smallBlindSize;
    private int wager;

    @Override
    public void start(String humanId, int playersNumber, int stack) throws RoundOfPlayIsOverException, ForbiddenMoveException, NotPositiveWagerException, IsNotEnoughMoneyException, UnallowableMoveException, GameHaveNotBeenStartedException {
        final int numberOfBots = playersNumber - 1;
        nicks = nNamesExceptThis(numberOfBots, humanId);
        nicks.add(humanId);
        Collections.shuffle(nicks);
        nicks.forEach(it -> users.add(new User(it, stack)));
        players.addAll(users);
        smallBlindSize = stack / 1000;

        poker = new PokerBuilder()
                .registerPlayers(players)
                .setDealerId(nicks.get(0))
                .setSmallBlindWager(smallBlindSize)
                .registerPlay(this)
                .create();
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
    public List<String> getPlayersMoney() {
        return players.stream()
                .map(it -> "{\n\t\"id\": \"" + it.id() + "\",\n\t\"stack\": " + it.stack() + ",\n\t\"wager\": " + it.wager() + "\n }")
                .collect(toList());
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
        cards.addAll(players.stream()
                .filter(it -> it.id().equals(playerId))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .cards());
        return cards;
    }

    @Override
    public void playerFold(String playerId) {
        //TODO
        users.stream()
                .filter(it -> it.id().equals(playerId))
                .findFirst()
                .ifPresent(o -> o.setActive(false));
    }

    @Override
    public void playerMoneyUpdated(String playerId, int playerStack, int playerWager) {
        User currentUser = users.stream()
                .filter(it -> it.id().equals(playerId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        currentUser.setStack(playerStack);
        currentUser.setWager(playerWager);
    }

    @Override
    public void stateChanged(String stateName) {
        //FIXME bug with states gameIsOver after preflop
        currentStateName = stateName;
        System.out.println(currentStateName);

        if ("RoundOfPlayIsOverState".equals(stateName)) {
            long cnt = players.stream().filter(x -> x.stack() > 0).count();
            System.out.println(cnt);
            if (cnt == 1) {
                currentStateName = "Finished";
                return;
            }
            users.forEach(it -> it.setWager(0));

            players.removeIf(it -> it.stack() <= 0);

            smallBlindSize *= 1.25;

            communityCards.clear();
            showedDownPlayers.clear();

            Collections.rotate(nicks, -1);

            poker = new PokerBuilder()
                    .registerPlayers(players)
                    .setDealerId(nicks.get(0))
                    .setSmallBlindWager(smallBlindSize)
                    .registerPlay(this)
                    .create();
        }
    }

    @Override
    public void currentPlayerChanged(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    @Override
    public void communityCardsDealt(List<Card> addedCommunityCards) {
        communityCards.addAll(addedCommunityCards);
    }

    @Override
    public void bankMoneyUpdated(int money, int wager) {
        bank = money;
        this.wager = wager;
    }

    @Override
    public void blindWagersPlaced() {

    }

    @Override
    public void holeCardsDealt(Map<String, List<Card>> playerIdToCards) {
        users.forEach(it -> it.setCards(playerIdToCards.get(it.id())));
    }

    @Override
    public void playerAllinned(String playerId) {

    }

    @Override
    public void playerCalled(String playerId) {

    }

    @Override
    public void playerChecked(String playerId) {

    }

    @Override
    public void playerRaised(String playerId) {

    }

    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {
        showedDownPlayers.put(playerId, hand);
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
