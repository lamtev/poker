package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;

import java.util.List;

abstract class WageringPokerState extends ActionPokerState {

    private int playerIndex;
    private MoveValidator moveValidator;
    private int raises = 0;
    private int continuousChecks = 0;

    WageringPokerState(ActionPokerState state) {
        this(state.wageringEndListeners, state.poker, state.players, state.bank, state.dealer, state.commonCards);
    }

    WageringPokerState(List<WageringEndListener> wageringEndListeners, Poker poker, Players players, Bank bank, Dealer dealer, Cards commonCards) {
        super(wageringEndListeners, poker, players, bank, dealer, commonCards);
        playerIndex = this instanceof PreflopWageringPokerState ? 2 : 0;
        moveValidator = new MoveValidator(players, bank);
    }

    @Override
    public void call() throws Exception {
        moveValidator.validateCall(playerIndex);
        bank.acceptCallFromPlayer(playerIndex);
        changePlayerIndex();
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        moveValidator.validateRaise(raises);
        bank.acceptRaiseFromPlayer(additionalWager, playerIndex);
        changePlayerIndex();
        raises++;
        continuousChecks = 0;
    }

    @Override
    public void fold() throws Exception {
        players.get(playerIndex).fold();
        changePlayerIndex();
        if (isOnlyOneActivePlayer()) {
            winnerDeterminingState();
        }
    }

    @Override
    public void check() throws Exception {
        moveValidator.validateCheck();
        changePlayerIndex();
        continuousChecks++;
    }

    @Override
    public Cards showDown() throws Exception {
        throw new Exception("Can't show down when wagering");
    }

    boolean isTimeToNextState() {
        return doPlayersHaveSameWagers() && raises == 3 ||
                continuousChecks == players.activePlayersNumber();
    }

    private boolean doPlayersHaveSameWagers() {
        for (Player player : players) {
            if (player.getWager() != bank.getCurrentWager()) {
                return false;
            }
        }
        return true;
    }

    private void changePlayerIndex() {
        playerIndex++;
        playerIndex %= players.size();
        while (!players.get(playerIndex).isActive()) {
            playerIndex++;
            playerIndex %= players.size();
        }
    }

    private boolean isOnlyOneActivePlayer() {
        return players.activePlayersNumber() == 1;
    }

    private void winnerDeterminingState() throws Exception {
        poker.setState(new GameIsOverPokerState(this));
    }

}
