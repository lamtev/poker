package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerMoney;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.hands.PokerHandFactory;
import com.lamtev.poker.core.model.Player;
import com.lamtev.poker.core.states.exceptions.ForbiddenMoveException;

import java.util.*;

class ShowdownPokerState extends ActionPokerState {

    private int showDowns = 0;
    private Map<String, PokerHand> madeShowDown = new LinkedHashMap<>();

    ShowdownPokerState(ActionPokerState state, Player latestAggressor) {
        super(state);
        if (latestAggressor == null) {
            //TODO latest aggressor when all in
            players().nextActiveAfterDealer();
        } else {
            players().setLatestAggressor(latestAggressor);
        }
        poker().notifyCurrentPlayerChangedListeners(players().current().id());
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
    public void fold() {
        if (showDowns == 0) {
            throw new RuntimeException("Can't fold when nobody did showDown");
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
        ++showDowns;
        PokerHandFactory phf = new PokerHandFactory(communityCards());
        PokerHand pokerHand = phf.createCombination(players().current().cards());
        madeShowDown.put(players().current().id(), pokerHand);
        poker().notifyPlayerShowedDownListeners(players().current().id(), pokerHand);
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
            PokerHand maxPokerHand = Collections.max(madeShowDown.values());
            List<String> winnersIds = new ArrayList<>();

            madeShowDown.entrySet().stream()
                    .filter(e -> e.getValue().equals(maxPokerHand))
                    .forEach(e -> winnersIds.add(e.getKey()));

            bank().giveMoneyToWinners(winnersIds);

            //TODO think about rename WagerPlacedListener
            winnersIds.forEach(winnerId -> {
                Player winner = players().get(winnerId);
                poker().notifyWagerPlacedListeners(
                        winnerId,
                        new PlayerMoney(winner.stack(), winner.wager()),
                        bank().money()
                );
            });
            poker().setState(new GameIsOverPokerState(this));
        }
    }

    private boolean timeToDetermineWinners() {
        return showDowns == players().activePlayersNumber();
    }

}
