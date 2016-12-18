package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PokerCombinationFactory {

    private List<Card> commonCards;
    private final static Comparator<Card> REVERSED_COMPARATOR_BY_RANK = Comparator.comparing(Card::getRank).reversed();

    public PokerCombinationFactory(List<Card> commonCards) {
        this.commonCards = commonCards;
    }

    PokerCombination createCombination(List<Card> playerCards) {
        List<Card> cards = new ArrayList<>();
        commonCards.forEach(cards::add);
        playerCards.forEach(cards::add);

        cards.sort(REVERSED_COMPARATOR_BY_RANK);
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
        for (int i = 0; i < 3; ++i) {
            final Suit suit = cards.get(i).getSuit();
            int numberOfSameSuits = 1;
            for (int j = i + 1; j < cards.size(); ++j) {
                if (cards.get(i).getSuit().equals(cards.get(j).getSuit()) && ++numberOfSameSuits == 5) {
                    final Rank highCardRank = determineRankOfHighCardWithThisSuit(cards, suit);
                    return new Flush(highCardRank);
                }
            }
        }
        return null;
    }

    private Rank determineRankOfHighCardWithThisSuit(List<Card> cards, Suit suit) {
        return Collections.max(cards, (c1, c2) -> {
            if (c1.getSuit() == suit && c1.getSuit() == c2.getSuit()) {
                return c1.getRank().compareTo(c2.getRank());
            } else {
                if (c1.getSuit() == suit) return 1;
                else return -1;
            }
        }).getRank();
    }

    private PokerCombination parseStraight(List<Card> cards) {
        for (int i = Rank.ACE.ordinal(); i >= Rank.FIVE.ordinal(); --i) {
            Rank rank = Rank.values()[i];
            if (isStraightFromRank(cards, rank)) {
                return new Straight(rank);
            }
        }
        return null;
    }

    private boolean isStraightFromRank(List<Card> cards, Rank rank) {
        for (int i = rank.ordinal(); i >= -1 && i > rank.ordinal() - 5; --i) {
            int currentRankIndex = i == -1 ? Rank.ACE.ordinal() : i;
            final Card card = new Card(Rank.values()[currentRankIndex], Suit.HEARTS);
            if (Collections.binarySearch(cards, card, REVERSED_COMPARATOR_BY_RANK) < 0) {
                return false;
            }
        }
        return true;
    }

    private PokerCombination parseStraightFlush(List<Card> cards) {
        for (int i = 0; i < 4; ++i) {
            int numberOfSameSuits = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).getSuit().equals(cards.get(j).getSuit()) && ++numberOfSameSuits == 5) {
                    final Rank highCardRank = cards.get(i).getRank();
                    if (isStraightFromRank(cards, highCardRank)) {
                        return new StraightFlush(highCardRank);
                    }
                }
            }
        }
        return null;
    }

    private PokerCombination parseRoyalFlush(List<Card> cards) {
        for (int i = 0; i < 4; ++i) {
            int numberOfSameSuits = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).getSuit().equals(cards.get(j).getSuit()) && ++numberOfSameSuits == 5) {
                    final Rank highCardRank = cards.get(i).getRank();
                    if (highCardRank == Rank.ACE && isStraightFromRank(cards, highCardRank)) {
                        return new RoyalFlush();
                    }
                }
            }
        }
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
        for (int i = 0; i < 6; ++i) {
            int numberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).getRank().equals(cards.get(j).getRank()) && ++numberOfSameRanks == 3) {
                    //TODO implement it
                }
            }
        }
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
