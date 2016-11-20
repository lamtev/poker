package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;

import java.util.ArrayList;
import java.util.List;

abstract class WageringPokerState extends ActionPokerState {

    private int playerIndex;
    private MoveValidator moveValidator;
    private int raises = 0;
    private int checks = 0;
    private List<Player> allInners = new ArrayList<>();

    WageringPokerState(ActionPokerState state) {
        this(state.wageringEndListeners, state.poker, state.players, state.bank, state.dealer, state.commonCards);
    }

    WageringPokerState(List<WageringEndListener> wageringEndListeners, Poker poker,
                       Players players, Bank bank, Dealer dealer, Cards commonCards) {
        super(wageringEndListeners, poker, players, bank, dealer, commonCards);
        playerIndex = this instanceof PreflopWageringPokerState ? 2 : 0;
        moveValidator = new MoveValidator(players, bank);
    }

    @Override
    public void call() throws Exception {
        moveValidator.validateCall(playerIndex);
        bank.acceptCallFromPlayer(currentPlayer());
        if (thisMoveIsAllIn()) {
            allInners.add(currentPlayer());
        }
        changePlayerIndex();
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        moveValidator.validateRaise(raises);
        bank.acceptRaiseFromPlayer(additionalWager, currentPlayer());
        if (thisMoveIsAllIn()) {
            allInners.add(currentPlayer());
        }
        changePlayerIndex();
        raises++;
    }

    @Override
    public void allIn() throws Exception {
        int additionalWager = currentPlayer().getStack() - (bank.getCurrentWager() - currentPlayer().getWager());

        if (additionalWager == 0) {
            call();
        } else if (additionalWager > 0) {
            raise(additionalWager);
        } else {
            bank.acceptAllInFromPlayer(currentPlayer());
            allInners.add(currentPlayer());
            changePlayerIndex();
        }
    }

    private Player currentPlayer() {
        return players.get(playerIndex);
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
        moveValidator.validateCheck(raises);
        changePlayerIndex();
        checks++;
    }

    @Override
    public Cards showDown() throws Exception {
        throw new Exception("Can't show down when wagering");
    }

    boolean isTimeToNextState() {
//        System.out.println(this.getClass().getSimpleName());
//        System.out.println("checks: " + checks);
//        System.out.println("allInners: " + allInners.size());
        return checks == players.activePlayersNumber() ||
                notAllInnersActivePlayersWithSameWagers() + allInners.size() == players.activePlayersNumber() && raises > 0;
    }

    boolean preflopHasBeenFinished() {
        return notAllInnersActivePlayersWithSameWagers() + allInners.size() == players.activePlayersNumber();
    }

    void setState(PokerState newState) {
        wageringEndListeners.forEach(WageringEndListener::onWageringEnd);
        if (allInners.size() != 0) {
            poker.setState(new ShowdownPokerState(this));
        } else {
            poker.setState(newState);
        }
    }

    private boolean thisMoveIsAllIn() {
        return currentPlayer().getStack() == 0;
    }

    private int notAllInnersActivePlayersWithSameWagers() {
        int count = 0;
        for (Player player : players) {
            if (player.isActive() && !allInners.contains(player) &&
                    player.getWager() == bank.getCurrentWager()) {
                count++;
            }
        }
        return count;
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
        poker.setState(new GameIsOverPokerState(this));
    }

}
