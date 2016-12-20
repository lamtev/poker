package com.lamtev.poker.core.combinations;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PokerCombinationFactory {

    private final List<Card> commonCards;
    private final static Comparator<Card> COMPARATOR_BY_RANK = Comparator.comparing(Card::getRank);

    public PokerCombinationFactory(List<Card> commonCards) {
        this.commonCards = commonCards;
    }

    PokerCombination createCombination(List<Card> playerCards) {
        List<Card> cards = new ArrayList<>();
        commonCards.forEach(cards::add);
        playerCards.forEach(cards::add);

        cards.sort(COMPARATOR_BY_RANK.reversed());
        PokerCombination pokerCombination = parseRoyalFlush(cards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        pokerCombination = parseStraightFlush(cards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        pokerCombination = parseFourOfAKind(cards, playerCards);
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

        pokerCombination = parseThreeOfAKind(cards, playerCards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        pokerCombination = parseTwoPairs(cards, playerCards);
        if (pokerCombination != null) {
            return pokerCombination;
        }

        pokerCombination = parsePair(cards, playerCards);
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
            if (Collections.binarySearch(cards, card, COMPARATOR_BY_RANK.reversed()) < 0) {
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

    //TODO remove code duplicate
    //TODO increase code readability
    private PokerCombination parseFourOfAKind(List<Card> cards, List<Card> playerCards) {
        for (int i = 0; i < 5; ++i) {
            int numberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).getRank().equals(cards.get(j).getRank()) && ++numberOfSameRanks == 4) {
                    Rank kicker = determineKicker(playerCards, cards.get(i).getRank());
                    return new FourOfAKind(cards.get(i).getRank(), kicker);
                }
            }
        }
        return null;
    }

    //TODO remove code duplicate
    //TODO increase code readability
    private PokerCombination parseFullHouse(List<Card> cards) {
        Rank threeOfAKindHighCardRank = null;
        for (int i = 0; i < 6; ++i) {
            int threeOfAKindNumberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).getRank().equals(cards.get(j).getRank()) && ++threeOfAKindNumberOfSameRanks == 3) {
                    threeOfAKindHighCardRank = cards.get(i).getRank();
                    break;
                }
                if (threeOfAKindHighCardRank != null) {
                    break;
                }
            }
        }
        if (threeOfAKindHighCardRank != null) {
            for (int i = 0; i < 7; ++i) {
                int pairNumberOfSameRanks = 1;
                Rank pairHighCardRank;
                for (int k = 0; k < cards.size(); ++k) {
                    if (i != k && cards.get(i).getRank() != threeOfAKindHighCardRank && cards.get(i).getRank().equals(cards.get(k).getRank()) && ++pairNumberOfSameRanks == 2) {
                        pairHighCardRank = cards.get(i).getRank();
                        return new FullHouse(threeOfAKindHighCardRank, pairHighCardRank);
                    }
                }
            }
        }
        return null;
    }

    //TODO remove code duplicate
    //TODO increase code readability
    private PokerCombination parseThreeOfAKind(List<Card> cards, List<Card> playerCards) {
        for (int i = 0; i < 6; ++i) {
            int numberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).getRank().equals(cards.get(j).getRank()) && ++numberOfSameRanks == 3) {
                    Rank kicker = determineKicker(playerCards, cards.get(i).getRank());
                    return new ThreeOfAKind(cards.get(i).getRank(), kicker);
                }
            }
        }
        return null;
    }

    private Rank determineKicker(List<Card> playerCards, Rank... combinationHighCardRanks) {
        int indexOfMax = playerCards.indexOf(Collections.max(playerCards, COMPARATOR_BY_RANK));
        boolean maxHasNotSameRank = false;
        boolean minHasSameRank = false;
        for (Rank combinationHighCardRank : combinationHighCardRanks) {
            maxHasNotSameRank = playerCards.get(indexOfMax).getRank() != combinationHighCardRank;
            minHasSameRank = playerCards.get((indexOfMax + 1) % 2).getRank() == combinationHighCardRank;
        }
        if (maxHasNotSameRank || minHasSameRank) {
            return playerCards.get(indexOfMax).getRank();
        } else {
            return playerCards.get((indexOfMax + 1) % 2).getRank();
        }
    }

    //TODO remove code duplicate
    //TODO increase code readability
    private PokerCombination parseTwoPairs(List<Card> cards, List<Card> playerCards) {
        Rank firstPairHighCardRank = null;
        for (int i = 0; i < 7; ++i) {
            int firstPairNumberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).getRank().equals(cards.get(j).getRank()) && ++firstPairNumberOfSameRanks == 2) {
                    firstPairHighCardRank = cards.get(i).getRank();
                    break;
                }
                if (firstPairHighCardRank != null) {
                    break;
                }
            }
        }
        if (firstPairHighCardRank != null) {
            for (int i = 0; i < 7; ++i) {
                int secondPairNumberOfSameRanks = 1;
                Rank secondPairHighCardRank;
                for (int k = 0; k < cards.size(); ++k) {
                    if (i != k && cards.get(i).getRank() != firstPairHighCardRank && cards.get(i).getRank().equals(cards.get(k).getRank()) && ++secondPairNumberOfSameRanks == 2) {
                        secondPairHighCardRank = cards.get(i).getRank();
                        Rank kicker = determineKicker(playerCards, firstPairHighCardRank, secondPairHighCardRank);
                        return new TwoPairs(firstPairHighCardRank, secondPairHighCardRank, kicker);
                    }
                }
            }
        }
        return null;
    }

    //TODO remove code duplicate
    //TODO increase code readability
    private PokerCombination parsePair(List<Card> cards, List<Card> playerCards) {
        for (int i = 0; i < 7; ++i) {
            int numberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).getRank().equals(cards.get(j).getRank()) && ++numberOfSameRanks == 2) {
                    Rank kicker = determineKicker(playerCards, cards.get(i).getRank());
                    return new Pair(cards.get(i).getRank(), kicker);
                }
            }
        }
        return null;
    }

    private PokerCombination parseHighCard(List<Card> cards) {
        //TODO implement
        return null;
    }

}
