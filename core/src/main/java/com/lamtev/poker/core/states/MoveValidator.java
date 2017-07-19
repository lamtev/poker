package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Player;
import com.lamtev.poker.core.model.Players;
import com.lamtev.poker.core.states.exceptions.UnallowableMoveException;

class MoveValidator {

    private final Players players;
    private final Bank bank;

    MoveValidator(Players players, Bank bank) {
        this.players = players;
        this.bank = bank;
    }

    void validateCall(Player player) throws UnallowableMoveException {
        if (player.wager() >= bank.wager()) {
            throw new UnallowableMoveException("Call");
        }
    }

    void validateRaise(int raises) throws UnallowableMoveException {
        if ((players.activePlayersNumber() <= 2 || raises >= 3) && players.activePlayersNumber() != 2) {
            throw new UnallowableMoveException("Raise");
        }
    }

    void validateCheck(int raises) throws UnallowableMoveException {
        if (raises != 0) {
            throw new UnallowableMoveException("Check");
        }
    }

}
