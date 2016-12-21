package com.lamtev.poker.core.states;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.hands.PokerHandFactory;

import java.util.*;

class ShowdownPokerState extends ActionPokerState {

    private int showDowns = 0;
    private Map<String, PokerHand> madeShowDown = new TreeMap<>();

    ShowdownPokerState(ActionPokerState state, int latestAggressorIndex) {
        super(state);
        playerIndex = latestAggressorIndex;
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
        changePlayerIndex();
        attemptDetermineWinners();
    }

    @Override
    public void check() throws Exception {
        throw new Exception();
    }

    @Override
    public PokerHand.Name showDown() throws Exception {
        ++showDowns;
        PokerHandFactory phf = new PokerHandFactory(commonCards);
        PokerHand pokerHand = phf.createCombination(currentPlayer().getCards());
        madeShowDown.put(currentPlayer().getId(), pokerHand);
        attemptDetermineWinners();
        changePlayerIndex();
        return pokerHand.getName();
    }

    //TODO     add feature for action: not showDown and not fold
    //TODO     When it would be added, if only one action player then state = ShowDown
    //TODO     and player will have 2 variants: do this action or showDown

    private void attemptDetermineWinners() {
        if (timeToDetermineWinners()) {
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
