package com.lamtev.poker.webserver;

import com.lamtev.poker.core.api.PlayerIdStack;
import com.lamtev.poker.core.api.PokerBuilder;
import com.lamtev.poker.core.api.Play;
import com.lamtev.poker.core.api.RoundOfPlay;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.states.exceptions.*;

import java.util.*;

import static com.lamtev.poker.webserver.Util.names;

public class PokerGame implements Play, GameAPI {

    private RoundOfPlay poker;
    private List<Card> communityCards = new ArrayList<>();
    private Map<String, Map.Entry<Integer, Integer>> playersMoney = new LinkedHashMap<>();
    private Set<String> foldPlayers = new HashSet<>();
    private Map<String, List<Card>> playersCards = new LinkedHashMap<>();
    private String currentPlayerId;
    private String currentStateName;
    private int bank;
    private int smallBlindSize;
    private int wager;

    @Override
    public void start(String humanId, int playersNumber, int stack) throws RoundOfPlayIsOverException, ForbiddenMoveException, NotPositiveWagerException, IsNotEnoughMoneyException, UnallowableMoveException, GameHaveNotBeenStartedException {
        final int numberOfBots = playersNumber - 1;
        List<String> players = names(numberOfBots);
        //TODO check name existence
        players.add(humanId);
        Collections.shuffle(players);
        players.forEach(id -> playersMoney.put(id, new AbstractMap.SimpleEntry<Integer, Integer>(stack, 0)));
        List<PlayerIdStack> playersStacks = new ArrayList<>();
        playersMoney.forEach((id, info) -> playersStacks.add(new PlayerIdStack(id, info.getKey())));
        smallBlindSize = stack / 1000;
        poker = new PokerBuilder()
                .registerPlayers(playersStacks)
                .setDealerId(playersStacks.get(0).id())
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
    public List<Map.Entry<String, Map.Entry<Integer, Integer>>> getPlayersMoney() {
        return new ArrayList<>(playersMoney.entrySet());
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
        cards.addAll(playersCards.get(playerId));
        return cards;
    }

    @Override
    public void playerFold(String playerId) {
        //TODO
        foldPlayers.add(playerId);
    }

    @Override
    public void playerMoneyUpdated(String playerId, int playerStack, int playerWager) {
        playersMoney.remove(playerId);
        playersMoney.put(playerId, new AbstractMap.SimpleEntry<>(playerStack, playerWager));
    }

    @Override
    public void stateChanged(String stateName) {
        //FIXME bug with states gameIsOver after preflop
        currentStateName = stateName;
        System.out.println(currentStateName);
    }

    @Override
    public void currentPlayerChanged(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    @Override
    public void roundOfPlayIsOver(List<PlayerIdStack> playersInfo) {
        long cnt = playersInfo.stream().filter(x -> x.stack() > 0).count();
        System.out.println(cnt);
        if (cnt == 1) {
            currentStateName = "Finished";
            return;
        }
        this.playersMoney = new LinkedHashMap<>();
        playersInfo.stream()
                .filter(playerIdStack -> playerIdStack.stack() > 0)
                .forEach(playerIdStack -> {
                    String id = playerIdStack.id();
                    int stack = playerIdStack.stack();
                    this.playersMoney.put(id, new AbstractMap.SimpleEntry<>(stack, 0));
                });
        foldPlayers = new HashSet<>();

        smallBlindSize *= 1.25;

        communityCards = new ArrayList<>();
        playersCards = new LinkedHashMap<>();

        List<PlayerIdStack> playersStacks = new ArrayList<>();
        this.playersMoney.forEach((id, info) -> playersStacks.add(new PlayerIdStack(id, info.getKey())));

        poker = new PokerBuilder()
                .registerPlayers(playersStacks)
                .setDealerId(playersStacks.get(0).id())
                .setSmallBlindWager(smallBlindSize)
                .registerPlay(this)
                .create();
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
        playerIdToCards.forEach(playersCards::put);
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
