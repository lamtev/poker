package com.lamtev.poker.core.ai;

import com.lamtev.poker.core.api.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import org.junit.Test;

import java.util.*;

import static com.lamtev.poker.core.TestUtils.nTimes;
import static org.junit.Assert.assertEquals;

public class SimpleAITest implements Play {

    private MoveAbility moveAbility = new MoveAbility();
    private String currentPlayerId;
    private String state;
    private Deque<String> log = new ArrayDeque<>();

    @Test
    public void test() throws Exception {
        AI bot = new SimpleAI("bot", 1000);

        List<Player> players = new ArrayList<Player>() {{
            add(new User("aaa", 1000));
            add(new User("bbb", 1000));
            add(bot);
            add(new User("xxx", 1000));
            add(new User("yyy", 1000));
            add(new User("zzz", 1000));
            add(new User("www", 1000));
        }};

        RoundOfPlay poker = new PokerBuilder()
                .registerPlayers(players)
                .setDealerId("www")
                .setSmallBlindWager(50)
                .registerPlay(this)
                .create();

        assertEquals("PreflopWageringState", state);
        assertEquals("bot", currentPlayerId);

        bot.makeAMove();
        assertEquals("bot called", log.pop());
        assertEquals("xxx", currentPlayerId);

        nTimes(5, poker::call);
        poker.check();
        assertEquals("FlopWageringState", state);
        assertEquals("aaa", currentPlayerId);

        nTimes(2, poker::check);
        assertEquals("bot", currentPlayerId);

        bot.makeAMove();
        assertEquals("bot checked", log.pop());
        assertEquals("xxx", currentPlayerId);

        nTimes(4, poker::check);
        assertEquals("TurnWageringState", state);
        assertEquals("aaa", currentPlayerId);

        nTimes(2, poker::check);
        assertEquals("bot", currentPlayerId);

        bot.makeAMove();
        assertEquals("bot checked", log.pop());
        assertEquals("xxx", currentPlayerId);

        nTimes(4, poker::check);
        assertEquals("RiverWageringState", state);
        assertEquals("aaa", currentPlayerId);

        nTimes(2, poker::check);
        assertEquals("bot", currentPlayerId);

        bot.makeAMove();
        assertEquals("bot checked", log.pop());
        assertEquals("xxx", currentPlayerId);

        nTimes(4, poker::check);
        assertEquals("ShowdownState", state);
        assertEquals("aaa", currentPlayerId);

        nTimes(2, poker::showDown);
        assertEquals("bot", currentPlayerId);

        bot.makeAMove();
        assertEquals("bot showed down", log.pop());
        assertEquals("xxx", currentPlayerId);

        nTimes(4, poker::showDown);
        assertEquals("RoundOfPlayIsOverState", state);
    }

    @Override
    public void bankMoneyUpdated(int money, int wager) {

    }

    @Override
    public void blindWagersPlaced() {

    }

    @Override
    public void communityCardsDealt(List<Card> addedCommunityCards) {

    }

    @Override
    public void currentPlayerChanged(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    @Override
    public void holeCardsDealt(Map<String, List<Card>> playerIdToCards) {

    }

    @Override
    public void allInAbilityChanged(boolean flag) {
        moveAbility.setAllInIsAble(flag);
    }

    @Override
    public void callAbilityChanged(boolean flag) {
        moveAbility.setCallIsAble(flag);
    }

    @Override
    public void checkAbilityChanged(boolean flag) {
        moveAbility.setCheckIsAble(flag);
    }

    @Override
    public void raiseAbilityChanged(boolean flag) {
        moveAbility.setRaiseIsAble(flag);
    }

    @Override
    public void foldAbilityChanged(boolean flag) {
        moveAbility.setFoldIsAble(flag);
    }

    @Override
    public void showDownAbilityChanged(boolean flag) {
        moveAbility.setShowdownIsAble(flag);
    }

    @Override
    public void playerAllinned(String playerId) {
        log.push(playerId + " made all in");
    }

    @Override
    public void playerCalled(String playerId) {
        log.push(playerId + " called");

    }

    @Override
    public void playerChecked(String playerId) {
        log.push(playerId + " checked");

    }

    @Override
    public void playerFold(String playerId) {
        log.push(playerId + " fold");

    }

    @Override
    public void playerMoneyUpdated(String playerId, int playerStack, int playerWager) {

    }

    @Override
    public void playerRaised(String playerId) {
        log.push(playerId + " raised");

    }

    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {
        log.push(playerId + " showed down");

    }

    @Override
    public void stateChanged(String stateName) {
        this.state = stateName;
    }
}