package com.lamtev.poker.core.ai;

import com.lamtev.poker.core.api.*;
import com.lamtev.poker.core.event_listeners.MoveAbility;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SimpleAITest implements PokerPlay {

    private MoveAbility moveAbility = new MoveAbility();
    private String currentPlayerId;
    private String state;

    @Test
    public void test() throws Exception {
        PokerAI bot = new SimpleAI("bot1", 1000);

        List<PlayerIdStack> playerIdStackList = new ArrayList<PlayerIdStack>() {{
            add(new PlayerIdStack("xxx", 1000));
            add(new PlayerIdStack("yyy", 1000));
            add(new PlayerIdStack("zzz", 1000));
            add(new PlayerIdStack("www", 1000));
            add(new PlayerIdStack("aaa", 1000));
            add(new PlayerIdStack("bbb", 1000));
            add(new PlayerIdStack("bot1", 1000));
        }};

        RoundOfPlay poker = new PokerBuilder()
                .registerPlayers(playerIdStackList)
                .setDealerId("www")
                .setSmallBlindWager(50)
                .registerPlay(this)
                .registerAI(bot)
                .create();

        assertEquals("PreflopWageringState", state);
        assertEquals("bot1", currentPlayerId);

        bot.makeAMove();
        assertEquals("xxx", currentPlayerId);

        nTimes(5, poker::call);
        poker.check();
        assertEquals("FlopWageringState", state);
        assertEquals("aaa", currentPlayerId);

        nTimes(2, poker::check);
        assertEquals("bot1", currentPlayerId);

        bot.makeAMove();
        assertEquals("xxx", currentPlayerId);

        nTimes(4, poker::check);
        assertEquals("TurnWageringState", state);
        assertEquals("aaa", currentPlayerId);

        nTimes(2, poker::check);
        assertEquals("bot1", currentPlayerId);

        bot.makeAMove();
        assertEquals("xxx", currentPlayerId);

        nTimes(4, poker::check);
        assertEquals("RiverWageringState", state);
        assertEquals("aaa", currentPlayerId);

        nTimes(2, poker::check);
        assertEquals("bot1", currentPlayerId);

        bot.makeAMove();
        assertEquals("xxx", currentPlayerId);

        nTimes(4, poker::check);
        assertEquals("ShowdownState", state);
        assertEquals("aaa", currentPlayerId);

        nTimes(2, poker::showDown);
        assertEquals("bot1", currentPlayerId);

        bot.makeAMove();
        assertEquals("xxx", currentPlayerId);

        nTimes(4, poker::showDown);
        assertEquals("RoundOfPlayIsOverState", state);
    }

    private interface Action {
        void run() throws Exception;
    }

    private void nTimes(int n, Action action) throws Exception {
        for (int i = 0; i < n; ++i) action.run();
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

    }

    @Override
    public void playerCalled(String playerId) {

    }

    @Override
    public void playerChecked(String playerId) {

    }

    @Override
    public void playerFold(String playerId) {

    }

    @Override
    public void playerMoneyUpdated(String playerId, int playerStack, int playerWager) {

    }

    @Override
    public void playerRaised(String playerId) {

    }

    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {

    }

    @Override
    public void roundOfPlayIsOver(List<PlayerIdStack> playersInfo) {

    }

    @Override
    public void stateChanged(String stateName) {
        this.state = stateName;
    }
}