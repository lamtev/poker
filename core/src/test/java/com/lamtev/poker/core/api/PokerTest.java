package com.lamtev.poker.core.api;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PokerTest implements PokerEventListener {

    //TODO more test scenarios

    private String state;
    private List<PlayerIdStack> playersInfo;
    private List<Card> communityCards = new ArrayList<>();
    private Map<String, PokerHand> hands = new HashMap<>();
    private String currentPlayerId;
    private Map<String, Cards> playersCards = new HashMap<>();

    private List<PlayerIdStack> generatePlayersInfo() {
        List<PlayerIdStack> playersInfo = new ArrayList<>();
        playersInfo.add(new PlayerIdStack("c1", 300));
        playersInfo.add(new PlayerIdStack("a1", 100));
        playersInfo.add(new PlayerIdStack("b1", 200));
        return playersInfo;
    }

    @Override
    public void gameOver(List<PlayerIdStack> playersInfo) {
        this.playersInfo = playersInfo;
    }

    @Override
    public void stateChanged(String stateName) {
        this.state = stateName;
    }

    @Test
    public void test() throws Exception {

        Poker poker = new Poker();
        poker.subscribe(this);

        System.out.println(state);
        assertEquals("SettingsPokerState", state);

        poker.setUp(generatePlayersInfo(), "c1", 30);
        System.out.println(state);
        assertEquals("BlindsPokerState", state);

        poker.placeBlindWagers();
        System.out.println(state);
        assertEquals("PreflopWageringPokerState", state);

        poker.call();
        poker.call();
        poker.check();

        System.out.println(state);
        assertEquals("FlopWageringPokerState", state);
        assertEquals(3, communityCards.size());

        poker.check();
        poker.check();
        poker.check();

        System.out.println(state);
        assertEquals("TurnWageringPokerState", state);
        assertEquals(4, communityCards.size());

        poker.raise(40);
        poker.allIn();
        poker.call();

        System.out.println(state);
        assertEquals("ShowdownPokerState", state);

        String id = currentPlayerId;
        poker.showDown();
        PokerHand hand = hands.get(id);
        poker.fold();
        poker.fold();

        System.out.println(state);
        System.out.println(communityCards.toString() + playersCards.get(id).toString() + hand.getName());
        assertEquals("GameIsOverPokerState", state);

        playersInfo.forEach(System.out::println);
    }

    @Override
    public void playerFold(String foldPlayerId) {

    }

    @Override
    public void wagerPlaced(String playerId, PlayerMoney playerMoney, int bank) {

    }

    @Override
    public void currentPlayerChanged(String playerId) {
        currentPlayerId = playerId;
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

    @Override
    public void preflopMade(Map<String, Cards> playerIdToCards) {
        playersCards = playerIdToCards;
    }

    @Override
    public void communityCardsAdded(List<Card> addedCommunityCards) {
        communityCards.addAll(addedCommunityCards);
    }

    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {
        hands.put(playerId, hand);
    }
}
