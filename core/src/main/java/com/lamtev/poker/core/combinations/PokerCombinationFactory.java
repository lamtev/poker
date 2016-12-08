package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PokerCombinationFactory {

    private Cards commonCards;
    private final static Comparator<Card> COMPARATOR_BY_RANK = Comparator.comparing(Card::getRank).reversed();

    public PokerCombinationFactory(Cards commonCards) {
        this.commonCards = commonCards;
    }

    PokerCombination createCombination(List<Card> playerCards) {
        List<Card> cards = new ArrayList<>();
        commonCards.forEach(cards::add);
        playerCards.forEach(cards::add);

        PokerCombination pokerCombination = parseRoyalFlush(cards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        pokerCombination = parseStraightFlush(cards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        pokerCombination = parseFourOfAKind(cards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        pokerCombination = parseFullHouse(cards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        pokerCombination = parseFlush(cards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        pokerCombination = parseStraight(cards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        pokerCombination = parseThreeOfAKind(cards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        pokerCombination = parseTwoPairs(cards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        pokerCombination = parsePair(cards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        pokerCombination = parseHighCard(cards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        return null;
    }

    private PokerCombination parseFlush(List<Card> cards) {
        cards.sort(COMPARATOR_BY_RANK);
        for (int i = 0; i < cards.size(); ++i) {
            int numberOfSameSuits = 0;
            Suit suit = cards.get(i).getSuit();
            for (Card card : cards) {
                if (card.getSuit().equals(suit)) {
                    //TODO think about how to do it better
                    ++numberOfSameSuits;
                }
                if (numberOfSameSuits == 5) {
                    return new Flush(cards.get(i).getRank());
                }
            }
        }
        return null;
    }

    private PokerCombination parseStraight(List<Card> cards) {
        cards.sort(COMPARATOR_BY_RANK);
        for (int i = Rank.ACE.ordinal(); i >= Rank.FIVE.ordinal(); --i) {
            int numberOfSequentialRanks = 0;
            int highCardIndex = 0;
            Rank rank = Rank.values()[i];
            for (int j = rank.ordinal(); j >= - 1 && j >= rank.ordinal() - 4; --i) {
                int rankIndex = j == -1 ? Rank.ACE.ordinal() : j;
                Card keyCard = new Card(Rank.values()[rankIndex], Suit.HEARTS);
                int keyIndex = Collections.binarySearch(cards, keyCard, COMPARATOR_BY_RANK);
                if (keyIndex >= 0) {
                    ++numberOfSequentialRanks;
                    highCardIndex = numberOfSequentialRanks == 1 ? keyIndex : highCardIndex;
                }
            }
            if (numberOfSequentialRanks == 5) {
                //TODO think about how to do it better
                return new Straight(cards.get(highCardIndex).getRank());
            }
        }
        return null;
    }



    private PokerCombination parseStraightFlush(List<Card> cards) {
        //TODO implement
        return null;
    }

    private PokerCombination parseRoyalFlush(List<Card> cards) {
        //TODO implement
        return null;
    }

    private PokerCombination parseFourOfAKind(List<Card> cards) {
        //TODO implement
        return null;
    }

    private PokerCombination parseFullHouse(List<Card> cards) {
        //TODO implement
        return null;
    }

    private PokerCombination parseThreeOfAKind(List<Card> cards) {
        //TODO implement
        return null;
    }

    private PokerCombination parseTwoPairs(List<Card> cards) {
        //TODO implement
        return null;
    }

    private PokerCombination parsePair(List<Card> cards) {
        //TODO implement
        return null;
    }

    private PokerCombination parseHighCard(List<Card> cards) {
        //TODO implement
        return null;
    }

}
