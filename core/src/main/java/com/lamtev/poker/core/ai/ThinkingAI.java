package com.lamtev.poker.core.ai;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.hands.PokerHandFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lamtev.poker.core.hands.PokerHand.Name.*;

public class ThinkingAI extends AbstractAI {

    public ThinkingAI(String id, int stack) {
        super(id, stack);
    }

    @Override
    public void makeAMove() {
        try {
            switch (state()) {
                case "PreflopWageringState":
                    onPreflopWagering();
                    break;
                case "FlopWageringState":
                    onFlopWagering();
                    break;
                case "TurnWageringState":
                case "RiverWageringState":
                    if (moveAbility.checkIsAble()) {
                        poker().check();
                    } else if (moveAbility.callIsAble()) {
                        poker().call();
                    } else if (moveAbility.allInIsAble()) {
                        poker().allIn();
                    } else {
                        poker().fold();
                    }
                    break;
                case "ShowdownState":
                    onShowdown();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onFlopWagering() {
        try {
            if (moveAbility.checkIsAble()) {
                poker().check();
            } else if (moveAbility.callIsAble()) {
                if (statusOnFlopIsGood()) {
                    poker().call();
                } else {
                    poker().fold();
                }
            } else if (moveAbility.allInIsAble()) {
                if (statusOnFlopIsGood()) {
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

    private boolean statusOnFlopIsGood() {
        PokerHandFactory phf = new PokerHandFactory(communityCards);
        PokerHand.Name hand = phf.createCombination(cards()).getName();
        return hand != PAIR && hand != HIGH_CARD;
    }

    private void onPreflopWagering() {
        try {
            if (moveAbility.checkIsAble()) {
                poker().check();
            } else if (moveAbility.callIsAble()) {
                if (statusOnPreflopIsGood()) {
                    poker().call();
                } else {
                    poker().fold();
                }
            } else if (moveAbility.allInIsAble()) {
                if (statusOnPreflopIsGood()) {
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

    private boolean statusOnPreflopIsGood() {
        return cards().get(0).rank().equals(cards().get(1).rank()) || averageStack() * 0.5 <= stack();
    }

    private double averageStack() {
        return rivals.values()
                .stream()
                .map(Rival::stack)
                .mapToInt(Number::intValue)
                .average()
                .orElseThrow(RuntimeException::new);
    }

    private void onShowdown() throws Exception {
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
