package com.lamtev.poker.core.hands;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Rank;
import com.lamtev.poker.core.model.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PokerHandFactory {

    private final Cards commonCards;
    private final static Comparator<Card> COMPARATOR_BY_RANK = Comparator.comparing(Card::getRank);

    public PokerHandFactory(Cards commonCards) {
        this.commonCards = commonCards;
    }

    public PokerHand createCombination(Cards playerCards) {
        List<Card> cards = new ArrayList<>();
        commonCards.forEach(cards::add);
        playerCards.forEach(cards::add);

        cards.sort(COMPARATOR_BY_RANK.reversed());
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

    private PokerHand parseRoyalFlush(List<Card> cards) {
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

    private PokerHand parseStraightFlush(List<Card> cards) {
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

    //TODO remove code duplicate
    //TODO increase code readability
    private PokerHand parseFourOfAKind(List<Card> cards) {
        for (int i = 0; i < 5; ++i) {
            int numberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).getRank().equals(cards.get(j).getRank()) && ++numberOfSameRanks == 4) {
                    Rank otherCardRank = determineRanksExceptThese(cards, cards.get(i).getRank()).get(0);
                    return new FourOfAKind(cards.get(i).getRank(), otherCardRank);
                }
            }
        }
        return null;
    }

    //TODO remove code duplicate
    //TODO increase code readability
    private PokerHand parseFullHouse(List<Card> cards) {
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

    private PokerHand parseFlush(List<Card> cards) {
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

    private PokerHand parseStraight(List<Card> cards) {
        for (int i = Rank.ACE.ordinal(); i >= Rank.FIVE.ordinal(); --i) {
            Rank rank = Rank.values()[i];
            if (isStraightFromRank(cards, rank)) {
                return new Straight(rank);
            }
        }
        return null;
    }

    //TODO remove code duplicate
    //TODO increase code readability
    private PokerHand parseThreeOfAKind(List<Card> cards) {
        for (int i = 0; i < 6; ++i) {
            int numberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).getRank().equals(cards.get(j).getRank()) && ++numberOfSameRanks == 3) {
                    List<Rank> otherCardsRanks = determineRanksExceptThese(cards, cards.get(i).getRank()).subList(0, 2);
                    return new ThreeOfAKind(cards.get(i).getRank(), otherCardsRanks);
                }
            }
        }
        return null;
    }

    //TODO remove code duplicate
    //TODO increase code readability
    private PokerHand parseTwoPairs(List<Card> cards) {
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
                        Rank otherCardRank = determineRanksExceptThese(cards, firstPairHighCardRank, secondPairHighCardRank).get(0);
                        return new TwoPairs(firstPairHighCardRank, secondPairHighCardRank, otherCardRank);
                    }
                }
            }
        }
        return null;
    }

    //TODO remove code duplicate
    //TODO increase code readability
    private PokerHand parsePair(List<Card> cards) {
        for (int i = 0; i < 7; ++i) {
            int numberOfSameRanks = 1;
            for (int j = 0; j < cards.size(); ++j) {
                if (i != j && cards.get(i).getRank().equals(cards.get(j).getRank()) && ++numberOfSameRanks == 2) {
                    List<Rank> otherCardsRanks = determineRanksExceptThese(cards, cards.get(i).getRank()).subList(0, 3);
                    return new Pair(cards.get(i).getRank(), otherCardsRanks);
                }
            }
        }
        return null;
    }

    private PokerHand parseHighCard(List<Card> cards) {
        List<Rank> cardsRanks = determineRanksExceptThese(cards).subList(0, 5);
        return new HighCard(cardsRanks);
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

    //TODO think how to use stream here
    private List<Rank> determineRanksExceptThese(List<Card> cards, Rank... exceptedRanks) {
        List<Rank> ranks = new ArrayList<>();
        if (exceptedRanks == null || exceptedRanks.length == 0) {
            cards.forEach(card -> ranks.add(card.getRank()));
        } else {
            for (Rank rank : exceptedRanks) {
                cards.forEach(card -> {
                    if (card.getRank() != rank) {
                        ranks.add(card.getRank());
                    }
                });
            }
        }
        return ranks;
    }


}
