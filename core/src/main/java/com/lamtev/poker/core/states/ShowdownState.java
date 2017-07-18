package com.lamtev.poker.core.states;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.hands.PokerHandFactory;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Player;
import com.lamtev.poker.core.states.exceptions.ForbiddenMoveException;
import com.lamtev.poker.core.states.exceptions.UnallowableMoveException;

import java.util.*;

class ShowdownState extends ActionState {

    private final Map<Player, PokerHand> showedDownPlayers = new HashMap<>();
    private final PokerHandFactory handFactory;

    ShowdownState(ActionState state, Player latestAggressor) {
        super(state);
        if (latestAggressor == null) {
            players().nextActiveAfterDealer();
        } else {
            players().setLatestAggressor(latestAggressor);
        }
        poker().notifyCurrentPlayerChangedListeners(players().current().id());
        handFactory = new PokerHandFactory(communityCards());
    }

    @Override
    public void placeBlindWagers() throws ForbiddenMoveException {
        throw new ForbiddenMoveException("Placing the blind wagers", toString());
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
    public void fold() throws UnallowableMoveException {
        if (showedDownPlayers.isEmpty() || players().current().isAllinner()) {
            throw new UnallowableMoveException("Fold");
        }
        players().current().fold();
        poker().notifyPlayerFoldListeners(players().current().id());
        changePlayerIndex();
        attemptDetermineWinners();
    }

    @Override
    public void check() throws ForbiddenMoveException {
        throw new ForbiddenMoveException("Check", toString());
    }

    @Override
    public void showDown() {
        Player currentPlayer = players().current();
        PokerHand pokerHand = handFactory.createCombination(currentPlayer.cards());
        showedDownPlayers.put(currentPlayer, pokerHand);
        List<Card> holeCards = new ArrayList<>();
        currentPlayer.cards().forEach(holeCards::add);
        poker().notifyPlayerShowedDownListeners(currentPlayer.id(), holeCards, pokerHand);
        changePlayerIndex();
        attemptDetermineWinners();
    }

    @Override
    void changePlayerIndex() {
        players().nextActive();
        poker().notifyCurrentPlayerChangedListeners(players().current().id());
    }

    //TODO     add feature for action: not showDown and not fold
    //TODO     When it would be added, if only one action player then state = ShowDown
    //TODO     and player will have 2 variants: do this action or showDown

    private void attemptDetermineWinners() {
        if (timeToDetermineWinners()) {

            Set<Player> winners = bank().giveMoneyToWinners(showedDownPlayers);

            //TODO think about rename MoneyChangedListener
            winners.forEach(winner -> poker().notifyMoneyChangedListeners(
                    winner.id(),
                    winner.stack(),
                    winner.wager(),
                    bank().money()
            ));
            poker().setState(new RoundOfPlayIsOverState(this));
        }
    }

    private boolean timeToDetermineWinners() {
        return showedDownPlayers.size() == players().activePlayersNumber();
    }

}
