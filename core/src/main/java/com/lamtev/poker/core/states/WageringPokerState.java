package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;

abstract class WageringPokerState extends SettingsFinishedPokerState {

    private int playerIndex;
    private MoveValidator moveValidator;
    private int raises = 0;
    private int continuousChecks = 0;

    WageringPokerState(Poker poker, Players players, Bank bank, Dealer dealer, Cards commonCards) {
        super(poker, players, bank, dealer, commonCards);
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

    boolean isTimeToNextState() {
        return doPlayersHaveSameWagers() && raises == 3 ||
                continuousChecks == players.activePlayersNumber();
    }

    void setState(Class<? extends SettingsFinishedPokerState> className) throws Exception {
        poker.setState(
                className
                        .getConstructor(Poker.class, Players.class, Bank.class, Dealer.class, Cards.class)
                        .newInstance(poker, players, bank, dealer, commonCards)
        );
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
        setState(WinnersDeterminationPokerState.class);
    }

}
