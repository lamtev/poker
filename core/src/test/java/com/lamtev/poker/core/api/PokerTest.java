package com.lamtev.poker.core.api;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PokerTest implements PokerEventListener {

    private String state;
    private List<PlayerIdStack> playersInfo;
    private List<Card> communityCards;
    private Map<String, PokerHand> hands;
    private String currentPlayerId;
    private Map<String, Cards> playersCards;
    private List<String> foldPlayers;
    private int bank;

    private List<PlayerIdStack> generatePlayersInfo1() {
        List<PlayerIdStack> playersInfo = new ArrayList<>();
        playersInfo.add(new PlayerIdStack("c1", 300));
        playersInfo.add(new PlayerIdStack("a1", 100));
        playersInfo.add(new PlayerIdStack("b1", 200));
        return playersInfo;
    }

    private List<PlayerIdStack> generatePlayersInfo() {
        List<PlayerIdStack> playersInfo = new ArrayList<>();
        playersInfo.add(new PlayerIdStack("a", 500));
        playersInfo.add(new PlayerIdStack("b", 500));
        playersInfo.add(new PlayerIdStack("c", 500));
        playersInfo.add(new PlayerIdStack("d", 500));
        playersInfo.add(new PlayerIdStack("e", 500));
        playersInfo.add(new PlayerIdStack("f", 500));
        return playersInfo;
    }

    @Before
    public void cleanUp() {
        state = null;
        playersInfo = null;
        communityCards = new ArrayList<>();
        hands = new HashMap<>();
        currentPlayerId = null;
        playersCards = new HashMap<>();
        foldPlayers = new ArrayList<>();
        bank = 0;

//        when(cardDeck.pickUpTop()).thenReturn(
//                new Card(QUEEN, PIKES),
//                new Card(TWO, CLOVERS),
//                new Card(KING, CLOVERS),
//                new Card(ACE, TILES),
//                new Card(EIGHT, CLOVERS),
//                new Card(FOUR, HEARTS),
//                new Card(JACK, PIKES),
//                new Card(NINE, HEARTS),
//                new Card(SEVEN, TILES),
//                new Card(ACE, HEARTS),
//                new Card(THREE, PIKES),
//                new Card(FOUR, TILES),
//                new Card(QUEEN, CLOVERS),
//                new Card(JACK, CLOVERS),
//                new Card(TWO, TILES),
//                new Card(NINE, CLOVERS),
//                new Card(TEN, CLOVERS)
//        );
    }

    @Test
    public void test() throws Exception {

        PokerAPI poker = new Poker();
        poker.subscribe(this);
        assertEquals("SettingsPokerState", state);

        poker.setUp(generatePlayersInfo(), "a", 5);
        assertEquals("BlindsPokerState", state);

        poker.placeBlindWagers();
        assertEquals("PreflopWageringPokerState", state);

        poker.call();
        poker.raise(90);
        for (int i = 0; i < 5; ++i) poker.call();
        assertEquals("FlopWageringPokerState", state);

        for (int i = 0; i < 6; ++i) poker.check();
        assertEquals("TurnWageringPokerState", state);

        for (int i = 0; i < 6; ++i) poker.check();
        assertEquals("RiverWageringPokerState", state);

        poker.raise(100);
        poker.raise(100);
        poker.raise(100);
        for (int i = 0; i < 5; ++i) poker.call();
        assertEquals("ShowdownPokerState", state);

        for (int i = 0; i < 6; ++i) poker.showDown();
        assertEquals("GameIsOverPokerState", state);
        hands.values().forEach(System.out::println);
    }

    @Test
    public void test1() throws Exception {

        Poker poker = new Poker();
        poker.subscribe(this);

        System.out.println(state);
        assertEquals("SettingsPokerState", state);

        poker.setUp(generatePlayersInfo1(), "c1", 30);
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

        poker.allIn();
        poker.call();
        poker.call();

        System.out.println(state);
        assertEquals("RiverWageringPokerState", state);

        poker.check();
        poker.check();
        System.out.println(state);
        assertEquals("ShowdownPokerState", state);

        String id = currentPlayerId;
        poker.showDown();
        PokerHand hand = hands.get(id);
        poker.showDown();
        poker.showDown();


        System.out.println(state);
        System.out.println(communityCards.toString() + playersCards.get(id).toString() + hand.getName());
        assertEquals("GameIsOverPokerState", state);

        playersInfo.forEach(System.out::println);
    }

    @Override
    public void gameOver(List<PlayerIdStack> playersInfo) {
        this.playersInfo = playersInfo;
    }

    @Override
    public void stateChanged(String stateName) {
        this.state = stateName;
    }

    @Override
    public void playerFold(String foldPlayerId) {
        foldPlayers.add(foldPlayerId);
    }

    @Override
    public void wagerPlaced(String playerId, PlayerMoney playerMoney, int bank) {

    }

    @Override
    public void currentPlayerChanged(String playerId) {
        currentPlayerId = playerId;
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
