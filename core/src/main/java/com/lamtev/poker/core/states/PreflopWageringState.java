package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.states.exceptions.ForbiddenMoveException;
import com.lamtev.poker.core.states.exceptions.UnallowableMoveException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

final class PreflopWageringState extends WageringState {

    PreflopWageringState(ActionState state) {
        super(state);
    }

    @Override
    void makeDealerJob() {
        dealer.makePreflop();
        poker.notifyHoleCardsDealtListeners(new LinkedHashMap<String, List<Card>>() {{
            players.forEach(player -> put(player.id(), new ArrayList<Card>() {{
                player.cards().forEach(this::add);
            }}));
        }});
    }

    @Override
    public void check() throws ForbiddenMoveException, UnallowableMoveException {
        if (moveIsFinalBigBlindMove()) {
            super.check();
            return;
        }
        throw new UnallowableMoveException("Check");
    }

    @Override
    public void fold() throws UnallowableMoveException {
        if (moveIsFinalBigBlindMove()) {
            throw new UnallowableMoveException("Fold");
        }
        super.fold();
    }

    @Override
    boolean attemptNextState() {
        if (timeToForcedShowdown()) {
            dealer.makeFlop();
            dealer.makeTurn();
            dealer.makeRiver();
            poker.notifyCommunityCardsDealtListeners(new ArrayList<Card>() {{
                communityCards.forEach(this::add);
            }});
            poker.setState(new ShowdownState(this, latestAggressor()));
            return true;
        } else if (timeToNextState()) {
            poker.setState(new FlopWageringState(this));
            return true;
        }
        return false;
    }

    @Override
    boolean timeToNextState() {
        return bigBlindChecked()
                || bigBlindIsAllinner()
                && noRaisesAndAllActivePlayersAreAllinnersOrHaveSameWagers()
                || super.timeToNextState();
    }

    @Override
    void determineUnderTheGunPosition() {
        players.nextAfterBigBlind();
    }

    @Override
    void updateMoveAbility() {
        moveAbility.setRaiseIsAble(raiseIsAble());
        moveAbility.setCallIsAble(!moveIsFinalBigBlindMove() && callIsAble());
        moveAbility.setCheckIsAble(moveIsFinalBigBlindMove());
        moveAbility.setFoldIsAble(!moveIsFinalBigBlindMove());
        poker.notifyMoveAbilityListeners(players.current().id(), moveAbility);
    }

    private boolean moveIsFinalBigBlindMove() {
        return raises() == 0 &&
                players.current() == players.bigBlind();
    }

    private boolean bigBlindChecked() {
        return checks() == 1 && players.activePlayersNumber()
                == players.activeNonAllinnersWithSameWagerNumber(bank.wager())
                + players.allinnersNumber();
    }

    private boolean bigBlindIsAllinner() {
        return players.bigBlind().isAllinner();
    }

    private boolean noRaisesAndAllActivePlayersAreAllinnersOrHaveSameWagers() {
        return raises() == 0 && players.activePlayersNumber()
                == players.activeNonAllinnersWithSameWagerNumber(bank.wager()) + players.allinnersNumber();
    }

}
