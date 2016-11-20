package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Players;

//TODO
class ShowdownPokerState extends ActionPokerState {

    private int showDowns = 0;
    private CombinationAnalyser combinationAnalyser;

    ShowdownPokerState(ActionPokerState state, int latestAggressorIndex) {
        super(state);
        combinationAnalyser = new CombinationAnalyser(players, commonCards);
        playerIndex = latestAggressorIndex;
    }

    @Override
    public Cards getPlayerCards(String playerID) throws Exception {
        return players.get(playerID).getCards();
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
            bank.giveMoneyToWinners(determineWinners());
            poker.setState(new GameIsOverPokerState(this));
        }
    }

    @Override
    public void check() throws Exception {
        throw new Exception();
    }

    @Override
    public Cards showDown() throws Exception {
        showDowns++;
        if (timeToDetermineWinners()) {
            bank.giveMoneyToWinners(determineWinners());
            poker.setState(new GameIsOverPokerState(this));
        }
        Cards cards = currentPlayer().getCards();
        changePlayerIndex();
        return cards;
    }

    private boolean timeToDetermineWinners() {
        return showDowns == players.activePlayersNumber();
    }

    private Players determineWinners() {
        return new Players() {{
           players.forEach(player -> {
               if (player.isActive()) {
                   add(player);
               }
           });
        }};
    }

}
