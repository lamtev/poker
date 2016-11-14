package com.lamtev.poker.core.poker_states;

import com.lamtev.poker.core.Bank;
import com.lamtev.poker.core.Players;
import com.lamtev.poker.core.Poker;

abstract class WageringState implements PokerState {

    private Poker poker;
    private Players players;
    private Bank bank;
    private int playerIndex;
    private MoveValidator moveValidator;
    private int raises = 0;

    WageringState(Poker poker) {
        this.poker = poker;
        this.players = poker.getPlayers();
        this.bank = poker.getBank();
        playerIndex = this instanceof PreflopWageringPokerState ? 2 : 0;
        moveValidator = new MoveValidator(players, bank);
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
        playerIndex %= players.size();
        while (! players.get(playerIndex).isActive()) {
            playerIndex++;
            playerIndex %= players.size();
        }
    }

}
