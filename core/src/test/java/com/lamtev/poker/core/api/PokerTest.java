package com.lamtev.poker.core.api;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.states.exceptions.ForbiddenMoveException;
import com.lamtev.poker.core.states.exceptions.GameHaveNotBeenStartedException;
import com.lamtev.poker.core.states.exceptions.RoundOfPlayIsOverException;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.junit.Assert.*;

public class PokerTest implements Play {

    private String state;
    private List<PlayerIdStack> playersInfo;
    private List<User> users;
    private List<Player> players = new ArrayList<>();
    private List<Card> communityCards;
    private Map<String, PokerHand> hands;
    private String currentPlayerId;
    private int bank;

    private List<User> generatePlayers() {
        List<User> playersInfo = new ArrayList<>();
        playersInfo.add(new User("b", 500));
        playersInfo.add(new User("c", 500));
        playersInfo.add(new User("d", 500));
        playersInfo.add(new User("e", 500));
        playersInfo.add(new User("f", 500));
        playersInfo.add(new User("a", 500));
        return playersInfo;
    }

    @Before
    public void cleanUp() {
        state = null;
        communityCards = new ArrayList<>();
        hands = new HashMap<>();
        currentPlayerId = null;
        users = generatePlayers();
        players.addAll(users);
        playersInfo = users.stream()
                .map(it -> new PlayerIdStack(it.id(), it.stack()))
                .collect(Collectors.toList());
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
        users.forEach(it -> assertEquals(2, it.cards().size()));

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
        users.forEach(it -> {
            assertEquals(100, it.stack());
            assertEquals(400, it.wager());
        });

        players.forEach(it -> assertTrue(isNull(hands.get(it.id()))));

        players.forEach(it -> {
            try {
                poker.showDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        players.stream()
                .filter(it -> hands.containsKey(it.id()))
                .forEach(it -> System.out.println(it.id() + ": " + hands.get(it.id())));

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
        users.stream()
                .filter(it -> it.id().equals(playerId))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .setActive(false);
    }

    @Override
    public void playerMoneyUpdated(String playerId, int playerStack, int playerWager) {
        User user = users.stream()
                .filter(it -> it.id()
                        .equals(playerId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        user.setStack(playerStack);
        user.setWager(playerWager);
    }

    @Override
    public void currentPlayerChanged(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    @Override
    public void holeCardsDealt(Map<String, List<Card>> playerIdToCards) {
        playerIdToCards.forEach((id, cards) -> users.stream()
                .filter(it -> it.id().equals(id))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .setCards(cards));
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
