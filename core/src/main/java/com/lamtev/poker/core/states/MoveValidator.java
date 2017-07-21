package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Players;
import com.lamtev.poker.core.states.exceptions.UnallowableMoveException;

class MoveValidator {

    private final Players players;
    private final Bank bank;

    MoveValidator(Players players, Bank bank) {
        this.players = players;
        this.bank = bank;
    }

    void validateCall() throws UnallowableMoveException {
        if (!callIsAble()) {
            throw new UnallowableMoveException("Call");
        }
    }

    boolean callIsAble() {
        return players.current().wager() < bank.wager();
    }

    void validateRaise(int raises) throws UnallowableMoveException {
        if (!raiseIsAble(raises)) {
            throw new UnallowableMoveException("Raise");
        }
    }

    boolean raiseIsAble(int raises) {
        return players.activePlayersNumber() > 2 && raises < 3 || players.activePlayersNumber() == 2;
    }

    void validateCheck(int raises) throws UnallowableMoveException {
        if (!checkIsAble(raises)) {
            throw new UnallowableMoveException("Check");
        }
    }

    boolean checkIsAble(int raises) {
        return raises == 0;
    }

}
