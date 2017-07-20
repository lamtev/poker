package com.lamtev.poker.core.ai;

import com.lamtev.poker.core.api.*;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class SimpleAITest implements PokerPlay {

    @Test
    public void test() {
        PokerAI ai = new SimpleAI("bot", 1000);
        RoundOfPlay poker = new Poker();
        poker.subscribe(ai);
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

    }

    @Override
    public void holeCardsDealt(Map<String, List<Card>> playerIdToCards) {

    }

    @Override
    public void allInAbilityChanged(boolean flag) {

    }

    @Override
    public void callAbilityChanged(boolean flag) {

    }

    @Override
    public void checkAbilityChanged(boolean flag) {

    }

    @Override
    public void foldAbilityChanged(boolean flag) {

    }

    @Override
    public void raiseAbilityChanged(boolean flag) {

    }

    @Override
    public void showDownAbilityChanged(boolean flag) {

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

    }
}