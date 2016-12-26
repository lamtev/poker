package com.lamtev.poker.core.states;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.hands.PokerHandFactory;

import java.util.*;

class ShowdownPokerState extends ActionPokerState {

    private int showDowns = 0;
    private Map<String, PokerHand> madeShowDown = new TreeMap<>();

    ShowdownPokerState(ActionPokerState state, int latestAggressorIndex) {
        super(state);

        System.out.println(commonCards.size());
        playerIndex = latestAggressorIndex;
        System.out.println(players.get(playerIndex).getId() + " " + currentPlayer().getId());
        poker.notifyCurrentPlayerListeners(currentPlayer().getId());
    }

    @Override
    public void call() throws Exception {
        throw new Exception();
    }

    @Override
    public void raise(int additionalWager) throws Exception {
        throw new Exception();
    }

    @Override
    public void allIn() throws Exception {
        throw new Exception();
    }

    @Override
    public void fold() throws Exception {
        if (showDowns == 0) {
            throw new Exception("Can't fold when nobody did showDown");
        }
        currentPlayer().fold();
        poker.notifyPlayerFoldListeners(currentPlayer().getId());
        changePlayerIndex();
        attemptDetermineWinners();
    }

    @Override
    public void check() throws Exception {
        throw new Exception("Can't check when showDown poker state");
    }

    @Override
    public void showDown() throws Exception {
        ++showDowns;
        PokerHandFactory phf = new PokerHandFactory(commonCards);
        players.forEach(player -> {
            if (player.isActive()) {
                System.out.println(player.getId() + " " + player.getCards());
            }
        });
        System.out.println(currentPlayer().getId() + " " + currentPlayer().getCards());
        PokerHand pokerHand = phf.createCombination(currentPlayer().getCards());
        madeShowDown.put(currentPlayer().getId(), pokerHand);
        poker.notifyPlayerShowedDownListeners(currentPlayer().getId(), pokerHand);
        changePlayerIndex();
        attemptDetermineWinners();
    }

    //TODO     add feature for action: not showDown and not fold
    //TODO     When it would be added, if only one action player then state = ShowDown
    //TODO     and player will have 2 variants: do this action or showDown

    private void attemptDetermineWinners() {
        if (timeToDetermineWinners()) {
            madeShowDown.forEach((k, v) -> System.out.println(k));
            PokerHand maxPokerHand = Collections.max(madeShowDown.values());
            List<String> winners = new ArrayList<>();

            madeShowDown.entrySet().stream()
                    .filter(e -> e.getValue().equals(maxPokerHand))
                    .forEach(e -> winners.add(e.getKey()));
            bank.giveMoneyToWinners(winners);

            poker.setState(new GameIsOverPokerState(this));
        }
    }

    private boolean timeToDetermineWinners() {
        return showDowns == players.activePlayersNumber();
    }

}
