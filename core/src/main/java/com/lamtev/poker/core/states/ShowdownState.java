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
    private final Player latestAggressor;

    ShowdownState(ActionState state, Player latestAggressor) {
        super(state);
        handFactory = new PokerHandFactory(communityCards);
        this.latestAggressor = latestAggressor;
    }

    @Override
    public void start() {
        if (latestAggressor == null) {
            players.nextActiveAfterDealer();
        } else {
            players.setLatestAggressor(latestAggressor);
        }
        String currentPlayerId = players.current().id();
        moveAbility.setAllInIsAble(false);
        moveAbility.setCallIsAble(false);
        moveAbility.setCheckIsAble(false);
        moveAbility.setRaiseIsAble(false);
        moveAbility.setFoldIsAble(false);
        moveAbility.setShowdownIsAble(true);
        poker.notifyMoveAbilityListeners(currentPlayerId, moveAbility);
        poker.notifyCurrentPlayerChangedListeners(currentPlayerId);
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
        if (currentPlayerCantFold()) {
            throw new UnallowableMoveException("Fold");
        }
        Player currentPlayer = players.current();
        currentPlayer.fold();
        poker.notifyPlayerFoldListeners(currentPlayer.id());
        changePlayerIndex();
        attemptDetermineWinners();
    }

    @Override
    public void check() throws ForbiddenMoveException {
        throw new ForbiddenMoveException("Check", toString());
    }

    @Override
    public void showDown() {
        Player currentPlayer = players.current();
        PokerHand pokerHand = handFactory.createCombination(currentPlayer.cards());
        showedDownPlayers.put(currentPlayer, pokerHand);
        List<Card> holeCards = new ArrayList<>();
        currentPlayer.cards().forEach(holeCards::add);
        poker.notifyPlayerShowedDownListeners(currentPlayer.id(), holeCards, pokerHand);
        changePlayerIndex();
        attemptDetermineWinners();
    }

    @Override
    void updateMoveAbility() {
        moveAbility.setFoldIsAble(!currentPlayerCantFold());
        poker.notifyMoveAbilityListeners(players.current().id(), moveAbility);
    }

    @Override
    void changePlayerIndex() {
        players.nextActive();
        updateMoveAbility();
        poker.notifyCurrentPlayerChangedListeners(players.current().id());
    }

    private boolean currentPlayerCantFold() {
        return showedDownPlayers.isEmpty() || players.current().isAllinner();
    }

    private void attemptDetermineWinners() {
        if (timeToDetermineWinners()) {

            Set<Player> winners = bank.giveMoneyToWinners(showedDownPlayers);

            winners.forEach(winner -> poker.notifyPlayerMoneyUpdatedListeners(
                    winner.id(),
                    winner.stack(),
                    winner.wager()
            ));
            poker.notifyBankMoneyUpdatedListeners(bank.money(), bank.wager());
            poker.setState(new RoundOfPlayIsOverState());
        }
    }

    private boolean timeToDetermineWinners() {
        return showedDownPlayers.size() == players.activePlayersNumber();
    }

}
