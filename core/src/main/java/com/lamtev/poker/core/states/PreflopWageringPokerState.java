package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.states.exceptions.ForbiddenMoveException;
import com.lamtev.poker.core.states.exceptions.UnallowableMoveException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

class PreflopWageringPokerState extends WageringPokerState {

    PreflopWageringPokerState(ActionPokerState state) {
        super(state);
        dealer().makePreflop();
        Map<String, Cards> playerIdToCards = new LinkedHashMap<>();
        players().forEach(player -> playerIdToCards.put(player.id(), player.cards()));
        poker().notifyPreflopMadeListeners(playerIdToCards);
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
            poker().notifyCommunityCardsChangedListeners(new ArrayList<Card>() {{
                communityCards().forEach(this::add);
            }});
            poker().setState(new ShowdownPokerState(this, latestAggressor()));
        } else if (timeToNextState()) {
            poker().setState(new FlopWageringPokerState(this));
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

    private boolean moveIsFinalBigBlindMove() {
        return raisers().isEmpty() &&
                players().current() == players().bigBlind();
    }

    private boolean bigBlindChecked() {
        return checks() == 1 && players().activePlayersNumber()
                == players().activeNonAllinnersWithSameWagerNumber(bank().currentWager())
                + players().allinnersNumber();
    }

    private boolean bigBlindIsAllinner() {
        return players().bigBlind().isAllinner();
    }

    private boolean noRaisesAndAllActivePlayersAreAllinnersOrHaveSameWagers() {
        return raisers().isEmpty() && players().activePlayersNumber()
                == players().activeNonAllinnersWithSameWagerNumber(bank().currentWager()) + players().allinnersNumber();
    }

}
