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

public class PokerTest implements PlayerShowedDownListener, CommunityCardsListener, CurrentPlayerListener, GameIsOverListener, MoveAbilityListener, PlayerFoldListener, PreflopMadeListener, StateChangedListener, WagerPlacedListener {

    //TODO functional tests

    private String state;
    private List<PlayerInfo> playersInfo;
    private List<Card> communityCards = new ArrayList<>();
    private Map<String, PokerHand> hands = new HashMap<>();
    private String currentPlayerId;
    private Map<String, Cards> playersCards = new HashMap<>();

    private List<PlayerInfo> generatePlayersInfo() {
        List<PlayerInfo> playersInfo = new ArrayList<>();
        playersInfo.add(new PlayerInfo("a1", 100));
        playersInfo.add(new PlayerInfo("b1", 200));
        playersInfo.add(new PlayerInfo("c1", 300));
        return playersInfo;
    }

    @Override
    public void gameIsOver(List<PlayerInfo> playersInfo) {
        this.playersInfo = playersInfo;
    }

    @Override
    public void stateChanged(String stateName) {
        this.state = stateName;
    }

    @Test
    public void test() throws Exception {

        Poker poker = new Poker();
        poker.addCommunityCardsListener(this);
        poker.addCurrentPlayerIdListener(this);
        poker.addMoveAbilityListener(this);
        poker.addPlayerFoldListener(this);
        poker.addPreflopMadeListener(this);
        poker.addStateChangedListener(this);
        poker.addGameIsOverListener(this);
        poker.addWagerPlacedListener(this);
        poker.addPlayerShowedDownListener(this);

        System.out.println(state);
        assertEquals("SettingsPokerState", state);

        poker.setUp(generatePlayersInfo(), 30);
        System.out.println(state);
        assertEquals("PreflopWageringPokerState", state);

        poker.call();
        poker.call();

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

        System.out.println(poker.getPlayersInfo().get(0).getStack());
        System.out.println(poker.getPlayersInfo().get(1).getStack());
        System.out.println(poker.getPlayersInfo().get(2).getStack());
    }

    @Override
    public void foldPlayerAdded(String foldPlayerId) {

    }

    @Override
    public void dataChanged(String playerId, int stack, int wager, int bank) {

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
