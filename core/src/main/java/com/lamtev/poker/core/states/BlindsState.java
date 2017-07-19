package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;
import com.lamtev.poker.core.states.exceptions.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

class BlindsState extends ActionState {

    private final int smallBlindSize;

    BlindsState(Poker poker, Players players, Bank bank, Dealer dealer, Cards communityCards, int smallBlindSize) {
        super(poker, players, bank, dealer, communityCards);
        this.smallBlindSize = smallBlindSize;
    }

    @Override
    public void placeBlindWagers() {
        bank().acceptBlindWagers(smallBlindSize);
        notifyWagerPlacedListeners(players().smallBlind(), players().bigBlind());
        nextState();
    }

    private void notifyWagerPlacedListeners(Player smallBlind, Player bigBlind) {
        poker().notifyPlayerMoneyUpdatedListeners(smallBlind.id(), smallBlind.stack(), smallBlind.wager());
        poker().notifyPlayerMoneyUpdatedListeners(bigBlind.id(), bigBlind.stack(), bigBlind.wager());
    }

    private void nextState() {
        if (timeToForcedShowdown()) {
            dealer().makePreflop();
            poker().notifyHoleCardsDealtListeners(new LinkedHashMap<String, List<Card>>() {{
                players().forEach(player -> put(player.id(), new ArrayList<Card>() {{
                    player.cards().forEach(this::add);
                }}));
            }});
            dealer().makeFlop();
            dealer().makeTurn();
            dealer().makeRiver();
            poker().notifyCommunityCardsDealtListeners(new ArrayList<Card>() {{
                communityCards().forEach(this::add);
            }});
            Player latestAggressor = players().bigBlind().isAllinner() ?
                    players().bigBlind() : players().smallBlind();
            poker().setState(new ShowdownState(this, latestAggressor));
        } else {
            poker().setState(new PreflopWageringState(this));
        }
    }

    @Override
    public void call() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnallowableMoveException,
            IsNotEnoughMoneyException,
            RoundOfPlayIsOverException {
    }

    @Override
    public void raise(int additionalWager) throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnallowableMoveException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException,
            RoundOfPlayIsOverException {
    }

    @Override
    public void allIn() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnallowableMoveException,
            RoundOfPlayIsOverException,
            IsNotEnoughMoneyException,
            NotPositiveWagerException {
    }

    @Override
    public void fold() throws
            UnallowableMoveException,
            RoundOfPlayIsOverException,
            GameHaveNotBeenStartedException {
    }

    @Override
    public void check() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            UnallowableMoveException,
            RoundOfPlayIsOverException {
    }

    @Override
    public void showDown() throws
            GameHaveNotBeenStartedException,
            ForbiddenMoveException,
            RoundOfPlayIsOverException {
    }
}
