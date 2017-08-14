package com.lamtev.poker.core.states;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

final class BlindsState extends ActionState {

    private final int smallBlindWager;

    BlindsState(Poker poker, Players players, Bank bank, Dealer dealer, Cards communityCards, MoveAbility moveAbility, int smallBlindWager) {
        super(poker, players, bank, dealer, communityCards, moveAbility);
        this.smallBlindWager = smallBlindWager;
    }

    @Override
    public void start() {
        bank.acceptBlindWagers(smallBlindWager);
        Player smallBlind = players.smallBlind();
        poker.notifyPlayerMoneyUpdatedListeners(smallBlind.id(), smallBlind.stack(), smallBlind.wager());
        Player bigBlind = players.bigBlind();
        poker.notifyPlayerMoneyUpdatedListeners(bigBlind.id(), bigBlind.stack(), bigBlind.wager());
        poker.notifyBankMoneyUpdatedListeners(bank.money(), bank.wager());
        poker.notifyBlindWagersPlacedListeners();
        nextState();
    }

    private void nextState() {
        if (timeToForcedShowdown()) {
            dealer.makePreflop();
            poker.notifyHoleCardsDealtListeners(new LinkedHashMap<String, List<Card>>() {{
                for (final Player player : players) {
                    put(player.id(), new ArrayList<Card>() {{
                        for (Card card : player.cards()) {
                            add(card);
                        }
                    }});
                }
            }});
            dealer.makeFlop();
            dealer.makeTurn();
            dealer.makeRiver();
            poker.notifyCommunityCardsDealtListeners(new ArrayList<Card>() {{
                for (Card communityCard : communityCards) {
                    add(communityCard);
                }
            }});
            Player latestAggressor = players.bigBlind().isAllinner() ?
                    players.bigBlind() : players.smallBlind();
            poker.setState(new ShowdownState(this, latestAggressor));
        } else {
            poker.setState(new PreflopWageringState(this));
        }
    }

    @Override
    public void call() {

    }

    @Override
    public void raise(int additionalWager) {

    }

    @Override
    public void allIn() {

    }

    @Override
    public void fold() {

    }

    @Override
    public void check() {

    }

    @Override
    public void showDown() {

    }

    @Override
    void updateMoveAbility() {

    }

}
