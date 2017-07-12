package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.PlayerMoney;
import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.hands.PokerHandFactory;
import com.lamtev.poker.core.model.*;
import com.lamtev.poker.core.states.exceptions.ForbiddenMoveException;

import java.util.*;

class ShowdownPokerState extends ActionPokerState {

    private int showDowns = 0;
    private Map<String, PokerHand> madeShowDown = new LinkedHashMap<>();

    ShowdownPokerState(Poker poker, Players players, Bank bank,
                       Dealer dealer, Cards commonCards, Player latestAggressor) {
        super(poker, players, bank, dealer, commonCards);
        players.setLatestAggressor(latestAggressor);
        poker.notifyCurrentPlayerChangedListeners(currentPlayer().getId());
    }

    ShowdownPokerState(ActionPokerState state, Player latestAggressor) {
        super(state);
        if (latestAggressor == null) {
            players().nextAfterDealer();
        } else {
            players().setLatestAggressor(latestAggressor);
        }
        poker().notifyCurrentPlayerChangedListeners(currentPlayer().getId());
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
    public void fold() throws Exception {
        if (showDowns == 0) {
            throw new Exception("Can't fold when nobody did showDown");
        }
        currentPlayer().fold();
        poker().notifyPlayerFoldListeners(currentPlayer().getId());
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
        PokerHand pokerHand = phf.createCombination(currentPlayer().getCards());
        madeShowDown.put(currentPlayer().getId(), pokerHand);
        poker().notifyPlayerShowedDownListeners(currentPlayer().getId(), pokerHand);
        changePlayerIndex();
        attemptDetermineWinners();
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
            winnersIds.forEach(
                    winnerId -> {
                        Player winner = players().get(winnerId);
                        poker().notifyWagerPlacedListeners(
                                winnerId,
                                new PlayerMoney(winner.getStack(), winner.getWager()),
                                bank().getMoney()
                        );
                    });
            System.out.println("happens after preflop");
            poker().setState(new GameIsOverPokerState(this));
        }
    }

    private boolean timeToDetermineWinners() {
        return showDowns == players().activePlayersNumber();
    }

}
