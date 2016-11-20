package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

abstract class WageringPokerState extends ActionPokerState {

    private MoveValidator moveValidator;
    private int checks = 0;
    private List<Player> allInners = new ArrayList<>();
    private List<Player> raisers = new ArrayList<>();

    WageringPokerState(Poker poker,
                       Players players, Bank bank, Dealer dealer, Cards commonCards) {
        super(poker, players, bank, dealer, commonCards);
        playerIndex = this instanceof PreflopWageringPokerState ? 2 : 0;
        moveValidator = new MoveValidator(players, bank);
    }

    WageringPokerState(ActionPokerState state) {
        this(state.poker, state.players, state.bank, state.dealer, state.commonCards);
    }

    @Override
    public void call() throws Exception {
        moveValidator.validateCall(currentPlayer());
        bank.acceptCallFromPlayer(currentPlayer());
        if (thisMoveIsAllIn()) {
            allInners.add(currentPlayer());
        }
        changePlayerIndex();
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        moveValidator.validateRaise(raisers.size());
        bank.acceptRaiseFromPlayer(additionalWager, currentPlayer());
        if (thisMoveIsAllIn()) {
            allInners.add(currentPlayer());
        }
        raisers.add(currentPlayer());
        changePlayerIndex();
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

    @Override
    public void fold() throws Exception {
        currentPlayer().fold();
        if (isOnlyOneActivePlayer()) {
            gameIsOverState();
        }
        changePlayerIndex();
    }

    @Override
    public void check() throws Exception {
        moveValidator.validateCheck(raisers.size());
        changePlayerIndex();
        checks++;
    }

    @Override
    public Cards showDown() throws Exception {
        throw new Exception("Can't show down when wagering");
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

    private boolean isOnlyOneActivePlayer() {
        return players.activePlayersNumber() == 1;
    }

    private void gameIsOverState() throws Exception {
        bank.giveMoneyToWinners(new Players() {{
            add(currentPlayer());
        }});
        poker.setState(new GameIsOverPokerState(this));
    }

    boolean isTimeToNextState() {
        return checks == players.activePlayersNumber() ||
                notAllInnersActivePlayersWithSameWagers() + allInners.size()
                        == players.activePlayersNumber() && raisers.size() > 0;
    }

    boolean preflopHasBeenFinished() {
        return notAllInnersActivePlayersWithSameWagers() + allInners.size() == players.activePlayersNumber();
    }

    void setState(PokerState newState) {
        if (allInners.size() != 0) {
            poker.setState(new ShowdownPokerState(this, latestAggressorIndex()));
        } else {
            poker.setState(newState);
        }
    }

    int latestAggressorIndex() {
        ListIterator<Player> it = raisers.listIterator(raisers.size());
        int i = 0;
        while (it.hasPrevious()) {
            if (it.previous().isActive()) {
                return i;
            }
            ++i;
        }
        i = 0;
        for (Player player : players) {
            if (player.isActive()) {
                return i;
            }
            ++i;
        }
        return -1;
    }

}
