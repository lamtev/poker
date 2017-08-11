package com.lamtev.poker.core.ai;

import com.lamtev.poker.core.hands.Pair;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.hands.PokerHandFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.lamtev.poker.core.hands.PokerHand.Name.*;
import static com.lamtev.poker.core.model.Rank.*;
import static java.util.Arrays.asList;

public class ThinkingAI extends AbstractAI {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public ThinkingAI(String id, int stack) {
        super(id, stack);
    }

    @Override
    public void makeAMove() {
        switch (state()) {
            case "PreflopWageringState":
                onPreflopWagering();
                break;
            case "FlopWageringState":
                onFlopWagering();
                break;
            case "TurnWageringState":
            case "RiverWageringState":
                onTurnOrRiverWagering();
                break;
            case "ShowdownState":
                onShowdown();
                break;
            default:
                break;
        }

    }

    private void onTurnOrRiverWagering() {
        try {
            int additionalWager = (int)
                    (currentWager() * 0.5 < stack() * 0.8 ? currentWager() * 0.5 : stack() * 0.8);
            boolean enoughMoney = stack() > additionalWager + currentWager() - wager();

            if (moveAbility.raiseIsAble() && enoughMoney && (statusIsGoodForRaiseOnTurnOrRiver() || timeToBluff())) {
                poker().raise(additionalWager);
            } else if (moveAbility.checkIsAble()) {
                poker().check();
            } else if (moveAbility.callIsAble()) {
                if (statusIsGoodForCallOnPostFlop()) {
                    poker().call();
                } else {
                    poker().fold();
                }
            } else if (moveAbility.allInIsAble()) {
                if (statusIsGoodForCallOnPostFlop()) {
                    poker().allIn();
                } else {
                    poker().fold();
                }
            } else {
                poker().fold();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Probability of true is 0.25
     *
     * @return true if there is time to bluff and false - if not.
     */
    private boolean timeToBluff() {
        return RANDOM.nextInt(4) == 0;
    }

    private boolean statusIsGoodForRaiseOnTurnOrRiver() {
        PokerHandFactory phf = new PokerHandFactory(communityCards);
        PokerHand.Name hand = phf.createCombination(cards()).getName();
        return hand == ROYAL_FLUSH ||
                hand == STRAIGHT_FLUSH ||
                hand == FOUR_OF_A_KIND ||
                hand == FULL_HOUSE ||
                hand == FLUSH ||
                hand == STRAIGHT ||
                hand == THREE_OF_A_KIND ||
                hand == TWO_PAIRS;
    }

    private void onFlopWagering() {
        try {
            if (moveAbility.checkIsAble()) {
                poker().check();
            } else if (moveAbility.callIsAble()) {
                if (statusIsGoodForCallOnPostFlop()) {
                    poker().call();
                } else {
                    poker().fold();
                }
            } else if (moveAbility.allInIsAble()) {
                if (statusIsGoodForCallOnPostFlop()) {
                    poker().allIn();
                } else {
                    poker().fold();
                }
            } else {
                poker().fold();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean statusIsGoodForCallOnPostFlop() {
        PokerHandFactory phf = new PokerHandFactory(communityCards);
        PokerHand hand = phf.createCombination(cards());
        PokerHand.Name handName = hand.getName();
        return handName != HIGH_CARD && hand.compareTo(new Pair(SEVEN, asList(KING, JACK, TWO))) > 0;
    }


    private void onPreflopWagering() {
        try {
            if (moveAbility.checkIsAble()) {
                poker().check();
            } else if (moveAbility.callIsAble()) {
                if (statusIsGoodForCallOnPreflop()) {
                    poker().call();
                } else {
                    poker().fold();
                }
            } else if (moveAbility.allInIsAble()) {
                if (statusIsGoodForCallOnPreflop()) {
                    poker().allIn();
                } else {
                    poker().fold();
                }
            } else {
                poker().fold();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean statusIsGoodForCallOnPreflop() {
        return cards().get(0).rank().equals(cards().get(1).rank()) ||
                cards().get(0).rank().equals(ACE) && cards().get(1).rank().equals(KING) ||
                cards().get(1).rank().equals(ACE) && cards().get(0).rank().equals(KING) ||
                (currentWager() - wager()) * 10 <= stack();
    }

    private void onShowdown() {
        try {
            PokerHandFactory handFactory = new PokerHandFactory(communityCards);
            PokerHand itsHand = handFactory.createCombination(cards());
            Optional<PokerHand> betterHand = handsOfPotConcurrents().stream()
                    .filter(it -> it.compareTo(itsHand) > 0)
                    .findFirst();
            if (betterHand.isPresent() && moveAbility.foldIsAble()) {
                poker().fold();
            } else {
                poker().showDown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ThinkingAI{" +
                "id='" + id() + '\'' +
                ", stack=" + stack() +
                ", wager=" + wager() +
                ", cards=" + cards() +
                ", isActive=" + isActive() +
                '}';
    }

    private List<PokerHand> handsOfPotConcurrents() {
        List<PokerHand> handsOfPotConcurrents = new ArrayList<>();
        playersHands.forEach((id, hand) -> {
            if (!id.equals(id()) && rivals.get(id).wager() <= wager()) {
                handsOfPotConcurrents.add(hand);
            }
        });
        return handsOfPotConcurrents;
    }

}
