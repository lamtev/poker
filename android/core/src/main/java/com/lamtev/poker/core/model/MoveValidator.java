package com.lamtev.poker.core.model;

import com.lamtev.poker.core.exceptions.UnallowableMoveException;

public final class MoveValidator {

    private final Players players;
    private final Bank bank;

    public MoveValidator(Players players, Bank bank) {
        this.players = players;
        this.bank = bank;
    }

    public void validateCall() throws UnallowableMoveException {
        if (!callIsAble()) {
            throw new UnallowableMoveException("Call");
        }
    }

    public boolean callIsAble() {
        return players.current().wager() < bank.wager()
                && players.current().stack() >= bank.wager() - players.current().wager();
    }

    public void validateRaise(int raises) throws UnallowableMoveException {
        if (!raiseIsAble(raises)) {
            throw new UnallowableMoveException("Raise");
        }
    }

    public boolean raiseIsAble(int raises) {
        return (players.activePlayersNumber() > 2 && raises < 3 || players.activePlayersNumber() == 2)
                && players.activeNonAllinnersNumber() != 1;
    }

    public void validateCheck(int raises) throws UnallowableMoveException {
        if (!checkIsAble(raises)) {
            throw new UnallowableMoveException("Check");
        }
    }

    public boolean checkIsAble(int raises) {
        return raises == 0;
    }

}
