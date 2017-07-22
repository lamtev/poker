package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.MoveAbility;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;
import com.lamtev.poker.core.states.exceptions.ForbiddenMoveException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

class BlindsState extends ActionState {

    private final int smallBlindSize;

    BlindsState(Poker poker, Players players, Bank bank, Dealer dealer, Cards communityCards, MoveAbility moveAbility, int smallBlindSize) {
        super(poker, players, bank, dealer, communityCards, moveAbility);
        this.smallBlindSize = smallBlindSize;
    }

    @Override
    public void start() {
        bank().acceptBlindWagers(smallBlindSize);
        Player smallBlind = players().smallBlind();
        poker().notifyPlayerMoneyUpdatedListeners(smallBlind.id(), smallBlind.stack(), smallBlind.wager());
        Player bigBlind = players().bigBlind();
        poker().notifyPlayerMoneyUpdatedListeners(bigBlind.id(), bigBlind.stack(), bigBlind.wager());
        poker().notifyBankMoneyUpdatedListeners(bank().money(), bank().wager());
        poker().notifyBlindWagersPlacedListeners();
        nextState();
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
    public void call() throws ForbiddenMoveException {
        throw new ForbiddenMoveException("Call", toString());
    }

    @Override
    public void raise(int additionalWager) throws ForbiddenMoveException {
        throw new ForbiddenMoveException("Raise", toString());
    }

    @Override
    public void allIn() throws ForbiddenMoveException {
        throw new ForbiddenMoveException("All in", toString());
    }

    @Override
    public void fold() throws ForbiddenMoveException {
        throw new ForbiddenMoveException("Fold", toString());
    }

    @Override
    public void check() throws ForbiddenMoveException {
        throw new ForbiddenMoveException("Check", toString());
    }

    @Override
    public void showDown() throws ForbiddenMoveException {
        throw new ForbiddenMoveException("Show down", toString());
    }

    @Override
    void updateMoveAbility() {

    }

}
