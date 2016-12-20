package com.lamtev.poker.core.states;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.hands.PokerHandFactory;
import com.lamtev.poker.core.model.Cards;

//TODO
class ShowdownPokerState extends ActionPokerState {

    private int showDowns = 0;

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
        if (timeToDetermineWinners()) {
            //bank.giveMoneyToWinners(combinationAnalyser.determineWinners());
            poker.setState(new GameIsOverPokerState(this));
        }
    }

    @Override
    public void check() throws Exception {
        throw new Exception();
    }

    //TODO think about actuality of wrappers for collections
    @Override
    public PokerHand.Name showDown() throws Exception {
        ++showDowns;
        if (timeToDetermineWinners()) {

            //bank.giveMoneyToWinners(combinationAnalyser.determineWinners());
            poker.setState(new GameIsOverPokerState(this));
        }
        Cards playerCards = currentPlayer().getCards();
        PokerHandFactory phf = new PokerHandFactory(commonCards);
        PokerHand pokerHand = phf.createCombination(playerCards);

        changePlayerIndex();
        return pokerHand.getName();
    }

    private boolean timeToDetermineWinners() {
        return showDowns == players.activePlayersNumber();
    }

}
