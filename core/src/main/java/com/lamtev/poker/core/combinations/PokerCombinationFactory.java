package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;

import java.util.ArrayList;
import java.util.List;

public class PokerCombinationFactory {

    private Cards commonCards;

    public PokerCombinationFactory(Cards commonCards) {
        this.commonCards = commonCards;
    }

    PokerCombination createCombination(Cards playerCards) {
        List<Card> cards = new ArrayList<>();
        commonCards.forEach(cards::add);
        playerCards.forEach(cards::add);
        for (Card card : cards) {

        }
        return new RoyalFlush();
    }

}
