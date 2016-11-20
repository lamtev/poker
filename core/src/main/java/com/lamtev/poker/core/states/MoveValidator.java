package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Player;
import com.lamtev.poker.core.model.Players;

class MoveValidator {

    private Players players;
    private Bank bank;

    MoveValidator(Players players, Bank bank) {
        this.players = players;
        this.bank = bank;
    }

    void validateCall(int playerIndex) throws Exception {
        if (players.get(playerIndex).getWager() >= bank.getCurrentWager()) {
            throw new Exception("can't call");
        }
    }

    void validateRaise(int raises) throws Exception {
        if ((players.activePlayersNumber() <= 2 || raises >= 3) && players.activePlayersNumber() != 2) {
            throw new Exception("can't raise");
        }
    }

    void validateCheck(int raises) throws Exception {
        if (raises != 0) {
            throw new Exception("can't check");
        }
    }

}
