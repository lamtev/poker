package com.lamtev.poker.core.poker_states;

import com.lamtev.poker.core.Bank;
import com.lamtev.poker.core.Players;

public abstract class WageringState implements PokerState {

    private Players players;
    private Bank bank;
    private int playerIndex;
    private MoveValidator moveValidator;
    private int raises = 0;

    public WageringState(Players players, Bank bank) {
        this.players = players;
        this.bank = bank;
        playerIndex = this instanceof PreflopWageringPokerState ? 2 : 0;
        moveValidator = new MoveValidator(players, bank);
    }

    @Override
    public void nextState(OnNextStateListener onNextStateListener) {
        onNextStateListener.nextState();
    }

    @Override
    public void setBlinds() {}

    @Override
    public void call() throws Exception {
        moveValidator.validateCall(playerIndex);
        bank.acceptCallFromPlayer(playerIndex);
        changePlayerIndex();
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        moveValidator.validateRaise(playerIndex, raises);
        bank.acceptRaiseFromPlayer(additionalWager, playerIndex);
        changePlayerIndex();
        raises++;
    }

    @Override
    public void fold() {
        players.get(playerIndex).fold();
        changePlayerIndex();
    }

    @Override
    public void check() throws Exception {
        moveValidator.validateCheck();
        changePlayerIndex();
    }

    private void changePlayerIndex() {
        playerIndex++;
        playerIndex %= players.activePlayersNumber();
    }

}
