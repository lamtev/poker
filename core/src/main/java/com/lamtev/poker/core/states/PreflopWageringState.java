package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.states.exceptions.ForbiddenMoveException;
import com.lamtev.poker.core.states.exceptions.UnallowableMoveException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

class PreflopWageringState extends WageringState {

    PreflopWageringState(ActionState state) {
        super(state);
        dealer().makePreflop();
        //TODO notify players from sb to dealer
        poker().notifyHoleCardsDealtListeners(new LinkedHashMap<String, List<Card>>() {{
            players().forEach(player -> put(player.id(), new ArrayList<Card>() {{
                player.cards().forEach(this::add);
            }}));
        }});
        moveAbility().setCallIsAble(true);
        moveAbility().setCheckIsAble(moveIsFinalBigBlindMove());
        moveAbility().setFoldIsAble(!moveIsFinalBigBlindMove());
        moveAbility().setRaiseIsAble(true);
        poker().notifyMoveAbilityListeners(players().current().id(), moveAbility());
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
    public void attemptNextState() {
        if (timeToForcedShowdown()) {
            dealer().makeFlop();
            dealer().makeTurn();
            dealer().makeRiver();
            poker().notifyCommunityCardsDealtListeners(new ArrayList<Card>() {{
                communityCards().forEach(this::add);
            }});
            poker().setState(new ShowdownState(this, latestAggressor()));
        } else if (timeToNextState()) {
            poker().setState(new FlopWageringState(this));
        }
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
        players().nextAfterBigBlind();
    }

    @Override
    void changePlayerIndex() {
        super.changePlayerIndex();
        moveAbility().setCallIsAble(!moveIsFinalBigBlindMove());
        moveAbility().setCheckIsAble(moveIsFinalBigBlindMove());
        moveAbility().setFoldIsAble(!moveIsFinalBigBlindMove());
        moveAbility().setRaiseIsAble(true);
    }

    private boolean moveIsFinalBigBlindMove() {
        return raisers().isEmpty() &&
                players().current() == players().bigBlind();
    }

    private boolean bigBlindChecked() {
        return checks() == 1 && players().activePlayersNumber()
                == players().activeNonAllinnersWithSameWagerNumber(bank().wager())
                + players().allinnersNumber();
    }

    private boolean bigBlindIsAllinner() {
        return players().bigBlind().isAllinner();
    }

    private boolean noRaisesAndAllActivePlayersAreAllinnersOrHaveSameWagers() {
        return raisers().isEmpty() && players().activePlayersNumber()
                == players().activeNonAllinnersWithSameWagerNumber(bank().wager()) + players().allinnersNumber();
    }

}
