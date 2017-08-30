package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public final class PokerHandFactory {

    private final static Comparator<Card> COMPARATOR_BY_RANK = Comparator.comparing(Card::rank).reversed();

    private PokerHandFactory() {

    }

    static PokerHand createCombination(List<Card> cards) {
        cards.sort(COMPARATOR_BY_RANK);
        PokerHand pokerHand = parseRoyalFlush(cards);
        if (pokerHand != null) {
            return pokerHand;
        }

        pokerHand = parseStraightFlush(cards);
        if (pokerHand != null) {
            return pokerHand;
        }

        pokerHand = parseFourOfAKind(cards);
        if (pokerHand != null) {
            return pokerHand;
        }

        pokerHand = parseFullHouse(cards);
        if (pokerHand != null) {
            return pokerHand;
        }

        pokerHand = parseFlush(cards);
        if (pokerHand != null) {
            return pokerHand;
        }

        pokerHand = parseStraight(cards);
        if (pokerHand != null) {
            return pokerHand;
        }

        pokerHand = parseThreeOfAKind(cards);
        if (pokerHand != null) {
            return pokerHand;
        }

        pokerHand = parseTwoPairs(cards);
        if (pokerHand != null) {
            return pokerHand;
        }

        pokerHand = parsePair(cards);
        if (pokerHand != null) {
            return pokerHand;
        }

        pokerHand = parseHighCard(cards);
        return pokerHand;
    }

    static PokerHand createCombination(List<Card> playerCards, List<Card> communityCards) {
        List<Card> cards = new ArrayList<>();
        cards.addAll(playerCards);
        cards.addAll(communityCards);
        return createCombination(cards);
    }

    static PokerHand createCombination(Cards playerCards, Cards communityCards) {
        List<Card> cards = new ArrayList<>();
        playerCards.forEach(cards::add);
        communityCards.forEach(cards::add);
        return createCombination(cards);
    }

    private static PokerHand parseRoyalFlush(List<Card> cards) {
        for (int i = 0; i < 4; ++i) {
            int numberOfSameSuits = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).suit().equals(cards.get(j).suit()) && ++numberOfSameSuits == 5) {
                    final Rank highCardRank = cards.get(i).rank();
                    if (highCardRank == Rank.ACE && isStraightFromRank(cards, highCardRank)) {
                        return new RoyalFlush();
                    }
                }
            }
        }
        return null;
    }

    private static PokerHand parseStraightFlush(List<Card> cards) {
        for (int i = 0; i < 4; ++i) {
            int numberOfSameSuits = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).suit().equals(cards.get(j).suit()) && ++numberOfSameSuits == 5) {
                    final Rank highCardRank = cards.get(i).rank();
                    if (isStraightFromRank(cards, highCardRank)) {
                        return new StraightFlush(highCardRank);
                    }
                }
            }
        }
        return null;
    }

    private static PokerHand parseFourOfAKind(List<Card> cards) {
        for (int i = 0; i < 5; ++i) {
            int numberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).rank().equals(cards.get(j).rank()) && ++numberOfSameRanks == 4) {
                    Rank otherCardRank = determineRanksExceptThese(cards, cards.get(i).rank()).get(0);
                    return new FourOfAKind(cards.get(i).rank(), otherCardRank);
                }
            }
        }
        return null;
    }

    private static PokerHand parseFullHouse(List<Card> cards) {
        Rank threeOfAKindHighCardRank = null;
        for (int i = 0; i < cards.size() - 1; ++i) {
            int threeOfAKindNumberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).rank().equals(cards.get(j).rank()) && ++threeOfAKindNumberOfSameRanks == 3) {
                    threeOfAKindHighCardRank = cards.get(i).rank();
                    break;
                }
                if (threeOfAKindHighCardRank != null) {
                    break;
                }
            }
        }
        if (threeOfAKindHighCardRank != null) {
            for (int i = 0; i < cards.size(); ++i) {
                int pairNumberOfSameRanks = 1;
                Rank pairHighCardRank;
                for (int k = 0; k < cards.size(); ++k) {
                    if (i != k && cards.get(i).rank() != threeOfAKindHighCardRank && cards.get(i).rank().equals(cards.get(k).rank()) && ++pairNumberOfSameRanks == 2) {
                        pairHighCardRank = cards.get(i).rank();
                        return new FullHouse(threeOfAKindHighCardRank, pairHighCardRank);
                    }
                }
            }
        }
        return null;
    }

    private static PokerHand parseFlush(List<Card> cards) {
        for (int i = 0; i < 3; ++i) {
            final Suit suit = cards.get(i).suit();
            int numberOfSameSuits = 1;
            for (int j = i + 1; j < cards.size(); ++j) {
                if (cards.get(i).suit().equals(cards.get(j).suit()) && ++numberOfSameSuits == 5) {
                    final List<Rank> cardsRanks = determineRanksOfFlush(cards, suit);
                    return new Flush(cardsRanks);
                }
            }
        }
        return null;
    }

    private static PokerHand parseStraight(List<Card> cards) {
        for (int i = Rank.ACE.ordinal(); i >= Rank.FIVE.ordinal(); --i) {
            Rank rank = Rank.values()[i];
            if (isStraightFromRank(cards, rank)) {
                return new Straight(rank);
            }
        }
        return null;
    }

    private static PokerHand parseThreeOfAKind(List<Card> cards) {
        for (int i = 0; i < cards.size() - 1; ++i) {
            int numberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).rank().equals(cards.get(j).rank()) && ++numberOfSameRanks == 3) {
                    List<Rank> otherCardsRanks = determineRanksExceptThese(cards, cards.get(i).rank()).subList(0, 2);
                    return new ThreeOfAKind(cards.get(i).rank(), otherCardsRanks);
                }
            }
        }
        return null;
    }

    private static PokerHand parseTwoPairs(List<Card> cards) {
        Rank firstPairHighCardRank = null;
        for (int i = 0; i < cards.size(); ++i) {
            int firstPairNumberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).rank().equals(cards.get(j).rank()) && ++firstPairNumberOfSameRanks == 2) {
                    firstPairHighCardRank = cards.get(i).rank();
                    break;
                }
                if (firstPairHighCardRank != null) {
                    break;
                }
            }
        }
        if (firstPairHighCardRank != null) {
            for (int i = 0; i < cards.size(); ++i) {
                int secondPairNumberOfSameRanks = 1;
                Rank secondPairHighCardRank;
                for (int k = 0; k < cards.size(); ++k) {
                    if (i != k && cards.get(i).rank() != firstPairHighCardRank && cards.get(i).rank().equals(cards.get(k).rank()) && ++secondPairNumberOfSameRanks == 2) {
                        secondPairHighCardRank = cards.get(i).rank();
                        Rank otherCardRank = determineRanksExceptThese(cards, firstPairHighCardRank, secondPairHighCardRank).get(0);
                        return new TwoPairs(firstPairHighCardRank, secondPairHighCardRank, otherCardRank);
                    }
                }
            }
        }
        return null;
    }

    private static PokerHand parsePair(List<Card> cards) {
        for (int i = 0; i < cards.size(); ++i) {
            int numberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).rank().equals(cards.get(j).rank()) && ++numberOfSameRanks == 2) {
                    List<Rank> otherCardsRanks = determineRanksExceptThese(cards, cards.get(i).rank()).subList(0, 3);
                    return new Pair(cards.get(i).rank(), otherCardsRanks);
                }
            }
        }
        return null;
    }

    private static PokerHand parseHighCard(List<Card> cards) {
        List<Rank> cardsRanks = determineRanksExceptThese(cards).subList(0, 5);
        return new HighCard(cardsRanks);
    }

    private static boolean isStraightFromRank(List<Card> cards, Rank rank) {
        for (int i = rank.ordinal(); i >= -1 && i > rank.ordinal() - 5; --i) {
            int currentRankIndex = i == -1 ? Rank.ACE.ordinal() : i;
            final Card card = new Card(Rank.values()[currentRankIndex], Suit.HEARTS);
            if (Collections.binarySearch(cards, card, COMPARATOR_BY_RANK) < 0) {
                return false;
            }
        }
        return true;
    }

    private static List<Rank> determineRanksOfFlush(List<Card> cards, Suit suit) {
        List<Rank> cardsRanks = new ArrayList<>();
        cards.forEach(it -> {
            if (cardsRanks.size() != 5 && it.suit() == suit) {
                cardsRanks.add(it.rank());
            }
        });
        return cardsRanks;
    }

    private static List<Rank> determineRanksExceptThese(List<Card> cards, Rank... exceptedRanks) {
        List<Rank> exceptedRanksList = asList(exceptedRanks);
        Predicate<Rank> isNotExcepted = exceptedRanksList::contains;
        isNotExcepted = isNotExcepted.negate();
        return cards.stream()
                .map(Card::rank)
                .filter(isNotExcepted)
                .collect(toList());
    }

}
