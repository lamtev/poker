package com.lamtev.poker.core.ai;

import com.lamtev.poker.core.api.AI;
import com.lamtev.poker.core.api.Player;
import com.lamtev.poker.core.api.RoundOfPlay;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.MoveAbility;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public abstract class AbstractAI implements AI {

    private final String id;
    private int stack;
    private int wager;
    private boolean isActive = true;
    private final List<Card> cards = new ArrayList<>();
    private RoundOfPlay poker;
    private String currentPlayer;
    private String state;
    private int bank;
    private int currentWager;

    final Map<String, Rival> rivals = new HashMap<>();
    final List<Card> communityCards = new ArrayList<>();
    final Map<String, PokerHand> playersHands = new HashMap<>();
    final MoveAbility moveAbility = new MoveAbility();
    final Deque<String> moves = new ArrayDeque<>();

    AbstractAI(String id, int stack) {
        this.id = id;
        this.stack = stack;
    }

    @Override
    final public String id() {
        return id;
    }

    @Override
    final public int stack() {
        return stack;
    }

    @Override
    final public int wager() {
        return wager;
    }

    @Override
    final public boolean isActive() {
        return isActive;
    }

    @Override
    final public List<Card> cards() {
        return cards;
    }

    final RoundOfPlay poker() {
        return poker;
    }

    final String currentPlayer() {
        return currentPlayer;
    }

    final String state() {
        return state;
    }

    final int bank() {
        return bank;
    }

    final int currentWager() {
        return currentWager;
    }

    @Override
    final public void holeCardsDealt(List<Card> holeCards) {
        cards.addAll(holeCards);
    }

    @Override
    final public void communityCardsDealt(List<Card> addedCommunityCards) {
        communityCards.addAll(addedCommunityCards);
    }

    @Override
    final public void bankMoneyUpdated(int money, int wager) {
        bank = money;
        currentWager = wager;
    }

    @Override
    final public void playerMoneyUpdated(String playerId, int playerStack, int playerWager) {
        if (id().equals(playerId)) {
            stack = playerStack;
            wager = playerWager;
        } else {
            Rival rival = rivals.get(playerId);
            rival.stack = playerStack;
            rival.wager = playerWager;
        }
    }

    @Override
    final public void stateChanged(String stateName) {
        state = stateName;
    }

    @Override
    final public void playerFold(String playerId) {
        if (id().equals(playerId)) {
            isActive = false;
        } else {
            rivals.get(playerId).isActive = false;
        }
    }

    @Override
    final public void roundOfPlayChanged(RoundOfPlay roundOfPlay) {
        poker = roundOfPlay;
        communityCards.clear();
        isActive = true;
        cards.clear();
        wager = 0;
        rivals.clear();
        playersHands.clear();
        moves.clear();
    }

    @Override
    final public void currentPlayerChanged(String currentPlayerId) {
        this.currentPlayer = currentPlayerId;
    }

    @Override
    final public void allInAbilityChanged(boolean flag) {
        moveAbility.setAllInIsAble(flag);
    }

    @Override
    final public void callAbilityChanged(boolean flag) {
        moveAbility.setCallIsAble(flag);
    }

    @Override
    final public void checkAbilityChanged(boolean flag) {
        moveAbility.setCheckIsAble(flag);
    }

    @Override
    final public void raiseAbilityChanged(boolean flag) {
        moveAbility.setRaiseIsAble(flag);
    }

    @Override
    final public void foldAbilityChanged(boolean flag) {
        moveAbility.setFoldIsAble(flag);
    }

    @Override
    final public void showDownAbilityChanged(boolean flag) {
        moveAbility.setShowdownIsAble(flag);
    }

    @Override
    final public void blindWagersPlaced() {

    }

    @Override
    final public void playerAllinned(String playerId) {
        moves.push(playerId + " made all in");
    }

    @Override
    final public void playerCalled(String playerId) {
        moves.push(playerId + " called");
    }

    @Override
    final public void playerChecked(String playerId) {
        moves.push(playerId + " checked");
    }

    @Override
    final public void playerRaised(String playerId) {
        moves.push(playerId + " raised");
    }

    @Override
    final public void playerShowedDown(String playerId, List<Card> holeCards, PokerHand hand) {
        playersHands.put(playerId, hand);
        moves.push(playerId + "showed down");
    }

    @Override
    public void rivalsBecomeKnown(List<Rival> rivals) {
        rivals.forEach(it -> this.rivals.put(it.id, it));
    }

    public static final class Rival implements Player {

        private final String id;
        private int stack;
        private int wager;
        private boolean isActive = true;

        public Rival(String id, int stack) {
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
        @Deprecated
        public List<Card> cards() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Rival rival = (Rival) o;

            return id.equals(rival.id);
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

    }

}
