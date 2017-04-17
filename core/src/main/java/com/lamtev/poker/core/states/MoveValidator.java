package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Player;
import com.lamtev.poker.core.model.Players;
import com.lamtev.poker.core.states.exceptions.UnavailableMoveException;

class MoveValidator {

    private Players players;
    private Bank bank;

    MoveValidator(Players players, Bank bank) {
        this.players = players;
        this.bank = bank;
    }

    //TODO validate allin

    void validateCall(Player player) throws UnavailableMoveException {
        if (player.getWager() >= bank.getCurrentWager()) {
            throw new UnavailableMoveException("Call");
        }
    }

    void validateRaise(int raises) throws UnavailableMoveException {
        if ((players.activePlayersNumber() <= 2 || raises >= 3) && players.activePlayersNumber() != 2) {
            throw new UnavailableMoveException("Raise");
        }
    }

    void validateCheck(int raises) throws UnavailableMoveException {
        if (raises != 0) {
            throw new UnavailableMoveException("Check");
        }
    }

}
