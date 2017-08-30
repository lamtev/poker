package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.exceptions.ForbiddenMoveException;
import com.lamtev.poker.core.exceptions.UnallowableMoveException;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Dealer;
import com.lamtev.poker.core.model.Players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class PreflopWageringState extends WageringState {

    PreflopWageringState(ActionState state) {
        super(state);
    }

    static void makeDealerJob(Poker poker, Players players, Dealer dealer) {
        dealer.makePreflop();
        Map<String, List<Card>> playersIdToCards = new HashMap<>();
        players.forEach(it -> {
            List<Card> cards = new ArrayList<>();
            it.cards().forEach(cards::add);
            playersIdToCards.put(it.id(), cards);
        });
        poker.notifyHoleCardsDealtListeners(playersIdToCards);
    }

    @Override
    void makeDealerJob() {
        makeDealerJob(poker, players, dealer);
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
            FlopWageringState.makeDealerJob(poker, dealer, communityCards);
            TurnWageringState.makeDealerJob(poker, dealer, communityCards);
            RiverWageringState.makeDealerJob(poker, dealer, communityCards);
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
        moveAbility.setAllInIsAble(allInIsAble());
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
