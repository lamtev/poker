package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;

public class PokerCombinationFactory {

    private Cards commonCards;

    public PokerCombinationFactory(Cards commonCards) {
        this.commonCards = commonCards;
    }

    PokerCombination createCombination(Cards playerCards) {
        Cards cards = new Cards();
        commonCards.forEach(cards::add);
        playerCards.forEach(cards::add);
        for (Card card : cards) {

        }
        return new RoyalFlush();
    }

}
