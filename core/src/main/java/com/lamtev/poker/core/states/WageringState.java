package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Player;
import com.lamtev.poker.core.model.Players;

abstract class WageringState implements PokerState {

    private Poker poker;
    private Players players;
    private Bank bank;
    private int playerIndex;
    private MoveValidator moveValidator;
    private int raises = 0;
    private int continuousChecks = 0;

    WageringState(Poker poker) {
        this.poker = poker;
        this.players = poker.getPlayers();
        this.bank = poker.getBank();
        playerIndex = this instanceof PreflopWageringPokerState ? 2 : 0;
        moveValidator = new MoveValidator(players, bank);
    }

    @Override
    public void setBlinds() throws Exception {
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
            finalState();
        }
    }

    @Override
    public void check() throws Exception {
        moveValidator.validateCheck();
        changePlayerIndex();
        continuousChecks++;
    }

    boolean isTimeToNextState() {
        return arePlayersHaveSameWagers() && raises == 3 ||
                continuousChecks == players.activePlayersNumber();
    }

    private boolean arePlayersHaveSameWagers() {
        for (Player player : poker.getPlayers()) {
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

    private void finalState() {
        poker.update(() -> poker.setState(new WinnersDeterminingPokerState(poker)));
    }

}
