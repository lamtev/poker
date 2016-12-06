package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;

import java.util.ArrayList;
import java.util.List;

import static com.lamtev.poker.core.combinations.Flush.isFlush;
import static com.lamtev.poker.core.combinations.FourOfAKind.isFourOfAKind;
import static com.lamtev.poker.core.combinations.FullHouse.isFullHouse;
import static com.lamtev.poker.core.combinations.Pair.isPair;
import static com.lamtev.poker.core.combinations.RoyalFlush.isRoyalFlush;
import static com.lamtev.poker.core.combinations.Straight.isStraight;
import static com.lamtev.poker.core.combinations.StraightFlush.isStraightFlush;
import static com.lamtev.poker.core.combinations.ThreeOfAKind.isThreeOfAKind;
import static com.lamtev.poker.core.combinations.TwoPairs.isTwoPairs;

public class PokerCombinationFactory {

    private Cards commonCards;

    public PokerCombinationFactory(Cards commonCards) {
        this.commonCards = commonCards;
    }

    PokerCombination createCombination(List<Card> playerCards) {
        List<Card> cards = new ArrayList<>();
        commonCards.forEach(cards::add);
        playerCards.forEach(cards::add);

        if (isRoyalFlush(cards)) {
            return new RoyalFlush();
        } else if (isStraightFlush(cards)) {
            return new StraightFlush(cards);
        } else if (isFourOfAKind(cards)) {
            return new FourOfAKind();
        } else if (isFullHouse(cards)) {
            return new FullHouse();
        } else if (isStraight(cards)) {
            return new Straight(cards);
        } else if (isFlush(cards)) {
            return new Flush(cards);
        } else if (isThreeOfAKind(cards)) {
            return new ThreeOfAKind();
        } else if (isTwoPairs(cards)) {
            return new TwoPairs();
        } else if (isPair(cards)) {
            return new Pair();
        } else {
            return new HighCard();
        }
    }

}
