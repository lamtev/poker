package com.lamtev.poker.core.api;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.junit.Assert.*;

public class PokerTest implements Play {

    private String state;
    private List<PlayerIdStack> playersInfo;
    private List<Player> players;
    private List<Card> communityCards;
    private Map<String, PokerHand> hands;
    private String currentPlayerId;
    private Map<String, Map.Entry<Integer, Integer>> playersMoney;
    private Map<String, List<Card>> playersCards;
    private List<String> foldPlayers;
    private int bank;

    private List<Player> generatePlayers() {
        List<Player> playersInfo = new ArrayList<>();
        playersInfo.add(new User("a", 500));
        playersInfo.add(new User("b", 500));
        playersInfo.add(new User("c", 500));
        playersInfo.add(new User("d", 500));
        playersInfo.add(new User("e", 500));
        playersInfo.add(new User("f", 500));
        return playersInfo;
    }

    @Before
    public void cleanUp() {
        state = null;
        communityCards = new ArrayList<>();
        hands = new HashMap<>();
        currentPlayerId = null;
        playersCards = new HashMap<>();
        players = generatePlayers();
        playersInfo = players.stream().map(it -> new PlayerIdStack(it.id(), it.stack())).collect(Collectors.toList());
        playersMoney = new HashMap<>();
        playersInfo.forEach(it -> playersMoney.put(it.id(), new AbstractMap.SimpleEntry<>(it.stack(), 0)));
        foldPlayers = new ArrayList<>();
        bank = 0;
    }

    @Test
    public void test() throws Exception {

        RoundOfPlay poker = new PokerBuilder()
                .registerPlayers(players)
                .setDealerId("a")
                .setSmallBlindWager(5)
                .registerPlay(this)
                .create();

        assertEquals("PreflopWageringState", state);
        assertEquals(15, bank);
        playersCards.forEach((id, cards) -> assertEquals(2, cards.size()));

        poker.call();
        poker.raise(90);
        for (int i = 0; i < 5; ++i) poker.call();
        assertEquals("FlopWageringState", state);
        assertEquals(600, bank);
        assertEquals(3, communityCards.size());

        for (int i = 0; i < 6; ++i) poker.check();
        assertEquals("TurnWageringState", state);
        assertEquals(4, communityCards.size());

        for (int i = 0; i < 6; ++i) poker.check();
        assertEquals("RiverWageringState", state);
        assertEquals(5, communityCards.size());

        poker.raise(100);
        poker.raise(100);
        poker.raise(100);
        for (int i = 0; i < 5; ++i) poker.call();
        assertEquals("ShowdownState", state);
        assertEquals(2400, bank);
        playersMoney.forEach((id, playerMoney) -> {
            assertEquals(Integer.valueOf(100), playerMoney.getKey());
            assertEquals(Integer.valueOf(400), playerMoney.getValue());
        });

        for (int i = 0; i < 6; ++i) {
            String id = currentPlayerId;
            assertTrue(isNull(hands.get(id)));
            poker.showDown();
            assertFalse(isNull(hands.get(id)));
            System.out.println(id + ": " + hands.get(id));
        }
        assertEquals("RoundOfPlayIsOverState", state);

        int actualPlayersStackSum = playersInfo
                .stream()
                .map(PlayerIdStack::stack)
                .mapToInt(Number::intValue)
                .sum();
        assertEquals(3000, actualPlayersStackSum);
        playersInfo.forEach(it -> System.out.println(it.id() + ": " + it.stack()));
    }

    @Override
    public void roundOfPlayIsOver(List<PlayerIdStack> playersInfo) {
        this.playersInfo = playersInfo;
    }

    @Override
    public void stateChanged(String stateName) {
        this.state = stateName;
    }

    @Override
    public void playerFold(String playerId) {
        foldPlayers.add(playerId);
    }

    @Override
    public void playerMoneyUpdated(String playerId, int playerStack, int playerWager) {
        playersMoney.remove(playerId);
        playersMoney.put(playerId, new AbstractMap.SimpleEntry<>(playerStack, playerWager));
    }

    @Override
    public void currentPlayerChanged(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    @Override
    public void holeCardsDealt(Map<String, List<Card>> playerIdToCards) {
        playersCards = playerIdToCards;
    }

    @Override
    public void communityCardsDealt(List<Card> addedCommunityCards) {
        communityCards.addAll(addedCommunityCards);
    }

    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {
        hands.put(playerId, hand);
    }

    @Override
    public void bankMoneyUpdated(int money, int wager) {
        bank = money;
    }

    @Override
    public void blindWagersPlaced() {

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
