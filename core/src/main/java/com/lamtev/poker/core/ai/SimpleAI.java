package com.lamtev.poker.core.ai;

import com.lamtev.poker.core.api.PlayerIdStack;
import com.lamtev.poker.core.api.PokerAI;
import com.lamtev.poker.core.api.RoundOfPlay;
import com.lamtev.poker.core.event_listeners.MoveAbility;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleAI implements PokerAI {

    private final String id;
    private int stack;
    private int wager;
    private boolean isActive = true;
    private List<Card> cards;
    private List<Card> communityCards = new ArrayList<>();
    private RoundOfPlay poker;
    private MoveAbility moveAbility = new MoveAbility();
    private String state;
    private Map<String, PokerHand> playersHands = new HashMap<>();

    public SimpleAI(String id, int stack) {
        this.id = id;
        this.stack = stack;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public int stack() {
        return stack;
    }

    @Override
    public int wager() {
        return wager;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public List<Card> cards() {
        return cards;
    }

    @Override
    public void roundOfPlayChanged(RoundOfPlay roundOfPlay) {
        poker = roundOfPlay;
    }

    @Override
    public void holeCardsDealt(List<Card> holeCards) {
        cards = holeCards;
    }

    @Override
    public void communityCardsDealt(List<Card> addedCommunityCards) {
        communityCards.addAll(addedCommunityCards);
    }

    @Override
    public void playerMoneyUpdated(String playerId, int playerStack, int playerWager) {
        if (id().equals(playerId)) {
            stack = playerStack;
            wager = playerWager;
        }
    }

    @Override
    public void currentPlayerChanged(String currentPlayerId) {
        try {
            if (id().equals(currentPlayerId)) {
                switch (state) {
                    case "PreflopWageringState":
                    case "FlopWageringState":
                    case "TurnWageringState":
                    case "RiverWageringState":
                        if (moveAbility.checkIsAble()) {
                            poker.check();
                            System.out.println(id + " checked");
                        } else if (moveAbility.callIsAble()) {
                            poker.call();
                            System.out.println(id + " called");
                        } else {
                            poker.fold();
                            System.out.println(id + " fold");
                        }
                        break;
                    case "ShowdownState":
                        poker.showDown();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void bankMoneyUpdated(int money, int wager) {

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
    public void playerFold(String playerId) {
        if (id().equals(playerId)) {
            isActive = false;
        }
    }

    @Override
    public void playerRaised(String playerId) {

    }

    @Override
    public void playerShowedDown(String playerId, List<Card> holeCards, PokerHand hand) {
        playersHands.put(playerId, hand);
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
    public void roundOfPlayIsOver(List<PlayerIdStack> playersInfo) {
        communityCards.clear();
    }

    @Override
    public void stateChanged(String stateName) {
        state = stateName;
    }

}
