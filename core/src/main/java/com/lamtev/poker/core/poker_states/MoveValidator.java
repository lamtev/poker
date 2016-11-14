package com.lamtev.poker.core.poker_states;

import com.lamtev.poker.core.Bank;
import com.lamtev.poker.core.Player;
import com.lamtev.poker.core.Players;

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

    void validateCheck() throws Exception {
        for (Player player : players) {
            if (player.getWager() != bank.getCurrentWager()) {
                throw new Exception("can't check");
            }
        }
    }

}
